package com.example.rabbitmqdemo.controller;

import com.example.rabbitmqdemo.model.Msg;
import com.example.rabbitmqdemo.service.IMsgService;
import com.example.rabbitmqdemo.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @createDate: 2019-09-13 12:26
 * @description:
 */
@RestController
@RequestMapping("/msg")
public class MsgController {
    private IMsgService iMsgService;

    public MsgController(IMsgService iMsgService) {
        this.iMsgService = iMsgService;
    }

    @GetMapping("/list")
    public JsonResult listMsg(){
        List<Msg> list = iMsgService.list();
        return new JsonResult<>(list);
    }
}