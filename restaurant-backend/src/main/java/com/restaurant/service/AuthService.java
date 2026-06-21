package com.restaurant.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.restaurant.entity.Admin;
import com.restaurant.entity.Employee;
import com.restaurant.entity.Users;
import com.restaurant.mapper.AdminMapper;
import com.restaurant.mapper.EmployeeMapper;
import com.restaurant.mapper.UsersMapper;
import com.restaurant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> userRegister(Users users) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, users.getUsername());
        if (usersMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        users.setPassword(DigestUtil.md5Hex(users.getPassword()));
        users.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        usersMapper.insert(users);
        return doUserLogin(users.getUsername(), users.getPassword(), true);
    }

    public Map<String, Object> userLogin(String username, String password) {
        return doUserLogin(username, DigestUtil.md5Hex(password), true);
    }

    private Map<String, Object> doUserLogin(String username, String md5Password, boolean isUser) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username).eq(Users::getPassword, md5Password);
        Users user = usersMapper.selectOne(wrapper);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), "user");
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("role", "user");
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        return result;
    }

    public Map<String, Object> employeeRegister(Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        if (employeeMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        employee.setPassword(DigestUtil.md5Hex(employee.getPassword()));
        employee.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        employeeMapper.insert(employee);
        return doEmployeeLogin(employee.getUsername(), employee.getPassword(), true);
    }

    public Map<String, Object> employeeLogin(String username, String password) {
        return doEmployeeLogin(username, DigestUtil.md5Hex(password), true);
    }

    private Map<String, Object> doEmployeeLogin(String username, String md5Password, boolean isEmployee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, username).eq(Employee::getPassword, md5Password);
        Employee employee = employeeMapper.selectOne(wrapper);
        if (employee == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(employee.getId(), employee.getUsername(), "employee");
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("role", "employee");
        result.put("userId", employee.getId());
        result.put("username", employee.getUsername());
        return result;
    }

    public Map<String, Object> adminLogin(String username, String password) {
        String md5Password = DigestUtil.md5Hex(password);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username).eq(Admin::getPassword, md5Password);
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "admin");
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("role", "admin");
        result.put("userId", admin.getId());
        result.put("username", admin.getUsername());
        return result;
    }
}
