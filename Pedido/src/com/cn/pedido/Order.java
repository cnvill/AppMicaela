package com.cn.pedido;
import java.io.Serializable;

public class Order implements Serializable
{
	protected String idorder;
	protected String idproduct;
	protected Double quantity;
	protected String name;
	
	public Order(){
		
	}
	
	public Order(String idorder, String idproduct, Double quantity, String name) {
        this.idorder = idorder;
        this.idproduct = idproduct;
        this.quantity = quantity;
		this.name=name;
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
}
