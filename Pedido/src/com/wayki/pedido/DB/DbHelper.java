package com.wayki.pedido.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{

    private static final String dbName="bdNotifyMe.sqlite";
    private static final int dbSchemeVersion=1;

    public DbHelper(Context context) {
        super(context, dbName, null, dbSchemeVersion);
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
       
        db.execSQL(DataBaseManager.createTableParameter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub se ejecuta cuando cambia de version el Schema

    }

}
