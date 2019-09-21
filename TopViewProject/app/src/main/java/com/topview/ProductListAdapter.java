package com.topview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import location.android.com.local_json_parsing.DetailsListPojo.Detail;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<Product> dataSet =new ArrayList<>();
    Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MyHelper mMyHelper;
        private final SQLiteDatabase mSQLiteDB;
        private final ContentValues cv;
        TextView name;
        TextView price;
        ImageView img;
        TextView quantity;
        Spinner spinner;
        int pos;
        int spinneritemselected;
        String data;
        /*List<ProdforCheckout> prodforCheckouts;
        ProdforCheckout data;*/


        public MyViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.img = (ImageView) itemView.findViewById(R.id.prd_img);
            this.name = (TextView) itemView.findViewById(R.id.prd_name);
            this.price = (TextView) itemView.findViewById(R.id.prd_price);
            this.quantity =(TextView) itemView.findViewById(R.id.quanti);
            //spinner initialization
            this.spinner = (Spinner) itemView.findViewById(R.id.prd_quantity);
            pos=0;
            spinneritemselected=0;
            data="";
            mMyHelper = new MyHelper(itemView.getContext(),"SURYADB", null,1);
            mSQLiteDB = mMyHelper.getWritableDatabase();
            //mSQLiteDB.delete("ProdForCheckout", null,null);
            cv= new ContentValues();

            /*prodforCheckouts= new ArrayList<>();
            data= new ProdforCheckout();*/


        }

        public void bind(Product product) {

                name.setText(product.getName());
                price.setText(String.valueOf(product.getPrice())+"$");
                List<String> list= new ArrayList<>();
                list.add("0");
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                ArrayAdapter<String> qAdapter= new ArrayAdapter<>(itemView.getContext(),android.R.layout.simple_spinner_item,list);
                qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(qAdapter);

            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(img);

            //Log.d("AvengerIMG",""+product.getImage());


        }
        @Override
        public void onClick(View view) {
            view.isActivated();
            quantity.setVisibility(View.VISIBLE);  //only visible on bike click
            spinner.setVisibility(View.VISIBLE);
            pos =getLayoutPosition();
            //Log.d("CheckoutProduct", "onClick: "+name.getText().toString()+"  "+price.getText().toString().substring(0,price.getText().toString().length()-1));
            final Intent intent = new Intent(itemView.getContext(), MainActivity.class);
            //setting detals for checkout to a custom object data
            //data=data+pos+"%";
            cv.put("ProductName",name.getText().toString());
            cv.put("ProductPrice",price.getText().toString().substring(0,price.getText().toString().length()-1));
            //Toast.makeText(itemView.getContext(),"Clicked "+pos, Toast.LENGTH_SHORT).show();
            Log.d("clicked", "clicked: "+pos);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //getting and setting quantity of product
                    spinneritemselected= Integer.parseInt(spinner.getSelectedItem().toString());
                    cv.put("ProductQuant",spinneritemselected);
                    Log.d("Avengerspinner", "onItemSelected: "+spinneritemselected+" pos "+pos);
                    long id = mSQLiteDB.insert("ProdForCheckout",null,cv);

                    Log.d("Avengerspinner", "column inserted: "+id);
                    //intent.putExtra("ProdforCheckout", data);
                    //list.add(data);
                    //Log.d("Avengerspinner", "onCreate: "+list);
                    //PreferenceManager.getDefaultSharedPreferences(itemView.getContext()).edit().putStringSet("MYLABEL",list ).apply();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            /*for(int i=0;i<prodforCheckouts.size();i++){
                Log.d("mainactivity", "onCreate: "+prodforCheckouts.get(i).getProductName());}
            Log.d("after product", "onClick: I have added products for checkout");*/




        }
    }


    public ProductListAdapter(List<Product> data, Context context){
        for(int i=0;i<data.size();i++){
            if(data.get(i).getType().equalsIgnoreCase("bike")){
                this.dataSet.add(data.get(i));
                //Log.d("count", "ProductListAdapter count: "+i+" "+data.get(i).getType());
            }
        }
        this.context=context;

    }


    @NonNull
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.MyViewHolder myViewHolder, int position) {
            myViewHolder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
