package com.abayomi.stockbay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText txLogin;
    private EditText txtSenha;
    private Button btnLogin;
    private TextView Register;
    private RadioButton LembrameMe;

    ///firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txLogin = (EditText) findViewById(R.id.editNmProduto);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        Register = findViewById(R.id.Register);
        LembrameMe = findViewById(R.id.LembraMe);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(txLogin.getText().toString(), txtSenha.getText().toString());
            }

        });

    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Bem-Vindo de Volta!" + " " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                            startActivity(intent);

                            openPrincipalActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "E-mail ou senha invalido!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean userConnected() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userConnected()) {
            openPrincipalActivity();
        }
    }

    private void openPrincipalActivity() {
        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register:
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
                break;
            case R.id.LembraMe:
                lembraMe();
                break;
        }
    }

    private void lembraMe() {
        if (LembrameMe == null) {
            Intent Login = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Login);
        } else {
            userConnected();
        }

    }

    public void callReset(View view) {
        Intent intent = new Intent(this, ResetActivity.class);
        startActivity(intent);
    }
}

