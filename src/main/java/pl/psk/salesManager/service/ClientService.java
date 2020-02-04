package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clients;

    public void addClientToRepository(String firstName,
                                      String familyName,
                                      String address,
                                      String email,
                                      String phone,
                                      String bankName,
                                      String bankAccountNumber) {
        Client client = new Client();
        client.setClientDetails(firstName, familyName, address, email, phone, bankName, bankAccountNumber);
        client.setOrders(new ArrayList<>());
        client.setRegularCustomer(false);
        clients.save(client);
    }

    public List<Client> findAllClients() {
        return clients.findAll();
    }
}
