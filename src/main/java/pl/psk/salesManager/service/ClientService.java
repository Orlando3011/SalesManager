package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clients;

    public void addClientToRepository(Client client) {
        client.setClientFullName(client.getFirstName() + " " + client.getFamilyName());
        client.setOrders(new ArrayList<>());
        client.setRegularCustomer(false);
        clients.save(client);
    }

    public List<Client> findAllClients() {
        return clients.findAll();
    }

    public void removeClient(int clientId) {
        clients.delete(clients.findById(clientId));
    }

    public Client findClient(int id) {
        return clients.findById(id);
    }

    public void editClient(Client client) {
        clients.save(client);
    }
}
