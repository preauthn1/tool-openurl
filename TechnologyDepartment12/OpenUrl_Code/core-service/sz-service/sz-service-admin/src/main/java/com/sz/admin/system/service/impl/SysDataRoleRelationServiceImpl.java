package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysDataRoleRelationMapper;
import com.sz.admin.system.pojo.po.SysDataRoleRelation;
import com.sz.admin.system.service.SysDataRoleRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统数据角色-关联表 服务实现类
 * </p>
 *
 * @author sz-admin
 * @since 2024-07-11
 */
@Service
@RequiredArgsConstructor
public class SysDataRoleRelationServiceImpl extends ServiceImpl<SysDataRoleRelationMapper, SysDataRoleRelation> implements SysDataRoleRelationService {

    @Transactional
    @Override
    public void batchSave(Long roleId, String relationTypeCd, List<Long> relationIds) {
        LambdaQueryWrapper<SysDataRoleRelation> wrapper = Wrappers.lambdaQuery(SysDataRoleRelation.class)
                .eq(SysDataRoleRelation::getId, roleId)
                .eq(SysDataRoleRelation::getRelationTypeCd, relationTypeCd);
        long count = count(wrapper);
        if (count > 0) {
            remove(wrapper);
        }
        List<SysDataRoleRelation> roleRelations = new ArrayList<>();
        SysDataRoleRelation roleRelation = null;
        for (Long relationId : relationIds) {
            roleRelation = new SysDataRoleRelation();
            roleRelation.setRoleId(roleId);
            roleRelation.setRelationId(relationId);
            roleRelation.setRelationTypeCd(relationTypeCd);
            roleRelations.add(roleRelation);
        }
        if (!roleRelations.isEmpty())
            saveBatch(roleRelations);
    }

    @Override
    public List<Long> getSelectRelationId(Long roleId, String relationTypeCd) {
        LambdaQueryWrapper<SysDataRoleRelation> wrapper = Wrappers.lambdaQuery(SysDataRoleRelation.class)
                .select(SysDataRoleRelation::getRelationId)
                .eq(SysDataRoleRelation::getId, roleId)
                .eq(SysDataRoleRelation::getRelationTypeCd, relationTypeCd);
        return list(wrapper).stream().map(SysDataRoleRelation::getRelationId).collect(Collectors.toList());
    }

}