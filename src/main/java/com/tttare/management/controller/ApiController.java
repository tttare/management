package com.tttare.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.common.utils.FileUtil;
import com.tttare.management.common.utils.LocationUtil;
import com.tttare.management.model.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CastUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * API
 * 无需进行权限验证
 * */
@Slf4j
@RestController
@RequestMapping(value="/api")
public class ApiController {

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    @Autowired
    private LocationUtil locationUtil;

    @RequestMapping(value = "/locationInfo",method = RequestMethod.POST)
    public ResponseParam getlocation(@RequestBody Map<String,Object> param){
        Map<String,Object> location = locationUtil.getLocation();
        int level = CastUtils.cast(param.get("level"));
        String key = Contant.LOCATION_INFO+"_"+level;
        List<Option> options = new ArrayList<>();
        if(redisUtil.get(key)==null){
            Map<String,Object> province = (Map<String,Object>)location.get("86");
            mapConvertToOptions(options,province,level,location);
            redisUtil.setObject(key,options,null);
        }else{
            options = redisUtil.getList(key, Option[].class);
        }
        formatOptions(options);
        return  new ResponseParam(Contant.SUCCESS,options,null);
    }

    private void mapConvertToOptions(List<Option> options,Map<String,Object> map,int level,Map<String,Object> location){
        if(level<1 || map==null){
            return;
        }
        level--;
        Set<String> keys =  map.keySet();
        for (String key : keys) {
            Option option = new Option(key,(String)map.get(key));
            options.add(option);
            Collections.sort(options);
            mapConvertToOptions(option.getChildren(),(Map<String,Object>)location.get(key),level,location);
        }
    }

    private void formatOptions(List<Option> options){
        for(Option option : options){
            if(option.getChildren()!=null){
                if(option.getChildren().isEmpty()){
                    option.setChildren(null);
                }else{
                    formatOptions(option.getChildren());
                }
            }
        }

    }



}
