package com.example.myapplication.data.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDTO {

    @SerializedName("_id")
    @Expose
    private String id;
    private List<ProductDTO> products = null;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
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
        return "CartDTO{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", idUser='" + idUser + '\'' +
                ", price=" + price +
                '}';
    }
}



