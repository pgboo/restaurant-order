package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("forum")
public class Forum implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long userId;
    private String content;
    private String addtime;
}
