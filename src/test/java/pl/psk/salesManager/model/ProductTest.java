package pl.psk.salesManager.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;
    int quantity;

    @Test
    public void shouldReturnOneElementList() {
        //given
        product = new Product();
        List<Product> products;
        //when
        quantity = 1;
        products = product.multiplyProduct(quantity);
        //then
        assertEquals(quantity, products.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        //given
        product = new Product();
        List<Product> products;
        //when
        quantity = 0;
        products = product.multiplyProduct(quantity);
        //then
        assertEquals(quantity, products.size());
    }
}