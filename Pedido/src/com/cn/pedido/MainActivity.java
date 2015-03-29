package com.cn.pedido;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView lvProducts = (ListView) findViewById(R.id.lv_products);
        ArrayList<Product> productsAvaiable = new ArrayList<Product>();

        try {
            // Llamamos al servicio web para recuperar los datos
            HttpGet httpGet = new HttpGet("http://loswaykis.com/ws/wsproductos.php");
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = (HttpResponse)httpClient.execute(httpGet);
            //HttpEntity entity = response.getEntity();
            //BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
            //InputStream iStream = buffer.getContent();

			/*
            String aux = "";

            BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                aux += line;
            }
			
            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject jsonObject = new JSONObject(aux);
            JSONArray products = jsonObject.getJSONArray("productos");

            // Recorremos el array con los elementos cities
            for(int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);

                // Creamos el objeto City
                Product c = new Product(
				product.getString("idproducto"), 
				product.getString("name"), 
				product.getString("description"), 
				product.getLong("price"), 
				product.getLong("stock"), 
				product.getInt("status"));
                c.setData(product.getString("photo"));

                // Almacenamos el objeto en el array que hemos creado anteriormente
                productsAvaiable.add(c);
            }
			*/
        }
        catch(Exception e) {
            Log.e("WebService", e.getMessage());
        }

        // Creamos el objeto CityAdapter y lo asignamos al ListView
        //ProductAdapter productAdapter = new ProductAdapter(this, productsAvaiable);
        //lvProducts.setAdapter(productAdapter);
    }
}
