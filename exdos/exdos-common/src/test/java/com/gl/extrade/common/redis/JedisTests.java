package com.gl.extrade.common.redis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gl.extrade.common.util.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

public class JedisTests {

	static JedisSentinelPool sentinelPool;

	static String host = "192.168.0.104";
	static String sentinelPortA = "6410";
	static String sentinelPortB = "6420";

	static String authCredential = "123456";
	
	static int DEFAULT_MAX_SIZE = 10000;

	Jedis jedis;

	@BeforeClass
	public static void init() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMinIdle(1);
		config.setMaxWaitMillis(30000);
		config.setTestOnBorrow(true);
		config.setTestOnCreate(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
		
		config.setMaxTotal(1000);
		Set<String> sentinels = new HashSet<String>();
		sentinels.add(String.format("%s:%s", host, sentinelPortA));
		sentinels.add(String.format("%s:%s", host, sentinelPortB));
		sentinelPool = new JedisSentinelPool("master01", sentinels, config, 6000,authCredential);
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
		try {
			jedis = sentinelPool.getResource();

			jedis.auth(authCredential);

			System.out.println("jedis="+jedis.toString());
			
			System.out.println("to remove all keys");
			
			String retCode = jedis.flushAll();


			System.out.println("retCode="+retCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		System.out.println("tear down");
		jedis.close();
	}

	@Test
	public void testString() {
		int maxSize = DEFAULT_MAX_SIZE;

		for (int i = 0; i < maxSize; i++) {
			String key = createObjectKey((long) i, User.class);
			jedis.del(key);
			boolean exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));

		}

		for (int i = 0; i < maxSize; i++) {
			String name = "test" + i;
			String psword = String.valueOf(System.currentTimeMillis());
			User u = new User((long) i, name, psword);

			String key = createObjectKey(u);

			boolean exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));

			String ret = jedis.set(key, JsonUtils.toJsonString(u));

			assertThat("result of set", ret, notNullValue());

