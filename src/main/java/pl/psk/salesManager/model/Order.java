package pl.psk.salesManager.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "order")
    private List<SoldProduct> productsOrdered;
    @CreatedDate
    private Date created;
    private String dateDisplayed;
    private Integer invoiceNumber;
    private String paymentMethod;
    private double totalPrice;
    private int clientIdNumber;
    private int productsBought;
    private String description;
    private String clientFullName;

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

    public List<SoldProduct> getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(List<SoldProduct> productsOrdered) {
        this.productsOrdered = productsOrdered;
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

    public int getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(int productsBought) {
        this.productsBought = productsBought;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClientIdNumber() {
        return clientIdNumber;
    }

    public void setClientIdNumber(int clientIdNumber) {
        this.clientIdNumber = clientIdNumber;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getDateDisplayed() {
        return dateDisplayed;
    }

    public void setDateDisplayed(String dateDisplayed) {
        this.dateDisplayed = dateDisplayed;
    }

    public void countProducts() {
        productsBought = productsOrdered.size();
    }

    public void countPrice() {
        double price = 0;
        for (SoldProduct p:productsOrdered) {
            price = price + p.getProduct().getPrice() * p.getQuantity();
        }
        totalPrice = price;
    }

    public void createClientFullName() {
        clientFullName = client.getFirstName() + " " + client.getFamilyName();
    }

    public void addProduct(SoldProduct soldProduct) {
        productsOrdered.add(soldProduct);
        productsBought = productsBought + soldProduct.getQuantity();
        totalPrice = totalPrice + soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
        totalPrice = BigDecimal.valueOf(totalPrice)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void incrementProduct(SoldProduct soldProduct) {
        for (SoldProduct s:productsOrdered) {
            if(s.getProduct().equals(soldProduct.getProduct())) {
                s.setQuantity(s.getQuantity() + soldProduct.getQuantity());
            }
        }
        productsBought = productsBought + soldProduct.getQuantity();
        totalPrice = totalPrice + soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
    }

    public boolean checkIfProductIsOrdered(SoldProduct soldProduct) {
        if(productsOrdered.size() != 0) {
            for (SoldProduct p:productsOrdered) {
                if (p.getProduct().equals(soldProduct.getProduct())) {
                    return true;
                }
            }
        }
        return false;
    }

    public double getTaxTotal() {
        double taxTotal = 0;
        for (SoldProduct product:productsOrdered) {
            taxTotal = taxTotal + product.getProduct().getTax() * (product.getQuantity() * product.getProduct().getPrice());
        }
        return taxTotal;
    }

    public double getPriceAndTax() {
        return getTotalPrice() + getTaxTotal();
    }
}