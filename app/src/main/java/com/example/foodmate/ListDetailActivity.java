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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListDetailActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList participants;
    String posts_id;
    int int_numOfRecruits, int_curRecruits;
    TextView peopleNum;




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
        TextView status = (TextView) findViewById(R.id.status);
        peopleNum = (TextView) findViewById(R.id.peopleNum);

        Button btn_join = findViewById(R.id.btn_join);


        // getIntent로 customAdapter에서 전달받은 값 가져오기


        String txt_title = intent.getExtras().getString("title");
        //String txt_nickname = intent.getExtras().getString("nickname");
        String txt_nickname = "익명";
        String txt_contents = intent.getExtras().getString("contents");
        String txt_publisher = intent.getExtras().getString("uid(publisher)");
        String txt_selectedCategory = intent.getExtras().getString("selectedCategory");
        String txt_createdAt = intent.getExtras().getString("created_at");
        String txt_status = intent.getExtras().getString("status");
        int_numOfRecruits = intent.getExtras().getInt("numOfRecruits");
        int_curRecruits = intent.getExtras().getInt("curRecruits");
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

            }
        });


    }

    private void joinIn(String uid) {

        // 현재 게시글의 id : posts_id
        DocumentReference postref = db.collection("Posts").document(posts_id);

        if(int_numOfRecruits != int_curRecruits) {
            //participants arraylist에 join하는사람 uid 추가
            postref.update("participants", FieldValue.arrayUnion(uid));


            //개인 유저의 참여한 게시글에 추가
            //user collection을 만들어서 필드에 참여한 게시글 추가..?


            //current 사람 추가
            int_curRecruits++;
            postref.update("curRecruits", FieldValue.increment(1));
            peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);

//            peopleNum = (TextView) findViewById(R.id.peopleNum);
        }else{
            startToast("모집이 완료된 글입니다.");
        }


    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
