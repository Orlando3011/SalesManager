package pl.psk.salesManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.service.ClientService;
import pl.psk.salesManager.service.OrderService;
import pl.psk.salesManager.service.ProductService;

import java.util.List;

@Controller
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger("application");
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @GetMapping("/orders")
    public String ShowOrdersPage(Model model) {
        model.addAttribute("ordersList", orderService.findAllOrders());
        return "order/orderList";
    }

    @GetMapping("/addOrder")
    public String ShowAddOrderPage(Model model) {
        model.addAttribute("order", new Order());
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        return "order/addOrder";
    }

    @PostMapping("/addOrder")
    public String AddNewOrder(@ModelAttribute Order order, Model model) {
        orderService.addOrderToRepository(order);
        model.addAttribute("ordersList", orderService.findAllOrders());
        LOGGER.info("Order added");
        return "redirect:/orders";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(Model model, @PathVariable(name = "id") int id){
        orderService.removeOrder(id);
        model.addAttribute("ordersList", orderService.findAllOrders());
        LOGGER.warn("Order removed");
        return "redirect:/orders";
    }

    @GetMapping("/editOrder/{id}")
    public String ShowEditOrderPage(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("order", orderService.findOrder(id));
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("productsList", productService.findAllProducts());
        model.addAttribute("orderedProductsList", orderService.findOrder(id).getProductsOrdered() );
        return "order/editOrder";
    }

    @PostMapping("/editOrder/{id}")
    public String editOrder(Model model, @PathVariable("id") int id) {
        orderService.editOrder(orderService.findOrder(id));
        model.addAttribute("ordersList", orderService.findAllOrders());
        LOGGER.info("Order modified");
        return "redirect:/orders";
    }

    @GetMapping("/orderProduct/{orderId}/product/{productId}")
    public String addProductToOrder(Model model, @PathVariable(name = "orderId") int orderId, @PathVariable(name = "productId") int productId) {
        orderService.addProductToOrder(productService.findProductById(productId), orderService.findOrder(orderId), 1);
        return "redirect:/editOrder/{orderId}";
    }

    @GetMapping("/cancelProduct/{soldProductId}/order/{orderId}")
    public String removeOneProduct(Model model, @PathVariable(name = "soldProductId") int soldProductId, @PathVariable(name = "orderId") int orderId) {
        orderService.removeOneProduct(orderService.findSoldProduct(soldProductId), 1);
        return "redirect:/editOrder/{orderId}";
    }

    @GetMapping("/cancelAllProduct/{soldProductId}/order/{orderId}")
    public String removeAllProduct(Model model, @PathVariable(name = "soldProductId") int soldProductId, @PathVariable(name = "orderId") int orderId) {
        orderService.removeAllProduct(orderService.findSoldProduct(soldProductId));
        return "redirect:/editOrder/{orderId}";
    }

    @GetMapping("/orderDetails/{id}")
    public String showOrderDetails(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("orderedProductsList", orderService.findOrder(id).getProductsOrdered());
        model.addAttribute("order", orderService.findOrder(id));
        return "order/orderDetails";
    }
}
