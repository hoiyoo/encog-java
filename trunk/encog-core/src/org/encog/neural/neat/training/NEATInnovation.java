/*
 * Encog(tm) Core v2.6 - Java Version
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
package org.encog.neural.neat.training;

import org.encog.ml.genetic.innovation.BasicInnovation;
import org.encog.neural.neat.NEATNeuronType;
import org.encog.persist.annotations.EGAttribute;
import org.encog.persist.map.PersistConst;
import org.encog.persist.map.PersistedObject;

/**
 * Implements a NEAT innovation. This lets NEAT track what changes it has
 * previously tried with a neural network.
 *
 * NeuroEvolution of Augmenting Topologies (NEAT) is a genetic algorithm for the
 * generation of evolving artificial neural networks. It was developed by Ken
 * Stanley while at The University of Texas at Austin.
 *
 * http://www.cs.ucf.edu/~kstanley/
 *
 */
public class NEATInnovation extends BasicInnovation {

	public final static String PROPERTY_FROM_NEURON_ID = "from";
	public final static String PROPERTY_TO_NEURON_ID = "to";
	public final static String PROPERTY_NEURON_ID = "neuron";
	public final static String PROPERTY_SPLIT_X = "splitX";
	public final static String PROPERTY_SPLIT_Y = "splitY";
	public final static String PROPERTY_INNOVATION_TYPE = "type";

	
	/**
	 * The from neuron id.
	 */
	@EGAttribute
	private long fromNeuronID;

	/**
	 * The type of innovation.
	 */
	@EGAttribute
	private NEATInnovationType innovationType;

	/**
	 * The neuron id.
	 */
	@EGAttribute
	private long neuronID;

	/**
	 * The type of neuron, or none, if this is a link innovation.
	 */
	@EGAttribute
	private NEATNeuronType neuronType;

	/**
	 * The split x property.
	 */
	@EGAttribute
	private double splitX;

	/**
	 * The split y property.
	 */
	@EGAttribute
	private double splitY;

	/**
	 * The to neuron's id.
	 */
	@EGAttribute
	private long toNeuronID;

	/**
	 * Default constructor, used mainly for persistence.
	 */
	public NEATInnovation() {

	}

	/**
	 * Construct an innovation.
	 *
	 * @param fromNeuronID
	 *            The from neuron.
	 * @param toNeuronID
	 *            The two neuron.
	 * @param innovationType
	 *            The innovation type.
	 * @param innovationID
	 *            The innovation id.
	 */
	public NEATInnovation(final long fromNeuronID, final long toNeuronID,
			final NEATInnovationType innovationType, final long innovationID) {

		this.fromNeuronID = fromNeuronID;
		this.toNeuronID = toNeuronID;
		this.innovationType = innovationType;
		setInnovationID(innovationID);

		this.neuronID = -1;
		this.splitX = 0;
		this.splitY = 0;
		this.neuronType = NEATNeuronType.None;
	}

	/**
	 * Construct an innovation.
	 *
	 * @param fromNeuronID
	 *            The from neuron.
	 * @param toNeuronID
	 *            The to neuron.
	 * @param innovationType
	 *            The innovation type.
	 * @param innovationID
	 *            The innovation id.
	 * @param neuronType
	 *            The neuron type.
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            THe y coordinate.
	 */
	public NEATInnovation(final long fromNeuronID, final long toNeuronID,
			final NEATInnovationType innovationType, final long innovationID,
			final NEATNeuronType neuronType, final double x, final double y) {
		this.fromNeuronID = fromNeuronID;
		this.toNeuronID = toNeuronID;
		this.innovationType = innovationType;
		setInnovationID(innovationID);
		this.neuronType = neuronType;
		this.splitX = x;
		this.splitY = y;

		this.neuronID = 0;
	}

	/**
	 * Construct an innovation.
	 *
	 * @param neuronGene
	 *            The neuron gene.
	 * @param innovationID
	 *            The innovation id.
	 * @param neuronID
	 *            The neuron id.
	 */
	public NEATInnovation(final NEATNeuronGene neuronGene,
			final long innovationID, final long neuronID) {

		this.neuronID = neuronID;
		setInnovationID(innovationID);
		this.splitX = neuronGene.getSplitX();
		this.splitY = neuronGene.getSplitY();

		this.neuronType = neuronGene.getNeuronType();
		this.innovationType = NEATInnovationType.NewNeuron;
		this.fromNeuronID = -1;
		this.toNeuronID = -1;
	}

	/**
	 * @return The from neuron id.
	 */
	public long getFromNeuronID() {
		return this.fromNeuronID;
	}

	/**
	 * @return The innovation type.
	 */
	public NEATInnovationType getInnovationType() {
		return this.innovationType;
	}

	/**
	 * @return The neuron ID.
	 */
	public long getNeuronID() {
		return this.neuronID;
	}

	/**
	 * @return The neuron type.
	 */
	public NEATNeuronType getNeuronType() {
		return this.neuronType;
	}

	/**
	 * @return The split x.
	 */
	public double getSplitX() {
		return this.splitX;
	}

	/**
	 * @return The split y.
	 */
	public double getSplitY() {
		return this.splitY;
	}

	/**
	 * @return The to neuron id.
	 */
	public long getToNeuronID() {
		return this.toNeuronID;
	}

	/**
	 * Set the neuron id.
	 *
	 * @param neuronID
	 *            The neuron id.
	 */
	public void setNeuronID(final long neuronID) {
		this.neuronID = neuronID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append("[NeatInnovation:type=");
		switch (this.innovationType) {
		case NewLink:
			result.append("link");
			break;
		case NewNeuron:
			result.append("neuron");
			break;
		}
		result.append(",from=");
		result.append(this.fromNeuronID);
		result.append(",to=");
		result.append(this.toNeuronID);
		result.append(",splitX=");
		result.append(this.splitX);
		result.append(",splitY=");
		result.append(this.splitY);
		result.append("]");
		return result.toString();
	}
	
	public boolean supportsMapPersistence()
	{
		return true;
	}
	
	public void persistToMap(PersistedObject obj)
	{
		obj.clear(PersistConst.TYPE_NEAT_INNOVATION);
		
		obj.setProperty(BasicInnovation.PROPERTY_INNOVATION_ID, this.getInnovationID(),true);
		obj.setProperty(NEATInnovation.PROPERTY_FROM_NEURON_ID, this.fromNeuronID,true);
		obj.setProperty(NEATInnovation.PROPERTY_TO_NEURON_ID, this.toNeuronID,true);
		obj.setProperty(NEATInnovation.PROPERTY_NEURON_ID, this.neuronID,true);
		obj.setProperty(NEATInnovation.PROPERTY_SPLIT_X, this.splitX,true);
		obj.setProperty(NEATInnovation.PROPERTY_SPLIT_Y, this.splitY,true);
				
		switch( this.innovationType )
		{
			case NewNeuron:
				obj.setProperty(NEATInnovation.PROPERTY_INNOVATION_TYPE, "neuron",true);
				break;
			case NewLink:
				obj.setProperty(NEATInnovation.PROPERTY_INNOVATION_TYPE, "link",true);
				break;
		}
		
	}
	
	public void persistFromMap(PersistedObject obj)
	{
		obj.requireType(PersistConst.TYPE_NEAT_INNOVATION);
		
	}

}
