package net.sf.yat.domain;

/**
 * Just pretty placeholder class for storing value pairs 
 */
public class Pair<K, V> {
	public final K first;
	public final V second;
	public Pair(K first, V second) {
		super();
		this.first = first;
		this.second = second;
	}	
}
