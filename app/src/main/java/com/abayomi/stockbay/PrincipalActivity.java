package com.abayomi.stockbay;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class PrincipalActivity extends AppCompatActivity {

    private ListView ListEstoque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ListEstoque = (ListView) findViewById(R.id.ListEstoque);

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





