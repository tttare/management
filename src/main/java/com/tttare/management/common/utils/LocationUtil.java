package com.tttare.management.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.redis.IRedis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.Map;

@Component
public class LocationUtil {

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    public Map<String,Object> getLocation() {
        Map<String,Object> location = null;
        if(redisUtil.get(Contant.LOCATION_INFO)!=null){
            location = redisUtil.getMap(Contant.LOCATION_INFO,String.class,Object.class);
        }else {
            //读取classpath地下区域的json字符串
            URL resource = this.getClass().getClassLoader().getResource("location.json");
            File file = new File(resource.getFile());
            String str = FileUtil.readFile(file,null);
            if(org.thymeleaf.util.StringUtils.isEmpty(str)){
                return null;
            }
            location = JSONObject.parseObject(str,Map.class);
            redisUtil.setObject(Contant.LOCATION_INFO,location,null);
        }
        return location;
    }

    public String formatLocationInfo(String location){
        if(StringUtils.isEmpty(location)||location.length()<2){
            return null;
        }
        Map<String, Object> locationMap = this.getLocation();
        String locationInfo = "";
        String[] codes = location.split("/");
        Map<String,Object> provinces = (Map<String,Object>)locationMap.get("86");
        Map<String,Object> citys = null;
        Map<String,Object> regions = null;
        if(codes.length==1){
            return (String)provinces.get(codes[0]);
        }
        if(codes.length==2){
            String provinceStr = (String)provinces.get(codes[0]);
            citys = (Map<String,Object>)locationMap.get(codes[0]);
            if(citys==null){
                return  provinceStr;
            }else{
                return provinceStr+(String)citys.get(codes[1]);
            }
        }
        if(codes.length==3){
            locationInfo = (String)provinces.get(codes[0]);
            citys = (Map<String,Object>)locationMap.get(codes[0]);
            if(citys==null){
                return  locationInfo;
            }else{
                locationInfo+=(String)citys.get(codes[1]);
                regions = (Map<String,Object>)locationMap.get(codes[1]);
                if(regions==null){
                    return  locationInfo;
                }else{
                    locationInfo+=(String)regions.get(codes[2]);
                }
            }

        }

        return null;
    }

}
