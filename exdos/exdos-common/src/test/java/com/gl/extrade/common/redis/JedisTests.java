package com.gl.extrade.common.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.gl.extrade.common.util.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisTests {

	

	@Test
	public void testJedis(){
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("127.0.0.1:6410");
		sentinels.add("127.0.0.1:6420");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("master01",sentinels,"123456");
		
		Jedis jedis = sentinelPool.getResource();
		
		User user = new User("abc","123");
		
		String json = JsonUtils.toJsonString(user);
		
		jedis.lpush("user123", json);
		
		System.out.println("json:"+json);
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sentinelPool.close();
		sentinelPool.destroy();
	}

}
