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

public class JedisPubTests {
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
		System.out.println("setup");
		
		System.out.println("create jedis");
		jedis = sentinelPool.getResource();
		
		
	}

	@After
	public void tearDown() {
		System.out.println("tear down");
		System.out.println("close jedis");
		jedis.close();
	}

	@Test
	public void testJedisPub() {
		String channel = LocalJedisPubSubListener.CHANNEL;
		
		String pubStr = null;
		String pubStrBase = "NOTICE";
		for(int i = 0; i<= 10; i++){
			pubStr = String.format("%s-%d", pubStrBase, i);
			System.out.println("pubStr:"+ pubStr);
			
			jedis.publish(channel, pubStr);
		}
		
		System.out.println("pub "+ LocalJedisPubSubListener.BYE);
		
		jedis.publish(channel, LocalJedisPubSubListener.BYE);
		
		System.out.println("exit testJedisPub");
	}
}
