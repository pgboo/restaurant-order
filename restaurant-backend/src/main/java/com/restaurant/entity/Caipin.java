package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("caipin")
public class Caipin implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long typeId;
    private BigDecimal price;
    private String image;
    private String description;
    private String status;
    private String addtime;
}
