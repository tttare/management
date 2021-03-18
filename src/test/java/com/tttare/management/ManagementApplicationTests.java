package com.tttare.management;

import com.tttare.management.common.model.Contant;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.common.utils.CommonUtil;
import com.tttare.management.mapper.UserMapper;
import com.tttare.management.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationTests {

	@Resource(name = "redisUtil")
	private IRedis redisUtil;

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setUserId(CommonUtil.getUUID());
		user.setUserName("tata");
		user.setNickName("塔塔");
		user.setState("0");
		user.setCreateDate(new Date());
		userMapper.insert(user);
	}

}
