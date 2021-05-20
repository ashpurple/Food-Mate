package com.example.foodmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG="ListActivity";
    private FirebaseAuth mAuth;
    public static Context mContext;

    List<WriteInfo> writeInfoList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;

    //firestore instance
    FirebaseFirestore db;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mContext = this;

        //init firestore
        db = FirebaseFirestore.getInstance();


        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);
        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //show data in recyclerVeiw
        showData();



    }

    public void showData() {

        db.collection("Posts")
                .orderBy("createdAt", Query.Direction.DESCENDING) // createdAt을 기준으로 내림차순으로 보이기
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //show data
                        for(DocumentSnapshot doc:task.getResult()){

                            WriteInfo writeInfo = new WriteInfo(
                                    doc.getString("nickname"),
                                    doc.getString("title"),
                                    doc.getString("contents"),
                                    doc.getString("publisher"),
                                    doc.getString("selectedCategory"),
                                    doc.getLong("numOfRecruits").intValue(),
                                    doc.getTimestamp("createdAt"),
                                    doc.getString("status"),
                                    doc.getLong("curRecruits").intValue());


                            writeInfoList.add(writeInfo);
                        }
                        //adapter
                        adapter = new CustomAdapter(ListActivity.this, writeInfoList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        startToast(e.getMessage());
                    }
                });
    }




    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}