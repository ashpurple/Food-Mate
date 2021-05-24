package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodmate.pushNoti.SendMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListDetailActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> participants;
    String posts_id;
    int int_numOfRecruits, int_curRecruits;
    TextView peopleNum;
    TextView status;
    boolean isJoined = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.title);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView category = (TextView) findViewById(R.id.category);
        TextView created_at = (TextView) findViewById(R.id.created_At);
        TextView contents = (TextView) findViewById(R.id.contents);
        Button btn_join = findViewById(R.id.btn_join);
        EditText host_comment = findViewById(R.id.host_comment);
        status = (TextView) findViewById(R.id.status);
        peopleNum = (TextView) findViewById(R.id.peopleNum);


        String txt_title = intent.getExtras().getString("title");
        String txt_nickname = intent.getExtras().getString("nickname");
        String txt_contents = intent.getExtras().getString("contents");
        String txt_publisher = intent.getExtras().getString("publisher");
        String txt_selectedCategory = intent.getExtras().getString("selectedCategory");
        String txt_createdAt = intent.getExtras().getString("created_at");
        String txt_status = intent.getExtras().getString("status");
        int_numOfRecruits = intent.getExtras().getInt("numOfRecruits");
        int_curRecruits = intent.getExtras().getInt("curRecruits");
        participants = intent.getExtras().getStringArrayList("participants");
        posts_id = intent.getExtras().getString("posts_id");

        DocumentReference postRef = db.collection("Posts").document(posts_id);


        title.setText(txt_title);
        nickname.setText(txt_nickname);
        category.setText(txt_selectedCategory);
        created_at.setText(txt_createdAt);
        contents.setText(txt_contents);
        status.setText(txt_status);
        peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);



        if (txt_status.equals("recruiting")) {
            // if the user is in the participants list, change isJoin as true
            if (participants.contains(user.getUid())) {
                isJoined = true;
            }


            btn_join = findViewById(R.id.btn_join);
            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt_publisher != user.getUid()) {
                        joinIn(user.getUid()); // participants uid
                    } else {
                        startToast("내가 작성한 글입니다!");
                    }
                }
            });
        } else if (txt_status.equals("recruited")) {
            if (!txt_publisher.equals(user.getUid())) { // if the user is NOT a host user
                btn_join.setVisibility(View.INVISIBLE); // hide the button
            } else { // if the user IS a host user
                btn_join.setText("배달 완료");
                host_comment.setVisibility(View.VISIBLE); // Open the host comments (EditText)
                btn_join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hostComment=host_comment.getText().toString();
                        postRef.update("status", "delivered");//update the firestore status
                        // push notification
                        String msgTitle = "'" + txt_title + "' 게시물 배달 완료 알림";
                        String msgContent = hostComment;
                        SendMessage sendMessage = new SendMessage(participants, msgTitle, msgContent);
                        startToast("참여자들에게 배달 완료 푸시 알림을 보냈습니다.");
                    }
                });
            }

        } else {
            btn_join.setVisibility(View.INVISIBLE);
        }

    }

    private void joinIn(String uid) {
        // current post's id : posts_id
        DocumentReference postRef = db.collection("Posts").document(posts_id);
        postRef = db.collection("Posts").document(posts_id);

        Intent intent = getIntent();
        if (int_numOfRecruits != int_curRecruits) {


            if (isJoined) {
                startToast("이미 참여한 글입니다.");
            } else {
                // add current participants uid to the firestore reference
                postRef.update("participants", FieldValue.arrayUnion(uid));
                startToast("참여 완료되었습니다!");
                isJoined = true;
                participants = intent.getExtras().getStringArrayList("participants");
                participants.add(uid); // add participants uid to arraylist


                postRef.update("curRecruits", FieldValue.increment(1)); // increase curRecruits in firestore
                ++int_curRecruits; // increase curRecruits in local
                peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);

                if (int_numOfRecruits == int_curRecruits) {
                    postRef.update("status", "recruited");// update firestore status
                    status.setText("recruited");

                    // notification all participants recruited
                    String title = intent.getExtras().getString("title");
                    int number = intent.getExtras().getInt("numOfRecruits");
                    String msgTitle = "'" + title + "' 게시물 모집완료 알림";
                    String msgContent = number + "명 모집이 완료되었습니다!";
                    SendMessage sendMessage = new SendMessage(participants, msgTitle, msgContent);
                }

                Log.d(TAG, "DocumentSnapshot successfully updated!");

            }
        } else {
            postRef.update("status", "recruited");// update firestore status
            status.setText("recruited");
            startToast("모집이 완료된 글입니다.");
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}