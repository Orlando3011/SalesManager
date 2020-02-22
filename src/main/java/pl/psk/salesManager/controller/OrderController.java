package pl.psk.salesManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.service.ClientService;
import pl.psk.salesManager.service.OrderService;
import pl.psk.salesManager.service.ProductService;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @GetMapping("/orders")
    public String ShowOrdersPage(Model model) {
        model.addAttribute("ordersList", orderService.getAllOrders());
        return "order/orderList";
    }

    @GetMapping("/addOrder")
    public String ShowAddOrderPage(Model model) {
        model.addAttribute("order", new Order());
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        return "order/addOrder";
    }
}
