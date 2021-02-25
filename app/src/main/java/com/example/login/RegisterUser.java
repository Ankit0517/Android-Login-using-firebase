package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.textView);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.register_button);
        registerUser.setOnClickListener(this);

        editTextFullName = findViewById(R.id.text_name);
        editTextEmail = findViewById(R.id.text_remail);
        editTextPassword = findViewById(R.id.text_repassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();

        if(fullname.isEmpty()) {
            editTextFullName.setError("Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("EMAIL is required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("PASSWORD is required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Password is too short");
            editTextPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user1 =mAuth.getCurrentUser();
                        user1.sendEmailVerification();

                        if(task.isSuccessful()){
                            User user = new User(fullname, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been Registered Successfully Please Verify Your Mail",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterUser.this,"Failed To Register",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterUser.this,"Failed To Register",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}