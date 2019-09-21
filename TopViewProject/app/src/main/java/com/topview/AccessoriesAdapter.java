package com.topview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.MyViewHolder> {

    private List<Product> dataSet =new ArrayList<>();
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        public MyViewHolder(@NonNull View itemView) {
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
        }

        public void bind(Product product){
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
            //Toast.makeText(itemView.getContext(),"Clicked "+pos +" and quantity "+spinneritemselected, Toast.LENGTH_SHORT).show();
            Log.d("clicked", "clicked: "+pos);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //getting and setting quantity of product
                    spinneritemselected= Integer.parseInt(spinner.getSelectedItem().toString());
                    cv.put("ProductQuant",spinneritemselected);
                    Log.d("Avengerspinner2", "onItemSelected: "+spinneritemselected+" pos "+pos);
                    long id = mSQLiteDB.insert("ProdForCheckout",null,cv);

                    //adding custom object to list
                    //prodforCheckouts.add(data);
                    Log.d("Avengerspinner2", "column inserted: "+id);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }
    public AccessoriesAdapter(List<Product> data, Context context){
        for(int i=0;i<data.size();i++){
            if(!data.get(i).getType().equalsIgnoreCase("bike") ){
                this.dataSet.add(data.get(i));
                //Log.d("count", "ProductListAdapter count: "+i+" "+data.get(i).getType());
            }
        }
        this.context=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product,viewGroup,false);
        AccessoriesAdapter.MyViewHolder myViewHolder=new AccessoriesAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
