package com.luban.daily.daily0413;

/**
 * 固定窗口限速器
 */
public class FixedWindowRateLimiter implements RateLimiter {

    /**
     * 每秒数量
     */
    private final long permitsPerSecond;

    /**
     * 上一个窗口的开始时间
     */
    public long preTime = System.currentTimeMillis();
    /**
     * 计数器
     */
    private int counter;

    public FixedWindowRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }


    @Override
    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
        /**
         * 当小于1s时间，判断令牌个数
         */
        if (now - preTime < 1000) {
            if (counter < permitsPerSecond) {
                counter++;
                return true;
            } else {
                return false;
            }
        }

        counter = 0;
        preTime = now;
        return false;
    }
}
