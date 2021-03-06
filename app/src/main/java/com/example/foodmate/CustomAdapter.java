package com.example.foodmate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ListActivity listActivity;
    List<WriteInfo> writeInfoList;


    public CustomAdapter(ListActivity listActivity, List<WriteInfo> writeInfoList) {
        this.listActivity = listActivity;
        this.writeInfoList = writeInfoList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(itemView);


        // item clicked
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(view.getContext(),ListDetailActivity.class);
                intent.putExtra("nickname",writeInfoList.get(position).getNickname());
                intent.putExtra("title",writeInfoList.get(position).getTitle());
                intent.putExtra("contents",writeInfoList.get(position).getContents());
                intent.putExtra("publisher",writeInfoList.get(position).getPublisher());
                intent.putExtra("selectedCategory",writeInfoList.get(position).getSelectedCategory());
                intent.putExtra("numOfRecruits",writeInfoList.get(position).getNumOfRecruits());
                intent.putExtra("status",writeInfoList.get(position).getStatus());
                intent.putExtra("curRecruits",writeInfoList.get(position).getCurRecruits());
                intent.putExtra("created_at",getTime(writeInfoList.get(position).getCreatedAt()));
                intent.putExtra("participants",writeInfoList.get(position).getParticipants());
                intent.putExtra("posts_id", writeInfoList.get(position).getPosts_id());

                listActivity.startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //bind views
        viewHolder.vTitle.setText(writeInfoList.get(i).getTitle());
        viewHolder.vContents.setText(writeInfoList.get(i).getContents());
        viewHolder.vNickname.setText(writeInfoList.get(i).getNickname());
        viewHolder.vUploadTime.setText(getTime(writeInfoList.get(i).getCreatedAt()));

    }

    @Override
    public int getItemCount() {
        return writeInfoList.size();
    }


    // getExtra cannot call the type timestamp
    // cast the type timestamp -> String
    static String getTime(Timestamp time) {
        Date date_createdAt = time.toDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy??? MM??? HH??? mm??? ss???");
        String txt_createdAt = formatter.format(date_createdAt);
        return txt_createdAt;
    }


}
