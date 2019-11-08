package com.stepsoln.mongock;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends MongoRepository<Client, String>
{

	@Query(value="{'lobs.lobName': '?0'}", fields="{lobs:1}}")
	List<Client> findByName_FirstName(String name);
	List<Name> findNameByName_FirstName(String name);
	@Query(value="{'lobs.lobName': '?0'}", fields="{lobs:1}}")
	List<Lob> findLobs(String name);

}
