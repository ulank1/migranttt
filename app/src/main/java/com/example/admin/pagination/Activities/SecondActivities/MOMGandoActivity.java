package com.example.admin.pagination.Activities.SecondActivities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Employment;

public class MOMGandoActivity extends AppCompatActivity {
    public TextView tvName;
    public Context context = MOMGandoActivity.this;
    public TextView tvAddress, tvManager, tvNumber, tvNumber1, tvNumber2;

    public Employment student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momgando);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Кыргызстан");

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.tv_name_employment);
        tvAddress = (TextView) findViewById(R.id.tv_address_second);
        tvManager = (TextView) findViewById(R.id.tv_manager_employment);
        tvNumber = (TextView) findViewById(R.id.tv_phone_number_second);
        tvNumber1 = (TextView) findViewById(R.id.tv_phone_number1_second);
        tvNumber2 = (TextView) findViewById(R.id.tv_phone_number2_second);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvAddress.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.toast_copy_to_buffer, Toast.LENGTH_SHORT).show();
            }
        });
        tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tvNumber.getText().toString(), null)));
            }

        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
