package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abayomi.stockbay.Conexao;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private EditText editResetEmail;
    private Button btnReset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        inicializaComponentes();
        eventoClick();

    }

    private void eventoClick() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editResetEmail.getText().toString().trim();
                resetSenha(email);
            }
        });
    }

    private void resetSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            alert("Um e-mail para a alteração da senha foi enviado.");
                            finish();
                        } else {
                            alert("E-mail fornecido não foi encontrado.");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(ResetActivity.this, s, Toast.LENGTH_LONG).show();
    }

    private void inicializaComponentes() {
        editResetEmail = findViewById(R.id.editResetEmail);
        btnReset = findViewById(R.id.btnReset);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }


}
