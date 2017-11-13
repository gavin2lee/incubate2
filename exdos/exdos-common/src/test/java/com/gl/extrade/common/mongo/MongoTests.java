package com.gl.extrade.common.mongo;

import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gl.extrade.common.testpojo.User;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTests {
	
	static String HOST = "192.168.0.104";
	static int PORT = 9700;
	static String DB = "test";
	
	MongoClient client;
	MongoDatabase db;

	@Before
	public void setUp(){
		client = new MongoClient(HOST,PORT);
		db = client.getDatabase(DB);
		
		Assert.assertNotNull(db);
	}
	
	@After
	public void tearDown(){
		if(client != null){
			client.close();
		}
	}
	
	@Test
	public void testSimpleScenario(){
		String collectionName = "testCollection0";
		MongoCollection<Document> collection = db.getCollection(collectionName);
		
//		Assert.assertNull(collection);
		if(collection != null){
			collection.drop();
		}
		
		db.createCollection(collectionName);
		collection = db.getCollection(collectionName);
		
		long count = collection.count();
		
		Assert.assertEquals(0L, count);
		
		User u = new User(1000L,"Jim","123456");
		
		Document userDoc = new Document("_id",u.getOid()).append("name", u.getUname()).append("psword", u.getPsword());
		
		collection.insertOne(userDoc);
		
		
		collection = db.getCollection(collectionName);
		count = collection.count();
		Assert.assertEquals(1L, count);
		
		
	}
}
