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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVForumQuestionsAndAnswersAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.VseAndUzery;

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
import java.util.ArrayList;
import java.util.List;

public class CommentStoryActivity extends AppCompatActivity {

    String id;
    DataHelper dataHelper;

    String TAG="TAG";
    String idUser="-1";

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVForumQuestionsAndAnswersAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<VseAndUzery> studentList;
    EditText edit;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_and_answers);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Вопросы и ответы");

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);
        Cursor cursor=dataHelper.getDataUser();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
        }
        id=getIntent().getStringExtra("id");
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum_questions_answers);
        studentList = new ArrayList<VseAndUzery>();
        handler = new Handler();
        edit=(EditText) findViewById(R.id.edit_forum_questions_answers_message);
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);




        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            Toast.makeText(this,"RAbotaet",Toast.LENGTH_SHORT).show();
            studentList.add(null);
            mAdapter = new RVForumQuestionsAndAnswersAdapter(studentList, mRecyclerView,this);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.notifyDataSetChanged();
            loadData();

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView


            // create an Object for Adapter
            mAdapter = new RVForumQuestionsAndAnswersAdapter(studentList, mRecyclerView,this);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.notifyDataSetChanged();


            if (studentList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);

            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
            }

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //add null , so the adapter will check view_type and show progress bar at bottom

                    Log.e("TAG_SUKA", "SUKA_RABOTAET");
                    int start = studentList.size();
                    if (start < total_count - 1)
                        progressBar.setVisibility(View.VISIBLE);
                    else progressBar.setVisibility(View.GONE);
                    new ParseTask(start, 0).execute();


                }
            });

        } else {
            Toast.makeText(this,"NeRAbotaet",Toast.LENGTH_SHORT).show();

             cursor = dataHelper.getDataAById(id);
            Log.e("TAG_QA",cursor.getCount()+" kol   "+id);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    VseAndUzery istories=new VseAndUzery();
                    istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.A_ANSWER_COLUMN)));
                    istories.setUsername(cursor.getString(cursor.getColumnIndex(DataHelper.A_USERNAME_COLUMN)));
                    studentList.add(istories);
                }
                mAdapter=new RVForumQuestionsAndAnswersAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);


            }


        }

    }


    // load initial data
    private void loadData() {
        new ParseTask(0,1).execute();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
    public void onClickQA(View view) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("user", "/api/v1/user/"+idUser+"/");
            obj.put("question","/api/v1/question/"+id+"/");
            obj.put("answer",edit.getText().toString());

            Log.e("TAG", "asd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        new SendJsonDataToServer().execute(String.valueOf(obj));
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";
        int a,b;
        public ParseTask(int a,int b){
            this.a=a;this.b=b;
        }
        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/comment/?question__id="+id+"&offset="+a+"&limit=15&format=json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.e("TAG_S",""+a);
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
            if (b==1) {
                studentList.remove(studentList.size() - 1);
                mAdapter.notifyItemRemoved(studentList.size());
            }
            Log.e("TAG", json);
            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");
                Log.e(TAG+"Total",total_count+""+a);
                JSONObject secondObject = menus.getJSONObject(1);
                secondName = secondObject.getString("id");
                Log.d(TAG, "Второе имя: " + secondName);

                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Log.d(TAG, "1: " );
                    VseAndUzery student = new VseAndUzery();
                    Log.d(TAG, "2: ");

                        user=menu.getJSONObject("user");
                        student.setUsername(user.getString("username"));
                        student.setText(menu.getString("answer"));
                    if (i==0&&b==1){dataHelper.deleteAById(id);
                        Log.e("TAG_NEWS","DELETE");
                    }
                    dataHelper.insertA(student,id);
                        studentList.add(student);
                    

                    Log.e("TAG_IS",student.getTitle()+"   "+student.getText());



                }

                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "NET NET dsfsadadsgf: ");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSON_Q_A_PIZDEC");
            }
            Log.e("TAG_1","NORM");





        }
    }
    class SendJsonDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://176.126.167.231:8000/api/v1/comment/");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                try {
                    writer.write(JsonDATA);
                } catch (Exception e) {
                    Log.e("TAG", "Error");
                }
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                Log.e("TAG", "asdfasd");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    return null;
                }
                JsonResponse = buffer.toString();
                Log.e("TAG", JsonResponse);
                return JsonResponse;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return "";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

}