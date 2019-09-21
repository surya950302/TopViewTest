package com.topview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;

public class Main2Activity extends AppCompatActivity {

    private MyHelper mMyHelper;
    private SQLiteDatabase mSQLiteDB;
    ProductListPojo listPojo;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static  RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Top View");

        //Setting up database
        mMyHelper = new MyHelper(this,"SURYADB", null,1);
        mSQLiteDB = mMyHelper.getWritableDatabase();

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //setting adapter to recycler view
        recyclerView= findViewById(R.id.product_list2);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listPojo = new Gson().fromJson(parseJSONData(),ProductListPojo.class);
        //Log.d("Avenger", "list pojo: "+listPojo.getProductList());
        adapter = new AccessoriesAdapter(listPojo.getProductList(), this);
        recyclerView.setAdapter(adapter);


        /*Cursor c= mSQLiteDB.query("ProdForCheckout",null,null,null,null,null,null);
        while(c.moveToNext()){
            Log.d("main2activity", "onCreate: "+c.getString(c.getColumnIndex("ProductName")));
        }*/

    }
    public String parseJSONData(){
        String JSONSting =null;
        JSONObject obj=  null;
        try{
            InputStream inputStream = getAssets().open("bikerentals.json");
            int sizeOfJSONFile =inputStream.available();
            byte[] b =new byte[sizeOfJSONFile];
            inputStream.read(b);
            inputStream.close();

            JSONSting = new String(b,"UTF-8");
            //Log.d("Avenger", "JSONString: "+JSONSting);


        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
        return JSONSting;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    public void nextactivity2(View view) {
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}
