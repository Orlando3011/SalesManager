package pl.psk.salesManager.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "order")
    private List<Product> products;
    @CreatedDate
    private Date created;
    private Integer invoiceNumber;
    private String paymentMethod;
    private double totalPrice;
    private String clientFullName;
    private int productsBought;

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public int getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(int productsBought) {
        this.productsBought = productsBought;
    }

    public void countProducts() {
        productsBought = products.size();
    }

    public void sumPrices() {
        totalPrice = 0;
        for(Product product: products) {
            totalPrice = totalPrice + product.getPrice();
        }
    }

    public void mergeClientCredentials() {
        clientFullName = client.getFirstName() + " " + client.getFamilyName();
    }
}
