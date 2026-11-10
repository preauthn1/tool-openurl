package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysUserRoleMapper;
import com.sz.admin.system.pojo.po.SysUserRole;
import com.sz.admin.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 系统用户-角色关联表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2022-10-01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getUserRolesByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery(SysUserRole.class).select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, userId);
        List<String> list = new java.util.ArrayList<>(List.of());
        list(wrapper).stream().map(SysUserRole::getRoleId).forEach(roleId -> list.add(String.valueOf(roleId)));
        return list;
    }

}