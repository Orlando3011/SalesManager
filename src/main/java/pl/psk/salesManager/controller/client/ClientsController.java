package pl.psk.salesManager.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.psk.salesManager.service.ClientService;

@Controller
public class ClientsController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String ShowClientsPage(Model model) {
        model.addAttribute("clientsList", clientService.findAllClients());
        return "clients";
    }

    @GetMapping("/addClient")
    public String ShowAddClientsPage() {
        return "addClient";
    }

    @PostMapping("/addClient")
    public String addClient(Model model,
                            @RequestParam String firstName,
                            @RequestParam String familyName,
                            @RequestParam String address,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam String bankName,
                            @RequestParam String bankAccountNumber) {
        clientService.addClientToRepository(firstName, familyName, address, email, phone, bankName, bankAccountNumber);
        model.addAttribute("clientsList", clientService.findAllClients());
        return "clients";
    }
}
