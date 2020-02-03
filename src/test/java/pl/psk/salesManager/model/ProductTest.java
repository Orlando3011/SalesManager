package pl.psk.salesManager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @Test
    public void shouldReturnFalse() {
        //given
        product = new Product();
        //when
        int inStock = 0;
        product.setInStock(inStock);
        //then
        assertFalse(product.isAvailable());
    }

    @Test
    public void shouldReturnTrue() {
        //given
        product = new Product();
        //when
        int inStock = 5;
        product.setInStock(inStock);
        //then
        assertTrue(product.isAvailable());
    }
}