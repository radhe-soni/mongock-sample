package com.stepsoln.mongock.clone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class CollectionCloneService
{

	@Autowired
	private DBCloneService dbCloneService;

	public List<MongoCollection<Document>> clone(String database, String targetCollectionPrefix,
			String sourceCollectionPrefix)
	{
		Predicate<MongoCollection<Document>> collectionFilter = collection -> sourceCollectionPrefix
				.equals(dbCloneService.getPrefix(collection));
		MongoDatabase sourceDb = dbCloneService.getDatabase(database);
		return getCollections(sourceDb).stream().filter(collectionFilter)
				.map(sourceCollection -> cloneCollection(targetCollectionPrefix, sourceDb, sourceCollection))
				.collect(Collectors.toList());
	}

	private List<MongoCollection<Document>> getCollections(MongoDatabase sourceDb)
	{
		return sourceDb.listCollectionNames().map(sourceDb::getCollection).into(new ArrayList<>());
	}

	private MongoCollection<Document> cloneCollection(String targetCollectionPrefix, MongoDatabase sourceDb,
			MongoCollection<Document> sourceCollection)
	{
		String sourceCollectionName = sourceCollection.getNamespace().getCollectionName();
		String targetCollectionName = getTargetCollectionName(targetCollectionPrefix, sourceCollectionName);
		/*
		 * if (getCollections(sourceDb).contains(targetDBName)) { throw new
		 * CloneException("Target Database already exist."); }
		 */
		sourceDb.createCollection(targetCollectionName);
		MongoCollection<Document> targetCollection = sourceDb.getCollection(targetCollectionName);
		ArrayList<Document> documents = sourceCollection.find().into(new ArrayList<>());
		if (!CollectionUtils.isEmpty(documents))
		{
			targetCollection.insertMany(documents);
		}
		return targetCollection;
	}

	private String getTargetCollectionName(String targetCollectionPrefix, String sourceCollectionName)
	{
		String targetCollectionName = targetCollectionPrefix + "_" + sourceCollectionName;
		if (sourceCollectionName.indexOf("_") > 1)
			targetCollectionName = targetCollectionPrefix + "_"
					+ sourceCollectionName.substring(sourceCollectionName.indexOf("_"), sourceCollectionName.length());
		return targetCollectionName;
	}
}
