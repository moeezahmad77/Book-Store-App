package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_store.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    EditText email,password,confirm_password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }
    private void init()
    {
        email=findViewById(R.id.s_email);
        password=findViewById(R.id.s_password);
        confirm_password=findViewById(R.id.s_confirm_password);
        auth=FirebaseAuth.getInstance();
    }
    public void signup(View view)
    {
        if(TextUtils.isEmpty(email.getText().toString())
        || TextUtils.isEmpty(password.getText().toString())
        || TextUtils.isEmpty(confirm_password.toString()))
        {
            Toast.makeText(this,"All fields must be filled",Toast.LENGTH_SHORT).show();
        }
        else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                email.setError("Not a valid email");
            } else {
                if (password.getText().toString().length() < 6) {
                    password.setError("Password length should be at least 6 characters");
                    password.requestFocus();
                } else {
                    if (!confirm_password.getText().toString().equals(password.getText().toString())) {
                        confirm_password.setError("Password did not match");
                        confirm_password.requestFocus();
                    } else {
                        try {
                            String em, pass;
                            em = email.getText().toString();
                            pass = password.getText().toString();
                            auth.createUserWithEmailAndPassword(em, pass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                //registration successfull
                                                Intent intent = new Intent(getApplicationContext(), login.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to Register!" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}