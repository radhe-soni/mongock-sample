package com.stepsoln.mongock;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService
{

	@Autowired
	private ClientRepository clientRepository;
	
	@PostConstruct
	public void getClients()
	{
		if(clientRepository.findAll().isEmpty())
		{
			clientRepository.save(new Client("Radhe", LocalDateTime.now().toString()));
		}
	}
}
