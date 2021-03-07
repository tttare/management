package com.tttare.management.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ClassName: CommonUtil <br/>
 * Description: <br/>
 * date: 2019/9/3 11:08<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Slf4j
public class CommonUtil {

    //生产UUID
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    //复杂java对象的json转化:适用于含有date信息等包装类型的复杂对象
    public static <T> T parseToJava(String jsonStr,Class<T> clazz){
        return JSON.parseObject(jsonStr,new TypeReference<T>(){});
    }

    /***
     *  Date -> String
     * @param：时间对象
     * @param：时间格式化表达式
     * @return:格式后的字符串
     */
    public static String dateFormat(Date date,String format){
        if(date==null){
            throw new NullPointerException("date is null");
        }
        if(format==null){
            format ="yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * String -> Date
     * @param：时间字符串
     * @param：时间格式化表达式
     * @return:格式后的日期对象
     * */
    public static Date parseDate(String dateStr,String format) {
        Date date = null;
        if(StringUtils.isEmpty(dateStr)){
            throw new NullPointerException("dateStr is null");
        }
        if(format==null){
            format ="yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            date = sdf.parse(dateStr);
        }catch (ParseException e){
            log.error("时间字符串与格式化表达式不符合，dateStr："+dateStr+",format:"+format);
        }
        return date;
    }
    // 为当前date 加或减  时H 天 D  月M  年Y
    public Date addOrSubDate(Date currentDate,char target,int num){
        return null;
    }

}
