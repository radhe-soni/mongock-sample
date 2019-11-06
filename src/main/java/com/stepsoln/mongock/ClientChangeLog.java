package com.stepsoln.mongock;

import java.time.LocalDateTime;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class ClientChangeLog {

    private static final String COLLECTION_NAME = "client";


    @ChangeSet(id= "withMongoDatabase", order = "001", author = "Mongock")
    public void changeSet1(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(getClient("MongoDatabase", LocalDateTime.now().toString())));
    }

    @ChangeSet(id= "withMongoTemplate", order = "002", author = "Mongock")
    public void changeSet2(MongoTemplate template) {
        template.insert(getClient("MongoTemplate", LocalDateTime.now().toString()), COLLECTION_NAME);
    }


    @ChangeSet(id= "withDB", order = "003", author = "Mongock")
    public void changeSet2(DB db) {
        db.getCollection(COLLECTION_NAME).insert(createDbObject(getClient("Db", LocalDateTime.now().toString())));
    }

    private DBObject createDbObject(ClientDomain client) {
        return new BasicDBObject("name", client.getName())
                .append("surname", client.getSurname());
    }

    private Document createMongoDocument(ClientDomain clientDomain) {
        return new Document()
                .append("name", clientDomain.getName())
                .append("surname", clientDomain.getSurname());
    }

    private ClientDomain getClient(String name, String surname) {
        return new ClientDomain(name, surname);
    }

}
