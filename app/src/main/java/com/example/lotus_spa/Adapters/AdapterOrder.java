package com.example.lotus_spa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotus_spa.Class.Order;
import com.example.lotus_spa.Class.Reserve;
import com.example.lotus_spa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.MyViewHolder>{

    private Context mContext;
    private List<Order> orders;


    public AdapterOrder(Context mContext, List<Order> orders){
        this.mContext = mContext;
        this.orders = orders;
    }


    @NonNull
    @NotNull
    @Override
    public AdapterOrder.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_order_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterOrder.MyViewHolder holder, int position) {

        holder.dateitem.setText(orders.get(position).getOrdDate());
        holder.priceitem.setText(String.valueOf(orders.get(position).getTotalPrice()));
        holder.payOptionitem.setText(orders.get(position).getPayOption());
    }
    
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView dateitem, priceitem, payOptionitem;

        public MyViewHolder(View itemView){
            super(itemView);

            dateitem = (TextView) itemView.findViewById(R.id.txtOrdDateLayout);
            priceitem = (TextView) itemView.findViewById(R.id.txtOrdTotalPriceLayout);
            payOptionitem = (TextView) itemView.findViewById(R.id.txtOrdPayOptionLayout);

        }
    }
}
