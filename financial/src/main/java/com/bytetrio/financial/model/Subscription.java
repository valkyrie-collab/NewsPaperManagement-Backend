package com.bytetrio.financial.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    private String id;
    private String title;
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<CustomerFinancial> customerFinancials;

    public String getId() {return id;}

    public Subscription setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {return title;}

    public Subscription setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<CustomerFinancial> getCustomerFinancials() {return customerFinancials;}

    public Subscription setCustomerFinancials(List<CustomerFinancial> customerFinancials) {
        this.customerFinancials = customerFinancials;
        return this;
    }
}
