package com.tttare.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tttare.management.model.SysRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findUserRoleAndMenu(String userId);
}
