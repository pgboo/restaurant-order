package com.restaurant.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Admin;
import com.restaurant.mapper.AdminMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "管理员管理")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public R<PageResult<Admin>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        Page<Admin> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like(Admin::getUsername, username);
        }
        wrapper.orderByDesc(Admin::getId);
        Page<Admin> result = adminMapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "详情")
    @GetMapping("/{id}")
    public R<Admin> getById(@PathVariable Long id) {
        return R.ok(adminMapper.selectById(id));
    }

    @Operation(summary = "新增")
    @PostMapping
    public R<Void> add(@RequestBody Admin admin) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, admin.getUsername());
        if (adminMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        admin.setPassword(DigestUtil.md5Hex(admin.getPassword()));
        admin.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        adminMapper.insert(admin);
        return R.ok();
    }

    @Operation(summary = "修改")
    @PutMapping
    public R<Void> update(@RequestBody Admin admin) {
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(DigestUtil.md5Hex(admin.getPassword()));
        } else {
            admin.setPassword(null);
        }
        adminMapper.updateById(admin);
        return R.ok();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        adminMapper.deleteById(id);
        return R.ok();
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("/profile")
    public R<Void> updateProfile(@RequestBody Admin admin) {
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(DigestUtil.md5Hex(admin.getPassword()));
        } else {
            admin.setPassword(null);
        }
        adminMapper.updateById(admin);
        return R.ok();
    }
}
