package com.gl.extrade.common.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gl.extrade.common.testpojo.User;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTests {
	
	static String HOST = "192.168.0.104";
	static int PORT = 9700;
	static String DB = "test";
	
	static int MAX_SIZE = 100000;
	
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
		
		collection = db.getCollection(collectionName);
		
		Assert.assertNotNull(collection);
		
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
	
	@Test
	public void testSinglePersonInsert(){
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		ServerAddress serverAddr = new ServerAddress(HOST,PORT);
		MongoClient mongoClient = new MongoClient(serverAddr, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		
		MongoDatabase database = mongoClient.getDatabase("testdb");
		MongoCollection<Person> collection = database.getCollection("test.Persons", Person.class);
		
		collection.insertOne(buildPerson());
		
		Block<Person> printBlock = new Block<Person>() {
		    public void apply(final Person person) {
		        System.out.println(person);
		    }
		};

		collection.find().forEach(printBlock);
		
		mongoClient.close();
	}
	
	@Test
	public void testBatchPersonInsert(){
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		ServerAddress serverAddr = new ServerAddress(HOST,PORT);
		MongoClient mongoClient = new MongoClient(serverAddr, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		
		MongoDatabase database = mongoClient.getDatabase("testdb");
		MongoCollection<Person> collection = database.getCollection("batch.Persons", Person.class);
		
		List<Person> persons = new ArrayList<Person>();
		for(int i = 0; i < MAX_SIZE; i++){
			persons.add(buildPerson());
		}
		collection.insertMany(persons);
		
		final AtomicInteger count = new AtomicInteger();
		Block<Person> countBlock = new Block<Person>() {
		    public void apply(final Person person) {
		        if(person != null){
		        	count.incrementAndGet();
		        }
		    }
		};

		collection.find().forEach(countBlock);
		
		
		Assert.assertEquals(MAX_SIZE, count.get());
		
		mongoClient.close();
	}
	
	private Person buildPerson(){
		Person p = new Person();
		
		p.setAge(new Random().nextInt(150));
		p.setName("test");
		
		Address addr = new Address();
		addr.setCity("深圳");
		addr.setStreet("南新");
		addr.setZip("518000");
		
		p.setAddress(addr);
		
		return p;
	}
}
