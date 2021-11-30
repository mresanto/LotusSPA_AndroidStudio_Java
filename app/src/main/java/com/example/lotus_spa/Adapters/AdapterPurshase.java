package com.example.lotus_spa.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterPurshase extends RecyclerView.Adapter<AdapterPurshase.MyViewHolder> {

    private static final String TAG = "AdapterPurshase";

    private Context mContext;
    private List<Product> products;
    private ItemClickListener mItemListener;
    public ImageView mAddProduct;

    public AdapterPurshase(Context mContext, List<Product> products, ItemClickListener mItemListener) {
        this.mContext = mContext;
        this.products = products;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_grid_product_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPurshase.MyViewHolder holder, int position) {

        holder.nameproduct.setText(products.get(position).getProdName());
        //Add image
        holder.imageproduct.setImageResource(R.drawable.ic_launcher_foreground);

        holder.btnadd.setOnClickListener(v ->{
            mItemListener.onAddClick(products.get(position));
        });

        holder.itemView.setOnClickListener(v -> {
            mItemListener.onItemClick(products.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface ItemClickListener{
        void onItemClick(Product product);
        void onAddClick(Product product);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameproduct;
        ImageView imageproduct;
        ImageView btnadd;

        public MyViewHolder(View itemView){
            super(itemView);

            nameproduct = (TextView) itemView.findViewById(R.id.txtProductNameDisplayGrid);
            imageproduct = (ImageView) itemView.findViewById(R.id.imgProductDisplayGrid);
            btnadd = (ImageView) itemView.findViewById(R.id.img_addproduct);
        }
    }
}
