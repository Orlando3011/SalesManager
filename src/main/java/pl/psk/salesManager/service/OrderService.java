package pl.psk.salesManager.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.model.Product;
import pl.psk.salesManager.model.SoldProduct;
import pl.psk.salesManager.repository.ClientRepository;
import pl.psk.salesManager.repository.OrderRepository;
import pl.psk.salesManager.repository.SoldProductRepository;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private SoldProductRepository soldProductRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrderToRepository(Order order) {
        order.setClient(clientRepository.findById(order.getClientIdNumber()));
        order.setProductsOrdered(new ArrayList<>());
        order.createClientFullName();
        order.setProductsBought(0);
        order.setTotalPrice(0);
        orderRepository.save(order);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        Date date = order.getCreated();
        order.setDateDisplayed(df.format(order.getCreated()));
        orderRepository.save(order);
    }

    public void removeOrder(int id) {
        Order order = orderRepository.findById(id);
        for (SoldProduct product:order.getProductsOrdered()) {
            soldProductRepository.delete(soldProductRepository.findById(product.getId()));
            order.getProductsOrdered().remove(product);
        }
        orderRepository.delete(orderRepository.findById(id));
    }

    public void editOrder(Order order) {
        order.setClient(clientRepository.findById(order.getClientIdNumber()));
        order.createClientFullName();
        orderRepository.save(order);
    }

    public Order findOrder(int id) {
        return orderRepository.findById(id);
    }

    public void addProductToOrder(Product product, Order order, int quantity) {
        SoldProduct soldProduct = new SoldProduct();
        soldProduct.setProduct(product);
        if(!order.checkIfProductIsOrdered(soldProduct)) {
            soldProduct.setOrder(order);
            soldProduct.setQuantity(quantity);
            order.addProduct(soldProduct);
            soldProductRepository.save(soldProduct);
        }
        else {
            soldProduct.setQuantity(quantity);
            order.incrementProduct(soldProduct);
        }
        updateProductQuantity(soldProduct);
        orderRepository.save(order);
    }

    private void updateProductQuantity(SoldProduct soldProduct) {
        Product product = productService.findProductById(soldProduct.getProduct().getId());
        product.setInStock(product.getInStock() - soldProduct.getQuantity());
        productService.editProduct(product);
    }
}
