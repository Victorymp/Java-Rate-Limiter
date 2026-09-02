package com.ratelimiter.ratelimiter;

import java.util.PriorityQueue;
import java.util.Queue;

//Token Bucket approach
//Each user has a bucket
public class BucketRateLimiter {
	
	private int bucketSize;
	private int refillRate;
	private int currentBucketSize;
	private final Queue<ClientRequest> tokens = new PriorityQueue();// this would be cache
	
	private int overflowSize;
	
	
	public BucketRateLimiter(int bucketSize, int refillRate) {
		this.bucketSize = bucketSize;
		this.refillRate = refillRate;
		this.currentBucketSize = bucketSize;
		this.overflowSize = 0;
	}
	
	public BucketRateLimiter(int bucketSize, int refillRate, int overflowSize) {
		this.bucketSize = bucketSize;
		this.refillRate = refillRate;
		this.currentBucketSize = bucketSize;
		this.overflowSize = overflowSize;
	}
	
	public boolean allowRequest() {
		// This algorithm doesn't care who made the request
		// Puts all request into a bucket
		// You wouldn't run this per request 
		long now = System.currentTimeMillis();
		ClientRequest lastToken = tokens.remove();
		
		if (now - lastToken.windowStart > 1000) {
			for (int i = 0; i < refillRate; i++) {
				if (currentBucketSize < bucketSize) {					
					currentBucketSize ++; 
				}
				else if (currentBucketSize == bucketSize) {
					overflowSize ++;
				}
			}
		}
		
		tokens.add(new ClientRequest(now,1));
		
		// Use available overflow first
		if (overflowSize > 0) {
			overflowSize --;
			return true;
		}
		
		if (currentBucketSize > 0 ) {
			currentBucketSize--;
			return true;
		}
		
		
		return false;
		
	}

}
