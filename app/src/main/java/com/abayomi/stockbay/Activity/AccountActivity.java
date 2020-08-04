package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AccountActivity<userID> extends AppCompatActivity {

    private EditText editNomeAnt, editNomeNew, editPhoneNew;
    private Switch switchDark;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
        setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        switchDark = findViewById(R.id.switchDark);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        editNomeAnt = (EditText) findViewById(R.id.editNomeAnt);
        editNomeNew = (EditText) findViewById(R.id.editNomeNew);
        editPhoneNew = (EditText) findViewById(R.id.editPhoneNew);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            switchDark.setChecked(true);
        }
        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });
    }

    public void upData(){
        String UID = editNomeAnt.getText().toString();
        String id = UUID.randomUUID().toString();
        String userID = mAuth.getCurrentUser().getUid();

        String nm = editNomeAnt.getText().toString();

        if (nm.isEmpty())
        {
            editNomeAnt.setError("Digite o nome antigo");
            editNomeAnt.requestFocus();
        }
        fstore.collection("User").document(userID).collection("Aut").document(UID)
                .update(
                        "UID", editNomeNew.getText().toString(),
                        "Empresa", editNomeNew.getText().toString(),
                        "Telefone", editPhoneNew.getText().toString().toLowerCase()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AccountActivity.this, "Alteração feita com sucesso ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "Aconteceu algum erro na digitação verifique os campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(),PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnsave:
                upData();
                break;

        }
    }
}
