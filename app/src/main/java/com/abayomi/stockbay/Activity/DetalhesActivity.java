package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.abayomi.stockbay.CustomAdapter;
import com.abayomi.stockbay.CustomAdapterStockDetails;
import com.abayomi.stockbay.Model.Model;
import com.abayomi.stockbay.Model.StockDetalhe;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetalhesActivity extends AppCompatActivity {
    // Firebase
    private FirebaseAuth mAuth;
    private RecyclerView mRecycleViewDetails;
    private FirebaseFirestore db;

    private CardView cardView;


    String userID;
    CustomAdapterStockDetails adapter;
    ProgressDialog pd;
    private boolean insertMode;
    SwipeRefreshLayout RefreshLayout;

    List<StockDetalhe> modelListDetails = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    private String selectedItemName;
    private String idSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        mRecycleViewDetails = findViewById(R.id.mRecycleViewDetails);

        //set recycler view properties
        mRecycleViewDetails.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleViewDetails.setLayoutManager(layoutManager);



        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        showData();
    }

    private void showData() {
        //set title of progress dialog
       idSelect = "Gg";
        userID = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userID)
                .collection("Estoque")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when data is retrieved
                        //show data
                        for (DocumentSnapshot doc : task.getResult()) {
                            StockDetalhe stockDetalhe = new StockDetalhe(doc.getString("id"),
                                    doc.getString("Nome"),
                                    doc.getString("Quantidade"),
                                    doc.getString("DataCompra"),
                                    doc.getString("ValorCusto"),
                                    doc.getString("ValoreVenda"),
                                    doc.getString("Descricao"));
                            modelListDetails.add(stockDetalhe);
                        }
                        //adapter
                        adapter = new CustomAdapterStockDetails(modelListDetails);
                        //set adapterto recyclerview
                        mRecycleViewDetails.setAdapter(adapter);
                    }
                });
    }

}

