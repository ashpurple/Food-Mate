package com.example.foodmate.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmate.ListActivity;
import com.example.foodmate.LoginActivity;
import com.example.foodmate.R;
import com.example.foodmate.ui.recruiting.RecruitingViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(com.example.foodmate.ui.settings.SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button btn_logout = (Button) root.findViewById(R.id.btn_logout);
        Button btn_history = (Button) root.findViewById(R.id.btn_history);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startToast("로그아웃되었습니다");
                startMyActivity(LoginActivity.class);
            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="delivered";
                int flag=2;
                startListActivity(ListActivity.class,flag,status);
            }
        });

        return root;
    }

    private void startMyActivity(Class c){
        Intent intent = new Intent(getActivity(),c);
        startActivity(intent);
    }
    private void startListActivity(Class c,int flag,String status){
        Intent intent = new Intent(getActivity(),c);
        intent.putExtra("Status",status);
        intent.putExtra("Flag",flag);
        startActivity(intent);
    }

    //Send Toast Message
    private void startToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


}
