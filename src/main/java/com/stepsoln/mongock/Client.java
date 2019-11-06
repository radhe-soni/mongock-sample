package com.stepsoln.mongock;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document("client")
@NoArgsConstructor
@AllArgsConstructor
public class Client
{
	private String name;
	private String surname;
}
