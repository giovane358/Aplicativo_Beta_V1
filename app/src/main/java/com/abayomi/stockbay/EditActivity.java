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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    private EditText editNmProduto, editQtd, editdtCompra, editVlCusto, editVlVenda, editDesc;
    private Button btnProd;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    private static final String TAG = "DocSnippets";
    private Object idProdut;
    private EditText id;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editNmProduto = findViewById(R.id.editNmProduto);
        editQtd = findViewById(R.id.editQtd);
        editVlCusto = findViewById(R.id.editVlCusto);
        editVlVenda = findViewById(R.id.editVlVenda);
        editDesc = findViewById(R.id.editDesc);
        btnProd = findViewById(R.id.btnProd);

    }

    private void update() {

        String UID = editNmProduto.getText().toString();
        String id = UUID.randomUUID().toString();
        userID = mAuth.getCurrentUser().getUid();

        String Nome = editNmProduto.getText().toString();
        String Qtde = editQtd.getText().toString();
        String Custo = editVlCusto.getText().toString();
        String venda = editVlVenda.getText().toString();
        String desc = editDesc.getText().toString();

        if (Nome.isEmpty()) {
            editNmProduto.setError("Nome inválido");
            editNmProduto.requestFocus();
            return;
        }
        if (Qtde.isEmpty()) {
            editQtd.setError("Quantidade Inválido");
            editQtd.requestFocus();
            return;
        }
        if (Custo.isEmpty()) {
            editVlCusto.setError("Valor de compra inválido");
            editVlCusto.requestFocus();
            return;

        }
        if (venda.isEmpty()) {
            editVlVenda.setError("Valor de venda inválido");
            editVlVenda.requestFocus();
            return;
        }
        if (desc.isEmpty()) {
            editDesc.setError("Descrição inválida");
            editDesc.requestFocus();
            return;
        }

        DocumentReference documentReference = fstore.collection("User").document(userID)
                .collection("Estoque").document(UID);
        Map<String, Object> Est = new HashMap<>();
        Est.put("Id", id);
        Est.put("Nome", editNmProduto.getText().toString());
        Est.put("Search", editNmProduto.getText().toString().toLowerCase());
        Est.put("Quantidade", editQtd.getText().toString());
        Est.put("DataCompra", editdtCompra.getText().toString());
        Est.put("ValoreVenda", editVlVenda.getText().toString());
        Est.put("ValorCusto", editVlCusto.getText().toString());
        Est.put("Descricao", editDesc.getText().toString());
        documentReference.set(Est).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void Void) {
                Toast.makeText(EditActivity.this, "Produto Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent Sucesso = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(Sucesso);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Não possível registrar o Produto!", e);
                    }
                });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProd:
                update();
                break;

        }
    }

}
