package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Feedback;
import com.restaurant.mapper.FeedbackMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "留言反馈管理")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackMapper mapper;

    @GetMapping("/list")
    public R<PageResult<Feedback>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        Page<Feedback> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            wrapper.eq(Feedback::getUserId, (Long) request.getAttribute("userId"));
        }
        wrapper.orderByDesc(Feedback::getId);
        Page<Feedback> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<Feedback> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody Feedback feedback, HttpServletRequest request) {
        feedback.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            feedback.setUserId((Long) request.getAttribute("userId"));
        }
        mapper.insert(feedback);
        return R.ok();
    }

    @PutMapping("/reply/{id}")
    public R<Void> reply(@PathVariable Long id, @RequestParam String reply) {
        Feedback feedback = new Feedback();
        feedback.setId(id);
        feedback.setReply(reply);
        mapper.updateById(feedback);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
