package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InsertActivity extends AppCompatActivity {

    private EditText editNmProduto, editQtd, editdtCompra,editVlCusto, editVlVenda, editDesc;
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
        editdtCompra.addTextChangedListener(tw);

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
        }


        String UID = editNmProduto.getText().toString();

        String id = UUID.randomUUID().toString();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID)
                .collection("Estoque").document(UID);
        Map<String, Object> Est = new HashMap<>();
        Est.put("Id"         , id);
        Est.put("Nome"       , editNmProduto.getText().toString());
        Est.put("Search"     , editNmProduto.getText().toString().toLowerCase());
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



    TextWatcher tw = new TextWatcher() {

        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }
                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                editdtCompra.setText(current);
                editdtCompra.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

