package com.mx.sso.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserRedisDao {

    @Value("${session.expiredTime: 30}")
    private long expiredTime;

    @Value("${session.expiredUnit: min}")
    private String unit;

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setWithExpiredTime(String key, Object value) {

        redisTemplate.opsForValue().set(key, value, expiredTime, convertTime());
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public TimeUnit convertTime() {
        TimeUnit timeUnit = null;
        if ("day".equalsIgnoreCase(unit) || "d".equalsIgnoreCase(unit)) {
            timeUnit = TimeUnit.MINUTES;
        } else if ("hour".equalsIgnoreCase(unit) || "h".equalsIgnoreCase(unit)) {
            timeUnit = TimeUnit.HOURS;
        } else if ("minute".equalsIgnoreCase(unit) || "min".equalsIgnoreCase(unit)) {
            timeUnit = TimeUnit.MINUTES;
        } else if ("second".equalsIgnoreCase(unit) || "s".equalsIgnoreCase(unit)) {
            timeUnit = TimeUnit.SECONDS;
        } else if ("millisecond".equalsIgnoreCase(unit) || "ms".equalsIgnoreCase(unit)) {
            timeUnit = TimeUnit.MILLISECONDS;
        } else {
            timeUnit = TimeUnit.MINUTES;
        }
        return timeUnit;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public String getUnit() {
        return unit;
    }
}
