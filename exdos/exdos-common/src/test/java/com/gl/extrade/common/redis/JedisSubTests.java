package com.gl.extrade.common.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSubTests {
	static JedisSentinelPool sentinelPool;
	
	Jedis jedis;

	@BeforeClass
	public static void init() {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("127.0.0.1:6410");
		sentinels.add("127.0.0.1:6420");
		sentinelPool = new JedisSentinelPool("master01", sentinels, "123456");
		System.out.println("set up sentinel pool");
		System.out.println("before class");
	}

	@AfterClass
	public static void close() {
		sentinelPool.close();
		sentinelPool.destroy();
		System.out.println("after class");
	}

	@Before
	public void setUp() {
		System.out.println("get jedis");
		jedis = sentinelPool.getResource();
		System.out.println("setup");
	}

	@After
	public void tearDown() {
		System.out.println("close jedis");
		jedis.close();
		System.out.println("tear down");
	}

	@Test
	public void testJedisSub() {
		LocalJedisPubSubListener listener = new LocalJedisPubSubListener();
		
		System.out.println("start to subscribe "+ LocalJedisPubSubListener.CHANNEL);
		jedis.subscribe(listener, LocalJedisPubSubListener.CHANNEL);
		
		System.out.println("exit testJedisSub");
	}
}
