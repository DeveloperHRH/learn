package com.learn.management.impl;


import com.learn.management.system.rpc.service.IRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Set<String> keys(final String key) {
        return redisTemplate.keys(key);
    }

    @Override
    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean hExists(final String key, final String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public <T> T getObject(String key, Class<T> requiredType) {
        if (requiredType == null) {
            return null;
        }
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj != null) {
            return requiredType.cast(obj);
        }
        return null;
    }

    @Override
    public String getString(String key) {
        return getObject(key, String.class);
    }

    @Override
    public Integer getInt(String key) {
        return getObject(key, Integer.class);
    }

    @Override
    public Long getLong(String key) {
        return getObject(key, Long.class);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public <T> T hgetObject(String key, String hashKey, final Class<T> requiredType) {
        if (requiredType == null) {
            return null;
        }
        Object obj = redisTemplate.opsForHash().get(key, hashKey);

        if (obj != null) {
            return requiredType.cast(obj);
        }
        return null;
    }

    @Override
    public <T> T mgetObject(String key, final Class<T> requiredType) {
        Map obj = redisTemplate.opsForHash().entries(key);
        if (obj != null) {
            return requiredType.cast(obj);
        }
        return null;
    }

    @Override
    public <T> void rpush(String key, T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public <T> void rpushAll(String key, List<T> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public <T> T lpop(String key, Class<T> requiredType) {
        if (requiredType == null) {
            return null;
        }
        Object obj = redisTemplate.opsForList().leftPop(key);
        if (obj != null) {
            return requiredType.cast(obj);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void hdelete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    @Override
    public void hdelete(String key, List<String> hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys.toArray());
    }

    @Override
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Long increment(final String key) {
        Object o = redisTemplate.execute((RedisCallback<Long>) connection -> {
            try {
                return connection.incr(key.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        if (o != null) {
            return (Long) o;
        }
        return 0L;
    }

    @Override
    public List<String> hashKeys(final String key, final String pattern) {
        Object o = redisTemplate.execute((RedisCallback<List<String>>) connection -> {
            Map<byte[], byte[]> datas = null;
            try {
                datas = connection.hGetAll(key.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            if (datas != null) {
                List<String> result = new ArrayList<String>(datas.size());
                for (byte[] key1 : datas.keySet()) {
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(new ByteArrayInputStream(key1));
                        String k = (String) ois.readObject();
                        if ((k != null) && ((pattern == null) || k.contains(pattern))) {
                            result.add(k);
                        }
                    } catch (Exception e) {
                        // TODO
                    } finally {
                        if (ois != null) {
                            try {
                                ois.close();
                            } catch (IOException e) {// TODO
                            }
                        }
                    }
                }
                return result;
            }
            return null;
        });

        if (o != null) {
            return (List<String>) o;
        }
        return new ArrayList<String>(0);
    }

    @Override
    public <T> void mset(String key, Map<String, T> dataMap) {
        this.redisTemplate.opsForHash().putAll(key, dataMap);
    }

    @Override
    public <T> void mdelete(String key, String... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    public boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        // 如果锁超时
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && (Long.parseLong(currentValue) < System
                .currentTimeMillis())) {
            // 获取上一个锁的时间
            String oldvalue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldvalue) && oldvalue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String value) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
        }
    }
}
