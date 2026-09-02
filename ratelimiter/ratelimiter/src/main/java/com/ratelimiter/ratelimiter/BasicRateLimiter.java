package com.ratelimiter.ratelimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicRateLimiter {

	private int maxRequest;
	private int windowMillis;
	private final Map<String, ClientRequest> clients = new ConcurrentHashMap();
	
	public BasicRateLimiter(int maxRequest, int windowMillis) {
		this.maxRequest = maxRequest;
		this.windowMillis = windowMillis;
	}
	
	public boolean allowRequest(String clientId) {
		long now = System.currentTimeMillis();
		
		ClientRequest existingClient = clients.get(clientId);
		if (existingClient == null || now - existingClient.windowStart >= windowMillis) {
			existingClient = new ClientRequest(now, 1);
			clients.put(clientId, existingClient);
			return true;
		}
		
		existingClient.requestCount ++;
		return existingClient.requestCount >= maxRequest;
		
	}
}
