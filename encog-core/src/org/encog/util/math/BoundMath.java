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
package org.encog.util.math;

/**
 * Java will sometimes return Math.NaN or Math.Infinity when numbers get 
 * to large or too small.  This can have undesirable effects.  This class
 * provides some basic math functions that may be in danger of returning
 * such a value.  This class imposes a very large and small ceiling and
 * floor to keep the numbers within range.
 * 
 * @author jheaton
 *
 */
public class BoundMath {
	
	public static double exp(double a)
	{
		return BoundNumbers.bound(Math.exp(a));
	}

	public static double pow(double a, double b) {
		return BoundNumbers.bound(Math.pow(a, b));
	}

	public static double log(double a) {
		return BoundNumbers.bound(Math.log(a));
	}

	public static double sin(double a) {
		return BoundNumbers.bound(Math.sin(a));
	}
	
	public static double cos(double a) {
		return BoundNumbers.bound(Math.cos(a));
	}

	public static double sqrt(double a) {
		return Math.sqrt(a);
	}
}
