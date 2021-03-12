package com.tttare.management.controller;

import com.tttare.management.common.redis.IRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * API
 * 无需进行权限验证
 * */
@Slf4j
@Controller
@RequestMapping(value="/api")
public class ApiController {

    @Resource(name = "redisUtil")
    private IRedis redisUtil;


}
