package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {

    private EditText editNmProduto, editQtd, editdtCompra,editVlCusto, editVlVenda, editDesc;
    private Button btnProd;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        fstore        = FirebaseFirestore.getInstance();
        mAuth         = FirebaseAuth.getInstance();
        editNmProduto = findViewById(R.id.editNmProduto);
        editQtd       = findViewById(R.id.editQtd);
        editdtCompra  = findViewById(R.id.editdtCompra);
        editVlCusto   = findViewById(R.id.editVlCusto);
        editVlVenda   = findViewById(R.id.editVlVenda);
        editDesc      = findViewById(R.id.editDesc);
        btnProd       = findViewById(R.id.btnProd);


    }

    private void RegisterProdutos(){

        userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("Estoque").document(userID);

            Map<String, Object> Est = new HashMap<>();
            Est.put("Nome"       , editNmProduto.getText().toString());
            Est.put("Quantida"   , editQtd.getText().toString());
            Est.put("DataCompra" , editdtCompra.getText().toString());
            Est.put("ValoreVenda", editVlVenda.getText().toString());
            Est.put("ValorCusto" , editVlCusto.getText().toString());
            Est.put("Descricao"  , editDesc.getText().toString());
        documentReference.set(Est).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InsertActivity.this, "Ok", Toast.LENGTH_SHORT).show();
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
            case R.id.btnProd:
                RegisterProdutos();
            break;
            case R.id.txtCancelar:
                Intent Voltar = new Intent(getApplicationContext() , PrincipalActivity.class);
                startActivity(Voltar);
                break;
        }
    }
}

