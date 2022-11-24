package com.example.myapplication.data.model;

import java.io.Serializable;
import java.util.List;

public class  Cart implements Serializable {
    private String id;
    private List<Product> products;
    private String idUser;
    private Integer price;
    private String dateCreated;

    public Cart(String id, List<Product> products, String idUser, Integer price, String dateCreated) {
        this.id = id;
        this.products = products;
        this.idUser = idUser;
        this.price = price;
        this.dateCreated = dateCreated;
    }

    public Cart(String id, List<Product> products, String idUser, Integer price) {
        this.id = id;
        this.products = products;
        this.idUser = idUser;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", idUser='" + idUser + '\'' +
                ", price=" + price +
                '}';
    }
}
