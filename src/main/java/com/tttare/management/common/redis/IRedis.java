package com.tttare.management.common.redis;

import java.util.List;
import java.util.Map;

/***
 *自定义redis操作类
 */
public interface IRedis {

    String get(String key);

    <T> T getObject(String key, Class<T> clazz);

    <T> List<T> getList(String key, Class<T[]> clazz);

    <K,V> Map<K,V> getMap(String key, Class<K> keyType, Class<V> valueType);

    <K,V> List<Map<K,V>> getMapList(String key, Class<K> keyType,Class<V> valueType);


    <T> T popObject(String key, Class<T> clazz);

    <T> void setObject(String key, T value, Long times);

    void delete(String key);
}
