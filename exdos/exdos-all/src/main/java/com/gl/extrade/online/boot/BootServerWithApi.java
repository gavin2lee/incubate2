package com.gl.extrade.online.boot;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.gl.extrade.online.service.impl.SimpleTradeOnlineServiceImpl;
import com.gl.extrade.spec.service.SimpleTradeService;

public class BootServerWithApi {

    public static void main(String[] args) throws IOException {
        SimpleTradeService simpleTradeService = new SimpleTradeOnlineServiceImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("simpleTradeServiceProvider");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setUsername("trade");
        registry.setPassword("123456");
        
        String sPort = System.getenv("dubboport");
        if(sPort == null){
            sPort = "28881";
        }
        
        System.out.println("port ==> " + sPort);

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(Integer.parseInt(sPort));
        protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<SimpleTradeService> service = new ServiceConfig<SimpleTradeService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(SimpleTradeService.class);
        service.setRef(simpleTradeService);
        service.setVersion("1.0");

        // 暴露及注册服务
        service.export();
        
        System.in.read();
    }

}
