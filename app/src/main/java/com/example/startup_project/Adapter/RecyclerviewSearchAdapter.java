package com.example.startup_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.startup_project.Models.OrganizationModel;
import com.example.startup_project.R;
import com.example.startup_project.open_on_click_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class RecyclerviewSearchAdapter extends FirebaseRecyclerAdapter<OrganizationModel,RecyclerviewSearchAdapter.viewholder> {

    Context context;

    public RecyclerviewSearchAdapter(@NonNull FirebaseRecyclerOptions<OrganizationModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull OrganizationModel model) {
        holder.name.setText(model.getOrganizationName());
        holder.address.setText(model.getOrganizationAddress());
        Picasso.get()
                .load(model.getOrgImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , open_on_click_item.class);
                intent.putExtra("organizationID",model.getOrganizationID());
                intent.putExtra("organizationName",model.getOrganizationName());
                intent.putExtra("organizationImage",model.getOrgImage());
//                intent.putExtra("userName",model.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,address;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nametext);
            address = itemView.findViewById(R.id.addresstext);
            img = itemView.findViewById(R.id.orgimguploaded);

        }
    }


}
