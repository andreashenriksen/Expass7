package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;

@Entity
public class Pincode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private Integer count;

    public Pincode() {}

    public Pincode(String code, Integer count) {
        this.pincode = code;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return pincode;
    }

    public void setCode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
