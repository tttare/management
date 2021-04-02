package com.tttare.management.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.common.utils.*;
import com.tttare.management.mapper.ResoucesMapper;
import com.tttare.management.model.*;
import com.tttare.management.service.LoginService;
import com.tttare.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

/**
 * ClassName: LoginContorller <br/>
 * Description: user login Controller<br/>
 * date: 2019/9/4 21:57<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Slf4j
@Controller
@RequestMapping(value="/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResoucesMapper resoucesMapper;

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    @Resource
    DefaultKaptcha defaultKaptcha;

    //md5加密
    private String algorithmName = "md5";
    private int hashIterations = 2;
    //密码盐
    private static final String CODE  = "verifyCode";


    private long verifyTTL = 60;//验证码过期时间60秒

    private String create16String()
    {
        return RandomUtils.generateString(16);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(Map<String, Object> map){
        loginService.logout();
        String key = create16String();
        map.put("key",key);
        return "/user/login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/getKey",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getKey(){
        loginService.logout();
        String key = create16String();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key",key);
        return map;
    }

    /**
     * 2、生成验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getVerifyCode")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        byte[] bytesCaptchaImg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            log.info("验证码文本:"+createText);
            //使用redis来做,抛弃session,保持验证码60s
            //redisUtil.setObject(createText,"r",verifyTTL*1000l);

            HttpSession session = request.getSession();
            session.setAttribute(CODE,createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage bufferedImage = defaultKaptcha.createImage(createText);
            ImageIO.write(bufferedImage, "jpg", jpegOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        bytesCaptchaImg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(bytesCaptchaImg);
        responseOutputStream.flush();

        responseOutputStream.close();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request,@RequestBody Map<String,String> param) throws Exception {
        String userName = param.get("username");
        String encryptedPassword = param.get("password");
        String key = param.get("key");

        String verifyCode = param.get(CODE);
        HttpSession session = request.getSession();
        String sessionCode = (String)session.getAttribute(CODE);
        if(sessionCode == null || !verifyCode.equals(sessionCode)){
            return new ResponseParam(Contant.FAIL,"验证码不正确或已失效,请点击验证码重新输入");
        }

        String password = AesUtils.decrypt(encryptedPassword,key);

        LoginResult loginResult = loginService.login(userName, password);
        if (loginResult.isLogin()) {
            //将用户信息存入session
            //获取已登录的用户信息
 //           User user = (User)SecurityUtils.getSubject().getPrincipal();
            //生成token
//            String token = MD5Util.EncoderByMd5(user.getUserName() + user.getPassword() + verifyCode);
//            redisUtil.setObject(token,user,verifyTTL*verifyTTL*1000l);
            return  new ResponseParam(Contant.SUCCESS,null);
        } else {
            return  new ResponseParam(Contant.FAIL,loginResult.getResult());
        }
    }

    //用户注册
    @RequestMapping(value = "/logon", method = RequestMethod.GET)
    public String toLogon(HttpServletRequest request){

        return "/user/logon";
    }

    //用户注册
    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @ResponseBody
    public ResponseParam logon(HttpServletRequest request,@RequestBody UserLogonRequest param){
        ResponseParam rp =null;
        String userName = param.getUserName();
        String name = param.getNickName();
        String password = param.getPassword();
        String email = param.getEmail();
        String emailCode = param.getEmailCode();
        String emailCodeCache = redisUtil.getObject(email, String.class);
        String birthDate = param.getBirthDate();
        List<String> location = param.getLocation();
        String locationStr = "";
        for (String str:location) {
            locationStr += str +"/";
        }
        if(locationStr.length()>0){
            locationStr = locationStr.substring(0,locationStr.length()-1);
        }

        if(StringUtils.isEmpty(emailCodeCache)){
            rp = new ResponseParam(Contant.FAIL,"验证码已过期");
        }else{
            if(emailCodeCache.equals(emailCode)){
                //作废缓存
                redisUtil.delete(email);
                //插入用户
                User user = new User();
                user.setUserId(CommonUtil.getUUID());
                user.setUserName(userName);
                user.setEmail(email);
                user.setSalt(create16String());
                user.setState("0");
                Date date = new Date();
                user.setCreateDate(new Timestamp(date.getTime()));
                // 所有用户默认只有一个月有效期
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MONTH,1);
                user.setExpiredDate(new Timestamp(calendar.getTime().getTime()));
                String  encryptPwd= EncryptUtils.encrypt(password,user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
                user.setPassword(encryptPwd);
                user.setNickName(name);
                user.setBirthDate(CommonUtil.parseDate(birthDate,"yyyy-MM-dd"));
                user.setLocation(locationStr);
                //处理用户上传的图像---图像处理失败不能影响用户注册
                try {
                    if(param.getHaveIcon()){
                        UploadObject uploadObject = param.getUploadObject();
                        //将文件名 改为 uuid的名称,记录 文件后缀

                        String fileName = uploadObject.getName();
                        String type = uploadObject.getType();
                        String suffix = fileName.split("\\.")[1];
                        String newFileName =CommonUtil.getUUID()+"."+suffix;
                        String dirPath = CommonUtil.dateFormat(date,"yyyy/MM/dd/HH");
                        File file = FileUtil.base64ToFile(uploadObject.getFileData(),Contant.File_UPLOAD_PATH+dirPath,newFileName);
                        if(file !=null){
                            user.setIconPath(file.getAbsolutePath());
                            Resouces resouces = new Resouces();
                            resouces.setUploadDate(date);
                            resouces.setUploaderId(user.getUserId());
                            resouces.setId(CommonUtil.getUUID());
                            resouces.setFileName(fileName);
                            resouces.setSuffix(suffix);
                            resouces.setFilePath(file.getAbsolutePath());
                            resouces.setType(type);
                            resouces.setState("0");
                            resouces.setSize(uploadObject.getSize());
                            resoucesMapper.insert(resouces);
                        }
                    }
                }catch (Exception e){
                    log.error("用户图像新增失败:"+e.getMessage());
                }
                try{
                    userService.addUser(user);
                    rp = new ResponseParam(Contant.SUCCESS,"注册成功");
                }catch (Exception e){
                    rp = new ResponseParam(Contant.FAIL,"数据异常:"+e.getMessage());
                }
            }else{
                rp = new ResponseParam(Contant.FAIL,"验证码已过期");
            }
        }
        return rp;
    }


    //邮箱验证
    @RequestMapping(value = "/confirmEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseParam confirmEmail(HttpServletRequest request, @RequestBody Map<String,Object> map){
        //检验邮箱是否已被注册
        ResponseParam rp = loginService.confirmEmail(map);
        return rp;
    }


}
