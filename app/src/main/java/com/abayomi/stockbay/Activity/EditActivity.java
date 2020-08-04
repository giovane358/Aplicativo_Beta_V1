package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    private EditText editNmProduto, editQtd, editdtCompra, editVlCusto, editVlVenda, editDesc,editUID;
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editNmProduto = findViewById(R.id.editNmProduto);
        editQtd = findViewById(R.id.editQtd);
        editVlCusto = findViewById(R.id.editVlCusto);
        editVlVenda = findViewById(R.id.editVlVenda);
        editDesc = findViewById(R.id.editDesc);
        editUID = findViewById(R.id.editUID);
        btnProd = findViewById(R.id.btnProd);

    }

    private void update() {

        String UID = editUID .getText().toString();
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

         fstore.collection("User").document(userID).collection("Estoque").document(UID)
                .update(
                        "UID", editUID.getText().toString(),
                        "Nome", editNmProduto.getText().toString(),
                        "Search", editNmProduto.getText().toString().toLowerCase(),
                        "Quantidade", editQtd.getText().toString(),
                        "ValoreVenda", editVlVenda.getText().toString(),
                        "ValorCusto", editVlCusto.getText().toString(),
                        "Descricao", editDesc.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 Toast.makeText(EditActivity.this, "Produto alterado com sucesso! ", Toast.LENGTH_SHORT).show();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show();
             }
         });



    /*addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void Void) {
                Toast.makeText(EditActivity.this, "Produto alterado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent Sucesso = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(Sucesso);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Não possível fazer a alteração do produto!", e);
            }
        });*/


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProd:
                update();
                break;
            case R.id.btnEditCod:
                scanCod();
                break;

        }
    }

    private void scanCod() {
        IntentIntegrator intregador = new IntentIntegrator(this);
        intregador.setCaptureActivity(CapterActivity.class);
        intregador.setOrientationLocked(false);
        intregador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intregador.setPrompt("Ler cod");
        intregador.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                editUID.setText(result.getContents());

            } else {
                Toast.makeText(this, "Não foi possível mostrar código de barra!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
