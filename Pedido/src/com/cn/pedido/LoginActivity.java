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
import org.apache.http.client.methods.*;
import org.apache.http.message.*;
import org.apache.http.client.entity.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import org.apache.http.*;
import android.content.*;
import java.util.UUID;
import java.text.*;
import com.cn.pedido.DB.DataBaseManager;
import android.database.Cursor;
import android.content.DialogInterface;
import java.util.UUID;

public class LoginActivity extends Activity
{

	private DataBaseManager manager;
    Cursor cursor;
	String phone;
	EditText txtName, txtPhone;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

        manager= new DataBaseManager(this);
		//Valida si existe parametro	
		if(manager.GetConfigNotify("config"))
		{
			//Phone
			phone = manager.GetConfigNotifyOption("config");
			if(ValidPhone(phone).equalsIgnoreCase("ok")){
				Intent mainActivity= new Intent(this, MainActivity.class);
				mainActivity.putExtra("phone", phone);
				startActivity(mainActivity);
				finish();
				
			}			
		}
		else
		{
			setContentView(R.layout.login);
			txtName=(EditText)findViewById(R.id.txtName);    
			txtPhone=(EditText)findViewById(R.id.txtPhone);    
		}
		
	}
	public void onLogin(View v)
	{
		if(txtName.getText().length()==0){
				Toast.makeText(getApplicationContext(), "Complete el campo nombre", Toast.LENGTH_LONG).show();
				txtName.setFocusable(true);
				return;
			}

		if(txtPhone.getText().length()==0){
				Toast.makeText(getApplicationContext(), "Complete el campo Nº Celular", Toast.LENGTH_LONG).show();
				txtPhone.setFocusable(true);
				return;
			}

		if(ValidPhone(txtPhone.getText().toString()).equalsIgnoreCase("ok")){
			manager.insertarParameter("config",txtPhone.getText().toString());
			Toast.makeText(getApplicationContext(), "Usted ya se registo anteriormente, Bienvenido", Toast.LENGTH_LONG).show();
			Intent mainActivity= new Intent(this, MainActivity.class);
			mainActivity.putExtra("phone", txtPhone.getText().toString());
			startActivity(mainActivity);
			finish();
			
		}else
		{
			manager.insertarParameter("config",txtPhone.getText().toString());
			try{

				UUID idusuario = UUID.randomUUID();

				StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);

				HttpClient oHttpclientRegist= new DefaultHttpClient();
				HttpPost oHttpPostRegist = new HttpPost("http://loswaykis.com/ws/wsusuarioinsert.php");
				List<BasicNameValuePair> namevalue= new ArrayList<BasicNameValuePair>();	

				namevalue.add(new BasicNameValuePair("idusuario", idusuario.toString()));
				namevalue.add(new BasicNameValuePair("name", txtName.getText().toString() ));
				namevalue.add(new BasicNameValuePair("phone", txtPhone.getText().toString() ));

				oHttpPostRegist.setEntity(new UrlEncodedFormEntity(namevalue));
				HttpResponse responseRegist=oHttpclientRegist.execute(oHttpPostRegist);
				if(responseRegist.getStatusLine().getStatusCode()== HttpStatus.SC_OK){

					HttpEntity entity = responseRegist.getEntity();
					BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
					InputStream iStream = buffer.getContent();

					String aux = "";

					BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
					String line;
					while ((line = r.readLine()) != null) {
						aux += line;
					}

					JSONObject jsonObject = new JSONObject(aux);
					JSONArray result = jsonObject.getJSONArray("result");

					if(result.getJSONObject(0).getString("status").equalsIgnoreCase("ok")){
						Toast.makeText(getApplicationContext(), "Se registro correctamente", Toast.LENGTH_LONG).show();
						Intent mainActivity= new Intent(this, MainActivity.class);
						mainActivity.putExtra("phone", txtPhone.getText().toString());
						startActivity(mainActivity);
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "No se llego a registrar intentende nuevo", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Ocurrio problema conexión con la red", Toast.LENGTH_LONG).show();
				}	
			}catch(Exception ex){
				Toast.makeText(getApplicationContext(), "Ocurrio problema conexión con la red", Toast.LENGTH_LONG).show();
				
			}
			
		}

			
	}

	public String ValidPhone(String phone){
		String resp="No";
		try{
			StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			HttpClient oHttpclient= new DefaultHttpClient();
			HttpPost oHttpPost = new HttpPost("http://loswaykis.com/ws/wslogin.php");
			List<BasicNameValuePair> namevaluePairs= new ArrayList<BasicNameValuePair>();		
			namevaluePairs.add(new BasicNameValuePair("phone", phone));

			oHttpPost.setEntity(new UrlEncodedFormEntity(namevaluePairs));
			HttpResponse response=oHttpclient.execute(oHttpPost);
			if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
				
					HttpEntity entity = response.getEntity();
					BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
					InputStream iStream = buffer.getContent();

					String aux = "";

					BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
					String line;
					while ((line = r.readLine()) != null) {
						aux += line;
					}

					JSONObject jsonObject = new JSONObject(aux);
					JSONArray result = jsonObject.getJSONArray("result");

					resp = result.getJSONObject(0).getString("status");
			}

		}catch(Exception ex){
			resp= ex.getMessage();
		}
		return resp;

	}
}
