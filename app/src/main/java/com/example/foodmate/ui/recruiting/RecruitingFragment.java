package com.example.foodmate.ui.recruiting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmate.ListActivity;
import com.example.foodmate.R;
import com.example.foodmate.WritingActivity;

public class RecruitingFragment extends Fragment {
    // Set status and flag value as "recruiting" states
    int flag=0;   // 0 = recruiting, 1 = recruited, 2 = delivered
    String status="recruiting";
    String category;

    private RecruitingViewModel recruitingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recruitingViewModel =
                new ViewModelProvider(this).get(com.example.foodmate.ui.recruiting.RecruitingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recruiting, container, false);
        final TextView textView = root.findViewById(R.id.text_recruiting);
        recruitingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        root.findViewById(R.id.btn_addNew).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_korean).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_schoolfood).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_Asian_European_food).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_fastfood).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_chinese).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_Etc).setOnClickListener(onClickListener);


        return root;


    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.btn_addNew:
                    startMyActivity(WritingActivity.class);
                    break;
                case R.id.btn_korean:
                    category="??????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
                case R.id.btn_schoolfood:
                    category="??????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
                case R.id.btn_Asian_European_food:
                    category="?????????/??????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
                case R.id.btn_fastfood:
                    category="???????????????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
                case R.id.btn_chinese:
                    category="??????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
                case R.id.btn_Etc:
                    category = "??????";
                    startListActivity(ListActivity.class,flag,status,category);
                    break;
            }
        }
    };

    private void startMyActivity(Class c){
        Intent intent = new Intent(getActivity(),c);
        startActivity(intent);
    }
    private void startListActivity(Class c,int flag,String status,String category){
        Intent intent = new Intent(getActivity(),c);
        intent.putExtra("Flag",flag);
        intent.putExtra("Status",status);
        intent.putExtra("Category",category);
        startActivity(intent);

    }
}
