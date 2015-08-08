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

public interface Baggage {
	
	BaggageItem get(String key);
	
	String [] getKeys();
	
	BaggageItem [] getItems();
	
	int size();

}
