package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.abayomi.stockbay.CustomAdapterNotification;
import com.abayomi.stockbay.Model.ModelNotification;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    RecyclerView recycler_view_not;
    CustomAdapterNotification adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    List<ModelNotification> modelNotificationList = new ArrayList<>();
    // layout kmanger for recycleview
    RecyclerView.LayoutManager layoutManager;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        swipeRefreshLayout = findViewById(R.id.SwipeNot);

        recycler_view_not = findViewById(R.id.recycler_view_not);

        //set recycler view properties
        recycler_view_not.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_view_not.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        showdata();
    }

    private void showdata() {
        userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).collection("Notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot doc : task.getResult()) {

                            ModelNotification modelNotification = new ModelNotification(doc.getString("id"),
                                    doc.getString("Nome"),
                                    doc.getString("Quantidade"),
                                    doc.getString("DtInclusao"),
                                    doc.getString("HrInclusao")
                                    );
                            modelNotificationList.add(modelNotification);

                        }
                        adapter = new CustomAdapterNotification(modelNotificationList);
                        recycler_view_not.setAdapter(adapter);
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Viewvoltar:
                Intent voltar = new Intent(this, ConfigActivity.class);
                startActivity(voltar);
                break;
        }
    }
}