			exists = jedis.exists(key);
			assertThat("exists is true", exists, is(true));
		}

		for (int i = 0; i < maxSize; i++) {
			String key = createObjectKey((long) i, User.class);
			boolean exists = jedis.exists(key);

			assertThat("exists is true", exists, is(true));

			String userJson = jedis.get(key);

			assertThat("result of user", userJson, notNullValue());

			long retLong = jedis.del(key);

			assertThat("retLong", retLong, equalTo(1L));

			exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));
		}
	}

	@Test
	public void testStringExpire() {
		int maxSize = DEFAULT_MAX_SIZE;

		for (int i = 0; i < maxSize; i++) {
			String key = createObjectKey((long) i, User.class);
			jedis.del(key);
			boolean exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));

		}

		for (int i = 0; i < maxSize; i++) {
			String name = "test" + i;
			String psword = String.valueOf(System.currentTimeMillis());
			User u = new User((long) i, name, psword);

			String key = createObjectKey(u);

			boolean exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));

			String ret = jedis.set(key, JsonUtils.toJsonString(u), "NX", "PX", 500);

			assertThat("result of set", ret, notNullValue());

			exists = jedis.exists(key);
			assertThat("exists is true", exists, is(true));
		}

		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
		}

		for (int i = 0; i < maxSize; i++) {
			String key = createObjectKey((long) i, User.class);
			boolean exists = jedis.exists(key);

			assertThat("exists is false", exists, is(false));

		}
	}

	@Test
	public void testList() {
		long st = System.nanoTime();
		String listKey = createListKey(User.class);

		String ret = null;

		do {
			ret = jedis.rpop(listKey);
		} while (ret != null);

		int maxSize = DEFAULT_MAX_SIZE;

		for (int i = 0; i < maxSize; i++) {
			User u = new User((long) i, "test-" + i, String.valueOf(System.currentTimeMillis()));
			String value = JsonUtils.toJsonString(u);
			jedis.lpush(listKey, value);

		}

		long len = jedis.llen(listKey);

		assertThat("check the length of list", len, equalTo((long) maxSize));

		long ed = System.nanoTime();

		System.out.println(String.format("push %d records and elapse %d nanoseconds", maxSize, ed - st));

		do {
			ret = jedis.rpop(listKey);
		} while (ret != null);

		len = jedis.llen(listKey);
		assertThat("list has been cleared", len, equalTo(0L));

	}

	@Test
	public void testSet() {
		long maxSize = DEFAULT_MAX_SIZE;

		String key = createSetKey(User.class);

		String element = null;
		do {
			element = jedis.spop(key);
		} while (element != null);

		for (long i = 0L; i < maxSize; i++) {
			User u = new User(i, "set-" + i, String.valueOf(System.currentTimeMillis()));

			String userJson = JsonUtils.toJsonString(u);

			boolean exists = jedis.sismember(key, userJson);

			assertThat("exists is false", exists, is(false));

			jedis.sadd(key, userJson);

			exists = jedis.sismember(key, userJson);
			assertThat("exists is true", exists, is(true));
		}

		long len = jedis.scard(key);

		assertThat("check the length of set", len, equalTo(maxSize));

		do {
			element = jedis.spop(key);
		} while (element != null);

		len = jedis.scard(key);

		assertThat("check the length of set", len, equalTo(0L));

	}

	@Test
	public void testSortedSet() {
		long maxSize = DEFAULT_MAX_SIZE;

		String key = createSortedSetKey(User.class);

		long count = jedis.zcard(key);

		String cursor = "0";

		do {
			ScanResult<Tuple> result = jedis.zscan(key, cursor);

			cursor = result.getStringCursor();

			System.out.println("size:" + result.getResult().size());
			System.out.println("cursor:" + cursor);

			for (Tuple t : result.getResult()) {
				jedis.zrem(key, t.getElement());
			}

			if (cursor.equals("0")) {
				ScanResult<Tuple> result0 = jedis.zscan(key, cursor);
				for (Tuple t : result0.getResult()) {
					jedis.zrem(key, t.getElement());
				}
				break;
			}
		} while (true);

		count = jedis.zcard(key);

		assertThat("the count should be zero", count, equalTo(0L));

		for (long i = 0L; i < maxSize; i++) {
			User u = new User(i, "set-" + i, String.valueOf(System.currentTimeMillis()));

			String userJson = JsonUtils.toJsonString(u);

			jedis.zadd(key, i, userJson);
			Double score = jedis.zscore(key, userJson);

			assertThat("score should not be null", score, notNullValue());

			double subRet = Math.abs(score - i);
			assertThat("score should equals to index", subRet, lessThan(0.01D));
		}

		count = jedis.zcard(key);

		assertThat("check the length of set", count, equalTo(maxSize));

		cursor = "0";
		do {
			ScanResult<Tuple> result = jedis.zscan(key, cursor);

			cursor = result.getStringCursor();
			System.out.println("cursor:" + cursor);
			System.out.println("size:" + result.getResult().size());

			for (Tuple t : result.getResult()) {
				jedis.zrem(key, t.getElement());
			}

			if (cursor.equals("0")) {
				ScanResult<Tuple> result0 = jedis.zscan(key, cursor);
				for (Tuple t : result0.getResult()) {
					jedis.zrem(key, t.getElement());
				}
				break;
			}
		} while (true);

		count = jedis.zcard(key);

		assertThat("the count should be zero", count, equalTo(0L));
	}

	@Test
	public void testHash() {
		long maxSize = DEFAULT_MAX_SIZE;
		for (long i = 0; i < maxSize; i++) {
			User u = new User(i, "hash" + i, String.valueOf(System.currentTimeMillis()));

			String key = createHashKey(u);

			jedis.hmset(key, convertObjToStringMap(u));
		}

		for (long i = 0; i < maxSize; i++) {
			String key = createHashKey(i, User.class);
			long ret = jedis.del(key);

			assertThat("ret equals to 1", ret, equalTo(1L));
		}
	}

	@Test
	public void testHashAndStringWhenKeyConfliction() {
		long oid = DEFAULT_MAX_SIZE;
		User u = new User(oid, "user" + oid, String.valueOf(System.currentTimeMillis()));
		String uJson = JsonUtils.toJsonString(u);

		Map<String, String> hashVal = convertObjToStringMap(u);

		String strKey = createObjectKey(u);
		String hashKey = createHashKey(u);

		assertThat("strKey equals to hashKey", strKey, not(equalTo(hashKey)));

		jedis.set(strKey, uJson);
		jedis.hmset(hashKey, hashVal);

	}

	@Test
	public void testJedis() {
		User user = new User("abc", "123");
		String key = "User:123";

		long ret = jedis.del(key);
		
		System.out.println("delete result:"+ret);

		String json = JsonUtils.toJsonString(user);
		System.out.println("json:" + json);

		String retSet = jedis.set(key, json);

		System.out.println("ret : " + retSet);

		retSet = jedis.get(key);

		Assert.assertNotNull(retSet);

	}

	private String createObjectKey(User user) {
		return User.class.getSimpleName() + ":" + user.getOid();
	}

	private String createObjectKey(long oid, Class<?> clz) {
		return clz.getSimpleName() + ":" + oid;
	}

	private String createListKey(Class<?> clz) {
		return "list:" + clz.getSimpleName();
	}

	private String createSetKey(Class<?> clz) {
		return "set:" + clz.getSimpleName();
	}

	private String createSortedSetKey(Class<?> clz) {
		return "sortedSet:" + clz.getSimpleName();
	}

	private String createHashKey(User user) {
		return "hash:" + User.class.getSimpleName() + ":" + user.getOid();
	}

	private String createHashKey(long oid, Class<?> clz) {
		return "hash:" + clz.getSimpleName() + ":" + oid;
	}

	private Map<String, String> convertObjToStringMap(User u) {
		Map<String, String> objMap = new HashMap<String, String>();
		objMap.put("oid", String.valueOf(u.getOid()));
		objMap.put("uname", u.getUname());
		objMap.put("psword", u.getPsword());

		return objMap;
	}
}
