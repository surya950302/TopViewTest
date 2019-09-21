package com.topview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    private final MyHelper mMyHelper;
    private final SQLiteDatabase mSQLiteDB;
    private Cursor c;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView cost;
        TextView quant;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name= (TextView) itemView.findViewById(R.id.name);
            this.cost= (TextView) itemView.findViewById(R.id.price);
            this.quant= (TextView) itemView.findViewById(R.id.quant);
         }
        public void bind(Cursor cursor){
            Log.d("cursor", "pname: "+cursor.getString(cursor.getColumnIndex("ProductName")));
            //setting vals to textviews
            name.setText(cursor.getString(cursor.getColumnIndex("ProductName")));
            Double prodcost=Double.parseDouble(cursor.getString(cursor.getColumnIndex("ProductPrice")));
            int q=cursor.getInt(cursor.getColumnIndex("ProductQuant"));
            prodcost=prodcost*q;
            Log.d("cursor", "pcostfinal: "+prodcost);
            Log.d("cursor", "quant: "+cursor.getInt(cursor.getColumnIndex("ProductQuant")));
            cost.setText("$"+prodcost);
            quant.setText("( $"+cursor.getString(cursor.getColumnIndex("ProductPrice"))+" * "+cursor.getInt(cursor.getColumnIndex("ProductQuant"))+")");
        }
    }
    public CartAdapter(Context context){
        this.context= context;
        mMyHelper = new MyHelper(context,"SURYADB", null,1);
        mSQLiteDB = mMyHelper.getWritableDatabase();
        c= mSQLiteDB.query("ProdForCheckout",null,null,null,null,null,null);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart,viewGroup,false);
        CartAdapter.MyViewHolder myViewHolder=new CartAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder myViewHolder, int i) {
        c.moveToNext();
        myViewHolder.bind(c);

    }

    @Override
    public int getItemCount() {
        Log.d("count", "getItemCount: "+c.getCount());
        return c.getCount();
    }


}
