package com.cn.pedido;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
public class Product
{
	protected String idproduct;
	protected String name;
	protected String data;
	protected String description;
	protected Long price;
	protected Long stock;
	protected Integer status;
	protected Bitmap photo;
	
	public Product(String idproduct, String name, String description, Long price, Long stock, Integer status ) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
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
