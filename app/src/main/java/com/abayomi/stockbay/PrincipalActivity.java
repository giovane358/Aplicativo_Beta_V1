package com.abayomi.stockbay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener {

    // Firebase
    private FirebaseAuth mAuth;
    private RecyclerView mRecycleView;
    private FirebaseFirestore db;

    private CardView cardView;

    private static final String TAG = "DocSnippets";
    private int selectedItem;
    String userID;
    CustomAdapter adapter;
    ProgressDialog pd;

    List<Model> modelList = new ArrayList<>();
    // layout kmanger for recycleview
    RecyclerView.LayoutManager layoutManager;

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
    }

    private void showData() {
        //set title of progress dialog
    userID = mAuth.getCurrentUser().getUid();
         db.collection("User").document(userID)
             .collection("Estoque")
                .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                      @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //called when data is retrieved
                        //show data
                        for(DocumentSnapshot doc: task.getResult())
                         {
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

        switch (item.getItemId())
        {
            case 121:
                Intent edit = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(edit);
                break;
            case 122:
                deleteItem();
        }

        return super.onContextItemSelected(item);
    }

    private void deleteItem()
    {

        userID = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userID)
                .collection("Estoque").document("t9NyO37FtPtlqSpsB0Ky")
                    .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Produto deletado com sucesso!");
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error ao deletar produto")
;                    }
                });
    }


}





