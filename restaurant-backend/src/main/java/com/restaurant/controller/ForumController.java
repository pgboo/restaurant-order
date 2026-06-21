package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Forum;
import com.restaurant.entity.ForumReply;
import com.restaurant.mapper.ForumMapper;
import com.restaurant.mapper.ForumReplyMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(name = "论坛管理")
@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private ForumReplyMapper replyMapper;

    @GetMapping("/list")
    public R<PageResult<Forum>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request) {
        Page<Forum> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Forum> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) wrapper.like(Forum::getTitle, title);
        if (userId != null) wrapper.eq(Forum::getUserId, userId);
        wrapper.orderByDesc(Forum::getId);
        Page<Forum> result = forumMapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<Forum> getById(@PathVariable Long id) {
        return R.ok(forumMapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody Forum forum, HttpServletRequest request) {
        forum.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            forum.setUserId((Long) request.getAttribute("userId"));
        }
        forumMapper.insert(forum);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Forum forum) {
        forumMapper.updateById(forum);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        forumMapper.deleteById(id);
        // 删除关联回复
        LambdaQueryWrapper<ForumReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ForumReply::getForumId, id);
        replyMapper.delete(wrapper);
        return R.ok();
    }

    @GetMapping("/{forumId}/replies")
    public R<List<ForumReply>> getReplies(@PathVariable Long forumId) {
        LambdaQueryWrapper<ForumReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ForumReply::getForumId, forumId).orderByAsc(ForumReply::getId);
        return R.ok(replyMapper.selectList(wrapper));
    }

    @PostMapping("/{forumId}/reply")
    public R<Void> addReply(@PathVariable Long forumId, @RequestBody ForumReply reply, HttpServletRequest request) {
        reply.setForumId(forumId);
        reply.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            reply.setUserId((Long) request.getAttribute("userId"));
        }
        replyMapper.insert(reply);
        return R.ok();
    }

    @DeleteMapping("/reply/{id}")
    public R<Void> deleteReply(@PathVariable Long id) {
        replyMapper.deleteById(id);
        return R.ok();
    }
}
