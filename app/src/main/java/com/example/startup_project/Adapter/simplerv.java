package com.example.startup_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.startup_project.ImageOpened;
import com.example.startup_project.R;

import java.util.ArrayList;

public class simplerv extends RecyclerView.Adapter<simplerv.viewholder> {

    ArrayList<String> list;
    Context context;
    String orgid;
    String orgname;

    public simplerv(ArrayList<String> list,String orgid,String orgname ,Context context) {
        this.list = list;
        this.orgid=orgid;
        this.orgname=orgname;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_places_design,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        String name=list.get(position);

        holder.imagenames.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageOpened.class);
                intent.putExtra("orgid",orgid);
                intent.putExtra("orgname",orgname);
                intent.putExtra("imgname",holder.imagenames.getText());
//                intent.putExtra("no","1");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView imagenames;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imagenames = itemView.findViewById(R.id.textNAME);

        }
    }

}
