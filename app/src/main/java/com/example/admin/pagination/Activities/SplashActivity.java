package com.example.admin.pagination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DataHelper dataHelper=new DataHelper(this);
        if (dataHelper.getDataDate1().getCount()==0) {

            dataHelper.insertDate("ss");
            dataHelper.insertDate("ss");

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SynchronizeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2 * 1000);
    }
}
