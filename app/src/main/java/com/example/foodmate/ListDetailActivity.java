package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListDetailActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList participants;
    String posts_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        TextView title = (TextView) findViewById(R.id.title);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView category = (TextView) findViewById(R.id.category);
        TextView created_at = (TextView) findViewById(R.id.created_At);
        ImageButton user_menu = (ImageButton) findViewById(R.id.user_menu);
        TextView contents = (TextView) findViewById(R.id.contents);
        ImageButton comment = (ImageButton) findViewById(R.id.comment);
//        TextView num_recruit = (TextView)findViewById(R.id.num_recruit;
        TextView status = (TextView) findViewById(R.id.status);
        TextView peopleNum = (TextView) findViewById(R.id.peopleNum);

        Button btn_join = findViewById(R.id.btn_join);


        // getIntent로 customAdapter에서 전달받은 값 가져오기
        Intent intent = getIntent();

        String txt_title = intent.getExtras().getString("title");
        //String txt_nickname = intent.getExtras().getString("nickname");
        String txt_nickname = "익명";
        String txt_contents = intent.getExtras().getString("contents");
        String txt_publisher = intent.getExtras().getString("uid(publisher)");
        String txt_selectedCategory = intent.getExtras().getString("selectedCategory");
        String txt_createdAt = intent.getExtras().getString("created_at");
        String txt_status = intent.getExtras().getString("status");
        int int_numOfRecruits = intent.getExtras().getInt("numOfRecruits");
        int int_curRecruits = intent.getExtras().getInt("curRecruits");

        participants = intent.getExtras().getStringArrayList("participants");
        posts_id = intent.getExtras().getString("posts_id");



        //위에서 받아온 내용 각각의 textview에 setText
        title.setText(txt_title);
        nickname.setText(txt_nickname);
        category.setText(txt_selectedCategory);
        created_at.setText(txt_createdAt);
        contents.setText(txt_contents);
        status.setText(txt_status);
        peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);


        //참여하기 버튼 클릭
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinIn(user.getUid()); //참여자의 uid
                startToast("참여 완료되었습니다!");
            }
        });


    }

    private void joinIn(String uid) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference postref = db.collection("Posts").document(posts_id);
//        final String documentID = userDocument.documentID;

        //특정 arrayfield 업데이트
        // Atomically add a new region to the "regions" array field.
        postref.update("participants", FieldValue.arrayUnion("greater_virginia")); //여기 uid가 들어가야할것같은데 일단 버지니아 넣어보자

        //저 리스트를 파베에 넣어야함
        //현재 이 게시글의 id는 어케가져오지







        //개인 유저의 참여한 게시글에 추가


        //current 사람 추가

    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
