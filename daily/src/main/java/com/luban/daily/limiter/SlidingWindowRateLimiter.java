package com.luban.daily.limiter;

import java.util.TreeMap;

/**
 * 滑动窗口限速器
 */
public class SlidingWindowRateLimiter implements RateLimiter {
    /**
     * 每分钟限制个数
     */
    private final long permitsPerMinute;

    /**
     * 每片分段计数器
     */
    private final TreeMap<Long, Integer> counters;

    public SlidingWindowRateLimiter(long permitsPerMinute) {
        this.permitsPerMinute = permitsPerMinute;
        this.counters = new TreeMap<>();
    }


    @Override
    public synchronized boolean tryAcquire() {
        //获取时间戳
        long timeMillis = System.currentTimeMillis();
        //计算当前时间，应该属于哪个分片，目前简单设计一分钟分为6片,
        long splitTime = (timeMillis / 10000) * 10;

        int currentWindowCount = getCurrentWindowCount(splitTime);
        if (currentWindowCount >= permitsPerMinute) {
            return false;
        }
        //计数器先加
        counters.merge(splitTime, 1, Integer::sum);
        return true;
    }

    /**
     * 获取当前窗口已有请求数
     *
     * @param splitTime
     * @return
     */
    private int getCurrentWindowCount(long splitTime) {
        long startTime = splitTime - 50;
        //移除已过期请求数
        counters.keySet().removeIf(key -> key < splitTime);
        //返回剩余请求数
        return counters.values().stream().mapToInt(Integer::intValue).sum();
    }
}
