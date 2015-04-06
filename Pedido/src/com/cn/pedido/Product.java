package com.cn.pedido;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.Serializable;

public class Product
{
	protected String idproduct;
	protected String name;
	protected String data;
	protected String description;
	protected Double price;
	protected Double stock;
	protected Integer status;
	protected Bitmap photo;
	
	public Product(String idproduct, String name, String description, Double price, Double stock, Integer status ) {
        this.idproduct = idproduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }


    public String getIdProduct() {
        return idproduct;
    }

    public void setIdProduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        try {
            byte[] byteData = Base64.decode(data, Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray( byteData, 0, byteData.length);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
