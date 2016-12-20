package com.example.admin.pagination.Activities.SecondActivities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVEmploymentSecondAdapter;
import com.example.admin.pagination.Adapters.RVForumAdapter;
import com.example.admin.pagination.Adapters.RVStorySecondAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Employment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EmploymentSecondActivity extends AppCompatActivity {
    DataHelper dataHelper;

    String TAG="TAG";
    private Toolbar toolbar;
    String idUser="d";
    Button button;
    EditText editText;
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVEmploymentSecondAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Employment> studentList;
    String category="";
    ProgressBar progressBar;
    protected Handler handler;
    ArrayList<String> arrayList;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyfrom_life_second);
        position=getIntent().getStringExtra("id");

        Log.e("POSITION_SUKA",position+"");
        category=getIntent().getStringExtra("text");
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(category+"");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

        button=(Button) findViewById(R.id.new_story_button);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum);
        studentList = new ArrayList<Employment>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser.equals("d")){
                    getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                }
                JSONObject obj = new JSONObject();
                try {
                    obj.put("user", "/api/v1/user/"+idUser+"/");
                    if (editText.getText().toString().length()>99){
                        title[0] =editText.getText().toString().substring(0,99);
                    }
                    else title[0]=editText.getText().toString();
                    obj.put("title",title[0]);
                    obj.put("text",editText.getText().toString());
                    obj.put("publish",false);

                    Log.e("TAG", "asd");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                new SendJsonDataToServer().execute(String.valueOf(obj));
            }
        });*/
      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idUser.equals("d")){
                    startActivity(new Intent(StoryfromLifeSecondActivity.this, RegisterActivity.class));
                }
                Intent intent=new Intent(StoryfromLifeSecondActivity.this,NewStoryActivity.class);
                intent.putExtra("text",position);

                startActivity(intent);
            }
        });*/
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
            Cursor cursor = dataHelper.getDataEmployment(position);
            studentList.clear();
            Employment istories=new Employment();


            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    istories=new Employment();
                    istories.setName(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_NAME_COLUMN)));
                    istories.setAdress(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_ADDRESS_COLUMN)));
                    istories.setManager(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_MANAGER_COLUMN)));
                    istories.setPhone_number(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_NUMBER_COLUMN)));
                    istories.setPhone_number1(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_NUMBER1_COLUMN)));
                    istories.setPhone_number2(cursor.getString(cursor.getColumnIndex(DataHelper.EMPLOYMENT_NUMBER2_COLUMN)));
                    studentList.add(istories);
                }

                mAdapter=new RVEmploymentSecondAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);

            }



    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
