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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.DateDateDB;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;
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
//Date 8
public class NewsActivity extends AppCompatActivity {

    DataHelper dataHelper;
    String title="";
    String TAG="TAG";
    private Toolbar toolbar;
    String dateDB,date;
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVNewsAdapter mAdapter;
    int total_count=100000;
    private List<Istories> studentList;
    ProgressBar progressBar;
    protected Handler handler;
    private MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_news);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataHelper = new DataHelper(this);


        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<>();
        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }


        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
//
pizdec();



        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        //  searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                title = query;

                    Cursor cursor=dataHelper.getData();
                    studentList.clear();
                    String titleDataHelper;
                    if(cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            titleDataHelper=cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)).toLowerCase();
                            if(titleDataHelper.contains(title.toLowerCase())){
                                Istories istories=new Istories();
                                istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)));
                                istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_TEXT_COLUMN)));
                                studentList.add(istories);
                            }

                        }
                        mAdapter=new RVNewsAdapter(studentList,mRecyclerView,NewsActivity.this);
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
        studentList.clear();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("8");
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
            }else pizdec();
        }
    }
    public void pizdec(){

            Cursor cursor = dataHelper.getData();
            Log.e("TAG_NEWS",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Istories istories=new Istories();
                    istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)));
                    istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_TEXT_COLUMN)));
                    istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_IMAGE_COLUMN)));
                    studentList.add(istories);
                }
                mAdapter=new RVNewsAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);


            }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();

                return true;
            case R.id.action_ubdate:
                dataHelper.updateDate("ss","8");
                ifConnect();

                return true;
            case R.id.action_search:
                searchView.setMenuItem(item);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }


    }


    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/news/?limit=0&format=json");

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
            Log.e("TAG", json);

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Istories student = new Istories();
                    student.setImage(menu.getString("image"));
                    student.setText(menu.getString("text_ru"));
                    student.setNickName(menu.getString("title_ru"));

                    if (i==0){dataHelper.delete();
                    }

                        dataHelper.insertNews(student);

                    studentList.add(student);


                }

                Log.d(TAG, "NET NET dsfsadadsgf: ");
            } catch (JSONException e) {
                e.printStackTrace();



                Log.d(TAG, "JSON_PIZDEC");
            }
            progressBar.setVisibility(View.GONE);
            dataHelper.updateDate(date,"8");
            Log.e("TAG_1","NORM");



            mAdapter=new RVNewsAdapter(studentList,mRecyclerView,NewsActivity.this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

}