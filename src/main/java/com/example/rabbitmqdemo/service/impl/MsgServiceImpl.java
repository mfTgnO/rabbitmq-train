package com.example.rabbitmqdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rabbitmqdemo.mapper.MsgMapper;
import com.example.rabbitmqdemo.model.Msg;
import com.example.rabbitmqdemo.service.IMsgService;
import org.springframework.stereotype.Service;

/**
 * @createDate: 2019-09-13 12:27
 * @description:
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {
    private MsgMapper msgMapper;

    public MsgServiceImpl(MsgMapper msgMapper) {
        this.msgMapper = msgMapper;
    }
}