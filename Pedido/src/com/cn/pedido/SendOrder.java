package com.cn.pedido;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.text.*;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.client.methods.*;
import org.apache.http.message.*;
import org.apache.http.client.entity.*;
import android.content.Context;

public class SendOrder extends Activity {
	ArrayList<Order> listOrders= new ArrayList<Order>();
	ArrayList<Product> listProducts= new ArrayList<Product>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_to_order);		
		
		ListView lsvSammary=(ListView)findViewById(R.id.lsvSummary);		
		TextView lblSammaryCostTotal=(TextView)findViewById(R.id.lblSummaryCostTotal);		
		
		Bundle extraOrders=getIntent().getBundleExtra("orders");
		listOrders=(ArrayList<Order>)extraOrders.getSerializable("ordersList");
		DecimalFormat df= new DecimalFormat("#");
		
		final ArrayList<String> from= new ArrayList<String>();
		for(int i=0;i<listOrders.size();i++)
			from.add(df.format(listOrders.get(i).getQuantity())+":"+listOrders.get(i).getName());		

		
		
		final StableArrayAdapter adapter= new StableArrayAdapter(this, android.R.layout.simple_list_item_1, from);
		lsvSammary.setAdapter(adapter);
		df= new DecimalFormat("#.##");
		lblSammaryCostTotal.setText("Costo Total: s/."+df.format(getIntent().getExtras().getDouble("totalCost")));	
	}	
	
	public void onFinishOrder(View v){
		try{
			HttpClient oHttpclient= new DefaultHttpClient();
			HttpPost oHttpPost = new HttpPost("http://loswaykis.com/ws/wspedidoinsert.php");
			List<BasicNameValuePair> namevaluePairs= new ArrayList<BasicNameValuePair>();
			
			for(int i=0; i<listOrders.size(); i++){
				
				namevaluePairs.add(new BasicNameValuePair("idOrders[]", listOrders.get(i).getIdOrder()));
				namevaluePairs.add(new BasicNameValuePair("idProducts[]", listOrders.get(i).getIdProduct()));
				namevaluePairs.add(new BasicNameValuePair("Quantities[]", ""+listOrders.get(i).getQuantity()));
			}
			
			oHttpPost.setEntity(new UrlEncodedFormEntity(namevaluePairs));
			HttpResponse response=oHttpclient.execute(oHttpPost);
			response.getStatusLine();
		}
		catch(Exception ex){
	
		}		
	}

	 private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
            List<String> objects) {
          super(context, textViewResourceId, objects);
          for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
          }
        }

        @Override
        public long getItemId(int position) {
          String item = getItem(position);
          return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
          return true;
        }

    }
}
