package com.example.foodmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
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



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMyActivity(MainActivity.class);
    }



    private void showData(int flag, String[] result) {

        db.collection("Posts")
                .orderBy("createdAt", Query.Direction.DESCENDING) // show from the recent posts
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //show data
                        for (DocumentSnapshot doc : task.getResult()) {

                            WriteInfo writeInfo = new WriteInfo(
                                    doc.getString("posts_id"),
                                    doc.getString("nickname"),
                                    doc.getString("title"),
                                    doc.getString("contents"),
                                    doc.getString("publisher"),
                                    doc.getString("selectedCategory"),
                                    doc.getLong("numOfRecruits").intValue(),
                                    doc.getTimestamp("createdAt"),
                                    doc.getString("status"),
                                    doc.getLong("curRecruits").intValue(),
                                    (ArrayList<String>) doc.get("participants"));

                            if(flag==0){ // recruiting
                                if(writeInfo.getStatus().equals(result[0])&&writeInfo.getSelectedCategory().equals(result[1])){
                                    writeInfoList.add(writeInfo);
                                }
                            }
                            else if(flag==1&&writeInfo.getParticipants().contains(mAuth.getUid())){ // recruited
                                if(writeInfo.getStatus().equals(result[0])){
                                    writeInfoList.add(writeInfo);
                                }
                            }
                            else if(flag==2){ // settings
                                if(writeInfo.getStatus().equals(result[0])&&writeInfo.getParticipants().contains(mAuth.getUid())){
                                    writeInfoList.add(writeInfo);
                                }
                            }
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


    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_list);
        mContext = this;



        //init firestore
        db = FirebaseFirestore.getInstance();

        writeInfoList.clear();
        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        TextView txt_category = findViewById(R.id.txt_category);
        //show data in recyclerview
        int flag=intent.getExtras().getInt("Flag");
        if (flag==0){ // recruiting
            String status=intent.getExtras().getString("Status");
            String category=intent.getExtras().getString("Category");
            txt_category.setText(category);
            String[] result={status,category};
            showData(flag,result);
        }
        else if(flag==1){ // recruited
            String status=intent.getExtras().getString("Status");
            String[] result={status};
            showData(flag,result);
        }
        else if(flag==2){ // settings
            String status=intent.getExtras().getString("Status");
            String[] result={status};
            showData(flag,result);
        }



    }

    private void startMyActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}