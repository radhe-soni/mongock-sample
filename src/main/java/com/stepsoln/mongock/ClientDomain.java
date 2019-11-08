package com.stepsoln.mongock;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class ClientDomain {

	private Name name;
	private List<Lob> lobs;
	public static ClientDomain newInstance(Name name, List<Lob> lobs)
	{
		ClientDomain c = new ClientDomain();
		c.name = name;
		c.lobs = lobs;
		return c;
	}
}
