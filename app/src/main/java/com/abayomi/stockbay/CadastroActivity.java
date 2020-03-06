package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class CadastroActivity extends AppCompatActivity {

   private EditText txtEmail, txtSenha;
    Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Initialize Firebase Auth
        mAuth       = FirebaseAuth.getInstance();

        txtEmail    = (EditText) findViewById(R.id.txtEmail);
        txtSenha    = (EditText) findViewById(R.id.txtSenha);
        btnRegister = (Button) findViewById(R.id.btnLogin);

    }

    @Override
    public void onStart() {
        super.onStart();
            if (mAuth.getCurrentUser() != null){

        }

    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
    }

    private void registerUser() {
       final String email = txtEmail.getText().toString();
       String senha = txtSenha.getText().toString();
       if (email.isEmpty()){
           txtEmail.setError("Email incorreto!");
           txtEmail.requestFocus();
           return;
       }
       if (senha.isEmpty()){
           txtSenha.setError("Senha tem que ter no minimo 6 digitos!");
           txtSenha.requestFocus();
           return;
       }

       mAuth.createUserWithEmailAndPassword(email, senha)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           User user = new User(
                               email
                           );
                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   progressBar.setVisibility(View.GONE);
                                 if (task.isSuccessful())
                                 {
                                     Toast.makeText(CadastroActivity.this, getString(R.string.btn_registrar), Toast.LENGTH_LONG).show();
                                     Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                     startActivity(intent);

                                 }else {
                                 }
                               }
                           });
                       }else {
                           Toast.makeText(CadastroActivity.this, "Não foi possível fazer fazer o Registro", Toast.LENGTH_SHORT).show();
                       }
                   }


               });
    }

    public void onClick (View v){
        switch (v.getId()){
            case R.id.btnRegister:
                registerUser();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}