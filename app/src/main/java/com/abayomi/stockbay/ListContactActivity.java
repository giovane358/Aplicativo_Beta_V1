package com.abayomi.stockbay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListContactActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String userID;
    RecyclerView mRecycleView;
    private FirebaseFirestore db;
    private static final String TAG = "DocSnippets";

    List<ModelContact> modelListContact = new ArrayList<>();
    // layout kmanger for recycleview
    RecyclerView.LayoutManager layoutManager;

    CustomAdapterContact adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        mRecycleView = findViewById(R.id.recycler_view_contact);
        //set recycler view properties
        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //show data in recyclerview
        showDataContact();
    }

    private void showDataContact() {
        //set title of progress dialog
        userID = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userID)
                .collection("Contato")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot cont: task.getResult()){
                            ModelContact modelContact = new ModelContact(cont.getString("id"),
                                    cont.getString("NomeFornc"),
                                    cont.getString("TelefoneFornec"));
                            modelListContact.add(modelContact);
                        }
                    }
                });
        adapter = new CustomAdapterContact(modelListContact);
        mRecycleView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucontact, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_settings:
                Intent config = new Intent(getApplicationContext(), ConfigActivity.class);
                startActivity(config);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.nav_novo:
                Intent insert = new Intent(getApplicationContext(), ContactInsert.class);
                startActivity(insert);
                break;

        }
    }
}
