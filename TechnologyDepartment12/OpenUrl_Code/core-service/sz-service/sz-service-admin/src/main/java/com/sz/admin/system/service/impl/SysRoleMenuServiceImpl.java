package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysRoleMenuMapper;
import com.sz.admin.system.mapper.SysUserRoleMapper;
import com.sz.admin.system.pojo.dto.sysrolemenu.SysRoleMenuDTO;
import com.sz.admin.system.pojo.po.SysRoleMenu;
import com.sz.admin.system.pojo.po.SysUserRole;
import com.sz.admin.system.pojo.vo.sysmenu.MenuTreeVO;
import com.sz.admin.system.pojo.vo.sysrolemenu.SysRoleMenuVO;
import com.sz.admin.system.service.SysMenuService;
import com.sz.admin.system.service.SysRoleMenuService;
import com.sz.core.common.event.EventPublisher;
import com.sz.core.util.Utils;
import com.sz.platform.event.PermissionChangeEvent;
import com.sz.platform.event.PermissionMeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统角色-菜单表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2022-10-01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final SysMenuService sysMenuService;

    private final EventPublisher eventPublisher;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional
    public void change(SysRoleMenuDTO dto) {
        // 根据角色id 查询影响用户范围，获取到用户id
        LambdaQueryWrapper<SysUserRole> userWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .select(SysUserRole::getUserId)
                .eq(SysUserRole::getRoleId, dto.getRoleId())
                .groupBy(SysUserRole::getUserId);
        // 获取到影响范围的userId
        List<Long> userIds = sysUserRoleMapper.selectList(userWrapper).stream().map(SysUserRole::getUserId).toList();

        // 删除当前角色下的所有记录
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, dto.getRoleId());
        remove(wrapper);
        if (Utils.isNotNull(dto.getMenuIds())) {
            baseMapper.insertBatchSysRoleMenu(dto.getMenuIds(), dto.getRoleId());
        }

        eventPublisher.publish(new PermissionChangeEvent(this, new PermissionMeta(userIds)));
    }

    /**
     * 查询角色分配的菜单，及全部菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRoleMenuVO queryRoleMenu(Long roleId) {
        List<MenuTreeVO> menuTreeVOS = sysMenuService.queryRoleMenuTree(true);
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                .select(SysRoleMenu::getMenuId)
                .eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> list = list(wrapper);
        List<String> selectIds = new ArrayList<>();
        if (Utils.isNotNull(list)) {
            selectIds = list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        }
        SysRoleMenuVO menuVO = new SysRoleMenuVO();
        menuVO.setMenuLists(menuTreeVOS);
        menuVO.setSelectIds(selectIds);
        return menuVO;
    }

}
