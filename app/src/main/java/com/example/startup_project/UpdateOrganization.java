package com.example.startup_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateOrganization extends AppCompatActivity {

    EditText OrganizationName;
    EditText OrganizationAddress;
    EditText Organizationimgname;
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
        setContentView(R.layout.activity_update_organization);

        OrganizationName = findViewById(R.id.OrganizationName2);
        OrganizationAddress = findViewById(R.id.OrganizationAddress2);
        mapImage = findViewById(R.id.imageButton2);
        submit = findViewById(R.id.UploadInfo2);
        OrganizationMapImage = findViewById(R.id.OrganizationMapImageWithoutPaths);

        Organizationimgname = findViewById(R.id.imagenameorg2);

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


                try {

                    final StorageReference reference2 = storage.getReference().child("Organizations").child(FirebaseAuth.getInstance().getUid())
                            .child(new Date().getTime() + "");
                    reference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String newimg = Organizationimgname.getText().toString();

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Organizations")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("mapImage");
                                    Map<String, Object> updates = new HashMap<String, Object>();
                                    updates.put(newimg, uri.toString());

                                    ref.updateChildren(updates);

                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

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