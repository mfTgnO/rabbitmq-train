package com.example.rabbitmqdemo.controller.aspectj;

public aspect MyAspectJDemo {

    pointcut recordLog():call(* HelloWord.sayHello(..));
    pointcut authCheck():call(* HelloWord.sayHello(..));
    before():authCheck(){
        System.out.println("sayHello方法执行前验证权限");
    }

    after():recordLog(){
        System.out.println("sayHello方法执行后记录日志");
    }
}
