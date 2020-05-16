package com.abayomi.stockbay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ContactInsert extends AppCompatActivity {

    private EditText editNm, editEmail, editFone;
    private Button btnAdicionar;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_insert);

        editNm = findViewById(R.id.editNm);
        editEmail = findViewById(R.id.editEmail);
        editFone  = findViewById(R.id.editFone);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        mAuth  = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }

    private void RegisterContact(){

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID)
                .collection("Contato").document();
        Map<String, Object> Contact = new HashMap<>();
        Contact.put("NomeFornc"       , editNm     .getText().toString());
        Contact.put("EmailFornc"      , editEmail  .getText().toString());
        Contact.put("TelefoneFornec"   , editFone   .getText().toString());

        documentReference.set(Contact).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ContactInsert.this, "Contato adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent contact = new Intent(getApplicationContext(), ListContactActivity.class);
                startActivity(contact);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Não possível registrar o Produto!", e);
                    }
                });
    }

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btnAdicionar:
                RegisterContact();
                break;
            case R.id.txtCancelar:
                Intent Voltar = new Intent(getApplicationContext() , PrincipalActivity.class);
                startActivity(Voltar);
                break;
        }
    }
}
