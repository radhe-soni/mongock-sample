package com.stepsoln.mongock;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;

@Configuration
public class MongoCloneDBConfiguration
{

	private Map<String, MongoDbFactory> mongoDBFactoryCache = new HashMap<>();
	
	@Bean(name="mongodb-factory-clone")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MongoDbFactory mongoDBFactory(MongoClient mongoClient, String database)
	{
		return new SimpleMongoDbFactory(mongoClient, database);
	}
	
	@Bean(name = "mongo-template-clone")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDBFactory)
	{
		return new MongoTemplate(mongoDBFactory);
	}
	
	@Bean("mongock-clone")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Mongock mongockSpringBoot(MongoClient mongoClient, String database)
	{
		return new SpringMongockBuilder(mongoClient, database,
				ClientChangeLog.class.getPackage().getName()).setLockQuickConfig().build();
	}

	
	public MongoDbFactory getMongoDbFactory(MongoClient mongoClient, String targetDBName)
	{
		return mongoDBFactoryCache.computeIfAbsent(targetDBName, key -> mongoDBFactory(mongoClient, targetDBName));
	}
}
