package com.gl.extrade.frontend.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gl.extrade.frontend.service.SimpleTradeFrontendService;

public class BootClientWithSpring {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/client-ctx-consumer.xml");
        
        SimpleTradeFrontendService frontend = ctx.getBean(SimpleTradeFrontendService.class); 
        
        String requestBody = "body";
        String responseBody = frontend.trade(requestBody);
        
        System.out.println(responseBody);
    }

}
