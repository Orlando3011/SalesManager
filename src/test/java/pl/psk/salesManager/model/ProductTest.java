package pl.psk.salesManager.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @Test
    public void shouldBeAvailable() {
        //given
        product = new Product();
        product.setInStock(10);
        //when
        product.checkAvailability();
        //then
        assertTrue(product.getIsAvailable());
    }

}