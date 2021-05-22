package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodmate.pushNoti.SendMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ImageButton user_menu = (ImageButton) findViewById(R.id.user_menu);
        TextView contents = (TextView) findViewById(R.id.contents);
        ImageButton comment = (ImageButton) findViewById(R.id.comment);
        //        TextView num_recruit = (TextView)findViewById(R.id.num_recruit);
        status = (TextView) findViewById(R.id.status);
        peopleNum = (TextView) findViewById(R.id.peopleNum);

        Button btn_join = findViewById(R.id.btn_join);



        String txt_title = intent.getExtras().getString("title");
        //String txt_nickname = intent.getExtras().getString("nickname");
        String txt_nickname = "익명";
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

        //위에서 받아온 내용 각각의 textview에 setText
        title.setText(txt_title);
        nickname.setText(txt_nickname);
        category.setText(txt_selectedCategory);
        created_at.setText(txt_createdAt);
        contents.setText(txt_contents);
        status.setText(txt_status);
        peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);

        System.out.println("txt_publisher = "+txt_publisher);
        System.out.println("user.getUid = "+user.getUid());

        //participants 리스트에 있으면 joined 를 true로 바꾸기
        if(participants.contains(user.getUid())){
            isJoined = true;
        }


        System.out.println("join 누르기 전 호출: isJoined = "+isJoined);
        //참여하기 버튼 클릭
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_publisher!=user.getUid()) {
                    joinIn(user.getUid()); //참여자의 uid
                }else{
                    startToast("내가 작성한 글입니다!");
                }

            }
        });

    }

    private void joinIn(String uid) {
        // 현재 게시글의 id : posts_id
        DocumentReference postRef = db.collection("Posts").document(posts_id);


        Intent intent = getIntent();
        if(int_numOfRecruits != int_curRecruits) {


            if(isJoined){
                startToast("이미 참여한 글입니다.");
            }else{
                postRef.update("participants", FieldValue.arrayUnion(uid));//파이어스토어 participants에 참여자 uid 추가
                startToast("참여 완료되었습니다!");
                isJoined = true;
                participants = intent.getExtras().getStringArrayList("participants");
                participants.add(uid); // 참여자에 추가
                System.out.println("participants : "+ participants);
                postRef.update("curRecruits", FieldValue.increment(1)); //파이어스토어에서 1 추가

                ++int_curRecruits;
                peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);

                if(int_numOfRecruits == int_curRecruits) {
                    postRef.update("status", "recruited");//파이어스토어에서 status 업데이트
                    status.setText("recruited"); //일단 숫자 같아지면 텍스트를 바꿈
                    // 모집완료 알림
                    String title = intent.getExtras().getString("title");
                    int number = intent.getExtras().getInt("numOfRecruits");
                    String msgTitle="'"+title+"' 게시물 모집완료 알림";
                    String msgContent=number+"명 모집이 완료되었습니다!";
                    SendMessage sendMessage = new SendMessage(participants,msgTitle,msgContent);
                }

                Log.d(TAG, "DocumentSnapshot successfully updated!");

            }
            System.out.println("****************************");
            System.out.println("participants: "+ participants);
            System.out.println("participants.contains(uid) : "+ participants.contains(uid));

        }

        else{
            postRef.update("status", "recruited");//파이어스토어에서 status 업데이트
            status.setText("recruited"); //일단 숫자 같아지면 텍스트를 바꿈
            startToast("모집이 완료된 글입니다.");
        }

    }

    @Override
    public void onBackPressed(){
        finish();
    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}