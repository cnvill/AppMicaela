package com.micaela.pedido;
import java.io.Serializable;

public class Order implements Serializable
{
	protected String idorder;
	protected String idproduct;
	protected Double quantity;
	protected String name;
	protected Double price;
	public Order(){
		
	}
	
	public Order(String idorder, String idproduct, Double quantity, String name, Double price) {
        this.idorder = idorder;
        this.idproduct = idproduct;
        this.quantity = quantity;
		this.name=name;
		this.price=price;
    }
	
	public String getIdOrder() {
        return idorder;
    }

    public void setIdOrder(String idorder) {
        this.idorder = idorder;
    }

    public String getIdProduct() {
        return idproduct;
    }

    public void setIdProduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
	
}
