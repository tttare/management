package com.tttare.management.service;


import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.model.LoginResult;
import org.apache.shiro.session.Session;

import java.util.Map;

/**
 * ClassName: LoginService <br/>
 * Description: <br/>
 * date: 2019/9/3 12:23<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
public interface LoginService {

    LoginResult login(String userName, String password);

    void logout();

    String getCurrentUserName();

    Session getSession();

    ResponseParam confirmEmail(Map<String, Object> params);
}
