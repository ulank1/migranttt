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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVEmbassyAdapter;
import com.example.admin.pagination.Adapters.RVEmbassySecondAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Embassy;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
//date 5
public class EmbassyActivity extends AppCompatActivity {
    EditText editSearch;
    String country="";
    DataHelper dataHelper;
    String date,dateDB;
    boolean bool=false;
    String TAG="TAG_EMBASSY";
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVEmbassyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Embassy> studentList;
    TextView title;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embassy);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        title= (TextView) findViewById(R.id.toolbar_title);
//        title.setText("Посольства");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_embassy);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);
        // editSearch=(EditText) findViewById(R.id.tv_embassy_search);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<>();
        handler = new Handler();
        mAdapter = new RVEmbassyAdapter(studentList, mRecyclerView, this);
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

     pizdec();



        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        //  searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
//                        .show();
                country=query;
                studentList.clear();
                String countryDataHelper;

                    Cursor cursor=dataHelper.getDataEmbassy();
                    if (cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            countryDataHelper=cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_COUNTRY_COLUMN)).toLowerCase();
                            if(countryDataHelper.contains(country.toLowerCase())){
                                Embassy istories=new Embassy();
                                istories.setEmail(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_EMAIL_COLUMN)));
                                istories.setSite(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_SITE_COLUMN)));
                                istories.setRegion(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_ADDRESS_COLUMN)));
                                istories.setId(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_JSON_ID_COLUMN)));
                                istories.setFax(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_FAX_COLUMN)));
                                istories.setCountry(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_COUNTRY_COLUMN)));
                                istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_IMAGE_COLUMN)));

                                istories.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_PHONE_COLUMN)));
                                studentList.add(istories);
                            }
                        }
                        mAdapter=new RVEmbassyAdapter(studentList,mRecyclerView,EmbassyActivity.this);
                        mRecyclerView.setAdapter(mAdapter);

                }



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


    }
    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;  mAdapter=new RVEmbassyAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);
        Cursor cursor=dataHelper.getDataDate("5");
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
            cursor.moveToPosition(0);

            dateDB=cursor.getString(cursor.getColumnIndex(DataHelper.DATE_LAST_DATE_COLUMN));
            if (!dateDB.equals(date)){
                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    new ParseTask().execute();
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();
                }
            }else pizdec();
        }
    }
    public void pizdec(){

            Cursor cursor = dataHelper.getDataEmbassy();
            Log.e("TAG_NEWS",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Embassy istories=new Embassy();
                    istories.setEmail(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_EMAIL_COLUMN)));
                    istories.setSite(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_SITE_COLUMN)));
                    istories.setRegion(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_ADDRESS_COLUMN)));
                    istories.setId(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_JSON_ID_COLUMN)));
                    istories.setFax(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_FAX_COLUMN)));
                    istories.setCountry(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_COUNTRY_COLUMN)));
                    istories.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_PHONE_COLUMN)));
                    istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.EMBASSY_IMAGE_COLUMN)));

                    studentList.add(istories);
                }
                mAdapter=new RVEmbassyAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);


            }


    }


    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/embassy/?limit=0&format=json");

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

            studentList.clear();
            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");



                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Log.d(TAG, "1: " );
                    Embassy student = new Embassy();
                    Log.d(TAG, "2: ");
                    student.setPhoneNumber(menu.getString("phone_number"));
                    student.setCountry(menu.getString("country"));
                    student.setEmail(menu.getString("email"));
                    student.setFax(menu.getString("fax"));
                    student.setId(menu.getString("id"));
                    student.setRegion(menu.getString("address"));
                    student.setSite(menu.getString("site"));
                    student.setImage(menu.getString("image"));

                    if (i==0){dataHelper.deleteEmbassy();
                        Log.e("TAG_NEWS","DELETE");
                    }
                    dataHelper.insertEmbassy(student);
                    studentList.add(student);





                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSON_PIZDEC_EMBASSY");
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

                URL url = new URL("http://176.126.167.249/api/v1/consulate/?limit=0&format=json");

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


            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");
                dataHelper.deleteConsulate();

                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Log.d(TAG, "1: " );
                    Consulate student = new Consulate();
                    Log.d(TAG, "2: ");
                    student.setPhoneNumber(menu.getString("phone_number"));

                    student.setRegion(menu.getString("region"));

                    student.setAddress(menu.getString("address"));

                    Log.e(TAG+"ADD",student.getAddress());

                    if (i==0){
                        Log.e("TAG_NEWS","DELETE");
                    }
                    JSONObject country=menu.getJSONObject("embassy");
                    String id=country.getString("id");
                    dataHelper.insertConsulate(student,id);






                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSON_PIZDEC_EMBASSY_second");
            }
            progressBar.setVisibility(View.GONE);

            mAdapter=new RVEmbassyAdapter(studentList,mRecyclerView,EmbassyActivity.this);
            mRecyclerView.setAdapter(mAdapter);

            dataHelper.updateDate(date,"5");
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
                dataHelper.updateDate("ss","5");
                ifConnect();

                return true;
            case R.id.action_search:
                searchView.setMenuItem(item);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);


        return true;
    }

}