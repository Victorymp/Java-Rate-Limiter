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
		
		long now = System.currentTimeMillis();
		
		long window = (now / 1000) / windowSize;

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
