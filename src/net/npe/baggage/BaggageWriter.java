/**
 * BaggageWriter.java
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
import java.io.FileOutputStream;
import java.io.IOException;

import net.npe.io.StreamWriter;

public class BaggageWriter {
	
	public static boolean write(String output, String [] files) {
		
		BaggageBuilder builder = new BaggageBuilder();
		
		for(String file : files) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte [] data = new byte[fis.available()];
				fis.read(data);
				fis.close();
				fis = null;
				builder.add(file, data);
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
		}
		
		return write(output, builder);
		
	}
	
	public static boolean write(String output, BaggageBuilder builder) {
		
		boolean result = false;
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(output);
			StreamWriter writer = new StreamWriter(fos, StreamWriter.LittleEndian);
			builder.write(writer);
			fos.close();
			fos = null;
			result = true;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(fos != null) {
				try {
					fos.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;

	}

}
