package com.pycogroup.pizza.product.database;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DatabaseTest {

  private MongoClient mongo;
  private MongoDatabase db;

  @BeforeEach
  public void setup() throws Exception {
    
    mongo = new MongoClient("localhost", 27017);
    db = mongo.getDatabase("pizza");

    MongoCollection<BasicDBObject> testCollection = db.getCollection("testCollection", BasicDBObject.class);

    if (testCollection == null) {
      db.createCollection("testCollection");
      testCollection = db.getCollection("testCollection", BasicDBObject.class);
    }
  }

  @AfterEach
  public void tearDown() throws Exception {
    
    db.getCollection("testCollection").drop();
  }

  @Test
  public void collectionTest() {
    
    // given
    MongoCollection<BasicDBObject> testCollection = db.getCollection("testCollection", BasicDBObject.class);

    // when
    testCollection.insertOne(new BasicDBObject("testDoc", new Date()));

    // then
    Assertions.assertEquals(1L, testCollection.countDocuments());
  }
}
