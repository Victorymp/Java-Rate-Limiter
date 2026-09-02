package com.ratelimiter.ratelimiter;

public class ClientRequest {

    long windowStart;
    int requestCount;

    ClientRequest(long windowStart, int requestCount) {
        this.windowStart = windowStart;
        this.requestCount = requestCount;
    }


}
