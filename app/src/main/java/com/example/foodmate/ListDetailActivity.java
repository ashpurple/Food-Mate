package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //listactivity의 show data를 가져오자
//        ((ListActivity)ListActivity.mContext).showData();

        TextView title = (TextView)findViewById(R.id.title);
        TextView nickname = (TextView)findViewById(R.id.nickname);
        TextView category = (TextView)findViewById(R.id.category);
        TextView created_at = (TextView)findViewById(R.id.created_At);
        ImageButton user_menu = (ImageButton)findViewById(R.id.user_menu);
        TextView contents = (TextView)findViewById(R.id.contents);
        ImageButton comment = (ImageButton)findViewById(R.id.comment);
//        TextView num_recruit = (TextView)findViewById(R.id.num_recruit;
        TextView status = (TextView)findViewById(R.id.status);
        TextView peopleNum = (TextView)findViewById(R.id.peopleNum);




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


        int age = intent.getExtras().getInt("");


//        intent.putExtra("status",writeInfoList.get(position).getStatus());
//        intent.putExtra("curRecruits",writeInfoList.get(position).getCurRecruits());
//
////        intent.putExtra("nickname",writeInfoList.get(position).getNickname());
//        intent.putExtra("title",writeInfoList.get(position).getTitle());
//        intent.putExtra("contents",writeInfoList.get(position).getContents());
//        intent.putExtra("uid(publisher)",writeInfoList.get(position).getPublisher());
//        intent.putExtra("selectedCategory",writeInfoList.get(position).getSelectedCategory());
//        intent.putExtra("numOfRecruit",writeInfoList.get(position).getNumOfRecruits());
//        intent.putExtra("created_at",writeInfoList.get(position).getCreatedAt());
//


        //각각의 textview에 set
        title.setText(txt_title);
        nickname.setText(txt_nickname);
        category.setText(txt_selectedCategory);
        created_at.setText(txt_createdAt);
        contents.setText(txt_contents);
        status.setText(txt_status);
        peopleNum.setText(int_curRecruits + "/" + int_numOfRecruits);

        // int_numOfRecuirts, 작성 시간 안나옴



   }
}
