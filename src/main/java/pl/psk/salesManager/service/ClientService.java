package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    ClientRepository clients;

    public void addClientToRepository(Client client) {
        clients.save(client);
    }
}
