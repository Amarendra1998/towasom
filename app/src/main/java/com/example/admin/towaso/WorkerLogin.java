package com.example.admin.towaso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WorkerLogin extends AppCompatActivity {
    private EditText loginemail;
    private EditText loginpassword;
    private Button loginbtn;
    private TextView textView;
    private FirebaseAuth mauth;
    private ProgressDialog mprogressdialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_login);
        loginemail = (EditText)findViewById(R.id.editText);
        loginpassword = (EditText)findViewById(R.id.editText2);
        loginbtn = (Button)findViewById(R.id.button);
        mauth = FirebaseAuth.getInstance();
        textView = (TextView)findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mmine = new Intent(WorkerLogin.this,WorkerRegisterActivity.class);
                startActivity(mmine);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginmail=loginemail.getText().toString();
                String loginpass =loginpassword.getText().toString();
                if (!TextUtils.isEmpty(loginmail)&& !TextUtils.isEmpty(loginpass)){
                    mprogressdialogue = new ProgressDialog(WorkerLogin.this);
                    mprogressdialogue.setTitle("Sign in ");
                    mprogressdialogue.setMessage("Please wait you are being logged in");
                    mprogressdialogue.setCanceledOnTouchOutside(false);
                    mprogressdialogue.show();
                    mauth.signInWithEmailAndPassword(loginmail,loginpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendToWorkerRequest();
                            }else {
                                String errr = task.getException().getMessage();
                                mprogressdialogue.dismiss();
                                Toast.makeText(WorkerLogin.this,"error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mauth.getCurrentUser();
        if (firebaseUser !=null){
            sendToWorkerRequest();
        }
    }
    private void sendToWorkerRequest() {
        Intent myintent = new Intent(WorkerLogin.this,WorkerRequest.class);
        startActivity(myintent);
        finish();
    }
}
