package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.MainActivity;
import com.example.book_store.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if(sharedPreferences.getBoolean("logged_in",false))
        {
            //user is already logged in so take the user to the main activity
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void init()
    {
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();
        sharedPreferences=this.getSharedPreferences("com.example.book_store",MODE_PRIVATE);
    }

    public void register_user(View view)
    {
        Intent intent= new Intent(this,sign_up.class);
        startActivity(intent);
    }

    public void login_user(View view)
    {
        if(TextUtils.isEmpty(email.getText().toString())
        || TextUtils.isEmpty(password.getText().toString()))
        {
            Toast.makeText(this,"All fields must be filled",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            {
                email.setError("Email invalid");
            }
            else
            {
                String em,pass;
                em=email.getText().toString();
                pass=password.getText().toString();
                auth.signInWithEmailAndPassword(em,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    sharedPreferences.edit().putBoolean("logged_in",true).apply();
                                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Login failed"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}