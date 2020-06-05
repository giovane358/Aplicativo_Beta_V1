package com.abayomi.stockbay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                break;
        }
    }

    public void deleteItem(View v) {


    }
}
