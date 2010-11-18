/*
 * Encog(tm) Examples v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.examples.neural.opencl;

import org.encog.Encog;
import org.encog.engine.network.train.prop.OpenCLTrainingProfile;
import org.encog.engine.opencl.EncogCLDevice;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.strategy.RequiredImprovementStrategy;
import org.encog.util.logging.Logging;
import org.encog.util.simple.EncogUtility;

/**
 * Learn the XOR operator using OpenCL.
 * 
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses resilient propagation (RPROP) to train the neural network.
 * RPROP is the best general purpose supervised training method provided by
 * Encog.
 * 
 * For the XOR example with RPROP I use 4 hidden neurons.  XOR can get by on just
 * 2, but often the random numbers generated for the weights are not enough for
 * RPROP to actually find a solution.  RPROP can have issues on really small
 * neural networks, but 4 neurons seems to work just fine.
 */
public class XOROpenCL {

	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
			{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };
	
	public static void main(final String args[]) {
		Logging.stopConsoleLogging();
		
		BasicNetwork network = EncogUtility.simpleFeedForward(2, 4, 0, 1, false);
		network.reset();
		
		NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);
		
		Encog.getInstance().initCL();
		
		// train the neural network
		EncogCLDevice device = Encog.getInstance().getCL().chooseDevice();
		OpenCLTrainingProfile profile = new OpenCLTrainingProfile(device);
		final Propagation train = new ResilientPropagation(network, trainingSet, profile);
		
		// reset if improve is less than 1% over 50 cycles
		train.addStrategy(new RequiredImprovementStrategy(5));
		
		EncogUtility.trainToError(train, network, trainingSet, 0.01);

		// test the neural network
		
		EncogUtility.evaluate(network, trainingSet);
		System.out.println("Neural Network Results:");
		
		System.out.println("OpenCL device used: " + profile.getDevice().toString());

	}
}