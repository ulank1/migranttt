package com.example.admin.pagination.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Activities.SecondActivities.RegisterActivity;
import com.example.admin.pagination.Adapters.RVProhibitionAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.RulesOfIncoming;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class InspectionActivity extends AppCompatActivity {
    EditText editfio,editday,editmonth,edityear;
    TextView textView,tvSuka;
    Calendar calendar;
    String fio,day,month,year;
    int DIALOG_DATE = 1;
    int myYear = 2016;
    int myMonth = 11;
    int myDay = 22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_blacklist);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvSuka=(TextView) findViewById(R.id.suka);
        editfio=(EditText) findViewById(R.id.edit_fio);
        editday=(EditText) findViewById(R.id.edit_day);
        editmonth=(EditText) findViewById(R.id.edit_month);
        edityear=(EditText) findViewById(R.id.edit_year);
        textView=(TextView) findViewById(R.id.tv_result);



    }


//    fio = request.POST.get('fio')
//    day = request.POST.get('day')
//    month = request.POST.get('month')
//    year = request.POST.get('year')
    public void onClickNewStory(View view) {
        fio=editfio.getText().toString();
        if (edityear.getText().toString().length()==1)
        year=edityear.getText().toString();
        else year="0"+edityear.getText().toString();
        if (editday.getText().toString().length()==1)
        day=editday.getText().toString();
        else  day="0"+editday.getText().toString();
if (editmonth.getText().toString().length()==0)
        month=editmonth.getText().toString();
        else month="0"+editmonth.getText().toString();
        new ParseTask().execute();

    }


    public void onclick(View view) {
        showDialog(DIALOG_DATE);
    }


    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(InspectionActivity.this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            editday.setText(myDay+"");
            editmonth.setText(""+myMonth);
            edityear.setText(""+myYear);
            tvSuka.setText(myDay+"."+myMonth+"."+myYear);
        }
    };

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";


        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/blacklist/?fio="+fio+"&day="+day+"&month="+month+"&year="+year+"&format=json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);



                textView.setText(dataJsonObject.getString("result"));




            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
