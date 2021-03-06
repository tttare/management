package com.tttare.management.shiro;


import com.tttare.management.model.SysMenu;
import com.tttare.management.model.SysPermission;
import com.tttare.management.model.SysRole;
import com.tttare.management.model.SysUser;
import com.tttare.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: MyShiroRealm <br/>
 * Description: <br/>
 * date: 2019/9/3 11:50<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //权限信息，包括角色以及权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        SysUser user  = (SysUser)principals.getPrimaryPrincipal();

        for(SysRole role:user.getRoleList()){
            authorizationInfo.addRole(role.getRole());
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String userName = (String)token.getPrincipal();
        log.info("----"+token.getCredentials());
        //通过username从数据库中查找 User对象.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        Map<String,Object> param = new HashMap<>();
        param.put("userName",userName);
        SysUser user = userService.findByUserName(param);
        log.info("----->>user="+user);
        if(user == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo;
        try{
            authenticationInfo = new SimpleAuthenticationInfo(
                    user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                    user.getPassword(), //密码
                    ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                    getName()  //realm name
            );
        }catch(AuthenticationException e){
            throw e;
        }
        return authenticationInfo;
    }
}
