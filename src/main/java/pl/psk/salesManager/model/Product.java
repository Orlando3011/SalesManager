package pl.psk.salesManager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    private String pkwiuSymbol;
    private boolean isAvailable;
    @NotNull
    private int inStock;
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

    public String getPkwiuSymbol() {
        return pkwiuSymbol;
    }

    public void setPkwiuSymbol(String pkwiuSymbol) {
        this.pkwiuSymbol = pkwiuSymbol;
    }

    public boolean getIsAvailable() {
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

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void checkAvailability() {
        if(inStock <= 0) {
            this.setAvailable(false);
        }
        else {
            this.setAvailable(true);
        }
    }
}
