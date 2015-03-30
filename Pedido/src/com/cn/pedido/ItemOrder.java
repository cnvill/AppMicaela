package com.cn.pedido;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.NumberPicker;


import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import java.util.*;

public class ItemOrder extends Activity
{
	NumberPicker npQunatity;
    ImageView imageProduct;
	TextView nameProduct, preciProduct, descriptionProduct;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);
		
		npQunatity=(NumberPicker)findViewById(R.id.orderQuantity);
		imageProduct=(ImageView)findViewById(R.id.productViewImage);
		nameProduct=(TextView)findViewById(R.id.productViewName);
		preciProduct=(TextView)findViewById(R.id.productViewPrice);
		descriptionProduct=(TextView)findViewById(R.id.productViewDescription);
		npQunatity.setMinValue(1);
		
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
			imageProduct.setImageBitmap(oProduct.getPhoto());
			nameProduct.setText(oProduct.getName());
			descriptionProduct.setText(oProduct.getDescription());
			preciProduct.setText("s/. "+oProduct.getPrice());
			Double stock=new Double(oProduct.getStock());
			npQunatity.setMaxValue(stock.intValue());
			//Toast.makeText(getApplicationContext(), "i"+oProduct.getStock(),Toast.LENGTH_SHORT).show();
			npQunatity.setWrapSelectorWheel(true);
			npQunatity.showContextMenu();
			
		}
	    
		}
	
}
