package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView register,forgotPassword;
    private Button signin_button;
    private EditText editTextEmail,editTextPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.view_register);
        register.setOnClickListener(this);

        signin_button = findViewById(R.id.login_button);
        signin_button.setOnClickListener(this);

        editTextEmail = findViewById(R.id.text_email);
        editTextPassword = findViewById(R.id.text_password);

        forgotPassword = findViewById(R.id.text_forgotpwd);
        forgotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.view_register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.login_button:
                userLogin();
                break;
            case R.id.text_forgotpwd:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is requires");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, Userlist.class));

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Verify your email",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}