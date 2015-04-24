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
import org.apache.http.*;
import android.content.*;
import java.util.UUID;
import java.text.*;

public class MainActivity extends Activity
{
	public  static  ArrayList<Product> productsAvaiable;
    public  static  ArrayList<Order> ordersList;
    private TextView lblTotalCost;
	private Button btnTotalProduct;
	private static Double totalPreci=0.00;
	String phone;
	private ProgressDialog progress;
	Handler updateBarHandler;
	public ListView lvProducts;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Bundle bundle=getIntent().getExtras();

		if(bundle.getString("phone")!=null){
			phone=bundle.getString("phone");
		  }
		  
        lvProducts = (ListView) findViewById(R.id.lv_products);
    	lblTotalCost=(TextView)findViewById(R.id.lblTotalPrice);    
		btnTotalProduct=(Button)findViewById(R.id.btnTotalProducts);
		
		productsAvaiable = new ArrayList<Product>();
		ordersList=new ArrayList<Order>();
		
			progress = new ProgressDialog(this);
			progress.setMessage("Cargando lista de productos ...");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setProgress(0);
			progress.setMax(100);
			progress.show(this, "Cargando lista de productos...", "Espere por favor", true, false);
			updateBarHandler= new Handler();
			new CargarProductos().execute();
			totalPreci=0.0;
					
	}
	
	public void onSendOrder(View v){
		if(ordersList.size()>0){
			
			Bundle orders= new Bundle();
			orders.putSerializable("ordersList", ordersList);
			Intent sendOrder= new Intent(getBaseContext(), SendOrderActivity.class);
			sendOrder.putExtra("orders", orders);
			sendOrder.putExtra("totalCost",totalPreci);
			sendOrder.putExtra("phone",phone);
			this.startActivityForResult(sendOrder, 2);
		}
		else
			Toast.makeText(getApplicationContext(), "No se agrego ningun producto, agrege para poder realizar el pedido",Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1) {
	     if(resultCode == 1)
			if(data.getExtras().get("addQuantity")!=null){
				String idProduct=data.getExtras().get("addIdProduct").toString();
				Integer indice=-1;
				for(int i=0; i<ordersList.size(); i++){
					if(ordersList.get(i).getIdProduct().equalsIgnoreCase(idProduct))
					{
						indice=i;
						break;
					}
				}
				
				if(indice>-1){
						
					ordersList.get(indice).setQuantity(ordersList.get(indice).getQuantity()+Double.parseDouble(data.getExtras().get("addQuantity").toString()));
					
				}else{
					
					Order oOrder = new Order();
					UUID uidd = UUID.randomUUID();

					oOrder.setIdOrder(uidd.toString());
					oOrder.setIdProduct(idProduct);
					oOrder.setQuantity(Double.parseDouble(data.getExtras().get("addQuantity").toString()));
					oOrder.setPrice(Double.parseDouble(data.getExtras().get("addPrice").toString()));
					oOrder.setName(data.getExtras().get("addName").toString());
					ordersList.add(oOrder);
				}
				
				totalPreci=totalPreci+Double.parseDouble(data.getExtras().get("addQuantity").toString())*Double.parseDouble(data.getExtras().get("addPrice").toString());	
				DecimalFormat df= new DecimalFormat("#.##");
				btnTotalProduct.setText("Agregados: "+ordersList.size());
				lblTotalCost.setText("Costo s/."+df.format(totalPreci));
			}
		}
		
		if(requestCode == 2) {
			if(resultCode == 2){

				Bundle extraOrders=data.getBundleExtra("updateOrders");

				ordersList=new ArrayList<Order>();
				ordersList=(ArrayList<Order>)extraOrders.getSerializable("ordersList");

				Double tempTotalPrice =0.0;
				for(int i=0; i<ordersList.size();i++)
					tempTotalPrice=tempTotalPrice+ordersList.get(i).getQuantity()*ordersList.get(i).getPrice();									
				
				totalPreci=tempTotalPrice;
				DecimalFormat df= new DecimalFormat("#.##");
				btnTotalProduct.setText("Agregados: "+ordersList.size());
				lblTotalCost.setText("Costo s/."+df.format(tempTotalPrice));
			}
		}
	}
	
	public class CargarProductos extends AsyncTask<Void, Void, Void>{

        final Thread tProgress = new Thread(){
			@Override
			public void run() {
				try {
					while (progress.getProgress() <= 100) {
						Thread.sleep(500);
						updateBarHandler.post(new Runnable() {
								public void run() {
									progress.incrementProgressBy(5);
								}
							});

						if (progress.getProgress() == progress.getMax()) {
							//progress.dismiss();
							//break;
						}
					}
				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), "Error al solicitar " + ex.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		};

        @Override
        protected void onPreExecute(){
            //Iniciando
			tProgress.start();
			
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
              	
				StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				
				// Llamamos al servicio web para recuperar los datos
				HttpGet httpGet = new HttpGet("http://loswaykis.com/ws/wsproductos.php");
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(httpGet);
				StatusLine statusLine=response.getStatusLine();
				if(statusLine.getStatusCode()==HttpStatus.SC_OK){
					HttpEntity entity = response.getEntity();
					BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
					InputStream iStream = buffer.getContent();


					String aux = "";

					BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
					String line;
					while ((line = r.readLine()) != null) {
						aux += line;
					}

					// Parseamos la respuesta obtenida del servidor a un objeto JSON
					JSONObject jsonObject = new JSONObject(aux);
					JSONArray products = jsonObject.getJSONArray("productos");


					// Recorremos el array con los elementos products
					for(int i = 0; i < products.length(); i++) {
						JSONObject product = products.getJSONObject(i);

						// Creamos el objeto product
						Product c = new Product(
							product.getString("idproducto"), 
							product.getString("name"), 
							product.getString("description"), 
							product.getDouble("price"), 
							product.getDouble("stock"), 
							product.getInt("status"));
						c.setData(product.getString("photo"));

						// Almacenamos el objeto en el array que hemos creado anteriormente
						productsAvaiable.add(c);
					}	
				}
				else
					Toast.makeText(getApplicationContext(), "No hay Conexion a Internet"+statusLine.getStatusCode(),Toast.LENGTH_SHORT).show();
				progress.dismiss();
				tProgress.stop();	
			//	Thread.sleep(20000); // 20 Segundos
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error al leer Mensaje", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
			try{
				ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productsAvaiable);
				lvProducts.setAdapter(productAdapter);
				Toast.makeText(getApplicationContext(), "Se recomienda activar su GPS, para determinar su ubicación",Toast.LENGTH_LONG).show();
				
			}catch(Exception ex){
				Toast.makeText(getApplicationContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();

			}
        }
    }
	
}
