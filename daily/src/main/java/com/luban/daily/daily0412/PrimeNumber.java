package com.luban.daily.daily0412;

import java.util.ArrayList;
import java.util.List;

/**
 * c
 */
public class PrimeNumber {

    public static void main(String[] args) {
        //查找30内的质数
        List<Integer> primeNumbers = findPrimeNumber(30);
        primeNumbers.forEach(System.out::println);
    }

    public static List<Integer> findPrimeNumber(Integer number) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= number; i++) {
            if (isPrime(i, primes)) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * 判断当前数是否为质数
     *
     * @param number 数量
     * @param primes 小于当前数的质数
     * @return
     */
    private static boolean isPrime(int number, List<Integer> primes) {
        int sqrt = (int) Math.sqrt(number);
        for (Integer prime : primes) {
            if (number % prime == 0) {
                return false;
            }
            if (prime > sqrt) {
                return true;
            }
        }
        return true;
    }
}
