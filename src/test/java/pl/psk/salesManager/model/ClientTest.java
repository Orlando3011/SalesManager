package pl.psk.salesManager.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    Client client;
    List<Order> orders;

    @Test
    public void shouldCountTwoOrders() {
        //given
        client = new Client();
        orders = new ArrayList<>();
        //when
        orders.add(new Order());
        orders.add(new Order());
        client.setOrders(orders);
        //then
        assertEquals(2, client.countOrders());
    }

    @Test
    public void shouldCountZeroOrders() {
        //given
        client = new Client();
        orders = new ArrayList<>();
        //when
        client.setOrders(orders);
        //then
        assertEquals(0, client.countOrders());
    }

}