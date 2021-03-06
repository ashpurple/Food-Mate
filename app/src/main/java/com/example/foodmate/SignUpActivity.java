package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG="SignUpActivity";
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.btn_sendEmail).setOnClickListener(onClickListener);
        findViewById(R.id.text_login).setOnClickListener(onClickListener);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onBackPressed(){
        startLoginActivity();
    }

    View.OnClickListener onClickListener=(v)->{
        switch(v.getId()){
            case R.id.btn_sendEmail:
                signUp();
                break;
            case R.id.text_login:
                startLoginActivity();
                break;
        }
    };

    private void signUp(){
        String email=((EditText)findViewById(R.id.edit_email)).getText().toString();
        String nickName=((EditText)findViewById(R.id.edit_nickname)).getText().toString();
        String password=((EditText)findViewById(R.id.edit_password)).getText().toString();
        String passwordCheck=((EditText)findViewById(R.id.edit_confirmPassword)).getText().toString();

        /* email parsing */
        String key = email.split("@")[0];   // extract id
        String domain = email.split("@")[1];

        if(email.length()>0 && password.length()>0 && passwordCheck.length()>0 && nickName.length()>0) {
            if (domain.equals("gachon.ac.kr")){// check university domain
                if (password.equals(passwordCheck)) { // check password confirmation
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, (task) -> {
                                /* Send Email Verification */
                                mAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(this, (task2) -> {
                                            if(task2.isSuccessful()){
                                                startToast("?????? ????????? ?????????????????????.");
                                            } else {
                                                if (task2.getException() != null) {
                                                    startToast(task2.getException().toString());
                                                }
                                            }
                                        });

                                if (task.isSuccessful()) {
                                    String uid=mAuth.getUid();

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

                                                    // store to FireStore
                                                    Map<String, Object> users = new HashMap<>();
                                                    users.put("uid", uid);
                                                    users.put("token", token);
                                                    users.put("email",email);
                                                    users.put("nickname",nickName);

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
                                                }
                                            });
                                    startToast("??????????????? ?????????????????????.\n????????? ????????? ??????????????? ???????????? ???????????????");
                                    startLoginActivity();
                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }

                            });
                } else {
                    Toast.makeText(this, "??????????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "??????????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            startToast("???????????? ?????? ????????? ????????????");
        }
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void startLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}