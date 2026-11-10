package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysUserDeptMapper;
import com.sz.admin.system.pojo.dto.sysuser.UserDeptDTO;
import com.sz.admin.system.pojo.po.SysUserDept;
import com.sz.admin.system.service.SysUserDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 用户-部门关系表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-04-02
 */

@Service
@RequiredArgsConstructor
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {

    @Override
    @Transactional
    public void bind(UserDeptDTO dto) {
        List<Long> userIds = dto.getUserIds();
        List<Long> deptIds = dto.getDeptIds();
        if (!userIds.isEmpty()) {
            List<Long> removeIds = list(Wrappers.lambdaQuery(SysUserDept.class).select(SysUserDept::getId).in(SysUserDept::getUserId, userIds))
                    .stream().map(SysUserDept::getId).toList();
            removeByIds(removeIds);
        }

        List<SysUserDept> batchList = new ArrayList<>();
        SysUserDept userDept = null;
        for (Long deptId : deptIds) {
            for (Long userId : userIds) {
                userDept = new SysUserDept();
                userDept.setUserId(userId);
                userDept.setDeptId(deptId);
                batchList.add(userDept);
            }
        }
        saveBatch(batchList); // 重新创建user 和dept的关系
    }

    @Override
    public void unbind(List<Long> userIds) {
        LambdaQueryWrapper<SysUserDept> removeWrapper = Wrappers.lambdaQuery(SysUserDept.class).select(SysUserDept::getId).in(SysUserDept::getUserId, userIds);
        remove(removeWrapper);
    }

}