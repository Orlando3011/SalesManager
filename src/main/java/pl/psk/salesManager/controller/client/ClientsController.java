package pl.psk.salesManager.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.psk.salesManager.repository.ClientRepository;

@Controller
public class ClientsController {
    @Autowired
    private ClientRepository clients;

    @GetMapping("/clients")
    public String ShowClientsPage(Model model) {
        model.addAttribute("clients", clients.findAll());
        return "clients";
    }
}
