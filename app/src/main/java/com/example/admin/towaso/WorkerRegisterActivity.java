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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkerRegisterActivity extends AppCompatActivity {
    private EditText emails,pass,name,address,phone,area;
    private Button button;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogressdialogue;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_register_activity);
        emails = (EditText)findViewById(R.id.editText8);
        pass = (EditText)findViewById(R.id.editText7);
        area = (EditText)findViewById(R.id.editText6);
        name = (EditText)findViewById(R.id.editText3);
        address = (EditText)findViewById(R.id.editText4);
        phone = (EditText)findViewById(R.id.editText5);
        button = (Button)findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = emails.getText().toString();
                String mpass = pass.getText().toString();
                String marea = area.getText().toString();
                String maddress = address.getText().toString();
                String mname = name.getText().toString();
                String mphone = phone.getText().toString();
                final Map<String,Object> note = new HashMap<>();
                note.put("name",mname);
                note.put("email",memail);
                note.put("password",mpass);
                note.put("address",maddress);
                note.put("area",marea);
                note.put("phone",mphone);
                if (!TextUtils.isEmpty(memail)&&!TextUtils.isEmpty(mpass)&&!TextUtils.isEmpty(marea)&&!TextUtils.isEmpty(maddress)&&!TextUtils.isEmpty(mname)&&!TextUtils.isEmpty(mphone)){
                    mprogressdialogue = new ProgressDialog(WorkerRegisterActivity.this);
                    mprogressdialogue.setTitle("Sign Up ");
                    mprogressdialogue.setMessage("Please wait you are being registered");
                    mprogressdialogue.setCanceledOnTouchOutside(false);
                    mprogressdialogue.show();
                        mAuth.createUserWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                   firebaseFirestore.collection("Worker").document("my registration").set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           Toast.makeText(WorkerRegisterActivity.this,"added successfully successfully",Toast.LENGTH_SHORT).show();
                                       }
                                   })
                                           .addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                   Toast.makeText(WorkerRegisterActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
                                               }
                                           });
                                    Toast.makeText(WorkerRegisterActivity.this,"sign up successfully",Toast.LENGTH_SHORT).show();
                                    sendToWorkerLogin();
                                }else {
                                    String errormessage = task.getException().getMessage();
                                    Toast.makeText(WorkerRegisterActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }else {
                    Toast.makeText(WorkerRegisterActivity.this,"confirmed password and password don't match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendToWorkerLogin() {
        Intent myintent = new Intent(WorkerRegisterActivity.this,WorkerLogin.class);
        startActivity(myintent);
        finish();
    }
}
