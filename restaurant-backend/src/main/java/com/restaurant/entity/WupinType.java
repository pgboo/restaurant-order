package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("wupin_type")
public class WupinType implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String addtime;
}
