package com.tttare.management.shiro;

import com.tttare.management.common.redis.IRedis;
import com.tttare.management.model.SysUser;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: TokenAuthFilter <br/>
 * Description: 自定义登录认证拦截器<br/>
 * date: 2020/5/24 16:34<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
//@Component
public class TokenAuthFilter extends BasicHttpAuthenticationFilter {

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        HttpServletRequest req= (HttpServletRequest)request;
        String token = req.getParameter("token");
        if(token==null){
            return false;
        }
        SysUser user = redisUtil.getObject(token, SysUser.class);
        if(user==null){
            return false;
        }
        return true;
    }

    //isAccessAllowed  返回false时进入此方法
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false; //false by default or we wouldn't be in this method
        if (isLoginAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn) {
            sendChallenge(request, response);
        }
        return loggedIn;
    }

}
