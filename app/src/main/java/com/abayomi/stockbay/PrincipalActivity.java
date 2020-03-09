package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class PrincipalActivity extends AppCompatActivity {

    private ListView ListEstoque;
    private FirebaseFirestore db;
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ListEstoque = (ListView) findViewById(R.id.ListEstoque);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }


    private void getEstoque(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

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

    //method do listview = txtEstoque

}





