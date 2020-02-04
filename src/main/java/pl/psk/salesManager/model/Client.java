package pl.psk.salesManager.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String firstName;
    @NotNull
    private String familyName;
    private String address;
    private String email;
    private String bankName;
    private String bankAccountNumber;
    private String phone;
    private Boolean regularCustomer;
    @OneToMany(mappedBy = "client")
    private List<Order> orders;
    @CreatedDate
    private Date created;

    public Client() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getRegularCustomer() {
        return regularCustomer;
    }

    public void setRegularCustomer(Boolean regularCustomer) {
        this.regularCustomer = regularCustomer;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int countOrders() {
        return orders.size();
    }

    public void setClientDetails(String firstName,
                                 String familyName,
                                 String address,
                                 String email,
                                 String phone,
                                 String bankName,
                                 String bankAccountNumber) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.bankName = bankName;
        this. bankAccountNumber = bankAccountNumber;

    }
}
