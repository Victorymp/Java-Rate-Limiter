package com.ratelimiter.ratelimiter;

//Token Bucket approach
//A bucket is a shared resource
public class BucketRateLimiter {
	
	private int refillRate;
	private int currentBucketSize;
	
	private int overflowSize;
	private int overflowRefillRate;
	
	private long lastRefillTime;
	
	
	public BucketRateLimiter(int bucketSize, int refillRate) {
		this.refillRate = refillRate;
		this.currentBucketSize = bucketSize;
		this.overflowSize = 0;
		this.lastRefillTime = System.currentTimeMillis();
	}
	
	public BucketRateLimiter(int bucketSize, int refillRate, int overflowSize, int overflowRefillRate) {
		this.refillRate = refillRate;
		this.currentBucketSize = bucketSize;
		this.overflowSize = overflowSize;
		this.lastRefillTime = System.currentTimeMillis();
		this.overflowRefillRate = overflowRefillRate;
	}
	
	public boolean allowRequest() {
		// This algorithm doesn't care who made the request
		// Puts all request into a bucket
		// You wouldn't run this per request 
		long now = System.currentTimeMillis();
		long elapsedTime = now - lastRefillTime / 1000;
		
		if (elapsedTime > 0) {
			currentBucketSize = Math.min(currentBucketSize, currentBucketSize + (int)(elapsedTime * refillRate));
			if (overflowRefillRate != 0) {
				overflowSize = Math.min(overflowSize, overflowSize + (int)(elapsedTime * refillRate));
			}
			lastRefillTime = elapsedTime*1000;
		}
		
		// Use available overflow first
		if (overflowSize > 0 && overflowRefillRate != 0) {
			overflowSize --;
			return true;
		}
		
		if (currentBucketSize > 0) {
			currentBucketSize--;
			return true;
		}
		
		
		return false;
		
	}

}
