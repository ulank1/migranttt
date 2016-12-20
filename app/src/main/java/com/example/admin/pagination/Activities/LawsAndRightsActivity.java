package com.example.admin.pagination.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.admin.pagination.Adapters.RVLawsAndRightsAdapter;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;

import java.util.ArrayList;

public class LawsAndRightsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Istories istories;
    ArrayList<Istories> listLaws;
    RVLawsAndRightsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws_and_rights);
        recyclerView=(RecyclerView) findViewById(R.id.rv_laws);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        listLaws=new ArrayList<>();
        initializeData();
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Права и обязанности");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    private void initializeData(){

        for( int i=0;i<4;i++){
            istories=new Istories();
            istories.setNickName("ulik");
            istories.setText("dfghjdd d f dsf d sf sd   dfd fdfdsdf \nvdsvfjdfdfs" +
                    "sdfhbdbfbsdjbhj \nhsdhfjbdsbfjsdhfjvdjsf");
            listLaws.add(istories);
        }
        adapter=new RVLawsAndRightsAdapter(LawsAndRightsActivity.this,listLaws);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
