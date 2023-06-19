package com.example.mcardiology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class recyclerDocConAdapter extends RecyclerView.Adapter<recyclerDocConAdapter.ViewHolder> {

    Context context;
    ArrayList<contactModel> arrContacts;
    recyclerDocConAdapter(Context context, ArrayList<contactModel> arrContacts){
        this.context = context;
        this.arrContacts = arrContacts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.doc_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(arrContacts.get(position).img);
        holder.name.setText(arrContacts.get(position).name);
        holder.num.setText(arrContacts.get(position).num);

    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, num;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.contact_img);
            name = itemView.findViewById(R.id.contact_name);
            num = itemView.findViewById(R.id.contact_num);

        }
    }
}
