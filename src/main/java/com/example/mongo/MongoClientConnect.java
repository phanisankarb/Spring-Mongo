package com.example.mongo;

import java.util.Iterator;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoDatabase;

public class MongoClientConnect {
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		System.out.println(mongoClient.getAddress());
		MongoDatabase mongoDB = mongoClient.getDatabase("otsi");
		mongoDB.createCollection("citizen");
		ListCollectionsIterable<Document> list =  mongoDB.listCollections();
		
		/*for (Document document : list) {
			
			
		}*/
		
		
	}
}
