package com.gl.extrade.common.util;

import org.junit.Test;

import com.gl.extrade.spec.dto.TradeRequest;

public class ReflectionUtilTest {

    @Test
    public void testToString() {
        TradeRequest req = new TradeRequest();
        req.setLifeCycle("NEW");
        req.setProductCode("dummy");
        
        req.setProductCode("000000");
        req.setCreatedTimeMillis(System.currentTimeMillis());
        
        System.out.println(req.toString());
    }

    @Test
    public void testToString0() {
        TradeRequest req = new TradeRequest();
        req.setLifeCycle("NEW");
        req.setProductCode("dummy");
        
        req.setProductCode("000000");
        req.setCreatedTimeMillis(System.currentTimeMillis());
        
        System.out.println(ReflectionUtil.toString0(req.getClass(), req));
    }

}
