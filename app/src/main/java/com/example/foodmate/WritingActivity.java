package com.example.foodmate;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class WritingActivity extends AppCompatActivity {
    public static final Integer UPLOAD_POST = 110;
    private FirebaseUser user;
    private String posts_id;
    private String nickname;
    Integer numOfRecruit;
    Button btn_upload;
    ImageButton btn_cancel;
    EditText maximum;
    String[] foodCategory;
    String selectedCategory;
    Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        foodCategory = getResources().getStringArray(R.array.food_category);
        Spinner spinner = (Spinner) findViewById(R.id.food_spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, foodCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                selectedCategory = foodCategory[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // upload button
        btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUpdate();
            }
        });
        // cancel button
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        maximum = findViewById(R.id.maximum);
        numOfRecruit = 0;

    }


    // check before upload the post
    private void postUpdate() {
        String title = ((EditText) findViewById(R.id.et_title)).getText().toString();
        String contents = ((EditText) findViewById(R.id.et_contents)).getText().toString();

        Timestamp created_at = new Timestamp(new Date());
        numOfRecruit = Integer.parseInt("" + maximum.getText());


        if (numOfRecruit == 0) {
            startToast("?????? ?????? ?????? ??????????????????.");
        }
        if (title.length() > 0 && contents.length() > 0) {
            user = FirebaseAuth.getInstance().getCurrentUser();


            nickname = "??????";
            String status = "recruiting";
            int curRecruits = 1;
            ArrayList participants = new ArrayList();
            String publisher = user.getUid();
            participants.add(publisher); //add writer(host)'s uid to the arraylist participants
            String postId = "tempID";

            // get user nickname from the Users
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference docRef = db.collection("Users").document(users.getUid());

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            nickname = document.getData().get("nickname").toString();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                }
            });


            //delay to getting user nickname from firestore
            handler.postDelayed(new Runnable() {
                public void run() {
                    startToast("????????? ????????????...");
                    WriteInfo writeInfo = new WriteInfo(postId, nickname, title, contents, publisher,
                            selectedCategory, numOfRecruit, created_at, status, curRecruits, participants);
                    postUploader(writeInfo);
                }
            }, 1000); // 1sec


        } else {
            startToast("?????? ?????? ????????? ??????????????????.");
        }

    }

    // upload the post
    private void postUploader(WriteInfo writeInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("Posts").add(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        posts_id = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        startToast("?????????????????????!");


                        // show the post right after the writing
                        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                        intent.putExtra("posts_id", posts_id); // send posts_id
                        startActivityForResult(intent, UPLOAD_POST);

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        startToast("????????? ?????????????????????.");
                    }
                });

    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}