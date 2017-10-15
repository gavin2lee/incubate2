package com.gl.extrade.online.boot;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootServerWithSpring {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/server-ctx-provider.xml");
        
        System.in.read();
    }

}
