package com.stepsoln.mongock.client;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stepsoln.mongock.Client;
import com.stepsoln.mongock.ClientRepository;
import com.stepsoln.mongock.Lob;
import com.stepsoln.mongock.Name;

@Service
public class ClientService
{

	@Autowired
	private ClientRepository clientRepository;
	
	@PostConstruct
	public List<Client> getClients()
	{
		return clientRepository.findAll();
	}

	public List<Client> getClients(String name)
	{
		return clientRepository.findByName_FirstName(name);
	}
	
	public List<Name> getNames(String name)
	{
		return clientRepository.findNameByName_FirstName(name);
	}
	
	public List<Lob> getLobs(String lobName)
	{
		return clientRepository.findLobs(lobName);
	}
}
