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

package org.encog.util.logging;

import java.text.NumberFormat;

import org.encog.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumpMatrix {
	
	/**
	 * The logging object.
	 */
	@SuppressWarnings("unused")
	final private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String dumpMatrix(Matrix matrix)
	{
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(3);
		format.setMaximumFractionDigits(3);
		
		StringBuilder result = new StringBuilder();
		result.append("==");
		result.append(matrix.toString());
		result.append("==\n");
		for(int row = 0; row< matrix.getRows();row++)
		{
			result.append("  [");
			for(int col = 0; col<matrix.getCols();col++)
			{
				if(col!=0)
					result.append(",");
				result.append(format.format(matrix.get(row, col)));
			}
			result.append("]\n");
		}
		return result.toString();
	}

	public static String dumpArray(double[] d) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(3);
		format.setMaximumFractionDigits(3);
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		for(int i=0;i<d.length;i++)
		{
			if(i!=0)
				result.append(",");
			result.append(format.format(d[i]));
		}
		result.append("]");
		return result.toString();
	}
}
