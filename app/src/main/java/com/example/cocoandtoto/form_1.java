package com.example.cocoandtoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class form_1 extends AppCompatActivity implements View.OnClickListener{

    private EditText nameC,mail1C,addressC,contactC,passC;
    private TextView CoConToTo,btn_sub;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1);

        mAuth =FirebaseAuth.getInstance();

        CoConToTo = (TextView) findViewById(R.id.CoConToTo);
        CoConToTo.setOnClickListener(this);

        btn_sub = (Button) findViewById(R.id.c_btn_sub);
        btn_sub.setOnClickListener(this);

        nameC = (EditText) findViewById(R.id.c_name);
        addressC = (EditText) findViewById(R.id.c_address);
        mail1C = (EditText) findViewById(R.id.c_mail1);
        contactC = (EditText) findViewById(R.id.c_contact);
        passC = (EditText) findViewById(R.id.c_pass);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.CoConToTo:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.c_btn_sub:
               btn_sub();
                break;
        }

    }

    private void btn_sub() {

        String name = nameC.getText().toString().trim();
        String address = addressC.getText().toString().trim();
        String email = mail1C.getText().toString().trim();
        String contact = contactC.getText().toString().trim();
        String pwd = passC.getText().toString().trim();

        if(name.isEmpty()){
            nameC.setError("Name is required");
            nameC.requestFocus();
            return;
        }


        if(address.isEmpty()){
            addressC.setError(" address is required");
            addressC.requestFocus();
            return;
        }

        if(email.isEmpty()){
            mail1C.setError("Email is required");
            mail1C.requestFocus();
            return;
        }



        if(contact.isEmpty()){
            contactC.setError("Phone number is required");
            contactC.requestFocus();
            return;
        }

        if(pwd.isEmpty()){
            passC.setError("Password is required");
            passC.requestFocus();
            return;
        }

        if(pwd.length()<5){
            passC.setError("Minimum password length should be 5 characters");
            passC.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   profile_form_1 proF =new profile_form_1(name,email,address,pwd,contact );

                   FirebaseDatabase.getInstance().getReference("profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .setValue(proF).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){
                               Toast.makeText(form_1.this, "Register Successful", Toast.LENGTH_SHORT).show();

                           }else{
                               Toast.makeText(form_1.this, "Register Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }

            }
        });

    }
}