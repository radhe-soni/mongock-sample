package com.stepsoln.mongock.clone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.stepsoln.mongock.MongoCloneDBConfiguration;

@Service
public class DBCloneService
{
	@Autowired
	private MongoClient mongoClient;
	@Autowired
	private MongoCloneDBConfiguration configuration;

	public void clone(String sourceDBName, String targetDBName)
	{
		MongoDatabase targetDb = getTargetDb(targetDBName);
		copyCollectionsInTargetDb(sourceDBName, targetDb, any -> Boolean.TRUE);
	}

	private MongoDatabase getTargetDb(String targetDBName)
	{
		if (mongoClient.listDatabaseNames().into(new ArrayList<>()).contains(targetDBName))
		{
			throw new CloneException("Target Database already exist.");
		}
		MongoDbFactory mongoDbFactory = configuration.getMongoDbFactory(mongoClient, targetDBName);
		MongoDatabase targetDb = mongoDbFactory.getDb(targetDBName);
		return targetDb;
	}

	private List<MongoCollection<Document>> copyCollectionsInTargetDb(String sourceDBName, MongoDatabase targetDb,
			Predicate<MongoCollection<Document>> collectionFilter)
	{
		MongoDatabase sourceDb = mongoClient.getDatabase(sourceDBName);
		return getCollections(sourceDb).stream().filter(collectionFilter)
				.map(sourceCollection -> copyCollectionInTargetDb(sourceCollection, targetDb)).collect(Collectors.toList());
	}

	private List<MongoCollection<Document>> getCollections(MongoDatabase sourceDb)
	{
		return sourceDb.listCollectionNames().map(sourceDb::getCollection).into(new ArrayList<>());
	}

	private MongoCollection<Document> copyCollectionInTargetDb(MongoCollection<Document> sourceCollection,
			MongoDatabase targetDb)
	{
		String collectionName = sourceCollection.getNamespace().getCollectionName();
		ArrayList<Document> documents = sourceCollection.find().into(new ArrayList<>());
		targetDb.createCollection(collectionName);
		MongoCollection<Document> targetCollection = targetDb.getCollection(collectionName);
		if (!CollectionUtils.isEmpty(documents))
		{
			targetCollection.insertMany(documents);
		}
		return targetCollection;
	}

	public List<MongoCollection<Document>> clone(String sourceDBName, String targetDBName,
			List<String> selectedCollectionsPrefix)
	{
		MongoDatabase targetDb = getTargetDb(targetDBName);
		Predicate<MongoCollection<Document>> collectionFilter = collectionName -> selectedCollectionsPrefix.isEmpty()
				? Boolean.TRUE
				: selectedCollectionsPrefix.contains(getPrefix(collectionName));
		return copyCollectionsInTargetDb(sourceDBName, targetDb, collectionFilter);
	}

	public String getPrefix(MongoCollection<Document> collection)
	{
		String collectionName = collection.getNamespace().getCollectionName();
		if (collectionName.indexOf("_") > 1)
			return collectionName.substring(0, collectionName.indexOf("_"));
		return collectionName;
	}

	public MongoDatabase getDatabase(String database)
	{
		return mongoClient.getDatabase(database);
	}

}
