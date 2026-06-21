package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.GonggaoType;
import com.restaurant.mapper.GonggaoTypeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "鍏憡绫诲瀷绠＄悊")
@RestController
@RequestMapping("/gonggao-type")
public class GonggaoTypeController {

    @Autowired
    private GonggaoTypeMapper mapper;

    @GetMapping("/list")
    public R<PageResult<GonggaoType>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        Page<GonggaoType> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<GonggaoType> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) wrapper.like(GonggaoType::getName, name);
        wrapper.orderByDesc(GonggaoType::getId);
        Page<GonggaoType> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/all")
    public R<java.util.List<GonggaoType>> all() {
        return R.ok(mapper.selectList(null));
    }

    @GetMapping("/{id}")
    public R<GonggaoType> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody GonggaoType entity) {
        entity.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.insert(entity);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody GonggaoType entity) {
        mapper.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
