package com.tttare.management.filters;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: COREFilter <br/>
 * Description: 处理跨域问题<br/>
 * date: 2020/5/23 15:19<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Log
//@WebFilter(urlPatterns = "/*",filterName = "COREFilter")
public class COREFilter implements Filter {

    /**
     * 处理跨域问题,实现前后台分离项目的session统一
     * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Cookie[] cookies =  req.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            log.info(cookies[i].getName() +"---"+cookies[i].getValue());
        }
        /**
         * getSession相当于getSession(true);ture 没有session时返回一个新的session;false 没有session时返回null
         * */
        log.info(req.getSession().getId());
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String origin = req.getHeader("Origin");
        if(origin == null){
            origin = req.getHeader("Referer");
        }
        resp.setHeader("Access-Control-Allow-Origin",origin);//不能设置 * ,* 代表接受所有域名访问,如写 * 则代表接受所有域名访问
        resp.setHeader("Access-Control-Allow-Credentials","true");//true 代表允许携带cookie
        resp.setHeader("Access-Control-Allow-Methods","*");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
