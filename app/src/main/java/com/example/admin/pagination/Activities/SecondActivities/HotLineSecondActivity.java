package com.example.admin.pagination.Activities.SecondActivities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.pagination.Adapters.RVHotLineAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Hotline;

public class HotLineSecondActivity extends AppCompatActivity {
    String phoneNumber;
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line_second);
        TextView tvTitle=(TextView) findViewById(R.id.tv_second_hot_line_title);

        TextView tvNumber=(TextView) findViewById(R.id.tv_phone_hot);
        Toolbar toolbar;
        dataHelper=new DataHelper(this);
        db();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_hotline);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        String  description;
        String id=getIntent().getStringExtra("id");

        Cursor cursor = dataHelper.getDataHot(id);
        Log.e("TAG_NEWS",cursor.getCount()+" kol");
        if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

             String title=(cursor.getString(cursor.getColumnIndex(DataHelper.HOT_TITLE_COLUMN)));
               phoneNumber=(cursor.getString(cursor.getColumnIndex(DataHelper.HOT_PHONE_COLUMN)));

            if (title.equals(".")){
                tvTitle.setVisibility(View.GONE);
            }else tvTitle.setText(title);

            tvNumber.setText(phoneNumber);



        }
    }
    public void db(){

    }
    public void onClickHot(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneNumber , null)));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

}
