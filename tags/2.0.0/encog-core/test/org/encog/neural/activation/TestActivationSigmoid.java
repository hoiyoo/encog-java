package org.encog.neural.activation;

import org.encog.persist.persistors.ActivationLOGPersistor;
import org.encog.persist.persistors.ActivationSigmoidPersistor;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class TestActivationSigmoid extends TestCase {
	@Test
	public void testSigmoid() throws Throwable {
		ActivationSigmoid activation = new ActivationSigmoid();
		Assert.assertTrue(activation.hasDerivative());

		ActivationSigmoid clone = (ActivationSigmoid) activation.clone();
		Assert.assertNotNull(clone);

		double[] input = { 0.0  };

		activation.activationFunction(input);

		Assert.assertEquals(0.5, input[0], 0.1);		

		// this will throw an error if it does not work
		ActivationSigmoidPersistor p = (ActivationSigmoidPersistor) activation
				.createPersistor();

		// test derivative, should throw an error
		activation.derivativeFunction(input);
		Assert.assertEquals(0.25, input[0], 0.1);

		// test name and description
		// names and descriptions are not stored for these
		activation.setName("name");
		activation.setDescription("name");
		Assert.assertEquals(null, activation.getName());
		Assert.assertEquals(null, activation.getDescription());

	}

	
}
