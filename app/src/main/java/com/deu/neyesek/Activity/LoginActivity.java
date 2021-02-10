package com.deu.neyesek.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deu.neyesek.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText EmailLogin, PasswordLogin;
    Button Login;
    TextView CreateAccount;
    FirebaseAuth fAuth;
    DatabaseReference referenceUser;
    FirebaseUser user;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailLogin = findViewById(R.id.emailLogin);
        PasswordLogin = findViewById(R.id.passwordLogin);
        fAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.login);
        CreateAccount = findViewById(R.id.createAccount);

        fAuth = FirebaseAuth.getInstance();
        referenceUser = FirebaseDatabase.getInstance().getReference().child("User");


        //¯\_(ツ)_/¯
        if( fAuth.getCurrentUser()!= null)
        {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                email = EmailLogin.getText().toString().trim();
                password = PasswordLogin.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    EmailLogin.setError("Please enter an email.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    PasswordLogin.setError("Please enter a password..");
                    return;
                }
                if (password.length() < 8) ////PASSWORD EŞİTSE OLACAK
                {
                    PasswordLogin.setError("Incorrect Form Of A Password.");
                    return;
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {

                    loginFunction();

                } else
                {

                    Toast.makeText(LoginActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));// Register Classına yönlendirilmesi gerek
            }
        });

    }

    private void loginFunction() {

        // login function
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {

                            Intent i = new Intent(LoginActivity.this, UserDrawer.class);
                            Toast.makeText(LoginActivity.this, "LoginSuccess", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

}
