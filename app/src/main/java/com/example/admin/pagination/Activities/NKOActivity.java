package com.example.admin.pagination.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVEAEUAdapter;
import com.example.admin.pagination.Adapters.RVHotLineAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.DateDateDB;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.NKO;
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
import java.util.Calendar;
//date 12
public class NKOActivity extends AppCompatActivity {
    DataHelper dataHelper;
    RVEAEUAdapter mAdapter;
    ArrayList<EAEU> studentList;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    String date,dateDB;
    Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line12);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_nko);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar =(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        dataHelper=new DataHelper(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        studentList=new ArrayList<>();
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        EAEU eaeu=new EAEU();
        eaeu.setName("РФ");

db();


    }
    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("12");
        if (cursor.getCount()==0){
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                new ParseTask().execute();

                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();
            }

        }
        else {
            cursor.moveToFirst();

            dateDB=cursor.getString(cursor.getColumnIndex(DataHelper.DATE_LAST_DATE_COLUMN));
            DateDateDB dateDateDB=new DateDateDB();
            if (dateDateDB.calendar1(dateDB)){
                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    new ParseTask().execute();
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();
                }
            }else db();
        }
    }
    public void db(){

        Cursor cursor = dataHelper.getNKOCountry();
        studentList.clear();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                EAEU eaeu=new EAEU();
                eaeu.setName(cursor.getString(cursor.getColumnIndex(DataHelper.NKOC_TITLE_COLUMN)));
                eaeu.setId(cursor.getString(cursor.getColumnIndex(DataHelper.NKOC_ID_COLUMN)));

                studentList.add(eaeu);
                mAdapter=new RVEAEUAdapter(studentList,mRecyclerView,this,6);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();

                return true;
            case R.id.action_ubdate:
                dataHelper.updateDate("ss","12");
                ifConnect();

                return true;

            default:  return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);



        return true;
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/region/?&limit=0&format=json");

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
                Log.e("TAG_S",3+"DELETE");

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG_S",1+"PIZDEC");
            }

            Log.e("TAG_S",4+"");
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            studentList.clear();

            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("name"));

                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteNKOCountry();
                    studentList.add(eaeu);
                    dataHelper.insertNKOCountry(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


            new ParseTask1().execute();


        }
    }
    public class ParseTask1 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/nko/?limit=0&format=json");

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

            Log.e("TAG_S",4+"");
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);


            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    NKO student = new NKO();
                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    student.setPhone(menu.getString("phone_number"));
                    student.setAddress(menu.getString("address"));
                    student.setMail(menu.getString("mail"));
                    student.setManager(menu.getString("manager"));
                    student.setPhone1(menu.getString("phone_number_1"));

                    JSONObject country=menu.getJSONObject("region");
                    String id=country.getString("id");
                    if (i==0){dataHelper.deleteNKO();
                    }
                    dataHelper.insertNKO(student,id);






                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }
            progressBar.setVisibility(View.GONE);

            mAdapter=new RVEAEUAdapter(studentList,mRecyclerView,NKOActivity.this,6);
            mRecyclerView.setAdapter(mAdapter);
            dataHelper.updateDate(date,"12");

        }
    }
}

