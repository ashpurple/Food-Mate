package com.example.foodmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodmate.pushNoti.SendMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static Activity Main_Activity;
    private FirebaseUser user;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "create main");
        // 토큰 발급

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        Log.d("Token", token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                        // 토큰 저장
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid=user.getUid();

                        Map<String, Object> users = new HashMap<>();
                        users.put("uid", uid);
                        users.put("token", token);

                        db = FirebaseFirestore.getInstance();
                        db.collection("Users").document(uid)
                                .set(users)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Token Save", "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Token Save", "Error writing document", e);
                                    }
                                });
                        List<String> temp = new ArrayList<>();
                        temp.add(uid);
                        SendMessage sendMessage = new SendMessage(temp,"모집완료","참여 게시물이 모집 완료되었습니다");
                    }
                });


        // 로그인 된 상태가 아니라면
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startMyActivity(LoginActivity.class);
        }
//        // 사용 버튼
//        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);
//        findViewById(R.id.btn_addNew).setOnClickListener(onClickListener);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_recruiting,
                R.id.nav_recruited,
                R.id.nav_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    private void startMyActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}