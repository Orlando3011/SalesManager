package pl.psk.salesManager.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @Test
    public void shouldReturnOneElementList() {
        //given
        product = new Product();
        List<Product> products;
        //when
        int quantity = 1;
        product.setInStock(quantity);
        products = product.multiplyProduct();
        //then
        assertEquals(quantity, products.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        //given
        product = new Product();
        List<Product> products;
        //when
        int quantity = 0;
        products = product.multiplyProduct();
        //then
        assertEquals(quantity, products.size());
    }
}