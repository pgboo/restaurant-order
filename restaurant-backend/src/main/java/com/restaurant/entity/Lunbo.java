package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("lunbo")
public class Lunbo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String image;
    private String url;
    private String addtime;
}
