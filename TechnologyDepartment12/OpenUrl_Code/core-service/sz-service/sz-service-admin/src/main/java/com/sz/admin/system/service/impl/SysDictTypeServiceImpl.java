package com.sz.admin.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.SysDictTypeMapper;
import com.sz.admin.system.pojo.dto.sysdict.SysDictTypeAddDTO;
import com.sz.admin.system.pojo.dto.sysdict.SysDictTypeListDTO;
import com.sz.admin.system.pojo.dto.sysdict.SysDictTypeUpDTO;
import com.sz.admin.system.pojo.po.SysDictType;
import com.sz.admin.system.pojo.vo.sysdict.DictTypeVO;
import com.sz.admin.system.service.SysDictTypeService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.core.util.SysConfigUtils;
import com.sz.core.util.Utils;
import com.sz.platform.factory.DictLoaderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author sz
 * @since 2023-08-18
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final DictLoaderFactory dictLoaderFactory;

    @Override
    public void create(SysDictTypeAddDTO dto) {
        String confValue = SysConfigUtils.getConfValue("sys.dict.startNo");
        SysDictType sysDictType = BeanCopyUtils.copy(dto, SysDictType.class);
        String type = dto.getType();
        if ("system".equals(type)) { // 系统字典
            AtomicReference<Long> maxId = new AtomicReference<>(0L);
            sysDictType.setId(maxId.get() + 1);
        } else {
            AtomicReference<Long> maxId = new AtomicReference<>(0L);
            if (Utils.isNotNull(maxId) && Utils.isNotNull(maxId.get())) {
                sysDictType.setId(maxId.get() + 1);
            } else {
                sysDictType.setId(Utils.getLongVal(confValue));
            }
        }
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getTypeCode, dto.getTypeCode());
        CommonResponseEnum.EXISTS.message("typeCode已存在").assertTrue(count(wrapper) > 0);
        save(sysDictType);
    }

    @Override
    public void update(SysDictTypeUpDTO dto) {
        SysDictType sysDictType = BeanCopyUtils.springCopy(dto, SysDictType.class);
        sysDictType.setId(dto.getId());
        // 修改时的重复性效验需要排除本身
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getTypeCode, dto.getTypeCode()).ne(SysDictType::getId, dto.getId());
        CommonResponseEnum.EXISTS.message("typeCode已存在").assertTrue(count(wrapper) > 0);
        saveOrUpdate(sysDictType);
    }

    @Override
    public void remove(SelectIdsDTO dto) {
        removeByIds(dto.getIds());
    }

    @Override
    public SysDictType detail(Long id) {
        SysDictType dictType = getById(id);
        CommonResponseEnum.INVALID_ID.assertNull(dictType);
        return dictType;
    }

    @Override
    public PageResult<SysDictType> list(SysDictTypeListDTO dto) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery(SysDictType.class)
                .eq(Utils.isNotNull(dto.getTypeName()), SysDictType::getTypeName, dto.getTypeName())
                .eq(Utils.isNotNull(dto.getTypeCode()), SysDictType::getTypeCode, dto.getTypeCode())
                .orderByAsc(SysDictType::getCreateTime);
        Page<SysDictType> page = page(PageUtils.getPage(dto), wrapper);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<DictTypeVO> findDictType() {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery(SysDictType.class).orderByDesc(SysDictType::getCreateTime);
        return list(wrapper).stream().map(map -> BeanCopyUtils.copy(map, DictTypeVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<DictTypeVO> selectDictTypeOptions() {
        return dictLoaderFactory.getAllDictType();
    }

}
