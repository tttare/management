package com.tttare.management.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.utils.LocationUtil;
import com.tttare.management.mapper.RoleMapper;
import com.tttare.management.mapper.UserMapper;
import com.tttare.management.model.SysRole;
import com.tttare.management.model.SysUser;
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
    private RoleMapper roleMappper;

    @Autowired
    private LocationUtil locationUtil;

    @Override
    public Page findUserPage(Map<String,Object> param){
        //1表示当前页，而10表示每页的显示显示的条目数
        Page page=new Page();
        page.setSize(param.get("size")==null?Contant.SIZE:Integer.valueOf(param.get("pageSize").toString()));
        page.setCurrent(param.get("current")==null?Contant.CURRENT:Integer.valueOf(param.get("pageNumber").toString()));
        //查询条件封装
        LambdaQueryWrapper<SysUser> userQuery = Wrappers.lambdaQuery();
        if(StringUtils.isNotEmpty((String)param.get("userName"))){
            userQuery.like(SysUser::getUserName,param.get("userName"));
        }
        if(StringUtils.isNotEmpty((String)param.get("nickName"))){
            userQuery.like(SysUser::getNickName,param.get("nickName"));
        }
        List<String> state = (List<String>)param.get("state");
        if(state!=null&&!state.isEmpty()){
            userQuery.in(SysUser::getState,state);
        }
        if(StringUtils.isNotEmpty((String)param.get("sortOrder"))){
            userQuery.orderBy(true,param.get("sortOrder").toString().equals(Contant.ORDER_ASC), SysUser::getCreateDate);
        }
        Page resPage = userMapper.selectPage(page, userQuery);
        List records = resPage.getRecords();
        for(Object record : records){
            SysUser user = (SysUser)record;
            user.setLocation(locationUtil.formatLocationInfo(user.getLocation()));
        }
        Integer count = userMapper.selectCount(userQuery);
        resPage.setTotal(count);
        return resPage;
    }

    @Override
    public SysUser findByUserName(Map<String,Object> param) {
        List<SysUser> users = userMapper.selectByMap(param);
        if(users.isEmpty()){
            return null;
        }
        SysUser user = users.get(0);
        List<SysRole> roles = roleMappper.findUserRoleAndMenu(user.getUserId());
        return user;
    }

    @Override
    public void addUser(SysUser user) {
        userMapper.insert(user);
    }
}
