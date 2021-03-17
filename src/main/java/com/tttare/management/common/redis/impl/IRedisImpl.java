package com.tttare.management.common.redis.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * redis操作类
 * */
@Component("redisUtil")
public class IRedisImpl implements IRedis {

    private static final long defaultexpires = 24*60*60l ; // 默认过期时间

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Gson gson = new Gson();


    public String get(String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    public void set(String key, String value, long validTime) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                connection.expire(serializer.serialize(key), validTime);
                return true;
            }
        });
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public <T> T getObject(String key,Class<T> clazz){
        return gson.fromJson(get(key), clazz);
    }

    @Override
    public <T> List<T> getList(String key, Class<T[]> clazz) {
        List<T> list = new ArrayList<T>();
        list.addAll(Arrays.asList(gson.fromJson(get(key), clazz)));
        return list;
    }

    @Override
    public <K,V> Map<K,V> getMap(String key, Class<K> keyType,Class<V> valueType){
        return JSONObject.parseObject(get(key),new TypeReference<Map<K, V>>(keyType, valueType) {});
    }

    @Override
    public <K,V> List<Map<K,V>> getMapList(String key, Class<K> keyType,Class<V> valueType) {
        return JSONObject.parseObject(get(key),new TypeReference<List<Map<K,V>>>(keyType, valueType) {});
    }





    @Override
    public <T> T popObject(String key, Class<T> clazz) {
        T a= getObject(key,clazz);
        if(a!=null){
            delete(key);
        }
        return a;
    }

    @Override
    public <T> void setObject(String key,T value,Long times) {
        times = times == null ? defaultexpires : times;
        set(key,value instanceof String  ? (String)value : JSONObject.toJSONString(value),times);
    }



}
