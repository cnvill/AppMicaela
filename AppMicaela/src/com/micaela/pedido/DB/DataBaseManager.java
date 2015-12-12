package com.micaela.pedido.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by CN on 19/10/2014.
 */
public class DataBaseManager {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context ctx){
        helper= new DbHelper(ctx);
        db=helper.getReadableDatabase();
    }
   
    //Create Table Parameter

    public static final String tableNameParameter="tparameter";
    public static final String pId="_id";
    public static final String pCode="codigo";
    public static final String pValue="valor";
    public static final String pEstado="estado";


    public static final String createTableParameter="create table "+tableNameParameter+ "("
            + pId + " integer primary key autoincrement, "
            + pCode + " text not null, "
            + pValue+ " text not null, "
            + pEstado +" integer );";

    public ContentValues generarParameterContentValues(String code, String value){
        ContentValues valores= new ContentValues();
        valores.put(pCode, code);
        valores.put(pValue, value);
        valores.put(pEstado, "1");
        return valores;
    }

    public void insertarParameter(String code, String value){
        db.insert(tableNameParameter, null ,generarParameterContentValues(code, value));
    }

    public boolean GetConfigNotify(String code){
        boolean respt=false;
        String[] columnas= new String[]{ pId, pCode, pValue, pEstado};
        Cursor c= db.query(tableNameParameter, columnas, pCode+"=?", new String[]{ code }, null, null, null);
        if(c.moveToFirst())
            respt=true;
            
        return respt;   
    }

    public String GetConfigNotifyOption(String code){
        String respt="";
        String[] columnas= new String[]{ pId, pCode, pValue, pEstado};
        Cursor c= db.query(tableNameParameter, columnas, pCode+"=?", new String[]{ code }, null, null, null);
        if(c.moveToFirst())
            respt = c.getString(2);
            
        return respt;   
    }

    //End Table Parameter 
   
    
}
