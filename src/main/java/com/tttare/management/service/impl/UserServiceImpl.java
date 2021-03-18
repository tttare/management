package com.tttare.management.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.utils.LocationUtil;
import com.tttare.management.mapper.UserMapper;
import com.tttare.management.model.User;
import com.tttare.management.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LocationUtil locationUtil;

    @Override
    public Page findUserPage(Map<String,Object> param){
        //1表示当前页，而10表示每页的显示显示的条目数
        Page page=new Page();
        page.setSize(param.get("size")==null?Contant.SIZE:Integer.valueOf(param.get("pageSize").toString()));
        page.setCurrent(param.get("current")==null?Contant.CURRENT:Integer.valueOf(param.get("pageNumber").toString()));
        //查询条件封装
        LambdaQueryWrapper<User> userQuery = Wrappers.lambdaQuery();
        userQuery.like(User::getUserName,param.get("userName"));
        userQuery.like(User::getNickName,param.get("nickName"));
        userQuery.eq(User::getState,param.get("state"));
        if(StringUtils.isNotEmpty((String)param.get("sortOrder"))){
            userQuery.orderBy(true,param.get("sortOrder").toString().equals(Contant.ORDER_ASC),User::getCreateDate);
        }
        Page resPage = userMapper.selectPage(page, userQuery);
        List records = resPage.getRecords();
        for(Object record : records){
            User user = (User)record;
            user.setLocation(locationUtil.formatLocationInfo(user.getLocation()));
        }
        Integer count = userMapper.selectCount(userQuery);
        resPage.setTotal(count);
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
