package com.restaurant.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.Employee;
import com.restaurant.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "员工管理")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeMapper mapper;

    @GetMapping("/list")
    public R<PageResult<Employee>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String name) {
        Page<Employee> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) wrapper.like(Employee::getUsername, username);
        if (name != null && !name.isEmpty()) wrapper.like(Employee::getName, name);
        wrapper.orderByDesc(Employee::getId);
        Page<Employee> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PutMapping
    public R<Void> update(@RequestBody Employee employee) {
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            employee.setPassword(DigestUtil.md5Hex(employee.getPassword()));
        } else {
            employee.setPassword(null);
        }
        mapper.updateById(employee);
        return R.ok();
    }

    @PutMapping("/profile")
    public R<Void> updateProfile(@RequestBody Employee employee) {
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            employee.setPassword(DigestUtil.md5Hex(employee.getPassword()));
        } else {
            employee.setPassword(null);
        }
        mapper.updateById(employee);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }
}
