package demotest.entity;

/**
 * Created by simon on 2017/5/26.
 */

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
//@NamedStoredProcedureQuery(name = "test", procedureName = "test", parameters = {
//        @StoredProcedureParameter(mode = ParameterMode.IN, name="startrow", type=Integer.class),
//        @StoredProcedureParameter(mode = ParameterMode.IN, name="pagesize", type=Integer.class)
//})
public class SimonOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String description;

    private Date  deliveryDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @ManyToOne(optional = false)
//    @ManyToOne
//    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "CUSTOMER_ID_FK"))
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<Item> items= new HashSet<Item>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
