package com.sz.applet.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sz.applet.pojo.dto.SearchPlatDTO;
import com.sz.applet.pojo.po.PlatformUrl;
import com.sz.applet.service.IPlatformUrlService;
import com.sz.core.common.entity.ResultMsg;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 跳转链接 前端控制器
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/platformUrl")
@AllArgsConstructor
public class PlatformUrlController {

    private final IPlatformUrlService platformUrlService;

    @PostMapping("pageList")
    public ResultMsg pageList(@RequestBody SearchPlatDTO searchPlatDTO){
        return ResultMsg.success(platformUrlService.pageList(searchPlatDTO));
    }

    @PostMapping("create")
    public ResultMsg create(@RequestBody PlatformUrl platformUrl){
        LambdaQueryWrapper<PlatformUrl> wrapper = Wrappers.lambdaQuery(PlatformUrl.class).eq(PlatformUrl::getPlatformId, platformUrl.getPlatformId())
                .eq(PlatformUrl::getOpenUrl, platformUrl.getOpenUrl());

        if (platformUrlService.count(wrapper) > 0){
            return ResultMsg.error("添加失败，该平台的链接已存在！");
        }

        platformUrl.setCreateTime(LocalDateTime.now());
        return ResultMsg.success(platformUrlService.save(platformUrl));
    }

    @PostMapping("update")
    public ResultMsg update(@RequestBody PlatformUrl platformUrl){
        LambdaQueryWrapper<PlatformUrl> wrapper = Wrappers.lambdaQuery(PlatformUrl.class).eq(PlatformUrl::getPlatformId, platformUrl.getPlatformId())
                .eq(PlatformUrl::getOpenUrl, platformUrl.getOpenUrl());

        if (platformUrlService.count(wrapper) > 0){
            return ResultMsg.error("编辑失败，该平台的链接已存在！");
        }
        return ResultMsg.success(platformUrlService.updateById(platformUrl));
    }

    @GetMapping("remove")
    public ResultMsg remove(@RequestParam Integer id){
        return ResultMsg.success(platformUrlService.removeById(id));
    }


}
