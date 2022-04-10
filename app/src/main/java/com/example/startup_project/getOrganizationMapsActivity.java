package com.example.startup_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.startup_project.Models.pathinmap;
import com.example.startup_project.databinding.ActivityGetOrganizationMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class getOrganizationMapsActivity extends AppCompatActivity {

    ActivityGetOrganizationMapsBinding binding;
    FirebaseStorage storage;
    Uri uri;
    FirebaseDatabase database;

    ProgressDialog dialog;
    String nodename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetOrganizationMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

//        binding.uploadMaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                binding.imagemapupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,10);
                    }
                });

                binding.uploadMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final StorageReference reference = storage.getReference().child("posts").child(FirebaseAuth.getInstance().getUid())
                                .child(new Date().getTime() + "");
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                    @Override
                                                                                    public void onSuccess(Uri uri) {
//                                                                                        pathinmap post = new pathinmap();
//                                                                                        post.setPlace1(uri.toString());
                                                                                        nodename=binding.imagename.getText().toString();
//                                                                                        post.setPlace2(uri.toString());
//                                                                                        post.setPlace3(uri.toString());
//                                                                                        post.setPlace4(uri.toString());

                                                                                        database.getReference().child("posts")
                                                                                                .child(binding.getorgid.getText().toString())
                                                                                                .child(binding.getorgname.getText().toString())
                                                                                                .child(nodename)
                                                                                                .push()
                                                                                                .setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void unused) {
//                                dialog.dismiss();
                                                                                                Toast.makeText(getOrganizationMapsActivity.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                                                                                                //push() function to push more then one value in the node.
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                );
                            }
                        });

                    }
                });


            }


            @Override
            public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (data.getData()!=null)
                {
                    uri=data.getData();
                    binding.OrganizationMapImagePaths.setImageURI(uri);
                }

            }

}