package com.abayomi.stockbay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationActivity extends AppCompatActivity {

    private CardView txtExit,txtResetSenha,txtTemo;
    private ImageView Viewvoltar,Viewshare;
    private TextView txtID;
    private TextView txtEmail;


    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtID = findViewById(R.id.txtID);
        Viewvoltar = findViewById(R.id.Viewvoltar);
        Viewshare = findViewById(R.id.Viewshare);
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
            txtEmail.setText(""+user.getEmail());
        }
    }

    public void onClick (View view){
        switch (view.getId()){
            case R.id.txtExit:
                FirebaseAuth.getInstance().signOut();
                Intent sair = new Intent(this, MainActivity.class);
                startActivity(sair);
                break;
            case R.id.txtResetSenha:
                Intent ResetSenha = new Intent(this, ResetActivity.class);
                startActivity(ResetSenha);
                break;
            case R.id.txtTemo:
                Intent Termo = new Intent(this, TermActivity.class);
                startActivity(Termo);
                break;
            case R.id.Viewvoltar:
                Intent voltar = new Intent(this, PrincipalActivity.class);
                startActivity(voltar);
                break;
            case R.id.Config:
                Intent setting = new Intent(this, ConfigActivity.class);
                startActivity(setting);
                break;
            case R.id.notification:
                Intent notification = new Intent(this, NotificationActivity.class);
                startActivity(notification);
                break;
        }
    }
}
