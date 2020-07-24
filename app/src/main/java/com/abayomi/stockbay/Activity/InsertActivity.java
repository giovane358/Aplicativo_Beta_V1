package com.abayomi.stockbay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertActivity extends AppCompatActivity {

    private EditText editNmProduto, editQtd, editdtCompra, editVlCusto, editVlVenda, editDesc;
    private Button btnProd;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private FirebaseStorage storage;
    String userID;
    private static final String TAG = "DocSnippets";
    private Object idProdut;
    private EditText id;
    String UID;
    private ProgressBar mProgressbar;
    StorageReference storageReference;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();
    LocalDateTime agora = LocalDateTime.now();
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editNmProduto = findViewById(R.id.editNmProduto);
        editQtd = findViewById(R.id.editQtd);
        editdtCompra = findViewById(R.id.editdtCompra);
        editVlCusto = findViewById(R.id.editVlCusto);
        editVlVenda = findViewById(R.id.editVlVenda);
        editDesc = findViewById(R.id.editDesc);
        btnProd = findViewById(R.id.btnProd);
        storage = FirebaseStorage.getInstance();
        editdtCompra.addTextChangedListener(tw);


    }

    private void Verif() {
        if (editNmProduto == null) {
            Toast.makeText(InsertActivity.this, "Colocar um nome!", Toast.LENGTH_SHORT).show();
        } else {
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
        String dataFormatada = simpleDateFormat.format(data).toString();

        double CustoComp = Double.parseDouble(editVlCusto.getText().toString());
        double VendaComp = Double.parseDouble(editVlVenda.getText().toString());


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
        if (CustoComp <= VendaComp) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Registando o produto, isso pode demorar um pouco!..");
            progressDialog.show();

            String UID = editNmProduto.getText().toString();
            String id = UUID.randomUUID().toString();
            userID = mAuth.getCurrentUser().getUid();
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
            Est.put("DataInsert", dataFormatada);
            documentReference.set(Est).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void Void) {
                    String dataFormatada = simpleDateFormat.format(data).toString();
                    String UID = editNmProduto.getText().toString();
                    String id = UUID.randomUUID().toString();
                    userID = mAuth.getCurrentUser().getUid();
                    String Hr = formatterHora.format(agora);
                    DocumentReference documentReference = fstore.collection("User").document(userID)
                            .collection("Notification").document(UID);
                    Map<String, Object> not = new HashMap<>();
                    not.put("Id", id);
                    not.put("Nome", editNmProduto.getText().toString());
                    not.put("Search", editNmProduto.getText().toString().toLowerCase());
                    not.put("Quantidade", editQtd.getText().toString());
                    not.put("DtInclusao", dataFormatada);
                    not.put("HrInclusao", Hr);
                    documentReference.set(not).addOnSuccessListener(new OnSuccessListener<java.lang.Void>() {
                        @Override
                        public void onSuccess(java.lang.Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(InsertActivity.this, "Produto Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent Sucesso = new Intent(getApplicationContext(), PrincipalActivity.class);
                            startActivity(Sucesso);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InsertActivity.this, "Não foi possivél Registar esse produto!", Toast.LENGTH_SHORT).show();
                        }
                    });/*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });*/

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Verificar os Valores", Toast.LENGTH_SHORT).show();
        }


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

                if (clean.length() < 8) {
                    clean = clean + ddmmyyyy.substring(clean.length());
                } else {
                    int day = Integer.parseInt(clean.substring(0, 2));
                    int mon = Integer.parseInt(clean.substring(2, 4));
                    int year = Integer.parseInt(clean.substring(4, 8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon - 1);
                    year = (year < 2000) ? 2000 : (year > 2020) ? 2020 : year;
                    cal.set(Calendar.YEAR, year);
                    day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                    clean = String.format("%02d%02d%02d", day, mon, year);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProd:
                Verif();
                break;
            case R.id.txtCancelar:
                Intent Voltar = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(Voltar);
                break;
        }
    }
}

