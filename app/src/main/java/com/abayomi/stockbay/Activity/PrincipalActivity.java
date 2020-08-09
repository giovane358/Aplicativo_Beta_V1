package com.abayomi.stockbay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abayomi.stockbay.CustomAdapter;
import com.abayomi.stockbay.Model.Model;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener {

    private FirebaseAuth mAuth;
    private RecyclerView mRecycleView;
    private FirebaseFirestore db;
    private CardView cardView;
    private static final String TAG = "DocSnippets";
    private int selectedItem;
    private boolean insertMode;
    private String selectedItemName;
    String userID;
    CustomAdapter adapter;
    ProgressDialog pd;
    SwipeRefreshLayout RefreshLayout;
    List<Model> modelList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mRecycleView = findViewById(R.id.recycler_view);
        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        RefreshLayout = findViewById(R.id.Swipe);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshLayout.setRefreshing(false);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }
        showData();
    }

    private void showData() {
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
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("Nome"),
                                    doc.getString("Quantidade"),
                                    doc.getString("ValoreVenda"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(modelList);
                        //set adapterto recyclerview
                        mRecycleView.setAdapter(adapter);
                    }
                });
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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

    private void searchData(String s) {
        db.collection("User").document(userID)
                .collection("Estoque").whereEqualTo("Search", s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("Nome"),
                                    doc.getString("Quantidade"),
                                    doc.getString("ValoreVenda"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(modelList);
                        //set adapterto recyclerview
                        mRecycleView.setAdapter(adapter);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PrincipalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_settings:
                Intent config = new Intent(getApplicationContext(), ConfigurationActivity.class);
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


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 121:
                Intent edit = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(edit);
                break;
            case 122:
                Intent delete = new Intent(getApplicationContext(), DeleteActivity.class);
                startActivity(delete);
                break;
            case 123:
                Intent details = new Intent(getApplicationContext(), DetalhesActivity.class);
                startActivity(details);
                break;
        }

        return super.onContextItemSelected(item);
    }
}