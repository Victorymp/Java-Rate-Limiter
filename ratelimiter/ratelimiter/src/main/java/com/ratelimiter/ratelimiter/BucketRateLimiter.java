package com.ratelimiter.ratelimiter;

import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

//Token Bucket approach
//Each user has a bucket
public class BucketRateLimiter {
	
	private int bucketSize;
	private int refillRate;
	private final Queue<ClientRequest> tokens = new PriorityQueue();
	
	private int overflowSize;
	private final Queue<ClientRequest> overflowBucket = new PriorityQueue();
	
	
	public BucketRateLimiter(int bucketSize, int refillRate) {
		this.bucketSize = bucketSize;
		this.refillRate = refillRate;
		this.overflowSize = 3;
	}
	
	public BucketRateLimiter(int bucketSize, int refillRate, int overflowSize) {
		this.bucketSize = bucketSize;
		this.refillRate = refillRate;
		this.overflowSize = overflowSize;
	}
	
	public boolean allowRequest() {
		// This algorithm doesn't care who made the request
		// Puts all request into a bucket
		long now = System.currentTimeMillis();
		if (bucketSize > 0 ) {
			ClientRequest lastToken = tokens.remove();
			
			
			if (now - lastToken.windowStart > 1000) {
				bucketSize = bucketSize + refillRate;
			}
			
			tokens.add(new ClientRequest(now,1));
			bucketSize--;
			return true;
		}
		return false;
		
	}

}
