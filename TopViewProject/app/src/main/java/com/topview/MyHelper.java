package com.topview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper( Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //deleteTable(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ProdForCheckout");
        sqLiteDatabase.execSQL("create table ProdForCheckout( id integer primary key,ProductName text, ProductPrice text, ProductQuant integer )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ProdForCheckout");
        onCreate(sqLiteDatabase);
    }
    public void deleteTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.delete("ProdForCheckout",null,null);
        sqLiteDatabase.close();
    }
}
