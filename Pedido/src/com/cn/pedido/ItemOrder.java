package com.cn.pedido;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.NumberPicker;
import android.content.Intent;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import java.util.*;
import java.text.*;

public class ItemOrder extends Activity
{
	EditText npQuantity;
    ImageView imageProduct;
	TextView nameProduct, descriptionProduct, costTotal;
	private static String idProduct;
	private static Double price = 0.0;
	private static Integer Quantity = 1;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);
		
		npQuantity=(EditText)findViewById(R.id.orderQuantity);
		imageProduct=(ImageView)findViewById(R.id.productViewImage);
		nameProduct=(TextView)findViewById(R.id.productViewName);
		descriptionProduct=(TextView)findViewById(R.id.productViewDescription);
		costTotal=(TextView)findViewById(R.id.costTotalView);

		npQuantity.setText("1");
		npQuantity.setEnabled(false);
		Bundle bundle=getIntent().getExtras();
		
		if(bundle.getString("idProduct")!=null){
			idProduct=bundle.getString("idProduct");
			int indice=0;
			ArrayList<Product> listProduct=	MainActivity.productsAvaiable;
			
			for(int i=0; i<listProduct.size(); i++){
				if(listProduct.get(i).getIdProduct().equalsIgnoreCase(idProduct))
				{
					indice=i;
					break;
				}
			}

			Product oProduct = listProduct.get(indice);
			price = oProduct.getPrice();
			imageProduct.setImageBitmap(oProduct.getPhoto());
			nameProduct.setText(oProduct.getName()+" s/."+price);
			descriptionProduct.setText(oProduct.getDescription());
			costTotal.setText(" s/."+price+" ");
			Double stock=new Double(oProduct.getStock());
			Quantity=stock.intValue();
		}
	   }
	   
	public void addToCart(View v){
		
		   Intent intent = getIntent();
		   intent.putExtra("addIdProduct", idProduct);
		   intent.putExtra("addPrice", price);
		   intent.putExtra("addQuantity",Integer.parseInt(npQuantity.getText().toString()));
		   intent.putExtra("addName", nameProduct.getText());
		   setResult(1, intent);
		   this.finish();
	   }
	public void onMenos(View v){
	 Integer value= Integer.parseInt(npQuantity.getText().toString());
	 value=value-1;
	 if(value<=1)
		 value=1;
	 if(Quantity==0)
		 value=0;
		 
		npQuantity.setText(""+value);
		DecimalFormat df= new DecimalFormat("#.##");
		costTotal.setText(" s/."+df.format(price*value)+" ");
	}
	
	public void onMas(View v){
		Integer value= Integer.parseInt(npQuantity.getText().toString());
		value=value+1;
		if(value>=Quantity)
			value=Quantity;
		npQuantity.setText(""+value);	
		DecimalFormat df= new DecimalFormat("#.##");
		costTotal.setText(" s/."+df.format(price*value)+" ");
	}
}
