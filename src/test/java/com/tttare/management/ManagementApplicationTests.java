package com.tttare.management;

import com.tttare.management.common.model.Contant;
import com.tttare.management.common.redis.IRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationTests {

	@Resource(name = "redisUtil")
	private IRedis redisUtil;

	@Test
	public void contextLoads() {
		redisUtil.delete(Contant.LOCATION_INFO);

	}

}
