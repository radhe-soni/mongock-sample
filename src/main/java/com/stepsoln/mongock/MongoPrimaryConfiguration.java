package com.stepsoln.mongock;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;

@Configuration
public class MongoPrimaryConfiguration
{

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "mongodb.primary")
	public MongoProperties templateDBProperties()
	{
		return new MongoProperties();
	}

	@Primary
	@Bean(name = "mongoTemplate")
	public MongoTemplate mongoTemplate(MongoDbFactory primaryFactory)
	{
		return new MongoTemplate(primaryFactory);
	}

	@Bean
	public MongoClient mongoClient(final MongoProperties mongo)
	{
		return new MongoClient(mongo.getHost(), mongo.getPort());
	}

	@Bean
	@Primary
	public MongoDbFactory primaryFactory(MongoClient mongoClient, final MongoProperties mongo)
	{
		return new SimpleMongoDbFactory(mongoClient, mongo.getDatabase());
	}

	@Bean("mongock")
	public Mongock mongockSpringBoot(MongoClient mongoClient, MongoProperties templateDBProperties)
	{
		return new SpringMongockBuilder(mongoClient, templateDBProperties.getDatabase(),
				ClientChangeLog.class.getPackage().getName()).setLockQuickConfig().build();
	}
}
