package com.restaurant.controller;

import com.restaurant.common.R;
import com.restaurant.entity.Employee;
import com.restaurant.entity.Users;
import com.restaurant.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/user/login")
    public R<Map<String, Object>> userLogin(@RequestBody Map<String, String> params) {
        return R.ok(authService.userLogin(params.get("username"), params.get("password")));
    }

    @Operation(summary = "用户注册")
    @PostMapping("/user/register")
    public R<Map<String, Object>> userRegister(@RequestBody Users users) {
        return R.ok(authService.userRegister(users));
    }

    @Operation(summary = "员工登录")
    @PostMapping("/employee/login")
    public R<Map<String, Object>> employeeLogin(@RequestBody Map<String, String> params) {
        return R.ok(authService.employeeLogin(params.get("username"), params.get("password")));
    }

    @Operation(summary = "员工注册")
    @PostMapping("/employee/register")
    public R<Map<String, Object>> employeeRegister(@RequestBody Employee employee) {
        return R.ok(authService.employeeRegister(employee));
    }

    @Operation(summary = "管理员登录")
    @PostMapping("/admin/login")
    public R<Map<String, Object>> adminLogin(@RequestBody Map<String, String> params) {
        return R.ok(authService.adminLogin(params.get("username"), params.get("password")));
    }
}
