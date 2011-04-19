/*
 * Encog(tm) Core v3.0 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.neural.cpn.training;

import org.encog.mathutil.BoundMath;
import org.encog.ml.TrainingImplementationType;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.neural.NeuralNetworkError;
import org.encog.neural.cpn.CPN;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.training.BasicTraining;
import org.encog.neural.networks.training.LearningRate;
import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.util.EngineArray;

/**
 * Used for Instar training of a CPN neural network. A CPN network is a hybrid
 * supervised/unsupervised network. The Instar training handles the unsupervised
 * portion of the training.
 * 
 */
public class TrainInstar extends BasicTraining implements LearningRate {

	/**
	 * The network being trained.
	 */
	private final CPN network;

	/**
	 * The training data. This is unsupervised training, so only the input
	 * portion of the training data will be used.
	 */
	private final NeuralDataSet training;

	/**
	 * The learning rate.
	 */
	private double learningRate;

	/**
	 * If the weights have not been initialized, then they can be initialized
	 * before training begins. This will be done on the first iteration.
	 */
	private boolean mustInit;

	/**
	 * Construct the instar training object.
	 * 
	 * @param network
	 *            The network to be trained.
	 * @param training
	 *            The training data.
	 * @param learningRate
	 *            The learning rate.
	 * @param initWeights
	 *            True, if the weights should be initialized from the training
	 *            data. If set to true, then you must have the same number of
	 *            training elements as instar neurons.
	 */
	public TrainInstar(final CPN network, final NeuralDataSet training,
			final double learningRate, boolean initWeights) {
		super(TrainingImplementationType.Iterative);
		this.network = network;
		this.training = training;
		this.learningRate = learningRate;
		this.mustInit = initWeights;
	}

	/**
	 * @return The learning rate.
	 */
	public double getLearningRate() {
		return this.learningRate;
	}

	/**
	 * @return The network being trained.
	 */
	public CPN getNetwork() {
		return this.network;
	}

	/**
	 * Approximate the weights based on the input values.
	 */
	private void initWeights() {

		if (this.training.getRecordCount() != this.network.getInstarCount()) {
			throw new NeuralNetworkError(
					"If the weights are to be set from the training data, then there must be one instar neuron for each training element.");
		}

		int i = 0;
		for (final MLDataPair pair : this.training) {
			for (int j = 0; j < this.network.getInputCount(); j++) {
				this.network.getWeightsInputToInstar().set(j, i,
						pair.getInput().getData(j));
			}
			i++;
		}
		this.mustInit = false;
	}

	/**
	 * Perform one training iteration.
	 */
	public void iteration() {

		if (this.mustInit) {
			initWeights();
		}

		double worstDistance = Double.NEGATIVE_INFINITY;

		for (final MLDataPair pair : this.training) {
			final MLData out = network.computeInstar(pair.getInput());

			// determine winner
			final int winner = EngineArray.indexOfLargest(out.getData());

			// calculate the distance
			double distance = 0;
			for (int i = 0; i < pair.getInput().size(); i++) {
				final double diff = pair.getInput().getData(i)
						- network.getWeightsInputToInstar().get(i, winner);
				distance += diff * diff;
			}
			distance = BoundMath.sqrt(distance);

			if (distance > worstDistance) {
				worstDistance = distance;
			}

			// train
			for (int j = 0; j < network.getInputCount(); j++) {
				final double delta = this.learningRate
						* (pair.getInput().getData(j) - network
								.getWeightsInputToInstar().get(j, winner));

				network.getWeightsInputToInstar().add(j, winner, delta);

			}
		}

		setError(worstDistance);
	}

	/**
	 * Set the learning rate.
	 * 
	 * @param rate
	 *            The new learning rate.
	 */
	public void setLearningRate(final double rate) {
		this.learningRate = rate;
	}

	@Override
	public boolean canContinue() {
		return false;
	}

	@Override
	public TrainingContinuation pause() {
		return null;
	}

	@Override
	public void resume(TrainingContinuation state) {

	}

}
