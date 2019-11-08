package com.stepsoln.mongock;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

@Component
@ChangeLog
public class ClientChangeLog
{

	private static final String COLLECTION_NAME = "preference";

	@ChangeSet(id = "V000.001", order = "001", author = "Mongock")
	public void changeSet1(MongoTemplate template)
	{
		template.insert(getClient("MongoTemplate1", LocalDateTime.now().toString()), COLLECTION_NAME);
		template.insert(getClient("MongoTemplate1", LocalDateTime.now().toString()), COLLECTION_NAME);
	}

	@ChangeSet(id = "V000.002", order = "002", author = "Mongock")
	public void changeSet2(MongoTemplate template)
	{
		template.insert(getClient("MongoTemplate", LocalDateTime.now().toString()), COLLECTION_NAME);
		template.insert(getClient("MongoTemplate", LocalDateTime.now().toString()), COLLECTION_NAME);
	}

	private Document createMongoDocument(ClientDomain clientDomain)
	{
		return new Document().append("client", new Document().append("name", clientDomain.getName().getFirstName()).append("surname", clientDomain.getName().getSurname()));
	}

	private ClientDomain getClient(String name, String surname)
	{
		return ClientDomain.newInstance(new Name(name, surname), Arrays.asList(new Lob("loan"), new Lob("anuity"), new Lob("insurance")));
	}

}
