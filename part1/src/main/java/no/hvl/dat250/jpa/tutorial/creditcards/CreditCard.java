package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Integer creditLimit;
    private Integer balance;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private Pincode pincode;

    @ManyToMany(mappedBy = "creditCards")
    Collection<Customer> owners = new ArrayList<>();

    public CreditCard() {}

    public CreditCard(Integer number, Integer creditLimit, Integer balance) {
        this.number = number;
        this.creditLimit = creditLimit;
        this.balance = balance;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public Integer getBalance() {
        return balance;
    }

    public Bank getOwningBank() {
        return bank;
    }

    public void setOwningBank(Bank bank) {
        this.bank = bank;
    }

    public Pincode getPincode() {
        return pincode;
    }

    public void setPincode(Pincode pincode) {
        this.pincode = pincode;
    }

    public Collection<Customer> getOwners() {
        return owners;
    }
}
