package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.CaipinComment;
import com.restaurant.mapper.CaipinCommentMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "菜品评价管理")
@RestController
@RequestMapping("/caipin-comment")
public class CaipinCommentController {

    @Autowired
    private CaipinCommentMapper mapper;

    @GetMapping("/list")
    public R<PageResult<CaipinComment>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long caipinId,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request) {
        Page<CaipinComment> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<CaipinComment> wrapper = new LambdaQueryWrapper<>();
        if (caipinId != null) wrapper.eq(CaipinComment::getCaipinId, caipinId);
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            wrapper.eq(CaipinComment::getUserId, (Long) request.getAttribute("userId"));
        } else if (userId != null) {
            wrapper.eq(CaipinComment::getUserId, userId);
        }
        wrapper.orderByDesc(CaipinComment::getId);
        Page<CaipinComment> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<CaipinComment> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody CaipinComment comment, HttpServletRequest request) {
        comment.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            comment.setUserId((Long) request.getAttribute("userId"));
        }
        mapper.insert(comment);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody CaipinComment comment) {
        mapper.updateById(comment);
        return R.ok();
    }

    @PutMapping("/reply/{id}")
    public R<Void> reply(@PathVariable Long id, @RequestParam String reply) {
        CaipinComment comment = new CaipinComment();
        comment.setId(id);
        comment.setReply(reply);
        mapper.updateById(comment);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
