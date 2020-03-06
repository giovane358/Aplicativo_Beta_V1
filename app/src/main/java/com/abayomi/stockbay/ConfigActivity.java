package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfigActivity extends AppCompatActivity {

    private TextView txtExit;
    private TextView txtID;
    private TextView txtEmail;

    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        //Inicialização do firebase//
        mAuth       = FirebaseAuth.getInstance();

        txtExit     = findViewById(R.id.txtExit);
        txtEmail    = findViewById(R.id.txtEmail);
        txtID       = findViewById(R.id.txtID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        virificar();
    }

    //Pegando Info do Usuário//
    public void virificar() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            finish();
        }else{
            txtID.setText("ID: "+" " +user.getUid());
            txtEmail.setText("Email: "+" "+user.getEmail());
        }
    }

    //Link dos text de Config//
    public void onClick (View view){
        switch (view.getId()){
            case R.id.txtExit:
                FirebaseAuth.getInstance().signOut();
                Intent sair = new Intent(this, MainActivity.class);
                startActivity(sair);
        }
    }
}
