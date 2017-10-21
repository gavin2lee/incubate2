package com.gl.extrade.common.redis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gl.extrade.common.util.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

public class JedisTests {

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
		jedis = sentinelPool.getResource();
	}

	@After
	public void tearDown() {
		System.out.println("tear down");
		jedis.close();
	}

	@Test
	public void testString() {
		int maxSize = 10000;
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
		int maxSize = 10000;
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

		int maxSize = 100000;

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
		long maxSize = 100000;

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
		long maxSize = 100000;

		String key = createSortedSetKey(User.class);

		long count = jedis.zcard(key);

		String cursor = "0";

		do {
			ScanResult<Tuple> result = jedis.zscan(key, cursor);
			
			cursor = result.getStringCursor();

			System.out.println("size:" + result.getResult().size());
			System.out.println("cursor:"+cursor);

			for (Tuple t : result.getResult()) {
				jedis.zrem(key, t.getElement());
			}
			
			if(cursor.equals("0")){
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
			System.out.println("cursor:"+cursor);
			System.out.println("size:" + result.getResult().size());

			for (Tuple t : result.getResult()) {
				jedis.zrem(key, t.getElement());
			}
			
			if(cursor.equals("0")){
				break;
			}
		} while (true);

		count = jedis.zcard(key);

		assertThat("the count should be zero", count, equalTo(0L));
	}

	@Test
	public void testJedis() {
		Jedis jedis = sentinelPool.getResource();

		User user = new User("abc", "123");
		String key = "user123";

		long lenBefore = jedis.llen(key);

		String json = JsonUtils.toJsonString(user);
		System.out.println("json:" + json);

		long ret = jedis.lpushx(key, json);

		System.out.println("ret : " + ret);

		long lenAfter = jedis.llen(key);

		Assert.assertEquals("compare length", lenBefore + 1, lenAfter);

		String retJson = jedis.lindex(key, 0);

		Assert.assertNotNull("returned json should not be null", retJson);

		User retUser = JsonUtils.toObject(retJson, User.class);

		System.out.println("retUser:" + retUser);

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

}
