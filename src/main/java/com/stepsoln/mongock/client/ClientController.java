package com.stepsoln.mongock.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.stepsoln.mongock.Client;
import com.stepsoln.mongock.Lob;
import com.stepsoln.mongock.Name;

@RestController
public class ClientController {

	@Autowired
	public ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getClients() {
        return clientService.getClients();
    }
    @GetMapping("/names/{name}")
    public List<Name> getNames(@PathVariable String name) {
        return clientService.getNames(name);
    }
    
    @GetMapping("/lobs/{lobName}")
    public List<Lob> getLobs(@PathVariable String lobName) {
        return clientService.getLobs(lobName);
    }
}
