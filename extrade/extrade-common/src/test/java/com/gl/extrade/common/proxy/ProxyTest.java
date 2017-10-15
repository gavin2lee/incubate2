package com.gl.extrade.common.proxy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProxyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		SimpleUserService sus = new SimpleUserServiceImpl();
		int id = 100;
		
		SimpleUserService proxy = ProxyFactory.getProxy(SimpleUserService.class, sus);
		SimpleUser user = proxy.getUser(id);
		
		System.out.println(user);
	}

}
