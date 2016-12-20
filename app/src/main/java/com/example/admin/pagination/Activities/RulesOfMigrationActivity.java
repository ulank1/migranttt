



package com.example.admin.pagination.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.pagination.Adapters.RVForumAdapter;
import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Adapters.RVRulesOfIncomingAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;
import com.example.admin.pagination.Serializables.RulesOfIncoming;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RulesOfMigrationActivity extends AppCompatActivity {

    Istories istories;
    int limit=15;
    int offset=0;
    DataHelper dataHelper;

    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVRulesOfIncomingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<RulesOfIncoming> studentList;
    String  position;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_of_incoming);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_abroad);
        position=getIntent().getStringExtra("id");

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_rules_of_incoming);
        studentList = new ArrayList<RulesOfIncoming>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

            Cursor cursor = dataHelper.getDataROM(position);
            Log.e("TAG_NEWS",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RulesOfIncoming istories=new RulesOfIncoming();
                    istories.setTitle(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN)));
                    istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_MIGRATION_TEXT_COLUMN)));
                    istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_MIGRATION_IMAGE_COLUMN)));

                    studentList.add(istories);
                }
                mAdapter=new RVRulesOfIncomingAdapter(studentList,mRecyclerView,this,getString(R.string.ac_abroad));
                mRecyclerView.setAdapter(mAdapter);


            }




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }


}