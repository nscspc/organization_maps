package com.example.startup_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.startup_project.Adapter.RecyclerviewSearchAdapter;
import com.example.startup_project.Adapter.RvAdapterPlaces;
import com.example.startup_project.Adapter.simplerv;
import com.example.startup_project.Models.OrganizationModel;
import com.example.startup_project.Models.pathinmap;
import com.example.startup_project.databinding.ActivityOpenOnClickItemBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.jar.Attributes;

public class open_on_click_item extends AppCompatActivity {

    ActivityOpenOnClickItemBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri uri;
    ArrayList<String> imagesNames = new ArrayList<>();
    RecyclerView recyclerView;
//    RvAdapterPlaces adapter;

    String organizationName;
    String organizationId;
    String organizationImg;

    simplerv simpleadapter;

//    Toolbar toolbar;
//    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpenOnClickItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();

//        (getSupportActionBar()).invalidateOptionsMenu();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        organizationId = getIntent().getStringExtra("organizationID");
        organizationName = getIntent().getStringExtra("organizationName");
        organizationImg = getIntent().getStringExtra("organizationImage");

        getSupportActionBar().setTitle(organizationName);
        Picasso.get()
                .load(organizationImg)
                .placeholder(R.drawable.placeholder)
                .into(binding.newimageset);
//        binding.openToolbar.setTitle(organizationName);
//        binding.textOid.setText(organizationId);
//        binding.textOname.setText(organizationName);

        database.getReference().child("Organizations")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //loop will run till it receives children from snapshot.
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            OrganizationModel model = dataSnapshot.getValue(OrganizationModel.class);
                            try {

                                if (((model.getOrganizationID() != null) && (model.getOrganizationName() != null)) && ((model.getOrganizationID().equals(organizationId)) && (model.getOrganizationName().equals(organizationName)))) {
                                    Picasso.get()
                                            .load(model.getOrgImage())
                                            .placeholder(R.drawable.ic_baseline_search_24)
                                            .into(binding.newimageset);
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            messagesModels.add(model);
                        }
//                        chattingMessagingAdapter.notifyDataSetChanged();//to update recyclerview at runtime.
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//        Uri uri = Uri.parse("android.resource://com.example.startup_project/res/drawable/ic_baseline_search_24.xml");

//        binding.addImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,10);
//            }
//        });
//
//        binding.getImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatabaseReference myReference = database.getReference("posts").child("ID").child("Name");
//
//
//                myReference.child("imagename").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                                String title = dataSnapshot1.getValue(String.class); //your Object
//                                Picasso.get()
//                                        .load(title)
//                                        .placeholder(R.drawable.ic_baseline_search_24)
//                                        .into(binding.newimageset);
////                                Toast.makeText(open_on_click_item.this, title, Toast.LENGTH_SHORT).show();
//
//                            }
//                            //set your adapter here
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//            }
//        });
//

        DatabaseReference myReference = database.getReference("posts").child(organizationId).child(organizationName);

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                        String imagename = dataSnapshot1.getKey();
                        imagesNames.add(imagename);

//                        Toast.makeText(open_on_click_item.this, imagename, Toast.LENGTH_LONG).show();

                        myReference.child(imagename).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){
                                    for (DataSnapshot dataSnapshot2: snapshot.getChildren()) {

                                        String title = dataSnapshot2.getValue(String.class); //your Object
                                        Picasso.get()
                                                .load(title)
                                                .placeholder(R.drawable.ic_baseline_search_24)
                                                .into(binding.newimageset);
//                                        Toast.makeText(open_on_click_item.this, title, Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(open_on_click_item.this, imagename, Toast.LENGTH_LONG).show();

                                        simpleadapter = new simplerv(imagesNames,organizationId,organizationName,open_on_click_item.this);
                                        recyclerView.setAdapter(simpleadapter);

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
                                                   if (dataSnapshot.exists()) {
                                                       for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                           String imagename = dataSnapshot1.getKey();

                                                           imagesNames.add(imagename);

//                        Toast.makeText(open_on_click_item.this, imagename, Toast.LENGTH_LONG).show();

                                                           myReference.child(imagename).addValueEventListener(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                   if (snapshot.exists()) {
                                                                       for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {

                                                                           String title = dataSnapshot2.getValue(String.class); //your Object
//                                                                           Picasso.get()
//                                                                                   .load(title)
//                                                                                   .placeholder(R.drawable.ic_baseline_search_24)
//                                                                                   .into(binding.newimageset);
//                                        Toast.makeText(open_on_click_item.this, title, Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(open_on_click_item.this, imagename, Toast.LENGTH_LONG).show();

                                                                           simpleadapter = new simplerv(imagesNames, organizationId, organizationName, open_on_click_item.this);
                                                                           recyclerView.setAdapter(simpleadapter);

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
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }

                                           });


        recyclerView = findViewById(R.id.rv_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        simpleadapter = new simplerv(imagesNames,organizationId,organizationName,open_on_click_item.this);
        recyclerView.setAdapter(simpleadapter);

//        FirebaseRecyclerOptions<String> options =
//                new FirebaseRecyclerOptions.Builder<String>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference("posts").child("ID").child("Name"), String.class)
//                        .build();
//
////        FirebaseRecyclerOptions<String> options2=imagesNames;
//
//        adapter = new RvAdapterPlaces(options,open_on_click_item.this);
//        recyclerView.setAdapter(adapter);



    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search_menu,menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processsearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processsearch(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    private void processsearch(String s) {
//
//        recyclerView = findViewById(R.id.rv_places);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<String> options =
//                new FirebaseRecyclerOptions.Builder<String>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts").child("ID").child("Name").startAt(s).endAt(s+"\uf8ff"), String.class)
//                        .build();
//
//        adapter = new RvAdapterPlaces(options,open_on_click_item.this);
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.search);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<String> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (String item : imagesNames) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
//            adapter.filterList(filteredlist);
            simpleadapter = new simplerv(filteredlist,organizationId,organizationName,open_on_click_item.this);
            recyclerView.setAdapter(simpleadapter);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData()!=null)
        {
            uri=data.getData();
        }

    }

}