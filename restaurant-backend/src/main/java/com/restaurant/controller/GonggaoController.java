package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Gonggao;
import com.restaurant.mapper.GonggaoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "鍏憡绠＄悊")
@RestController
@RequestMapping("/gonggao")
public class GonggaoController {

    @Autowired
    private GonggaoMapper mapper;

    @GetMapping("/list")
    public R<PageResult<Gonggao>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        Page<Gonggao> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Gonggao> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) wrapper.like(Gonggao::getTitle, title);
        wrapper.orderByDesc(Gonggao::getId);
        Page<Gonggao> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/all")
    public R<java.util.List<Gonggao>> all() {
        return R.ok(mapper.selectList(new LambdaQueryWrapper<Gonggao>().orderByDesc(Gonggao::getId)));
    }

    @GetMapping("/{id}")
    public R<Gonggao> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody Gonggao entity) {
        entity.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.insert(entity);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Gonggao entity) {
        mapper.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
