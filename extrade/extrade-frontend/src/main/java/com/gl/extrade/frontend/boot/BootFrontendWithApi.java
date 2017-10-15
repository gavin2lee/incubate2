package com.gl.extrade.frontend.boot;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.gl.extrade.common.BizConstants;
import com.gl.extrade.spec.dto.FrontendTradeRequest;
import com.gl.extrade.spec.dto.TradeRequest;
import com.gl.extrade.spec.dto.TradeResponse;
import com.gl.extrade.spec.exception.BizException;
import com.gl.extrade.spec.service.SimpleTradeService;

public class BootFrontendWithApi {

    public static void main(String[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("simpleTradeServiceConsumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setUsername("aaa");
        registry.setPassword("bbb");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<SimpleTradeService> reference = new ReferenceConfig<SimpleTradeService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(SimpleTradeService.class);
        reference.setVersion("1.0");

        reference.setCluster("failover");
        reference.setLoadbalance("leastactive");

        // 和本地bean一样使用xxxService
        SimpleTradeService simpleTradeService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用

        TradeRequest tradeRequest = new FrontendTradeRequest();
        tradeRequest.setCreatedTimeMillis(System.currentTimeMillis());
        tradeRequest.setLifeCycle(BizConstants.TradeLifeCycle.LC_NEW);
        tradeRequest.setSystemId("721691");

        try {
            for (int i = 0; i < 1000; i++) {
                tradeRequest.setProductCode("PRD-"+ (100000+i));
                tradeRequest.setTradeId("ID-" + System.currentTimeMillis());
                System.out.println();
                System.out.println("=================");
                System.out.println("req:"+tradeRequest.toString());
                TradeResponse tradeResponse = simpleTradeService.process(tradeRequest);
                
                System.out.println("resp:" + tradeResponse);
            }
        } catch (BizException e) {
            e.printStackTrace();
        }

        System.out.println(" === client exit === ");
    }

}
