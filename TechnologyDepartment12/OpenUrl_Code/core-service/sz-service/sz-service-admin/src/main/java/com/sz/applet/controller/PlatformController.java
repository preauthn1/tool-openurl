package com.sz.applet.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sz.applet.pojo.dto.SearchPlatDTO;
import com.sz.applet.pojo.po.Platform;
import com.sz.applet.pojo.po.PlatformUrl;
import com.sz.applet.service.IPlatformService;
import com.sz.applet.service.IPlatformUrlService;
import com.sz.core.common.entity.ResultMsg;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 社交平台表 前端控制器
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/platform")
@AllArgsConstructor
public class PlatformController {

    private final IPlatformService platformService;
    private final IPlatformUrlService platformUrlService;

    @PostMapping("pageList")
    public ResultMsg pageList(@RequestBody SearchPlatDTO searchPlatDTO){
        LambdaQueryWrapper<Platform> wrapper = Wrappers.lambdaQuery(Platform.class)
                .eq(searchPlatDTO.getId() != null, Platform::getId, searchPlatDTO.getId())
                .eq(searchPlatDTO.getStatus() != null, Platform::getStatus, searchPlatDTO.getStatus());

        Page<Platform> page = new Page<>(searchPlatDTO.getCurrent(), searchPlatDTO.getSize());

        return ResultMsg.success(platformService.page(page, wrapper));
    }

    @PostMapping("create")
    public ResultMsg create(@RequestBody Platform platform){
        platform.setCreateTime(LocalDateTime.now());
        LambdaQueryWrapper<Platform> wrapper = Wrappers.lambdaQuery(Platform.class)
                .eq(Platform::getName, platform.getName());

        if (platformService.count(wrapper) > 0){
            return ResultMsg.error("添加失败，该平台已存在！");
        }

        return ResultMsg.success(platformService.save(platform));
    }

    @PostMapping("update")
    public ResultMsg update(@RequestBody Platform platform){
        return ResultMsg.success(platformService.updateById(platform));
    }

    @PostMapping("editStatus")
    public ResultMsg editStatus(@RequestBody Platform platform){
        return ResultMsg.success(platformService.updateById(platform));
    }

    @GetMapping("remove")
    public ResultMsg remove(@RequestParam Integer id){
        return ResultMsg.success(platformService.removeById(id));
    }


    @GetMapping("dict")
    public ResultMsg dict(){
        return ResultMsg.success(platformService.list());
    }

    @GetMapping("getOpenUrls")
    public ResultMsg getOpenUrls(){
        LambdaQueryWrapper<Platform> wrapper = Wrappers.lambdaQuery(Platform.class).eq(Platform::getStatus, 1);
        List<Platform> list = platformService.list(wrapper);

        for (Platform platform : list){
            LambdaQueryWrapper<PlatformUrl> queryWrapper = Wrappers.lambdaQuery(PlatformUrl.class).eq(PlatformUrl::getPlatformId, platform.getId());
            List<PlatformUrl> urlList = platformUrlService.list(queryWrapper);
            platform.setPlatformUrls(urlList);
        }

        return ResultMsg.success(list);
    }



}
