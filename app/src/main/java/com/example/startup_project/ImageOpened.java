package com.example.startup_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.startup_project.Adapter.simplerv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ImageOpened extends AppCompatActivity {

    FirebaseDatabase database;
    ImageView imageopened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_opened);

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

        imageopened = findViewById(R.id.imageopened);

        String organizationId = getIntent().getStringExtra("orgid");
        String organizationName = getIntent().getStringExtra("orgname");
        String imgName = getIntent().getStringExtra("imgname");


        DatabaseReference myReference = database.getReference("posts").child(organizationId).child(organizationName);

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                        String imagename = dataSnapshot1.getKey();

                        Toast.makeText(ImageOpened.this, imagename, Toast.LENGTH_LONG).show();

                        myReference.child(imgName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){
                                    for (DataSnapshot dataSnapshot2: snapshot.getChildren()) {

                                        String title = dataSnapshot2.getValue(String.class); //your Object
                                        Picasso.get()
                                                .load(title)
                                                .placeholder(R.drawable.ic_baseline_search_24)
                                                .into(imageopened);
//                                        Toast.makeText(open_on_click_item.this, title, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(ImageOpened.this, imgName, Toast.LENGTH_LONG).show();

//                                        simpleadapter = new simplerv(imagesNames,open_on_click_item.this);
//                                        recyclerView.setAdapter(simpleadapter);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    //set your adapter here

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference myReference2 = database.getReference("Organizations").child(organizationId).child("mapImage");

        myReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                        String imagename = dataSnapshot1.getKey();

                        Toast.makeText(ImageOpened.this, imagename, Toast.LENGTH_LONG).show();

//                        myReference2.child(imgName).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                if (snapshot.exists()){
//                                    for (DataSnapshot dataSnapshot2: snapshot.getChildren()) {



                                        String title = dataSnapshot1.getValue(String.class); //your Object
                                        Picasso.get()
                                                .load(title)
                                                .placeholder(R.drawable.ic_baseline_search_24)
                                                .into(imageopened);
//                                        Toast.makeText(open_on_click_item.this, title, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(ImageOpened.this, imgName, Toast.LENGTH_LONG).show();

//                                        simpleadapter = new simplerv(imagesNames,open_on_click_item.this);
//                                        recyclerView.setAdapter(simpleadapter);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
        });



    }
}