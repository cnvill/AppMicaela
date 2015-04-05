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

public class ItemOrder extends Activity
{
	NumberPicker npQuantity;
    ImageView imageProduct;
	TextView nameProduct, preciProduct, descriptionProduct;
	private static String IdProduct;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);
		
		npQuantity=(NumberPicker)findViewById(R.id.orderQuantity);
		imageProduct=(ImageView)findViewById(R.id.productViewImage);
		nameProduct=(TextView)findViewById(R.id.productViewName);
		preciProduct=(TextView)findViewById(R.id.productViewPrice);
		descriptionProduct=(TextView)findViewById(R.id.productViewDescription);
		npQuantity.setMinValue(1);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle.getString("idProduct")!=null){
			String idProduct=bundle.getString("idProduct");
			int indice=0;
			ArrayList<Product> listProduct=	MainActivity.productsAvaiable;
			for(int i=0; i<listProduct.size(); i++){
				if(listProduct.get(i).getIdProduct().equalsIgnoreCase(idProduct))
				{
					indice=i;
					break;
				}
			}

			Product oProduct=listProduct.get(indice);
			IdProduct=oProduct.getIdProduct();
			imageProduct.setImageBitmap(oProduct.getPhoto());
			nameProduct.setText(oProduct.getName());
			descriptionProduct.setText(oProduct.getDescription());
			preciProduct.setText("s/. "+oProduct.getPrice());
			Double stock=new Double(oProduct.getStock());
			npQuantity.setMaxValue(stock.intValue());
			//Toast.makeText(getApplicationContext(), "i"+oProduct.getStock(),Toast.LENGTH_SHORT).show();
			npQuantity.setWrapSelectorWheel(true);
			npQuantity.showContextMenu();
			
		}
	   }
	   
	public void addToCart(View v){
		   Intent intent=getIntent();
		   intent.putExtra("addIdProduct",IdProduct);
		   intent.putExtra("addQuantity",npQuantity.getValue());
		   setResult(1, intent);
		   this.finish();
	   }
	
}
