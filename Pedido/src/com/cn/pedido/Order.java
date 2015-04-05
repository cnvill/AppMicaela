package com.cn.pedido;
import java.io.Serializable;

public class Order implements Serializable
{
	protected String idorder;
	protected String idproduct;
	protected Double quantity;
	
	public Order(){
		
	}
	
	public Order(String idorder, String idproduct, Double quantity) {
        this.idorder = idorder;
        this.idproduct = idproduct;
        this.quantity = quantity;
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
}
