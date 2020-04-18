package pl.psk.salesManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.psk.salesManager.service.InvoiceService;
import pl.psk.salesManager.service.OrderService;

import java.io.FileNotFoundException;
import java.text.ParseException;

@Controller
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    OrderService orderService;

    @GetMapping("invoice/{orderId}")
    public String generateInvoice(Model model, @PathVariable("orderId") int id) throws FileNotFoundException {
        invoiceService.generateInvoice(id);
        model.addAttribute(orderService.findAllOrders());
        return "redirect:/orders";
    }
}

