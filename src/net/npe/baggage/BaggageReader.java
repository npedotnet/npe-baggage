/**
 * Baggage.java
 * 
 * Copyright (c) 2015 Kenji Sasaki
 * Released under the MIT license.
 * https://github.com/npedotnet/npe-baggage/blob/master/LICENSE
 * 
 * This file is a part of npe-baggage.
 * https://github.com/npedotnet/npe-baggage
 *
 * For more details, see npe-baggage wiki.
 * https://github.com/npedotnet/npe-baggage/wiki
 * 
 */

package net.npe.baggage;

import java.io.FileInputStream;
import java.io.IOException;

import net.npe.io.StreamReader;

public class BaggageReader {
	
	public static Baggage read(String input) {
		
		BaggageBuilder builder = null;
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(input);
			StreamReader reader = new StreamReader(fis, StreamReader.LittleEndian);
			builder = new BaggageBuilder();
			builder.read(reader);
			fis.close();
			fis = null;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(fis != null) {
				try {
					fis.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return builder;
		
	}

}
