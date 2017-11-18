package com.gl.extrade.common.core;

import org.junit.Test;

public class GenericTests {
	
	public <T> T convert(Object obj) {
		return (T)obj;
	}
	
	public String concat(String target) {
		return "String:"+target;
	}

	@Test
	public void testConvert() {
		String s = "abc";
		String s0 = convert(s);
		
		
		String s1 = concat((String)convert(s));
//		String s2 = concat(convert(s));
	}
	
	
}
