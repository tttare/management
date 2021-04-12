package com.tttare.management.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.model.SysUser;

import java.util.Map;


public interface UserService {

    Page findUserPage(Map<String,Object> param);

    SysUser findByUserName(Map<String,Object> param);

    void addUser(SysUser user);

    //int countByCondition(Map<String,String> );
}
