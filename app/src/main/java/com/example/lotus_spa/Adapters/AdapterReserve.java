package com.example.lotus_spa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotus_spa.Class.Reserve;
import com.example.lotus_spa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterReserve extends RecyclerView.Adapter<AdapterReserve.MyViewHolder>{

    private Context mContext;
    private List<Reserve> reserves;


    public AdapterReserve(Context mContext, List<Reserve> reserves){
        this.mContext = mContext;
        this.reserves = reserves;
    }


    @NonNull
    @NotNull
    @Override
    public AdapterReserve.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_reserve_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterReserve.MyViewHolder holder, int position) {

        holder.validityitem.setText(reserves.get(position).getResValidity());
        holder.priceitem.setText(reserves.get(position).getPayValue());
        //holder.amountitem.setText(String.valueOf(reserves.get(position).getResAmount()));
        holder.payOptionitem.setText(reserves.get(position).getPayOption());
    }
    
    @Override
    public int getItemCount() {
        return reserves.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView validityitem, priceitem, amountitem, payOptionitem;

        public MyViewHolder(View itemView){
            super(itemView);

            validityitem = (TextView) itemView.findViewById(R.id.txtResValidity);
            priceitem = (TextView) itemView.findViewById(R.id.txtResPrice);
            amountitem = (TextView) itemView.findViewById(R.id.txtResAmount);
            payOptionitem = (TextView) itemView.findViewById(R.id.txtResPayOption);

        }
    }
}
