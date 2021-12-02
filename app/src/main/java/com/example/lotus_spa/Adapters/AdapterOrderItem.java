package com.example.lotus_spa.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterOrderItem extends RecyclerView.Adapter<AdapterOrderItem.MyViewHolder>  {

    private Context mContext;
    private List<OrderItem> orderItems;
    private AdapterOrderItem mItemListener;
    public ImageView mAddProduct;


    public AdapterOrderItem(Context mContext, List<OrderItem> orderItems, AdapterOrderItem.ItemClickListener mItemListener){
        this.mContext = mContext;
        this.orderItems = orderItems;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterOrderItem.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_ordem_item_layout, parent, false);



        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterOrderItem.MyViewHolder holder, int position) {


        holder.nameitem.setText("Nome: "+orderItems.get(position).getProdName());
        //Add image
        holder.imageitem.setImageResource(R.drawable.ic_launcher_foreground);

        holder.priceitem.setText("Preço: "+orderItems.get(position).getItemUnitaryPrice());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public interface ItemClickListener{
        void onItemClick(Product product);
        void onAddClick(Product product);
    }

    public void removeItem(int position) {
        orderItems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(OrderItem item, int position) {
        orderItems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    private void removeItem(OrderItem orderItem){
        int currPosition = orderItems.indexOf(orderItem);
        orderItems.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameitem;
        TextView priceitem;
        ImageView imageitem;
        CheckBox checkitem;

        public MyViewHolder(View itemView){
            super(itemView);

            nameitem = (TextView) itemView.findViewById(R.id.txtprodnamedisplay);
            priceitem = (TextView) itemView.findViewById(R.id.txtprodpricedisplay);
            imageitem = (ImageView) itemView.findViewById(R.id.imgProductOrderItemDisplay);
            checkitem = (CheckBox) itemView.findViewById(R.id.checkOrderItem);
        }
    }
}
