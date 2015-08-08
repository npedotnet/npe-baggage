/**
 * BaggageBuilder.java
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

import java.io.IOException;

import net.npe.io.InputReader;
import net.npe.io.OutputWriter;

public class BaggageBuilder implements Baggage {
	
	public BaggageBuilder() {
	}
	
	@Override
	public BaggageItem get(String name) {
		return map.get(name);
	}
	
	@Override
	public String [] getKeys() {
		return map.keySet().toArray(new String[0]);
	}
	
	@Override
	public BaggageItem [] getItems() {
		return map.values().toArray(new BaggageItem[0]);
	}
	
	@Override
	public int size() {
		return map.size();
	}

	public void add(String name, BaggageItem item) {
		if(map.containsKey(name)) return;
		map.put(name, item);
	}
	
	public void add(String name, byte [] data, int offset, int length) {
		add(name, new BaggageItem(data, offset, length));
	}
	
	public void add(String name, byte [] data) {
		add(name, new BaggageItem(data, 0, data.length));
	}
	
	public void read(InputReader reader) throws IOException {
		
		if(reader.read()=='B'&&reader.read()=='A'&&reader.read()=='G') {
			int byteOrder = reader.read();
			if(byteOrder == 'B') reader.setByteOrder(InputReader.BigEndian);
			else if(byteOrder == 'L') reader.setByteOrder(InputReader.LittleEndian);
			else throw new IOException("No Baggage file");
		}
		else throw new IOException("No Baggage file");
		
		int size = reader.readInt();
		
		reader.skip(8);
		
		for(int i=0; i<size; i++) {
			
			int nameLength = reader.readInt();
			int nameLengthWithPadding = reader.readInt();
			int dataLength = reader.readInt();
			int dataLengthWithPadding = reader.readInt();
			
			byte [] nameData = new byte[nameLength];
			reader.read(nameData, 0, nameLength);
			String name = new String(nameData);
			reader.skip(nameLengthWithPadding - nameLength);
			
			byte [] data = new byte[dataLength];
			for(int j=0; j<dataLength; j++) data[j] = (byte)reader.read();
			reader.skip(dataLengthWithPadding - dataLength);
			
			add(name, data);
			
		}
		
	}
	
	public void write(OutputWriter writer) throws IOException {
		
		// 0..3 'BAG' + 'B' or 'L'
		writer.write('B');
		writer.write('A');
		writer.write('G');
		writer.write(writer.getByteOrder()==OutputWriter.BigEndian?'B':'L');
		
		// 4..7 item size
		int size = size();
		writer.writeInt(size);
		
		// 7..15 reserved
		writer.writeInt(0);
		writer.writeInt(0);
		
		String [] keys = getKeys();
		BaggageItem [] items = getItems();
		
		for(int i=0; i<size; i++) {
			String key = keys[i];
			BaggageItem item = items[i];
			
			int nameLength = key.getBytes().length;
			int nameLengthWithPadding = 16*((nameLength+15)/16);
			
			int dataLength = item.getLength();
			int dataLengthWithPadding = 16*((dataLength+15)/16);
			
			// 16 byte data header
			writer.writeInt(nameLength);
			writer.writeInt(nameLengthWithPadding);
			writer.writeInt(dataLength);
			writer.writeInt(dataLengthWithPadding);
			
			// Name
			writer.write(key.getBytes(), 0, nameLength);
			for(int j=0; j<nameLengthWithPadding-nameLength; j++) {
				writer.write(0);
			}
			
			// Data
			writer.write(item.getData(), item.getOffset(), dataLength);
			for(int j=0; j<dataLengthWithPadding-dataLength; j++) {
				writer.write(0);
			}
		}
		
	}
	
	BaggageMap map = new BaggageMap();

}
