package com.example.smartcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ModelClass> userList;
    public Adapter (List<ModelClass> userList){
        this.userList=userList;
    }
    @NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {
        String product=userList.get(position).getProduct();
        String quantity=userList.get(position).getQuantity();
        String price=userList.get(position).getPrice();
        holder.setData(product,quantity,price);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView product_text, price_text, quantity_text;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            product_text=itemView.findViewById(R.id.prod_name);
            quantity_text=itemView.findViewById(R.id.quantity);
            price_text=itemView.findViewById(R.id.Price);
        }

        public void setData(String product, String quantity, String price) {
            product_text.setText(product);
            quantity_text.setText(quantity);
            price_text.setText(price);
        }
    }
}
