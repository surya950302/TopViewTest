package com.topview;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static  RecyclerView recyclerView;
    private MyHelper mMyHelper;
    private SQLiteDatabase mSQLiteDB;
    private Cursor c;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("Top View");

        //Setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        //setting adapter to recycler view
        recyclerView= findViewById(R.id.checkout_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Log.d("Avenger", "list pojo: "+listPojo.getProductList());
        adapter = new CartAdapter(this);
        recyclerView.setAdapter(adapter);

        //finding total
        mMyHelper = new MyHelper(this,"SURYADB", null,1);
        mSQLiteDB = mMyHelper.getWritableDatabase();
        c= mSQLiteDB.query("ProdForCheckout",null,null,null,null,null,null);
        double sum=0;
        while(c.moveToNext()){
            Double prodcost=Double.parseDouble(c.getString(c.getColumnIndex("ProductPrice")));
            int nar=c.getColumnIndex("ProductQuant");
            int q = c.getInt(nar);
            prodcost=prodcost*q;
            sum=sum+prodcost;
        }
        total = (TextView) findViewById(R.id.totalcost);
        DecimalFormat df = new DecimalFormat("#.00");
        total.setText("$"+df.format(sum).toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSQLiteDB.delete("ProdForCheckout", null,null);
        mSQLiteDB.close();
    }
    public void nextactivity3(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Purchase Successful!")
                .setMessage("Thank you for your purchase.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }
}
