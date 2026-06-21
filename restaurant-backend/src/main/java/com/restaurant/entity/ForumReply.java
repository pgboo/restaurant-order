package com.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("forum_reply")
public class ForumReply implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long forumId;
    private Long userId;
    private String content;
    private String addtime;
}
