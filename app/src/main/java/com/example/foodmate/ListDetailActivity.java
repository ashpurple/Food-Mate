package com.example.foodmate;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        TextView num_comment = (TextView)findViewById(R.id.num_comment);


        title.setText(txt_title);
        nickname.setText(txt_nickname);
        category.setText(txt_category);
        created_at.setText(txt_createdAt);
        contents.setText(txt_contents);
        num_comment.setText(String.valueOf(int_num_comment));

    }
}
