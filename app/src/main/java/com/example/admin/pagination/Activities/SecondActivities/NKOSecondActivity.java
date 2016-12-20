package com.example.admin.pagination.Activities.SecondActivities;

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
import com.example.admin.pagination.Adapters.RVNKOSecondAdapter;
import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.Istories;
import com.example.admin.pagination.Serializables.NKO;

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
public class NKOSecondActivity extends AppCompatActivity {
    String date,dateDB;
    Istories istories;
    int limit=15;
    int offset=0;
    DataHelper dataHelper;

    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVNKOSecondAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<NKO> studentList;
    String id;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyfrom_life_second);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_nko);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);
        id=getIntent().getStringExtra("id");
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum);
        studentList = new ArrayList<NKO>();
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
        Cursor cursor = dataHelper.getDataNKO(id);
        Log.e("TAG_NEWS",cursor.getCount()+" kol");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                NKO istories=new NKO();
                istories.setTitle(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_TITLE_COLUMN)));
                istories.setPhone1(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_PHONE1_COLUMN)));
                istories.setPhone(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_PHONE_COLUMN)));
                istories.setMail(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_MAIL_COLUMN)));
                istories.setManager(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_MANAGER_COLUMN)));
                istories.setAddress(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_ADDRESS_COLUMN)));
                istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NKO_TEXT_COLUMN)));
                studentList.add(istories);
            }
            mAdapter=new RVNKOSecondAdapter(studentList,mRecyclerView,this);
            mRecyclerView.setAdapter(mAdapter);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);



    }







}