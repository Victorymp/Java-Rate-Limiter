package com.ratelimiter.ratelimiter;

public class FixedWindowRatelimiter {
	
	private int windowSize;
	private int requestPerWindow;
	private int currentRequestSize;
	private long currentWindow = -1;
	
	
	public FixedWindowRatelimiter(int windowSize, int requestPerWindow) {
		this.windowSize = windowSize;
		this.requestPerWindow = requestPerWindow;
	}
	
	public boolean allowRequest() {
		// Both numbers eval as integers so java does integer division
		// This means it doesn't include the floating point
		long window = (System.currentTimeMillis() / 1000) / windowSize;
		
		// Assigning a window Id
		if (window != currentWindow) {
			currentWindow = window;
			currentRequestSize = 0;
		}
		
		if (currentRequestSize >= requestPerWindow) {
			return false;
		}
		currentRequestSize ++;
		return true;
	}

}
