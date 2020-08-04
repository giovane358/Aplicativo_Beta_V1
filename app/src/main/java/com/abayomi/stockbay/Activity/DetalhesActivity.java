package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.abayomi.stockbay.CustomAdapter;
import com.abayomi.stockbay.CustomAdapterStockDetails;
import com.abayomi.stockbay.Model.Model;
import com.abayomi.stockbay.Model.StockDetalhe;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetalhesActivity extends AppCompatActivity  implements AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener{
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
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
    private void searchData(String s) {
        db.collection("User").document(userID)
                .collection("Estoque").whereEqualTo("Search", s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelListDetails.clear();
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

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetalhesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusearch, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}

