package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Product;
import pl.psk.salesManager.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void addProductToRepository(Product product) {
        product.checkAvailability();
        productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void removeProduct(int productId) {
        productRepository.delete(productRepository.findById(productId));
    }

    public void editProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(int id) {
        return productRepository.findById(id);
    }
}
