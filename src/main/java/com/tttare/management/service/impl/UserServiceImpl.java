package com.tttare.management.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.common.model.Contant;
import com.tttare.management.mapper.UserMapper;
import com.tttare.management.model.User;
import com.tttare.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page findUserPage(Map<String,Object> param){
        //1表示当前页，而10表示每页的显示显示的条目数
        Page page=new Page();
        page.setSize(param.get("size")==null?Contant.SIZE:Integer.valueOf(param.get("size").toString()));
        page.setCurrent(param.get("current")==null?Contant.CURRENT:Integer.valueOf(param.get("current").toString()));
        //查询条件封装
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        Page resPage = userMapper.selectMapsPage(page, userLambdaQueryWrapper);
        return resPage;
    }

    @Override
    public User findByUserName(Map<String,Object> param) {
        List<User> users = userMapper.selectByMap(param);
        return users.isEmpty()?null:users.get(0);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }
}
