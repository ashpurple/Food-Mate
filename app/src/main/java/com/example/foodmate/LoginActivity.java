package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.text_signup).setOnClickListener(onClickListener);
        findViewById(R.id.text_forgotPW).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    View.OnClickListener onClickListener= (v)->{

        switch(v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.text_signup:
                startActivity(SignUpActivity.class);
                break;
            case R.id.text_forgotPW:
                startActivity(PasswordResetActivity.class);
        }

    };

    private void login(){
        String email=((EditText)findViewById(R.id.edit_email)).getText().toString();
        String password=((EditText)findViewById(R.id.edit_password)).getText().toString();

        if(email.length()>0 && password.length()>0) {
            /* Login */
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(mAuth.getCurrentUser().isEmailVerified()){ // check verification
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("로그인에 성공하였습니다.");
                                    startMainActivity();
                                }
                                else{
                                    startToast("인증되지 않은 이메일입니다.\n 메일을 확인해주세요");
                                }
                            } else {
                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        }else{
            startToast("입력하지 않은 항목이 있습니다");
        }
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // if login status, block back pressed
        startActivity(intent);
    }

}