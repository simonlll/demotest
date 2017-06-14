package demotest.entity;

/**
 * Created by simon on 2017/5/26.
 */

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private BigDecimal price;
    private int amount;

    private String name;

    public SimonOrder getOrder() {
        return order;
    }

    public void setOrder(SimonOrder order) {
        this.order = order;
    }

    @ManyToOne
    private SimonOrder order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



}
