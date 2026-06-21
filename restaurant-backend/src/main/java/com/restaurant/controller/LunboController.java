package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Lunbo;
import com.restaurant.mapper.LunboMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "杞挱鍥剧鐞? -SearchField ")
@RestController
@RequestMapping("/lunbo")
public class LunboController {

    @Autowired
    private LunboMapper mapper;

    @GetMapping("/list")
    public R<PageResult<Lunbo>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        Page<Lunbo> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Lunbo> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) wrapper.like(Lunbo::getTitle, title);
        wrapper.orderByDesc(Lunbo::getId);
        Page<Lunbo> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/all")
    public R<java.util.List<Lunbo>> all() {
        return R.ok(mapper.selectList(new LambdaQueryWrapper<Lunbo>().orderByDesc(Lunbo::getId)));
    }

    @GetMapping("/{id}")
    public R<Lunbo> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody Lunbo entity) {
        entity.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.insert(entity);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Lunbo entity) {
        mapper.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
