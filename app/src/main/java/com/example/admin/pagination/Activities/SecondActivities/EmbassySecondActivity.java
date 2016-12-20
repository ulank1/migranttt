package com.example.admin.pagination.Activities.SecondActivities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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


import com.example.admin.pagination.Adapters.RVEmbassyAdapter;
import com.example.admin.pagination.Adapters.RVEmbassySecondAdapter;
import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;

import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Istories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EmbassySecondActivity extends AppCompatActivity {

    String id="-1";
    DataHelper dataHelper;
    int id1;
    String TAG="TAG_EMBASSY";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVEmbassySecondAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Consulate> studentList;

    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embassy_second);
        TextView tvTitle=(TextView) findViewById(R.id.tv_embassy_second);
        TextView tvPhone=(TextView) findViewById(R.id.tv_phone_number_second);
        TextView tvFax=(TextView) findViewById(R.id.tv_fax_second);
        TextView tvSite=(TextView) findViewById(R.id.tv_site_second);
        TextView tvAddress=(TextView) findViewById(R.id.tv_address_second);
        TextView consulate=(TextView) findViewById(R.id.tv_consulate_second);
        final String phoneNumber;
        String s=getIntent().getStringExtra("phone_number");
        tvPhone.setText(s);
        Log.e("TAG_S",s);
        phoneNumber=s;

        final String fax=getIntent().getStringExtra("fax");
        tvFax.setText(fax);
        s=getIntent().getStringExtra("country");
        tvTitle.setText(s);
        final String site=getIntent().getStringExtra("site");
        tvSite.setText(site);
        final String address=getIntent().getStringExtra("address");
        tvAddress.setText(address);
        id=getIntent().getStringExtra("id");
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",address );
                clipboard.setPrimaryClip(clip);
                Toast.makeText(EmbassySecondActivity.this, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
            }
        });
        tvFax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",fax );
                clipboard.setPrimaryClip(clip);
                Toast.makeText(EmbassySecondActivity.this, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneNumber , null)));
            }
        });
        try {


            tvSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+site));
                    startActivity(browserIntent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this,"Такого сайта не существует",Toast.LENGTH_SHORT).show();
        }
        Log.e("TAG_S_ID",id);
        id1=Integer.parseInt(id);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_embassy);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulate);
        mRecyclerView.setVisibility(View.VISIBLE);
        studentList = new ArrayList<Consulate>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

            Cursor cursor = dataHelper.getDataConsulate(id);
            if ( cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Consulate istories=new Consulate();
                    istories.setAddress(cursor.getString(cursor.getColumnIndex(DataHelper.CONSULATE_ADDRESS_COLUMN)));
                    istories.setRegion(cursor.getString(cursor.getColumnIndex(DataHelper.CONSULATE_REGION_COLUMN)));
                    istories.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DataHelper.CONSULATE_PHONE_COLUMN)));
                    studentList.add(istories);
                }
                mAdapter=new RVEmbassySecondAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);


            } else {mRecyclerView.setVisibility(View.GONE);
                    consulate.setVisibility(View.GONE);
            }



    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}