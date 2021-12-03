package com.example.lotus_spa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPackages extends RecyclerView.Adapter<AdapterPackages.MyViewHolder> {

    private static final String TAG = "AdapterPurshase";

    private Context mContext;
    private List<Packages> packages;
    private ItemClickListener mItemListener;


    public AdapterPackages(Context mContext, List<Packages> packages, ItemClickListener mItemListener) {
        this.mContext = mContext;
        this.packages = packages;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_grid_packages_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPackages.MyViewHolder holder, int position) {

        holder.namepackage.setText(packages.get(position).getPackname());
        holder.pricepackage.setText(packages.get(position).getPackprice());
        //Add image
        holder.imagepackage.setImageResource(R.drawable.ic_launcher_foreground);

        holder.itemView.setOnClickListener(v ->{
            mItemListener.onItemClick(packages.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public interface ItemClickListener{
        void onItemClick(Packages packages);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView namepackage;
        ImageView imagepackage;
        TextView pricepackage;

        public MyViewHolder(View itemView){
            super(itemView);

            namepackage = (TextView) itemView.findViewById(R.id.txtPackageNameDisplayGrid);
            imagepackage = (ImageView) itemView.findViewById(R.id.imgPackageDisplayGrid);
            pricepackage = (TextView) itemView.findViewById(R.id.txtPackagePriceDisplayGrid);
        }
    }
}
