package com.sz.generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.generator.mapper.GeneratorTableColumnMapper;
import com.sz.generator.pojo.po.GeneratorTableColumn;
import com.sz.generator.service.GeneratorTableColumnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代码生成业务表字段 服务实现类
 * </p>
 *
 * @author sz
 * @since 2023-11-27
 */
@Service
public class GeneratorTableColumnServiceImpl extends ServiceImpl<GeneratorTableColumnMapper, GeneratorTableColumn> implements GeneratorTableColumnService {

    @Override
    public void batchInsert(List<GeneratorTableColumn> tableColumns) {
        saveBatch(tableColumns);
    }

    @Override
    public List<GeneratorTableColumn> getTableColumnsByTableId(Long tableId) {
        LambdaQueryWrapper<GeneratorTableColumn> wrapper = Wrappers.lambdaQuery(GeneratorTableColumn.class)
                .eq(GeneratorTableColumn::getTableId, tableId)
                .orderByAsc(GeneratorTableColumn::getSort);
        List<GeneratorTableColumn> list = list(wrapper);
        return list;
    }

    @Override
    public List<GeneratorTableColumn> getTableColumnsByTableName(Long tableId) {
        LambdaQueryWrapper<GeneratorTableColumn> wrapper = Wrappers.lambdaQuery(GeneratorTableColumn.class)
                .eq(GeneratorTableColumn::getTableId, tableId)
                .orderByAsc(GeneratorTableColumn::getSort);
        List<GeneratorTableColumn> list = list(wrapper);
        return list;
    }

    @Override
    public void updateBatchTableColumns(List<GeneratorTableColumn> columns) {
        updateBatchById(columns);
    }

    @Override
    public void remove(List<String> tableNames) {
        baseMapper.removeTableAndColumn(String.join(",", tableNames));
    }

}
