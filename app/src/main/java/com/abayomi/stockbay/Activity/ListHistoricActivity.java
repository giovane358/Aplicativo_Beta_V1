package com.abayomi.stockbay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.abayomi.stockbay.CustomAdapterHistoric;
import com.abayomi.stockbay.Model.ModelTotal;
import com.abayomi.stockbay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListHistoricActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView mRecycleView;
    private FirebaseFirestore db;
    CustomAdapterHistoric adapter;
    RecyclerView.LayoutManager layoutManager;

    private List<ModelTotal> modelTotals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historic);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

   /* private void showData() {
        String userID = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userID)
                .collection("Historico")
                .orderBy("DataInsert", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when data is retrieved
                        //show data
                        for (DocumentSnapshot doc : task.getResult()) {
                            ModelTotal modelTotal = new ModelTotal(doc.getString("id"),
                                    doc.getString("Total"));
                            modelTotals.add(modelTotal);
                        }
                        //adapter
                        adapter = new HistoricAdapter(modelTotals);
                        //set adapterto recyclerview
                        mRecycleView.setAdapter(adapter);
                    }
                });
    }*/
}