package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.common.PageResult;
import com.restaurant.common.R;
import com.restaurant.entity.CaipinOrder;
import com.restaurant.mapper.CaipinOrderMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "菜品订单管理")
@RestController
@RequestMapping("/caipin-order")
public class CaipinOrderController {

    @Autowired
    private CaipinOrderMapper mapper;

    @GetMapping("/list")
    public R<PageResult<CaipinOrder>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request) {
        Page<CaipinOrder> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<CaipinOrder> wrapper = new LambdaQueryWrapper<>();
        if (orderNo != null && !orderNo.isEmpty()) wrapper.like(CaipinOrder::getOrderNo, orderNo);
        if (status != null && !status.isEmpty()) wrapper.eq(CaipinOrder::getStatus, status);
        // 如果是用户角色，只查询自己的订单
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            Long currentUserId = (Long) request.getAttribute("userId");
            wrapper.eq(CaipinOrder::getUserId, currentUserId);
        } else if (userId != null) {
            wrapper.eq(CaipinOrder::getUserId, userId);
        }
        wrapper.orderByDesc(CaipinOrder::getId);
        Page<CaipinOrder> result = mapper.selectPage(pageParam, wrapper);
        return R.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @GetMapping("/{id}")
    public R<CaipinOrder> getById(@PathVariable Long id) {
        return R.ok(mapper.selectById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody CaipinOrder order, HttpServletRequest request) {
        order.setOrderNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
        order.setAddtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (order.getStatus() == null) order.setStatus("待处理");
        // 用户下单时自动设置userId
        String role = (String) request.getAttribute("role");
        if ("user".equals(role)) {
            order.setUserId((Long) request.getAttribute("userId"));
        }
        mapper.insert(order);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody CaipinOrder order) {
        mapper.updateById(order);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return R.ok();
    }

    @GetMapping("/report")
    public R<Map<String, Object>> report() {
        List<CaipinOrder> all = mapper.selectList(null);
        BigDecimal totalAmount = all.stream()
                .map(CaipinOrder::getTotalPrice)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> result = Map.of(
                "totalOrders", all.size(),
                "totalAmount", totalAmount,
                "pendingCount", all.stream().filter(o -> "待处理".equals(o.getStatus())).count(),
                "completedCount", all.stream().filter(o -> "已完成".equals(o.getStatus())).count()
        );
        return R.ok(result);
    }
}
