package com.gl.extrade.common.util;

import java.util.ArrayList;
import java.util.List;

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
        
        private int age;
        
        private String [] names;
        
        private int [] ages;

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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String[] getNames() {
			return names;
		}

		public void setNames(String[] names) {
			this.names = names;
		}

		public int[] getAges() {
			return ages;
		}

		public void setAges(int[] ages) {
			this.ages = ages;
		}
        
        
    }

    @Test
    public void testToString() {
        TradeRequest req = new TradeRequest();
        req.setLifeCycle("NEW");
        req.setProductCode("dummy");
        
        req.setProductCode("000000");
        req.setCreatedTimeMillis(System.currentTimeMillis());
        
        req.setAge(100);
        req.setAges(new int[]{100,200,300});
        req.setNames(new String[]{"aaa","bbb"});
        
        System.out.println(req.toString());
    }

    @Test
    public void testToString0() {
        TradeRequest req = new TradeRequest();
        req.setLifeCycle("NEW");
        req.setProductCode("dummy");
        
        req.setProductCode("000000");
        req.setCreatedTimeMillis(System.currentTimeMillis());
        
        req.setAge(100);
        req.setAges(new int[]{100,200,300});
        req.setNames(new String[]{"aaa","bbb"});
        
        System.out.println(ReflectionUtil.toString0(req.getClass(), req));
    }
    
    @Test
    public void testToString0List() {
    	
    	List<Integer> nums = new ArrayList<Integer>(){
    		{
    			add(1);
    			add(2);
    			add(3);
    			add(4);
    			add(5);
    		}
    	};
    	
    	System.out.println(ReflectionUtil.toString0(nums.getClass(), nums));
    }

}
