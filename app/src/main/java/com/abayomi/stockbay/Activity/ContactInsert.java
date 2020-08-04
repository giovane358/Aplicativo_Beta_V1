package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abayomi.stockbay.R;
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
    String lastChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_insert);

        editNm = findViewById(R.id.editNm);
        editEmail = findViewById(R.id.editEmail);
        editFone = findViewById(R.id.editFone);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        editFone.addTextChangedListener(textWatcher);
    }

    private void RegisterContact() {

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID)
                .collection("Contatos").document();
        Map<String, Object> Contact = new HashMap<>();
        Contact.put("NomeFornc", editNm.getText().toString());
        Contact.put("EmailFornc", editEmail.getText().toString());
        Contact.put("TelefoneFornec", editFone.getText().toString());

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

    TextWatcher textWatcher = new TextWatcher() {


        int length_before = 0;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            length_before = s.length();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void afterTextChanged(Editable s) {
            if (length_before < s.length()) {
                if (s.length() == 2 || s.length() == 12)
                    s.append("-");
                if (s.length() > 3) {
                    if (Character.isDigit(s.charAt(3)))
                        s.insert(3, "-");
                }
                if (s.length() > 8) {
                    if (Character.isDigit(s.charAt(7)))
                        s.insert(7, "-");
                }
            }
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdicionar:
                RegisterContact();
                break;
            case R.id.txtCancelar:
                Intent Voltar = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(Voltar);
                break;
        }
    }
}
