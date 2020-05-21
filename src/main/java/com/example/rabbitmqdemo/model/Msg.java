package com.example.rabbitmqdemo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @createDate: 2019-09-13 12:10
 * @description: 消息
 */
@TableName("t_msg")
@Data
public class Msg implements Serializable {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "msg")
    private String msg;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}