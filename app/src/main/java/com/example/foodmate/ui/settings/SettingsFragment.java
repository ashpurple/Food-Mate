package com.example.foodmate.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmate.ListActivity;
import com.example.foodmate.LoginActivity;
import com.example.foodmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends Fragment {
    private SettingsViewModel settingsViewModel;

    //firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
    DocumentReference docRef = db.collection("Users").document(users.getUid());


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

        // Get nickname from the firestore
        TextView nickname = root.findViewById(R.id.user_nickname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String txt_nickname = document.getData().get("nickname").toString();
                        nickname.setText("반갑습니다 " + txt_nickname + "님!");
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                }
            }
        });



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
