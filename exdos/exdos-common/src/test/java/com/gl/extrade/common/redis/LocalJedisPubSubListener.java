package com.gl.extrade.common.redis;

import redis.clients.jedis.JedisPubSub;

public class LocalJedisPubSubListener extends JedisPubSub {
	
	public static final String CHANNEL = "test01";

	public static final String BYE = "bye";

	@Override
	public void onMessage(String channel, String message) {
		
		System.out.println(String.format("%s - onMessage - channel:%s,message:%s", Thread.currentThread().getName(), channel, message));

		if (BYE.equals(message)) {
			System.out.println("try to unsubscribe");
			this.unsubscribe();
		}
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out
				.println(String.format("OnSubscribe - channels:%s,subscribedChannels:%d", channel, subscribedChannels));
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(String.format("onUnsubscribe - channel:%s,subscribedChannels:%d", channel,subscribedChannels));
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPSubscribe(pattern, subscribedChannels);
	}

	@Override
	public void onPong(String pattern) {
		// TODO Auto-generated method stub
		super.onPong(pattern);
	}

}
