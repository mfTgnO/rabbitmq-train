package com.example.rabbitmqdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rabbitmqdemo.model.Msg;
import org.springframework.stereotype.Repository;

/**
 * @createDate: 2019-09-13 12:28
 * @description:
 */
@Repository
public interface MsgMapper extends BaseMapper<Msg> {
}