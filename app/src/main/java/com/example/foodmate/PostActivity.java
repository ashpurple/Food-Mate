package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;


// 글쓰기 완료한 포스트를 보여주는 액티비티
public class PostActivity extends AppCompatActivity {
    private static final String TAG = "PostActivity";
    private String posts_id;
    private FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        TextView title = (TextView)findViewById(R.id.title);

        TextView nickname = (TextView)findViewById(R.id.nickname);
        TextView category = (TextView)findViewById(R.id.category);
        TextView created_at = (TextView)findViewById(R.id.created_At);
        ImageButton user_menu = (ImageButton)findViewById(R.id.user_menu);
        TextView contents = (TextView)findViewById(R.id.contents);
        ImageButton comment = (ImageButton)findViewById(R.id.comment);
        TextView num_comment = (TextView)findViewById(R.id.num_comment);
        //문서의 uid를 전달 받아서 해당 문서를 보여준다.
        Intent intent = getIntent();
        posts_id = intent.getStringExtra("posts_id");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Posts").document(posts_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String txt_title = document.getData().get("title").toString();
                        String txt_category = document.getData().get("selectedCategory").toString();
//                        String txt_nickname = document.getData().get("nickname").toString();
                        String txt_nickname = "익명"; //임시 닉네임
                        String txt_contents = document.getData().get("contents").toString();

                        Timestamp timestamp_createdAt = (Timestamp) document.getData().get("createdAt"); //파이어베이스 타임스탬프 받아오기
                        Date date_createdAt = timestamp_createdAt.toDate();//Date형식으로 변경
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 HH시 mm분 ss초");
                        String txt_createdAt = formatter.format(date_createdAt).toString();

                        //int int_num_comment = Integer.parseInt(String.valueOf(document.getData().get("num_comment")));
                        int int_num_comment = 0; //샘플 댓글 수
                        title.setText(txt_title);
                        nickname.setText(txt_nickname);
                        category.setText(txt_category);
                        created_at.setText(txt_createdAt);
                        contents.setText(txt_contents);
                        num_comment.setText(String.valueOf(int_num_comment));


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }


        });


    }


        private void toastMsg(String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
}
