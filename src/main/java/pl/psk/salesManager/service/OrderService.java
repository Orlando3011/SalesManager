package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.repository.ClientRepository;
import pl.psk.salesManager.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrderToRepository(Order order, Client client) {
        order.setClient(clientRepository.findById(order.getClientIdNumber()));
        order.createClientFullName();
        order.setProductsBought(0);
        order.setTotalPrice(0);
        orderRepository.save(order);
    }

    public void removeOrder(int id) {
        orderRepository.delete(orderRepository.findById(id));
    }
}
