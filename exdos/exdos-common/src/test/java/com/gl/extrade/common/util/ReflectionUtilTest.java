package com.gl.extrade.common.util;

import org.junit.Test;

import com.gl.extrade.common.dto.DTO;

public class ReflectionUtilTest {
    public static class TradeRequest extends DTO{

        /**
         * 
         */
        private static final long serialVersionUID = -5263305413224768204L;
        private String tradeId;
        private String lifeCycle;
        private String productCode;
        private String productName;
        private long createdTimeMillis;

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getLifeCycle() {
            return lifeCycle;
        }

        public void setLifeCycle(String lifeCycle) {
            this.lifeCycle = lifeCycle;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public long getCreatedTimeMillis() {
            return createdTimeMillis;
        }

        public void setCreatedTimeMillis(long createdTimeMillis) {
            this.createdTimeMillis = createdTimeMillis;
        }
    }

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
