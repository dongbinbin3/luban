package com.luban.daily.limiter;

/**
 * 漏桶算法
 */
public class LeakyBucketRateLimiter implements RateLimiter {

    //容量
    private final int capacity;

    // 漏出速率，每秒漏出个数
    private final int permitsPerSecond;

    // 剩余水量
    private long leftWater;
    // 上次注入时间
    private long timeStamp = System.currentTimeMillis();

    public LeakyBucketRateLimiter(int capacity, int permitsPerSecond) {
        this.capacity = capacity;
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long timeGap = (now - timeStamp) / 1000;
        leftWater = Math.max(0, leftWater - timeGap * permitsPerSecond);
        timeStamp = now;

        // 如果未满，则放行；否则限流
        if (leftWater < capacity) {
            leftWater += 1;
            return true;
        }
        return false;
    }
}
