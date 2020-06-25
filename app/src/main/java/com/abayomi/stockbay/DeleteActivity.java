package com.abayomi.stockbay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeleteActivity extends AppCompatActivity {

    private EditText editDelete_Name;
    private Button btnDelete;
    private String userID;

    private FirebaseAuth mAuth;
    private RecyclerView mRecycleView;
    private FirebaseFirestore db;

    private static final String TAG = "DocSnippets";
    private  static final String KEY_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        editDelete_Name = findViewById(R.id.editDelete_Name);
        btnDelete = findViewById(R.id.btnDelete);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClick (View view)
    {
        switch (view.getId()) {
            case R.id.btnDelete:
                deleteItem();
                break;
        }
    }

    public void deleteItem() {
        
        String nome = editDelete_Name.getText().toString();
        String id = UUID.randomUUID().toString();
             userID = mAuth.getCurrentUser().getUid();
                db.collection("User").document(userID)
                    .collection("Estoque").document(nome)
                        .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Toast.makeText(DeleteActivity.this, "Deletado com Sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent voltar = new Intent(getApplicationContext(), PrincipalActivity.class);
                                    startActivity(voltar);

                                }
                            })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                         Toast.makeText(DeleteActivity.this, "Não foi possível Deletar esse produto", Toast.LENGTH_SHORT).show();
                                    }
                                });
    }
}
