/*
 * Encog Artificial Intelligence Framework v2.x
 * Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.neural.pattern;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.synapse.SynapseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A recurrent self organizing map is a self organizing map that has
 * a recurrent context connection on the hidden layer.  This type
 * of neural network is adept at classifying temporal data.
 * @author jheaton
 *
 */
public class RSOMPattern implements NeuralNetworkPattern {

	/**
	 * The number of input neurons.
	 */
	private int inputNeurons;
	
	/**
	 * The number of output neurons.
	 */
	private int outputNeurons;

	/**
	 * The logging object.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Add a hidden layer.  SOM networks do not have hidden layers, so
	 * this will throw an error.
	 * @param count The number of hidden neurons.
	 */
	public void addHiddenLayer(final int count) {
		final String str = "A SOM network does not have hidden layers.";
		if (this.logger.isErrorEnabled()) {
			this.logger.error(str);
		}
		throw new PatternError(str);

	}

	/**
	 * Generate the RSOM network.
	 * @return The neural network.
	 */
	public BasicNetwork generate() {
		Layer output = new BasicLayer(new ActivationLinear(), false,
				this.outputNeurons);
		Layer input = new BasicLayer(new ActivationLinear(), false,
				this.inputNeurons);

		final BasicNetwork network = new BasicNetwork();
		final Layer context = new ContextLayer(this.outputNeurons);
		network.addLayer(input);
		network.addLayer(output);

		output.addNext(context, SynapseType.OneToOne);
		context.addNext(input);

		int y = PatternConst.START_Y;
		input.setX(PatternConst.START_X);
		input.setY(y);

		context.setX(PatternConst.INDENT_X);
		context.setY(y);

		y += PatternConst.INC_Y;

		output.setX(PatternConst.START_X);
		output.setY(y);

		network.getStructure().finalizeStructure();
		network.reset();
		return network;
	}

	/**
	 * Set the activation function.  A SOM uses a linear activation
	 * function, so this method throws an error.
	 * @param activation The activation function to use.
	 */
	public void setActivationFunction(final ActivationFunction activation) {
		final String str = "A SOM network can't define an activation function.";
		if (this.logger.isErrorEnabled()) {
			this.logger.error(str);
		}
		throw new PatternError(str);

	}

	/**
	 * Set the input neuron count.
	 * @param count The number of neurons.
	 */
	public void setInputNeurons(final int count) {
		this.inputNeurons = count;

	}
	/**
	 * Set the output neuron count.
	 * @param count The number of neurons.
	 */
	public void setOutputNeurons(final int count) {
		this.outputNeurons = count;
	}
}
