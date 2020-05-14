package pl.psk.salesManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.psk.salesManager.service.InvoiceService;
import pl.psk.salesManager.service.OrderService;

import java.io.IOException;

@Controller
public class InvoiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger("application");
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    OrderService orderService;

    @GetMapping("invoice/{orderId}")
    public String generateInvoice(Model model, @PathVariable("orderId") int id) throws IOException {
        invoiceService.generateInvoice(id);
        model.addAttribute(orderService.findAllOrders());
        LOGGER.info("Invoice generated");
        return "redirect:/orders";
    }
}

