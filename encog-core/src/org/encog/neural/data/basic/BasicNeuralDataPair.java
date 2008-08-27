/*
  * Encog Neural Network and Bot Library for Java v1.x
  * http://www.heatonresearch.com/encog/
  * http://code.google.com/p/encog-java/
  * 
  * Copyright 2008, Heaton Research Inc., and individual contributors.
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

package org.encog.neural.data.basic;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;

/**
 * A basic implementation of the NeuralDataPair interface.  This 
 * implementation simply holds and input and ideal NeuralData
 * object.
 * @author jheaton
 *
 */
public class BasicNeuralDataPair implements NeuralDataPair {

	/**
	 * The the expected output from the neural network, or null
	 * for unsupervised training.
	 */
	private NeuralData ideal;
	
	/**
	 * The training input to the neural network.
	 */
	private NeuralData input;
	
	/**
	 * Construct a BasicNeuralDataPair class with the specified input
	 * and ideal values.
	 * @param input The input to the neural network.
	 * @param ideal The expected results from the neural network.
	 */
	public BasicNeuralDataPair(final NeuralData input, final NeuralData ideal) {
		this.input = input;
		this.ideal = ideal;
	}

	/**
	 * Construct the object with only input. If this constructor
	 * is used, then unsupervised training is being used.
	 * @param input The input to the neural network.
	 */
	public BasicNeuralDataPair(final NeuralData input) {
		this.input = input;
	}

	/**
	 * Get the expected results.  Returns null if this is unsupervised 
	 * training. 
	 * @return Returns the expected results, or null if unsupervised
	 * training.
	 */
	public NeuralData getIdeal() {
		return this.ideal;
	}

	/**
	 * Get the input data.
	 * @return The input data.
	 */
	public NeuralData getInput() {
		return this.input;
	}
	
	/**
	 * Convert the object to a string.
	 * @return The object as a string.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder("[NeuralDataPair:");
		builder.append("Input:");
		builder.append(this.getInput());
		builder.append("Ideal:");
		builder.append(this.getIdeal());
		builder.append("]");
		return builder.toString();
	}

}
