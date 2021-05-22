package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

        //WritingActivity에서 posts_id 받기
        Intent intent = getIntent();
        posts_id = intent.getStringExtra("posts_id");
        //posts_id 필드 추가
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference db_post = db.collection("Posts").document(posts_id);
        db_post
                .update("posts_id", posts_id)// posts_id 필드를 WritingAvtivity 에서 받아온 id값으로 변경
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });



        TextView title = (TextView)findViewById(R.id.title);
        TextView nickname = (TextView)findViewById(R.id.nickname);
        TextView category = (TextView)findViewById(R.id.category);
        TextView created_at = (TextView)findViewById(R.id.created_At);
        ImageButton user_menu = (ImageButton)findViewById(R.id.user_menu);
        TextView contents = (TextView)findViewById(R.id.contents);
        ImageButton comment = (ImageButton)findViewById(R.id.comment);
        TextView num_comment = (TextView)findViewById(R.id.host_comment);
        TextView status = (TextView)findViewById(R.id.status);
        TextView peopleNum = (TextView)findViewById(R.id.peopleNum);

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
                        String txt_nickname = document.getData().get("nickname").toString();
                        String txt_contents = document.getData().get("contents").toString();
                        String txt_status = document.getData().get("status").toString();
                        String txt_totalPeople = document.getData().get("numOfRecruits").toString();
                        String txt_curPeople = document.getData().get("curRecruits").toString();
                        String txt_people=txt_curPeople+"/"+txt_totalPeople; // 참여 인원 텍스트
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
                        status.setText(txt_status);
                        peopleNum.setText(txt_people);


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
