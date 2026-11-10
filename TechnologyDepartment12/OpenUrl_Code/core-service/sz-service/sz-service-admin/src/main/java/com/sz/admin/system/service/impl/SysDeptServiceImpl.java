package com.sz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysDeptLeaderMapper;
import com.sz.admin.system.mapper.SysDeptMapper;
import com.sz.admin.system.mapper.SysUserMapper;
import com.sz.admin.system.pojo.dto.sysdept.SysDeptCreateDTO;
import com.sz.admin.system.pojo.dto.sysdept.SysDeptListDTO;
import com.sz.admin.system.pojo.dto.sysdept.SysDeptUpdateDTO;
import com.sz.admin.system.pojo.po.SysDept;
import com.sz.admin.system.pojo.po.SysDeptLeader;
import com.sz.admin.system.pojo.po.SysUser;
import com.sz.admin.system.pojo.po.SysUserDept;
import com.sz.admin.system.pojo.vo.sysdept.DeptTreeVO;
import com.sz.admin.system.pojo.vo.sysdept.SysDeptLeaderVO;
import com.sz.admin.system.pojo.vo.sysdept.SysDeptVO;
import com.sz.admin.system.pojo.vo.sysdept.TotalDeptVO;
import com.sz.admin.system.service.SysDeptClosureService;
import com.sz.admin.system.service.SysDeptLeaderService;
import com.sz.admin.system.service.SysDeptService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.core.util.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-03-20
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysUserMapper userMapper;

    private final SysDeptLeaderMapper leaderMapper;

    private final SysDeptLeaderService deptLeaderService;

    private final SysDeptClosureService deptClosureService;

    @Transactional
    @Override
    public void create(SysDeptCreateDTO dto) {
        SysDept sysDept = BeanCopyUtils.springCopy(dto, SysDept.class);
        if (dto.getPid() == 0) {
            sysDept.setDeep(1);
        } else {
            LambdaQueryWrapper<SysDept> wrapper = Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getId, dto.getPid());
            CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);
            SysDept parentDept = getOne(wrapper);
            parentDept.setHasChildren("T");
            saveOrUpdate(parentDept);
            sysDept.setDeep(parentDept.getDeep() + 1);
        }
        save(sysDept);
        Long deptId = sysDept.getId();
        Long pid = dto.getPid();
        // 设置负责人
        deptLeaderService.syncLeader(deptId, dto.getLeaders());
        deptClosureService.create(deptId, pid);

    }

    @Transactional
    @Override
    public void update(SysDeptUpdateDTO dto) {
        SysDept sysDept = BeanCopyUtils.springCopy(dto, SysDept.class);
        LambdaQueryWrapper<SysDept> wrapper = Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getId, dto.getId());
        SysDept one = getOne(wrapper);
        Long oldPid = one.getPid();
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);
        if (dto.getPid() == 0) {
            sysDept.setDeep(1);
        } else {
            one.setHasChildren("T");
            saveOrUpdate(one);
            sysDept.setDeep(one.getDeep() + 1);
        }
        // 设置负责人
        deptLeaderService.syncLeader(dto.getId(), dto.getLeaders());
        saveOrUpdate(sysDept);
        // 移动树
        if (!Objects.equals(oldPid, dto.getPid())) {
            deptClosureService.move(dto.getId(), dto.getPid());
        }
    }

    @Override
    public PageResult<SysDeptVO> page(SysDeptListDTO dto) {
        Page list = page(PageUtils.getPage(dto), buildQueryWrapper(dto));
        list.setRecords(BeanCopyUtils.copyList(list.getRecords(), SysDeptVO.class));
        return PageUtils.getPageResult(list);
    }

    @Override
    public List<SysDeptVO> list(SysDeptListDTO dto) {
//        QueryWrapper wrapper = QueryWrapper.create().select(SYS_DEPT.ALL_COLUMNS, QueryMethods.groupConcat(
//                        if_(SYS_USER.DEL_FLAG.eq("F"), QueryMethods.concatWs(QueryMethods.string(":"), SYS_DEPT_LEADER.LEADER_ID, SYS_USER.NICKNAME), null_())
//
//                ).as("leader_info")).from(SYS_DEPT).leftJoin(SYS_DEPT_LEADER).on(SYS_DEPT.ID.eq(SYS_DEPT_LEADER.DEPT_ID)).leftJoin(SYS_USER)
//                .on(SYS_DEPT_LEADER.LEADER_ID.eq(SYS_USER.ID)).groupBy(SYS_DEPT.ID);
//        List<SysDeptVO> deptVOS = listAs(wrapper, SysDeptVO.class);
        List<SysDeptVO> deptVOS = baseMapper.getList();
        SysDeptVO root = TreeUtils.getRoot(SysDeptVO.class);
        List<SysDeptVO> trees = TreeUtils.buildTree(deptVOS, root);
        return trees.getFirst().getChildren();
    }

    @Override
    public void remove(SelectIdsDTO dto) {
        List<Long> ids = (List<Long>) dto.getIds();
        CommonResponseEnum.INVALID_ID.assertTrue(ids.isEmpty());
        // 根据要删除的id，获取所有子集id
        List<Long> descendants = deptClosureService.descendants(ids);
        removeByIds(descendants);
    }

    @Override
    public SysDeptVO detail(Object id) {
        SysDept sysDept = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(sysDept);
        SysDeptVO deptVO = BeanCopyUtils.copy(sysDept, SysDeptVO.class);
        // 查询指定部门的负责人
        LambdaQueryWrapper<SysDeptLeader> wrapper = Wrappers.lambdaQuery(SysDeptLeader.class)
                .select(SysDeptLeader::getLeaderId)
                .eq(SysDeptLeader::getDeptId, id);
        List<Long> leaderIds = leaderMapper.selectList(wrapper).stream().map(SysDeptLeader::getLeaderId).toList();
        deptVO.setLeaders(leaderIds);
        return deptVO;
    }

    @Override
    public List<DeptTreeVO> getDepartmentTreeWithAdditionalNodes() {
        // 获取部门树
        List<DeptTreeVO> trees = getDeptTree(null, false, true);
        // 获取所有用户数量
        long allUserCount = count();
        // 获取未设置部门的用户数量
        long unsetDeptCount = allUserCount - baseMapper.getLeftCount();

        // 创建全部节点
        DeptTreeVO all = createAllNode(allUserCount);
        // 创建未设置部门节点
        DeptTreeVO unset = createUnsetNode(unsetDeptCount);
        // 将‘全部’、‘未设置部门’节点添加到集合头部
        trees.add(0, all);
        trees.add(1, unset);
        return trees;
    }

    @NotNull
    private DeptTreeVO createUnsetNode(Long unsetDeptCount) {
        DeptTreeVO unset = new DeptTreeVO();
        unset.setId(-2L);
        unset.setName("未设置部门");
        AtomicReference<Long> total = new AtomicReference<>(0L);
        unset.setUserTotal(total.get());
        return unset;
    }

    @NotNull
    private DeptTreeVO createAllNode(Long userCount) {
        DeptTreeVO all = new DeptTreeVO();
        all.setId(-1L);
        all.setName("全部");
        all.setUserTotal(userCount);
        return all;
    }

    @Override
    public List<DeptTreeVO> getDeptTree(Integer excludeNodeId, Boolean appendRoot, Boolean needSetTotal) {
        LambdaQueryWrapper<SysDept> wrapper = Wrappers.lambdaQuery(SysDept.class).orderByAsc(SysDept::getSort);
        // 获取所有部门信息
        List<SysDept> list = list(wrapper);
        List<DeptTreeVO> deptTreeVOS = BeanCopyUtils.copyList(list, DeptTreeVO.class);
        if (needSetTotal) {
            setUseTotal(deptTreeVOS);
        }
        DeptTreeVO root = TreeUtils.getRoot(DeptTreeVO.class);
        root.setName("根部门");
        List<DeptTreeVO> trees = TreeUtils.buildTree(deptTreeVOS, root, excludeNodeId);
        if (appendRoot != null && !appendRoot) {
            if (trees.getFirst().getChildren() == null) {
                trees = new ArrayList<>();
            } else {
                trees = trees.getFirst().getChildren();
            }
        }
        return trees;
    }

    private void setUseTotal(List<DeptTreeVO> deptTreeVOS) {
        // 查询直属部门的数量
//        QueryWrapper wrapper = QueryWrapper.create()
//                .select(SYS_DEPT.ID, SYS_DEPT.NAME, QueryMethods.count(case_().when(SYS_USER.DEL_FLAG.eq("F")).then(SYS_USER_DEPT.USER_ID).end()).as("total"))
//                .from(SYS_DEPT).leftJoin(SYS_USER_DEPT).on(SYS_DEPT.ID.eq(SYS_USER_DEPT.DEPT_ID)).leftJoin(SYS_USER).on(SYS_USER_DEPT.USER_ID.eq(SYS_USER.ID))
//                .groupBy(SYS_DEPT.ID, SYS_DEPT.NAME).orderBy(SYS_DEPT.DEEP.asc()).orderBy(SYS_DEPT.SORT.asc());


        List<TotalDeptVO> totalDeptVOS = baseMapper.getLeaderDept();

        /**
         * 查询非直属部门的数量
         *
         * SELECT d.id , d.name , COUNT(ud.user_id) AS total FROM sys_dept d LEFT JOIN
         * sys_dept_closure c ON d.id = c.ancestor_id LEFT JOIN sys_user_dept ud ON
         * c.descendant_id = ud.dept_id GROUP BY d.id, d.name;
         *
         */

        Map<Long, Long> totalDeptMap = new HashMap<>();
        if (totalDeptVOS != null) {
            totalDeptMap = totalDeptVOS.stream().collect(Collectors.toMap(TotalDeptVO::getId, TotalDeptVO::getTotal));
        }
        for (DeptTreeVO treeVO : deptTreeVOS) {
            if (totalDeptMap.containsKey(treeVO.getId())) {
                treeVO.setUserTotal(totalDeptMap.get(treeVO.getId()));
            }
        }
    }

    /**
     * 查询部门leader信息-穿梭框
     *
     * @return SysDeptLeaderVO
     */
    @Override
    public SysDeptLeaderVO findSysUserDeptLeader() {
        // 查询所有的用户
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(SysUser.class);
        List<SysDeptLeaderVO.LeaderInfoVO> userInfos = userMapper.selectList(wrapper).stream().map(map -> {
            SysDeptLeaderVO.LeaderInfoVO infoVO = new SysDeptLeaderVO.LeaderInfoVO();
            infoVO.setId(map.getId());
            infoVO.setNickname(map.getNickname());
            return infoVO;
        }).collect(Collectors.toList());
        SysDeptLeaderVO leaderVO = new SysDeptLeaderVO();
        leaderVO.setLeaderInfoVOS(userInfos);
        return leaderVO;
    }

    private static LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptListDTO dto) {
        return Wrappers.lambdaQuery();
    }

}