/**
 * BaggageItem.java
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

public class BaggageItem {

	public BaggageItem(byte [] data, int offset, int length) {
		this.data = data;
		this.offset = offset;
		this.length = length;
	}
	
	public byte [] getData() {
		return data;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getLength() {
		return length;
	}
	
	private byte [] data;
	private int offset;
	private int length;

}
