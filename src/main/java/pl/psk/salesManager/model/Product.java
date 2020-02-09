package pl.psk.salesManager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    private String PKWiUSymbol;
    @NotNull
    private boolean isAvailable;
    private String imageSource;
    @ManyToOne
    private Order order;


    public Product() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPKWiUSymbol() {
        return PKWiUSymbol;
    }

    public void setPKWiUSymbol(String PKWiUSymbol) {
        this.PKWiUSymbol = PKWiUSymbol;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Product copyProduct() {
        Product copy = new Product();
        copy.setName(this.name);
        copy.setPKWiUSymbol(this.PKWiUSymbol);
        copy.setPrice(this.price);
        copy.setAvailable(this.isAvailable);
        copy.setImageSource(this.imageSource);
        return copy;
    }

    public List<Product> multiplyProduct(int quantity) {
        List<Product> products = new ArrayList<>();
        if (quantity <= 0) {
            return products;
        }
        products.add(this);
        while(products.size() < quantity) {
            products.add(this.copyProduct());
        }
        return products;
    }
}
