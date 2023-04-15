package com.luban.daily.limiter;

/***
 * 限速器
 */
public interface RateLimiter {

    boolean tryAcquire();
}
