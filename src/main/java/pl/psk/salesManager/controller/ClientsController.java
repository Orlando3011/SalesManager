package pl.psk.salesManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.service.ClientService;

@Controller
public class ClientsController {
    private static final Logger LOGGER = LoggerFactory.getLogger("application");
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String ShowClientsPage(Model model) {
        model.addAttribute("clientsList", clientService.findAllClients());
        return "client/clientsList";
    }

    @GetMapping("/clientDetails/{id}")
    public String ShowClientsDetails(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("client", clientService.findClient(id));
        return "client/clientDetails";
    }

    @GetMapping("/deleteClient/{id}")
    public String deleteClient(Model model, @PathVariable(name = "id") int id){
        clientService.removeClient(id);
        model.addAttribute("clientsList", clientService.findAllClients());
        LOGGER.warn("Client deleted");
        return "redirect:/clients";
    }

    @GetMapping("/addClient")
    public String ShowAddClientPage(Model model) {
        model.addAttribute(new Client());
        return "client/addClient";
    }

    @PostMapping("/addClient")
    public String addClient(@ModelAttribute Client client, Model model) {
        clientService.addClientToRepository(client);
        model.addAttribute("clientsList", clientService.findAllClients());
        LOGGER.info("Client added");
        return "redirect:/clients";
    }

    @GetMapping("/editClient/{id}")
    public String ShowEditClientPage(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute(clientService.findClient(id));
        return "client/editClient";
    }

    @PostMapping("/editClient/{id}")
    public String editClient(Model model, @ModelAttribute Client client) {
        clientService.editClient(client);
        model.addAttribute("clientsList", clientService.findAllClients());
        LOGGER.info("Client edited");
        return "redirect:/clients";
    }
}
