package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.abayomi.stockbay.CustomAdapterHistoric;
import com.abayomi.stockbay.CustomAdapterNotification;
import com.abayomi.stockbay.CustomAdapterTotal;
import com.abayomi.stockbay.Model.Model;
import com.abayomi.stockbay.Model.ModelHistoric;
import com.abayomi.stockbay.Model.ModelNotification;
import com.abayomi.stockbay.Model.ModelTotal;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListHistoricActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView mRecycleView;
    private FirebaseFirestore db;

    CustomAdapterTotal adapter;
    CustomAdapterHistoric customAdapterHistoric;

    RecyclerView.LayoutManager layoutManager;
    private String userId;

    RecyclerView recyclerView_Total;
    RecyclerView recyclerView_Historic;

    private List<ModelTotal> modelTotalList = new ArrayList<>();
    private List<ModelHistoric> modelHistoricList = new ArrayList<>();

    SimpleDateFormat MES = new SimpleDateFormat("MM");
    Date data = new Date();

    String mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historic);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        recyclerView_Total = findViewById(R.id.recyclerView_Total);
        recyclerView_Historic = findViewById(R.id.recyclerView_Historic);


        recyclerView_Total.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_Total.setLayoutManager(layoutManager);

        recyclerView_Historic.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_Historic.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        showdata();
        showdataHist();
    }

    private void showdataHist() {
        userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).collection("Historico").document("2020").collection("08")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot doc : task.getResult()) {
                            ModelHistoric modelHistoric = new ModelHistoric(doc.getString("id"),
                                    doc.getString("Nome"),
                                    doc.getString("Quantidade_Comprada"),
                                    doc.getString("ValorVenda"),
                                    doc.getString("ValorTotal"));
                            modelHistoricList.add(modelHistoric);

                        }
                        customAdapterHistoric = new CustomAdapterHistoric(modelHistoricList);
                        recyclerView_Historic.setAdapter(customAdapterHistoric);
                    }
                });
    }

    private void showdata() {
        userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).collection("Total").document("2020").collection("08")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot doc : task.getResult()) {

                            ModelTotal modelTotal = new ModelTotal(doc.getString("id"),
                                    doc.getDouble("Total"));

                            modelTotalList.add(modelTotal);

                        }
                        adapter = new CustomAdapterTotal(modelTotalList);
                        recyclerView_Total.setAdapter(adapter);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucontact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                Intent config = new Intent(getApplicationContext(), ConfigurationActivity.class);
                startActivity(config);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}