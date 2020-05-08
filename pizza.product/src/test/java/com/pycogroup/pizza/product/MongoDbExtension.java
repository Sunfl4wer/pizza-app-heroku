package com.pycogroup.pizza.product;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.MongoClient;

public class MongoDbExtension implements BeforeTestExecutionCallback {

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
    MongoClient mongoClient = applicationContext.getBean(MongoClient.class);
    System.out.println("mongoClient: " + (mongoClient != null));
  }
}
