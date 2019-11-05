package com.learn.management.system.rpc.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 公共服务类
 */

public interface IRedisService {

    Set<String> keys(final String key);

    Boolean exists(final String key);

    boolean hExists(final String key, final String hashKey);

    /**
     * 返回指定对象
     *
     * @param key
     * @param requiredType 对象类型
     * @return
     */
    <T> T getObject(String key, Class<T> requiredType);

    /**
     * 返回字符串
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 返回整形
     *
     * @param key
     * @return
     */
    Integer getInt(String key);

    /**
     * 返回长整形
     *
     * @param key
     * @return
     */
    Long getLong(String key);

    /**
     * 保存值
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 保存值，并设置过期时间
     *
     * @param key
     * @param value
     * @param timeout 毫秒
     */
    void set(String key, Object value, long timeout);

    /**
     * @param key
     * @param hashKey
     * @param value
     */
    void hset(String key, String hashKey, Object value);

    /**
     * @param key
     * @param hashKey
     * @return
     */
    <T> T hgetObject(String key, String hashKey, final Class<T> requiredType);

    /**
     * 保存List
     *
     * @param <T>
     * @param key
     * @param value
     */
    <T> void rpush(String key, T value);

    /**
     * 保存List
     *
     * @param <T>
     * @param key
     * @param values
     */
    <T> void rpushAll(String key, List<T> values);

    /**
     * @param key
     * @param requiredType
     * @return
     */
    <T> T lpop(String key, Class<T> requiredType);

    /**
     * 删除某个值
     *
     * @param key
     */
    void delete(String key);

    /**
     * 删除某个值
     *
     * @param key
     */
    void hdelete(String key, String hashKey);

    /**
     * 批量删除key
     *
     * @param key
     * @param hashKeys
     */
    void hdelete(String key, List<String> hashKeys);

    /**
     * 设置某个Key的有效期
     *
     * @param key
     */
    void expire(String key, long timeout);

    /**
     * 自增长
     *
     * @param key
     * @return
     */
    Long increment(final String key);

    /**
     * 查找key下面的所有hashKey
     *
     * @return
     */
    List<String> hashKeys(final String key, final String pattern);

    /**
     * 添加缓存的Map
     *
     * @param key
     * @return
     */
    <T> void mset(String key, Map<String, T> dataMap);

    <T> void mdelete(String key, String... field);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param requiredType
     * @return
     */

    <T> T mgetObject(String key, final Class<T> requiredType);
}
