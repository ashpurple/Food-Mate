package com.example.foodmate;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class WritingActivity extends AppCompatActivity {
    private FirebaseUser user;
    Integer numOfRecruit;
    Button btn_upload;
    ImageButton btn_cancel;
    EditText maximum;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }
//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        moveTaskToBack(true);
//
//    }
//    View.OnClickListener onClickListener= (v)->{
//
//        switch(v.getId()){
//            case R.id.btn_upload:
//                postUpdate();
//                break;
//            case R.id.btn_cancle:
//                finish();
//                break;
//
//        }
//
//    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);


        //등록하기 버튼 클릭
        btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUpdate();
            }
        });
        //취소하기 버튼 클릭
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




    //포스트 업로드 전 체크
    private void postUpdate(){
        String title = ((EditText)findViewById(R.id.et_title)).getText().toString();
        String contents = ((EditText)findViewById(R.id.et_contents)).getText().toString();
        numOfRecruit = Integer.parseInt(""+maximum.getText());

        if(numOfRecruit == 0 ){
            startToast("모집 인원 수를 확인해주세요.");
            System.out.println("여기여기여기여기 !!!!!!!!!!!! numOfRecruitment: "+numOfRecruit);
        }
        if(title.length() > 0 && contents.length() > 0){
            user = FirebaseAuth.getInstance().getCurrentUser();
            //모집인원 수

            WriteInfo writeInfo = new WriteInfo(title, contents, user.getUid(), numOfRecruit,  new Date());
            postUploader(writeInfo);


        }else{
            startToast("제목 또는 내용을 입력해주세요.");
        }

    }

    //파이어베이스 posts에 업로드
    private void postUploader(WriteInfo writeInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Posts").add(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: "+documentReference.getId());
                        startToast("등록되었습니다!");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        startToast("등록에 실패하였습니다.");
                    }
                });
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}