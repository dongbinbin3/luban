package com.luban.daily.daily0413;

/***
 * 限速器
 */
public interface RateLimiter {

    boolean tryAcquire();
}
