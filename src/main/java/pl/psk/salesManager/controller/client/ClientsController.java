package pl.psk.salesManager.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.service.ClientService;

@Controller
public class ClientsController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String ShowClientsPage(Model model) {
        model.addAttribute("clientsList", clientService.findAllClients());
        return "client/clientsList";
    }

    @GetMapping("/deleteClient/{id}")
    public String deleteClient(Model model, @PathVariable(name = "id") int id){
        clientService.removeClient(id);
        model.addAttribute("clientsList", clientService.findAllClients());
        return "redirect:/clients";
    }

    @GetMapping("/addClient")
    public String ShowAddClientsPage(Model model) {
        model.addAttribute(new Client());
        return "client/addClient";
    }

    @PostMapping("/addClient")
    public String addClient(@ModelAttribute Client client, Model model) {
        clientService.addClientToRepository(client);
        model.addAttribute("clientsList", clientService.findAllClients());
        return "redirect:/clients";
    }

    @GetMapping("/editClient/{id}")
    public String ShowEditClientsPage(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute(clientService.findClient(id));
        return "client/editClient";
    }

    @PostMapping("/editClient/{id}")
    public String editClient(Model model, @ModelAttribute Client client) {
        clientService.editClient(client);
        model.addAttribute("clientsList", clientService.findAllClients());
        return "redirect:/clients";
    }
}
