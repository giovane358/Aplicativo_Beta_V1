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
import com.google.protobuf.Empty;

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

    private void Verif(){
        if (editNmProduto == null){
            Toast.makeText(InsertActivity.this, "Colocar um nome!", Toast.LENGTH_SHORT).show();
        }else{
            RegisterProdutos();
        }
    }



    private void RegisterProdutos() {

        String Nome = editNmProduto.getText().toString();
        String Qtde = editQtd.getText().toString();
        String Compra = editdtCompra.getText().toString();
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
        if (Compra.isEmpty()) {
            editdtCompra.setError("Data inválida");
            editdtCompra.requestFocus();
            return;
        }
        if (Custo.isEmpty()){
            editVlCusto.setError("Valor de compra inválido");
            editVlCusto.requestFocus();
            return;

        }if (venda.isEmpty()){
               editVlVenda.setError("Valor de venda inválido");
               editVlVenda.requestFocus();
               return;
        }if (desc.isEmpty()){
               editDesc.setError("Descrição inválida");
               editDesc.requestFocus();
               return;
        }if (Compra.length() <= 8) {
            editdtCompra.setError("Data deve conter no minímo 8 digitos!");
            editdtCompra.requestFocus();
            return;
        }


        userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("User").document(userID)
                                                        .collection("Estoque").document();
            Map<String, Object> Est = new HashMap<>();
            Est.put("Nome"       , editNmProduto.getText().toString());
            Est.put("Quantidade" , editQtd      .getText().toString());
            Est.put("DataCompra" , editdtCompra .getText().toString());
            Est.put("ValoreVenda", editVlVenda  .getText().toString());
            Est.put("ValorCusto" , editVlCusto  .getText().toString());
            Est.put("Descricao"  , editDesc     .getText().toString());
        documentReference.set(Est).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void Void) {
                Toast.makeText(InsertActivity.this, "Produto Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent Sucesso = new Intent(getApplicationContext(),PrincipalActivity.class);
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

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btnProd:
             Verif();
            break;
            case R.id.txtCancelar:
                Intent Voltar = new Intent(getApplicationContext() , PrincipalActivity.class);
                startActivity(Voltar);
                break;
        }
    }
}

