package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysDataRoleMenuMapper;
import com.sz.admin.system.pojo.po.SysDataRoleMenu;
import com.sz.admin.system.service.SysDataRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统数据角色-菜单表 服务实现类
 * </p>
 *
 * @author sz-admin
 * @since 2024-07-11
 */
@Service
@RequiredArgsConstructor
public class SysDataRoleMenuServiceImpl extends ServiceImpl<SysDataRoleMenuMapper, SysDataRoleMenu> implements SysDataRoleMenuService {

    @Override
    public void batchSave(Long roleId, List<String> menuIds) {
        LambdaQueryWrapper<SysDataRoleMenu> wrapper = Wrappers.lambdaQuery(SysDataRoleMenu.class)
                .eq(SysDataRoleMenu::getRoleId, roleId);
        if (count(wrapper) > 0) {
            remove(wrapper);
        }

        List<SysDataRoleMenu> roleMenus = new ArrayList<>();
        SysDataRoleMenu roleMenu = null;
        for (String menuId : menuIds) {
            roleMenu = new SysDataRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        if (!roleMenus.isEmpty())
            saveBatch(roleMenus);
    }

    @Override
    public List<String> getSelectMenuIdByRoleId(Long roleId) {
        LambdaQueryWrapper<SysDataRoleMenu> wrapper = Wrappers.lambdaQuery(SysDataRoleMenu.class)
                .eq(SysDataRoleMenu::getRoleId, roleId);
        List<String> list = list(wrapper).stream().map(SysDataRoleMenu::getMenuId).collect(Collectors.toList());
        return list;
    }

}