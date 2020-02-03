package pl.psk.salesManager.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.repository.ClientRepository;
import pl.psk.salesManager.service.ClientService;

@Controller
public class addClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/addClient")
    public String ShowAddClientsPage() {
        return "addClient";
    }

    @PostMapping("/addClient")
    public String addClient(Model model,
                            @RequestParam String firstName,
                            @RequestParam String familyName,
                            @RequestParam String address,
                            @RequestParam String email) {
    return "clients";

    }
}
