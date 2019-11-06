package com.stepsoln.mongock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@EnableMongoRepositories
public class Springboot207SampleApplication
{

	@Value("${spring.data.mongodb.database}")
	private String SPRING_BOOT_MONGO_DB;

	public static void main(String[] args)
	{
		SpringApplication.run(Springboot207SampleApplication.class, args);
	}

	@Bean("mongock-spring-boot")
	public Mongock mongockSpringBoot(MongoClient mongoclient)
	{
		return new SpringMongockBuilder(mongoclient, SPRING_BOOT_MONGO_DB, ClientChangeLog.class.getPackage().getName())
				.setLockQuickConfig().build();
	}

}
