package com.example.admin.pagination.Activities.SecondActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.admin.pagination.Adapters.RVMigSecondAdapter;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Mig2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class HumanTraffickingSecondActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    List<Mig2> list;
    RVMigSecondAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_of_migration2);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Инструкции");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        list=new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        String s;
        s = '"' + getIntent().getStringExtra("text") + '"';
        Mig2 mig2;

        for (Element element : Jsoup.parse(s).select("*")) {
            if (element.tagName().equals("p")) {
                 mig2=new Mig2();
                mig2.setType(0);
                mig2.setText(element.text());
                list.add(mig2);
            }else if(element.tagName().equals("img")) {
                mig2=new Mig2();
                mig2.setType(1);
                mig2.setText(element.attr("src"));
                list.add(mig2);
            }
        }
        Log.e("SUKA0001",list.size()+"");
       adapter= new RVMigSecondAdapter(list,mRecyclerView,this);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}