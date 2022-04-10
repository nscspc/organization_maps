package com.example.startup_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.startup_project.Models.OrganizationModel;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrganizationInfo extends AppCompatActivity {

    EditText OrganizationName;
    EditText OrganizationAddress;
//    EditText Organizationimgname;
    ImageView mapImage;
    Button submit;
    ImageView OrganizationMapImage;

    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    Boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_info);

        OrganizationName = findViewById(R.id.OrganizationName);
        OrganizationAddress = findViewById(R.id.OrganizationAddress);
        mapImage = findViewById(R.id.imageButton);
        submit = findViewById(R.id.UploadInfo);
        OrganizationMapImage = findViewById(R.id.OrganizationMapImage);

//        Organizationimgname = findViewById(R.id.imagenameorg);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
//        dialog = new ProgressDialog(getContext());


        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final StorageReference reference = storage.getReference().child("Organizations").child(FirebaseAuth.getInstance().getUid())
                            .child(new Date().getTime() + "");
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    OrganizationModel organizationModel = new OrganizationModel();
                                    organizationModel.setOrgImage(uri.toString());
                                    organizationModel.setOrganizationID(FirebaseAuth.getInstance().getUid());
                                    organizationModel.setOrganizationAddress(OrganizationAddress.getText().toString());
                                    organizationModel.setSubmittedAt(new Date().getTime());
                                    organizationModel.setOrganizationName(OrganizationName.getText().toString());

//                                organizationModel.setImage1(R.drawable.ic_baseline_search_24);

                                    database.getReference().child("Organizations")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(organizationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
//                                        dialog.dismiss();
                                            Toast.makeText(OrganizationInfo.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                                            //push() function to push more then one value in the node.
                                        }
                                    });
                                }
                            });
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
            OrganizationMapImage.setImageURI(uri);
            OrganizationMapImage.setVisibility(View.VISIBLE);

//            postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.follow_btn_bg));
//            postbtn.setTextColor(getContext().getResources().getColor(R.color.white));
//            postbtn.setEnabled(true);
        }

    }



}