package pl.psk.salesManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.psk.salesManager.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String ShowOrdersPage(Model model) {
        model.addAttribute("ordersList", orderService.getAllOrders());
        return "order/orderList";
    }
}
