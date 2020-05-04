package com.abayomi.stockbay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String userID;
    RecyclerView mRecycleView;
    private ListView ListEstoque;
    private FirebaseFirestore db;
    private static final String TAG = "DocSnippets";

    List<Model> modelList = new ArrayList<>();
    // layout kmanger for recycleview
    RecyclerView.LayoutManager layoutManager;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mRecycleView = findViewById(R.id.recycler_view);
        //set recycler view properties
        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //show data in recyclerview
        showData();


        ProgressDialog pb = new  ProgressDialog;
    }

    private void showData() {
        //set title of progress dialog
    pd.setTitle("Loading Data...");
    //show progress dialog
    pd.show();
    userID = mAuth.getCurrentUser().getUid();
    db.collection("User").document(userID)
            .collection("Estoque").document()
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    //called when data is retrieved
                    pd.dismiss();
                    //show data
                    for(DocumentSnapshot doc: task.getResult()){
                        Model model = new Model(doc.getString("id"),
                        doc.getString("nome"),
                        doc.getString("Quatidade"),
                        doc.getString("DataCompra"),
                        doc.getString("ValorCusto"),
                        doc.getString("ValorCusto"),
                        doc.getString("Descricao"));
                        modelList.add(model);
                    }
                    //adapter
                    adapter = new CustomAdapter(this, modelList);
                    //set adapterto recyclerview
                    mRecycleView.setAdapter(adapter);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //called when data is any error while retrieving
                    pd.dismiss();

                    Toast.makeText(PrincipalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }


    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_settings:
                Intent config = new Intent(getApplicationContext(), ConfigActivity.class);
                startActivity(config);
                break;

            case R.id.nav_insert:
                Intent insert = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(insert);
                break;
            case R.id.nav_contact:
                Intent contact = new Intent(getApplicationContext(), ListContactActivity.class);
                startActivity(contact);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}





