package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysDeptClosureMapper;
import com.sz.admin.system.pojo.po.SysDeptClosure;
import com.sz.admin.system.service.SysDeptClosureService;
import com.sz.core.common.enums.CommonResponseEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 部门祖籍关系表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-03-28
 */
@Service
@RequiredArgsConstructor
public class SysDeptClosureServiceImpl extends ServiceImpl<SysDeptClosureMapper, SysDeptClosure> implements SysDeptClosureService {

    @Override
    public List<SysDeptClosure> ancestorsPath(Long nodeId) {
        LambdaQueryWrapper<SysDeptClosure> wrapper = Wrappers.lambdaQuery(SysDeptClosure.class)
                .eq(SysDeptClosure::getDescendantId, nodeId)
                .ne(SysDeptClosure::getAncestorId, nodeId)
                .gt(SysDeptClosure::getDepth, 0);
        return list(wrapper);
    }

    @Override
    public void create(Long nodeId, Long pid) {
        List<SysDeptClosure> closures = new ArrayList<>();
        List<SysDeptClosure> pathList = ancestorsPath(pid);
        for (SysDeptClosure path : pathList) {
            SysDeptClosure closure = buildClosure(path.getAncestorId(), nodeId, path.getDepth() + 1);
            closures.add(closure);
        }
        // self
        SysDeptClosure self = buildClosure(nodeId, nodeId, 0);
        // parent
        SysDeptClosure parent = buildClosure(pid, nodeId, 1);
        closures.add(self);
        closures.add(parent);
        saveBatch(closures);
    }

    @Override
    @Transactional
    public void remove(Long nodeId) {
        SysDeptClosure one = getById(nodeId);
        CommonResponseEnum.INVALID_ID.assertNull(one);
        List<SysDeptClosure> desList = descendants(nodeId);
        for (SysDeptClosure closure : desList) {
            LambdaQueryWrapper<SysDeptClosure> removeWrapper = Wrappers.lambdaQuery(SysDeptClosure.class)
                    .eq(SysDeptClosure::getDescendantId, closure.getDescendantId())
                    .eq(SysDeptClosure::getAncestorId, nodeId);
            remove(removeWrapper);
        }
        removeById(nodeId);
    }

    /**
     * 查询所有子孙节点
     *
     * @param nodeId
     * @return
     */
    public List<SysDeptClosure> descendants(Long nodeId) {
        LambdaQueryWrapper<SysDeptClosure> wrapper = Wrappers.lambdaQuery(SysDeptClosure.class)
                .ne(SysDeptClosure::getDescendantId, nodeId)
                .eq(SysDeptClosure::getAncestorId, nodeId);
        return list(wrapper);
    }

    /**
     * 查询指定祖籍节点的所有子孙节点
     *
     * @param ancestorIds
     * @return
     */
    @Override
    public List<Long> descendants(List<Long> ancestorIds) {
        if (ancestorIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysDeptClosure> wrapper = Wrappers.lambdaQuery(SysDeptClosure.class)
                .select(SysDeptClosure::getDescendantId)
                .in(SysDeptClosure::getAncestorId, ancestorIds);
        return list(wrapper).stream().map(SysDeptClosure::getDescendantId).toList();
    }

    /**
     * 移动子树
     */
    @Override
    public void move(Long nodeId, Long newNodeId) {
        if (nodeId == newNodeId)
            return;
        List<SysDeptClosure> closures = baseMapper.selectDetachTree(nodeId);
        for (SysDeptClosure closure : closures) {
            LambdaQueryWrapper<SysDeptClosure> removeWrapper = Wrappers.lambdaQuery(SysDeptClosure.class)
                    .eq(SysDeptClosure::getAncestorId, closure.getAncestorId()).eq(SysDeptClosure::getDescendantId,
                            closure.getDescendantId());
            remove(removeWrapper);
        }
        baseMapper.graft(nodeId, newNodeId);
    }

    private SysDeptClosure buildClosure(Long ancestorId, Long descendantId, Integer depth) {
        SysDeptClosure closure = new SysDeptClosure();
        closure.setAncestorId(ancestorId);
        closure.setDescendantId(descendantId);
        closure.setDepth(depth);
        return closure;
    }

}