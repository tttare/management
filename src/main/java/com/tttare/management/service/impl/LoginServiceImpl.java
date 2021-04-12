package com.tttare.management.service.impl;

import com.tttare.management.common.model.Contant;
import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.common.utils.EmailUtil;
import com.tttare.management.common.utils.RandomUtils;
import com.tttare.management.mapper.UserMapper;
import com.tttare.management.model.LoginResult;
import com.tttare.management.model.SysUser;
import com.tttare.management.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LoginServiceImpl <br/>
 * Description: <br/>
 * date: 2019/9/3 23:46<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    @Override
    public LoginResult login(String userName, String password) {
        LoginResult loginResult = new LoginResult();
        if(userName==null || userName.isEmpty())
        {
            loginResult.setLogin(false);
            loginResult.setResult("用户名为空");
            return loginResult;
        }
        String msg="";
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();

//        // 2、判断当前用户是否登录
//        if (currentUser.isAuthenticated() == false) {
//
//        }

        // 3、将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        // 4、认证
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            loginResult.setLogin(true);
            SysUser user = (SysUser)currentUser.getPrincipal();
            SysUser updateUser = new SysUser();
            updateUser.setUserId(user.getUserId());
            updateUser.setLastLoginDate(new Date());
            userMapper.updateById(updateUser);
            return loginResult;
        }catch (UnknownAccountException e)
        {
            msg = "账号不存在!";
        }
        catch (IncorrectCredentialsException e)
        {
            msg = "密码不正确!";
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            msg="用户验证失败!";
        }

        loginResult.setLogin(false);
        loginResult.setResult(msg);

        return loginResult;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public String getCurrentUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String)session.getAttribute("userName");
    }

    @Override
    public Session getSession() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session;
    }

    @Override
    public ResponseParam confirmEmail(Map<String,Object> params) {
        // 判断邮箱是否已经被使用
        List<SysUser> userList = userMapper.selectByMap(params);
        if(userList.size() > 0){
            return new ResponseParam(Contant.FAIL,"该邮箱已被注册使用,请更换邮箱");
        }
        String email = (String)params.get("email");
        String code = RandomUtils.generateString(6);
        log.info("邮箱验证码:"+code);
        try {
            EmailUtil.send(email,"邮箱验证",code);
        }catch (Exception e){
            log.error("email post fail:"+e.getMessage());
            return new ResponseParam(Contant.FAIL,"抱歉,邮箱验证失败,请稍后重试");
        }
        long times=60*5;//五分钟后过期
        redisUtil.setObject(email,code,times);
        return new ResponseParam(Contant.SUCCESS,"邮件验证成功");
    }
}
