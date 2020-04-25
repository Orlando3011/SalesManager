package pl.psk.salesManager.service;

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
        List<SoldProduct> productsToRemove = new ArrayList<>();
        for (SoldProduct soldProduct:order.getProductsOrdered()) {
            Product product = soldProduct.getProduct();
            product.setInStock(product.getInStock() + soldProduct.getQuantity());
            productService.addProductToRepository(product);

            productsToRemove.add(soldProduct);
            soldProductRepository.delete(soldProductRepository.findById(soldProduct.getId()));
        }
        order.getProductsOrdered().removeAll(productsToRemove);
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

    public SoldProduct findSoldProduct(int id) {
        return soldProductRepository.findById(id);
    }

    public void addProductToOrder(Product product, Order order, int quantity) {
        SoldProduct soldProduct = new SoldProduct();
        soldProduct.setProduct(product);
        if(product.getInStock() > 0) {
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
    }

    public void removeOneProduct(SoldProduct soldProduct, int quantity) {
        if(soldProduct.getQuantity() == quantity) removeAllProduct(soldProduct);
        else {
            soldProduct.setQuantity(soldProduct.getQuantity() - quantity);
            Product product = soldProduct.getProduct();
            product.setInStock(product.getInStock() + quantity);
            soldProductRepository.save(soldProduct);
            productService.addProductToRepository(product);
        }
    }

    public void removeAllProduct(SoldProduct soldProduct) {
        Order order = soldProduct.getOrder();
        Product product = soldProduct.getProduct();
        product.setInStock(product.getInStock() + soldProduct.getQuantity());
        order.getProductsOrdered().remove(soldProduct);
        order.countPrice();
        order.countProducts();
        orderRepository.save(order);
        productService.addProductToRepository(product);
        soldProductRepository.delete(soldProduct);
    }

    private void updateProductQuantity(SoldProduct soldProduct) {
        Product product = productService.findProductById(soldProduct.getProduct().getId());
        product.setInStock(product.getInStock() - soldProduct.getQuantity());
        productService.editProduct(product);
    }
}
