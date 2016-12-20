package com.example.admin.pagination.Activities;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.pagination.Adapters.RVEmbassyAdapter;
import com.example.admin.pagination.Adapters.RVHotLineAdapter;
import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.Istories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//date 11
public class HotLineActivity extends AppCompatActivity {
    String date,dateDB;
    Istories istories;
    int limit=15;
    int offset=0;
    DataHelper dataHelper;

    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVHotLineAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Hotline> studentList;
    String id;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_hotline);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);
id=getIntent().getStringExtra("id");
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<Hotline>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);



db();




    }
    public void db(){
        Cursor cursor = dataHelper.getDataHot(id);
        Log.e("TAG_NEWS",cursor.getCount()+" kol");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Hotline istories=new Hotline();
                istories.setTitle(cursor.getString(cursor.getColumnIndex(DataHelper.HOT_TITLE_COLUMN)));
                istories.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DataHelper.HOT_PHONE_COLUMN)));
                istories.setDescription(cursor.getString(cursor.getColumnIndex(DataHelper.HOT_DESCRIPTION_COLUMN)));
                studentList.add(istories);
            }
            mAdapter=new RVHotLineAdapter(studentList,mRecyclerView,this);
            mRecyclerView.setAdapter(mAdapter);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            finish();

             return super.onOptionsItemSelected(item);



    }







}