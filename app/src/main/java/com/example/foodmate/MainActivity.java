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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    public static Activity Main_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


//    View.OnClickListener onClickListener=new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//
//            switch (v.getId()){
//                case R.id.btn_logout:
//                    FirebaseAuth.getInstance().signOut();
//                    startToast("로그아웃되었습니다");
//                    startMyActivity(LoginActivity.class);
//                    break;
//                case R.id.btn_addNew:
//                    startMyActivity(WritingActivity.class);
//                    break;
//            }
//        }
//    };





    private void startMyActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}