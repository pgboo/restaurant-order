package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Danpage;
import com.restaurant.mapper.DanpageMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "鍗曢〉鏁版嵁绠＄悊")
@RestController
@RequestMapping("/danpage")
public class DanpageController {

    @Autowired
    private DanpageMapper mapper;

    @GetMapping("/list")
    public R<PageResult<Danpage>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        Page<Danpage> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Danpage> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) wrapper.like(Danpage::getTitle, title);
        wrapper.orderByDesc(Danpage::getId);
        Page<Danpage> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/all")
    public R<java.util.List<Danpage>> all() {
        return R.ok(mapper.selectList(new LambdaQueryWrapper<Danpage>().orderByDesc(Danpage::getId)));
    }

    @GetMapping("/{id}")
    public R<Danpage> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody Danpage entity) {
        entity.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.insert(entity);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Danpage entity) {
        mapper.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
