package com.tttare.management.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.model.User;

import java.util.Map;


public interface UserService {

    Page findUserPage(Map<String,Object> param);

    User findByUserName(Map<String,Object> param);

    void addUser(User user);

    //int countByCondition(Map<String,String> );
}
