package com.misha.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by misha on 17.11.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT c FROM Product c")
})
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "products_id_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private int code;

    @Column(name = "name")
    @Size(min = 1,max = 100,message = "Name size must be between 1 and 10 symbols")
    private String name;

    @Column(name = "price")
    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Europe/Moscow")
    @Column(name = "p_date")
    private Date p_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getP_date() {
        return p_date;
    }

    public void setP_date(Date p_date) {
        this.p_date = p_date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "p_date=" + p_date +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
