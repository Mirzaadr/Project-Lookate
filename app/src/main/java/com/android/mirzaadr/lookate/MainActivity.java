package com.android.mirzaadr.lookate;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    public void openFood(View v) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
    public void openTransport(View v){
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent);
    }
    public void openResiden(View v){
        Intent intent = new Intent(MainActivity.this, MainActivity4.class);
        startActivity(intent);
    }
    public void openShop(View v){
        Intent intent = new Intent(MainActivity.this, MainActivity5.class);
        startActivity(intent);
    }
    public void openPublicplace(View v){
        Intent intent = new Intent(MainActivity.this, MainActivity6.class);
        startActivity(intent);
    }

}