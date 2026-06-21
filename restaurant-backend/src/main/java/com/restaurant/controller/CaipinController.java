package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Caipin;
import com.restaurant.mapper.CaipinMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "菜品管理")
@RestController
@RequestMapping("/caipin")
public class CaipinController {

    @Autowired
    private CaipinMapper caipinMapper;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public R<PageResult<Caipin>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String status) {
        Page<Caipin> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Caipin> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) wrapper.like(Caipin::getName, name);
        if (typeId != null) wrapper.eq(Caipin::getTypeId, typeId);
        if (status != null && !status.isEmpty()) wrapper.eq(Caipin::getStatus, status);
        wrapper.orderByDesc(Caipin::getId);
        Page<Caipin> result = caipinMapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "详情")
    @GetMapping("/{id}")
    public R<Caipin> getById(@PathVariable Long id) {
        return R.ok(caipinMapper.selectById(id));
    }

    @Operation(summary = "新增")
    @PostMapping
    public R<Void> add(@RequestBody Caipin caipin) {
        caipin.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (caipin.getStatus() == null) caipin.setStatus("下架");
        caipinMapper.insert(caipin);
        return R.ok();
    }

    @Operation(summary = "修改")
    @PutMapping
    public R<Void> update(@RequestBody Caipin caipin) {
        caipinMapper.updateById(caipin);
        return R.ok();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        caipinMapper.deleteById(id);
        return R.ok();
    }

    @Operation(summary = "上架/下架")
    @PutMapping("/status/{id}")
    public R<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Caipin caipin = new Caipin();
        caipin.setId(id);
        caipin.setStatus(status);
        caipinMapper.updateById(caipin);
        return R.ok();
    }
}
