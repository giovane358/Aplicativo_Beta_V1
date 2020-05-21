package com.abayomi.stockbay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.internal.$Gson$Preconditions;

import java.io.IOException;
import java.util.UUID;

public class ConfigActivity extends AppCompatActivity {

    private CardView txtExit,txtResetSenha,txtTemo;
    private ImageView Viewvoltar,Viewshare, img_photo;
    private TextView txtID;
    private TextView txtEmail;
    private Button btnSelectPhoto;


    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;

    private Uri mSelectUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Inicialização do firebase//
        mAuth   = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        txtExit = findViewById(R.id.txtExit);
        txtEmail = findViewById(R.id.txtEmail);
        txtID = findViewById(R.id.txtID);
        Viewvoltar = findViewById(R.id.Viewvoltar);
        Viewshare = findViewById(R.id.Viewshare);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        img_photo = findViewById(R.id.img_photo);
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

    //Link dos text de Config//
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
