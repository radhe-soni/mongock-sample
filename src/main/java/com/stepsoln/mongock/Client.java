package com.stepsoln.mongock;

import java.util.List;
import javax.persistence.ElementCollection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document("client")
public class Client
{
	@Id
	private String id;
	private Name name;
	@ElementCollection
	private List<Lob> lobs;
	public static Client newInstance(Name name, List<Lob> lobs)
	{
		Client c = new Client();
		c.name = name;
		c.lobs = lobs;
		return c;
	}
}
