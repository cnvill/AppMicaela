package com.cn.pedido;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.NumberPicker;


import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class ItemOrder extends Activity
{
	NumberPicker npQunatity;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);
		npQunatity=(NumberPicker)findViewById(R.id.orderQuantity);
		npQunatity.setMinValue(1);
		npQunatity.setMaxValue(10);
		npQunatity.setWrapSelectorWheel(true);
		
		}
	
}
