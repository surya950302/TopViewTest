package com.topview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ProductListPojo listPojo;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static  RecyclerView recyclerView;
    /*private List<String> prodforcheckout =new ArrayList<>();
    Button next;*/
    private MyHelper mMyHelper;
    private SQLiteDatabase mSQLiteDB;

    //private List<ProdforCheckout> pfc = new ArrayList<>();
    //private ProductListAdapter.MyViewHolder obj;

    public MainActivity() {
        listPojo = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Top View");

        //Setting up database
        mMyHelper = new MyHelper(this,"SURYADB", null,1);
        mSQLiteDB = mMyHelper.getWritableDatabase();
        mSQLiteDB.delete("ProdForCheckout", null,null);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        //setting adapter to recycler view
        recyclerView= findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listPojo = new Gson().fromJson(parseJSONData(),ProductListPojo.class);
        //Log.d("Avenger", "list pojo: "+listPojo.getProductList());
        adapter = new ProductListAdapter(listPojo.getProductList(), this);
        recyclerView.setAdapter(adapter);

        //getting data
        /*Intent intent= this.getIntent();
            prodforcheckout.add(intent.getStringExtra("ProdforCheckout"));*/
        //String s=PreferenceManager.getDefaultSharedPreferences(this).getString("MYLABEL", "defaultStringIfNothingFound");

        /*for(int i=0;i<pfc.size();i++)*/
        //String arr[]= {"1","2","3"};
        //final Set<String> s= new HashSet<String>(Arrays.asList(arr));
        /*final String s= this.toString();
        next= (Button) findViewById(R.id.nextbtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //prodforcheckout=(PreferenceManager.getDefaultSharedPreferences(view.getContext()).getStringSet("MYLABEL", s));
                //Log.d("mainactivity", "onCreate: "+prodforcheckout);

                Intent intent = String.valueOf(s).getIntent();
                prodforcheckout.add(intent.getStringExtra("ProdforCheckout"));
                Log.d("mainactivity", "onCreate: "+prodforcheckout);

            }
        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSQLiteDB.delete("ProdForCheckout", null,null);
        mSQLiteDB.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
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

    public void onProductClicked(View view) {
        Log.d("Avenger", "onProductClicked: yes");
    }

    public void nextactivity(View view) {
        /*Intent intent = this.getIntent();
        prodforcheckout.add(intent.getStringExtra("ProdforCheckout"));*/
        Cursor c= mSQLiteDB.query("ProdForCheckout",null,null,null,null,null,null);
        /*while(c.moveToNext()){
            Log.d("mainactivity", "onCreate: "+c.getString(c.getColumnIndex("ProductName")));
        }*/

        Intent intent= new Intent(this,Main2Activity.class);
        if(c.getCount() !=0){
            startActivity(intent);
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Option Denied")
                    .setMessage("Please select at least one Bike")
                    .setPositiveButton(android.R.string.ok,null )
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

}
