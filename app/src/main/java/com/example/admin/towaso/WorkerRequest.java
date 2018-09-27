package com.example.admin.towaso;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WorkerRequest extends AppCompatActivity {
private Button button;
private TextView textView,textView2,textView4,textView5,textView6;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogressdialogue;
    String current_user_id;
    private static final String mkeyadd = "address";
    private static final String mkeytitle = "landmark";
    private static final String mkeylocal = "locality";
    private static final String mkeypin = "pincode";
    private static final String mkeystate = "state";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseFirestore noteref = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_request);
        button = (Button)findViewById(R.id.button3);
        current_user_id = mAuth.getCurrentUser().getUid();
        textView2 = (TextView)findViewById(R.id.textView3);
        textView = (TextView)findViewById(R.id.textView2);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        textView6 = (TextView)findViewById(R.id.textView6);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String item = format.format(calendar.getTime());
                String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                final Map<String,Object> note = new HashMap<>();
                note.put("time",item);
                note.put("date",currentdate);
                firebaseFirestore.collection("WorkerRequest").document("Accepted Request").set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(WorkerRequest.this,"added successfully successfully",Toast.LENGTH_SHORT).show();
                         noteref.collection("UserRequest").document(current_user_id).collection("details").
                                 addOnSuccessListener(new OnSuccessLister<DocumentSnapshot>(){
                                     public void onSuccess(DocumentSnapshot documentSnapshot){
                                         String title = documentSnapshot.getString(mkeyadd);
                                         String land = documentSnapshot.getString(mkeytitle);
                                         String local = documentSnapshot.getString(mkeylocal);
                                         String pin = documentSnapshot.getString(mkeypin);
                                         String state = documentSnapshot.getString(mkeystate);
                                         button.setText(" Accepted");
                                         button.setEnabled(false);
                                         textView.setVisibility(View.VISIBLE);
                                         textView2.setVisibility(View.VISIBLE);
                                         textView4.setText(local);
                                         textView5.setText(pin);
                                         textView6.setText(state);
                                         textView2.setText(title);
                                         textView.setText(land);
                                     }
                                 })
                         .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(WorkerRequest.this,"something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(WorkerRequest.this,"something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}
