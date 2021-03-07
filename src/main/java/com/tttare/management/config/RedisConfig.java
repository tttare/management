
		/**  
		 * Project Name:hx_web  
		 * File Name:RedisConfig.java  
		 * Package Name:com.whty.hxx.web.config  
		 * Date:2018年6月21日下午6:52:27  
		 * Copyright (c) 2018
		 *  
		*/  
		package com.tttare.management.config;

        import com.fasterxml.jackson.annotation.JsonAutoDetect;
        import com.fasterxml.jackson.annotation.PropertyAccessor;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.cache.CacheManager;
        import org.springframework.cache.annotation.CachingConfigurerSupport;
        import org.springframework.cache.annotation.EnableCaching;
        import org.springframework.cache.interceptor.KeyGenerator;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.redis.cache.RedisCacheConfiguration;
        import org.springframework.data.redis.cache.RedisCacheManager;
        import org.springframework.data.redis.cache.RedisCacheWriter;
        import org.springframework.data.redis.connection.RedisConnectionFactory;
        import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
        import org.springframework.data.redis.core.RedisTemplate;
        import org.springframework.data.redis.core.StringRedisTemplate;
        import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

        import java.lang.reflect.Method;

        /**
                 * ClassName:RedisConfig <br/>
                 * Function: redi是缓存配置. <br/>
                 * Reason:   TODO ADD REASON. <br/>
                 * Date:     2018年6月21日 下午6:52:27 <br/>
                 * @author   verywell
                 * @version
                 * @since    JDK 1.8
                 * @see
                 */
        @Configuration
        @EnableCaching
        public class RedisConfig extends CachingConfigurerSupport {

            @Autowired
            private RedisConnectionFactory factory;

            @Bean
            public KeyGenerator wiselyKeyGenerator(){
                return new KeyGenerator() {
                    @Override
                    public Object generate(Object target, Method method, Object... params) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(target.getClass().getName());
                        sb.append(method.getName());
                        for (Object obj : params) {
                            sb.append(obj.toString());
                        }
                        return sb.toString();
                    }
                };

            }

            @Bean(name="cacheManager")
            public CacheManager cacheManager() {
                //初始化一个RedisCacheWriter
                RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
                RedisCacheManager cacheManager=new RedisCacheManager(redisCacheWriter,RedisCacheConfiguration.defaultCacheConfig());
        //    	RedisCacheManager cacheManager=RedisCacheManager.builder(factory).withInitialCacheConfigurations(cacheConfigurations).build();
                return cacheManager;
            }

            /*@Bean(name="redisTemplate")
            public RedisTemplate<String, String> getRedisTemplate() {
                RedisTemplate<String, String> template = new RedisTemplate<>();

                RedisSerializer<String> redisSerializer = new StringRedisSerializer();
                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(om);

                template.setConnectionFactory(factory);
                //key序列化方式
                template.setKeySerializer(redisSerializer);
                //value序列化
                template.setValueSerializer(new JdkSerializationRedisSerializer());
              //value hashkey序列化
                template.setHashKeySerializer(redisSerializer);
                //value hashmap序列化
                template.setHashValueSerializer(jackson2JsonRedisSerializer);
                return template;
            }*/

            /**
             * 获取缓存操作助手对象
             *
             * @return
             */
            @Bean(name="redisTemplate")
            public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory) {
                //创建Redis缓存操作助手RedisTemplate对象
                StringRedisTemplate template = new StringRedisTemplate();
                template.setConnectionFactory(factory);
                //以下代码为将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
                //此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(om);
                template.setValueSerializer(jackson2JsonRedisSerializer);
                template.afterPropertiesSet();
                return template;//StringRedisTemplate是RedisTempLate<String, String>的子类
            }


        }
  
	