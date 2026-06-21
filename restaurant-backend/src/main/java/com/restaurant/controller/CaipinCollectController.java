package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.CaipinCollect;
import com.restaurant.mapper.CaipinCollectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "菜品收藏管理")
@RestController
@RequestMapping("/caipin-collect")
public class CaipinCollectController {

    @Autowired
    private CaipinCollectMapper mapper;

    @GetMapping("/list")
    public R<PageResult<CaipinCollect>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long caipinId,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request) {
        Page<CaipinCollect> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<CaipinCollect> wrapper = new LambdaQueryWrapper<>();
        if (caipinId != null) wrapper.eq(CaipinCollect::getCaipinId, caipinId);
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            wrapper.eq(CaipinCollect::getUserId, (Long) request.getAttribute("userId"));
        } else if (userId != null) {
            wrapper.eq(CaipinCollect::getUserId, userId);
        }
        wrapper.orderByDesc(CaipinCollect::getId);
        Page<CaipinCollect> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<CaipinCollect> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody CaipinCollect collect, HttpServletRequest request) {
        collect.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            collect.setUserId((Long) request.getAttribute("userId"));
        }
        // 检查是否已收藏
        LambdaQueryWrapper<CaipinCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaipinCollect::getUserId, collect.getUserId())
               .eq(CaipinCollect::getCaipinId, collect.getCaipinId());
        if (mapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("已收藏该菜品");
        }
        mapper.insert(collect);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
