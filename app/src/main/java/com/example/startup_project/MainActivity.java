package com.example.startup_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.startup_project.Adapter.RecyclerviewSearchAdapter;
import com.example.startup_project.Authentication.Login;
import com.example.startup_project.Models.OrganizationModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button Organization;
    RecyclerView recyclerView;
    RecyclerviewSearchAdapter adapter;
    Button uo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Organization Maps");


        recyclerView = findViewById(R.id.recyclerviewsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OrganizationModel> options =
                new FirebaseRecyclerOptions.Builder<OrganizationModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Organizations"), OrganizationModel.class)
                .build();

        adapter = new RecyclerviewSearchAdapter(options,MainActivity.this);
        recyclerView.setAdapter(adapter);

        Organization = findViewById(R.id.Organization);
        Organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OrganizationInfo.class);
                startActivity(intent);
            }
        });

        uo=findViewById(R.id.updateOrganization);
        uo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UpdateOrganization.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void processsearch(String s) {

        recyclerView = findViewById(R.id.recyclerviewsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OrganizationModel> options =
                new FirebaseRecyclerOptions.Builder<OrganizationModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Organizations").orderByChild("organizationName").startAt(s).endAt(s+"\uf8ff"), OrganizationModel.class)
                        .build();

        adapter = new RecyclerviewSearchAdapter(options,MainActivity.this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;

            case R.id.uploadOrganizationsMaps:
                Intent intent2 = new Intent(MainActivity.this,getOrganizationMapsActivity.class);
                startActivity(intent2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
