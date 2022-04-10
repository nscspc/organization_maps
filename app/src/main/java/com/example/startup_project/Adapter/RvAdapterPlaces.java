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

import com.example.startup_project.ImageOpened;
import com.example.startup_project.Models.OrganizationModel;
import com.example.startup_project.R;
import com.example.startup_project.open_on_click_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RvAdapterPlaces extends FirebaseRecyclerAdapter<String,RvAdapterPlaces.viewholder> {

    Context context;

    public RvAdapterPlaces(@NonNull FirebaseRecyclerOptions<String> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RvAdapterPlaces.viewholder holder, int position, @NonNull String model) {
        holder.name.setText(model);
//        holder.address.setText(model.getOrganizationAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , ImageOpened.class);
                intent.putExtra("imagename",model);
//                intent.putExtra("organizationName",model.getOrganizationName());
//                intent.putExtra("userName",model.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public RvAdapterPlaces.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_places_design,parent,false);
        return new RvAdapterPlaces.viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView name;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textNAME);
//            address = itemView.findViewById(R.id.addresstext);

        }
    }


}
