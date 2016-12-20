package com.example.admin.pagination.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Activities.SecondActivities.NewQuestionActivity;
import com.example.admin.pagination.Activities.SecondActivities.NewStoryActivity;
import com.example.admin.pagination.Activities.SecondActivities.RegisterActivity;
import com.example.admin.pagination.Activities.SecondActivities.StoryfromLifeSecondActivity;
import com.example.admin.pagination.Adapters.RVForumAdapter;
import com.example.admin.pagination.Adapters.RVQuestionAdapter;
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


public class QuestionFragment extends Fragment{
    DataHelper dataHelper;
    int notPublishCounter=0;
    String TAG="TAG";
    private Toolbar toolbar;
    String idUser="d";
    Button button;
    EditText editText;
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVQuestionAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private ArrayList<String> studentList;

    ProgressBar progressBar;
    protected Handler handler;
    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_question, container, false);
        dataHelper=new DataHelper(getActivity());

        button=(Button) view.findViewById(R.id.new_story_button);
        tvEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_forum);
        studentList = new ArrayList<String>();
        handler = new Handler();
        progressBar=(ProgressBar) view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        Cursor cursor=dataHelper.getDataUser();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
        }
        final String[] title = new String[1];
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idUser.equals("d")){
                    getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                }else {
                    Intent intent=new Intent(getActivity(), NewQuestionActivity.class);

                    intent.putExtra("text",-1);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        studentList.add("Пасспорт и документы");
        studentList.add("Рабочие моменты");
        studentList.add("Семейные дела");
        studentList.add("Похищение");


        mAdapter=new RVQuestionAdapter(studentList,mRecyclerView,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor=dataHelper.getDataUser();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
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
                URL url = new URL("http://176.126.167.231:8000/api/v1/story/");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                try {
                    writer.write(JsonDATA);
                } catch (Exception e) {
                    Log.e("TAG_SEND", "Error");
                }
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                Log.e("TAG_SEND", "asdfasd");
                reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.e("TAG_____________", "1");
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                Log.e("TAG_____________", "2");

                if (buffer.length() == 0) {
                    Log.e("TAG_____________", "1");
                    return null;
                }
                JsonResponse = buffer.toString();
                Log.e("TAG_____________", JsonResponse);
                return JsonResponse;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            Log.e("TAG_____________", JsonResponse);
            return "";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
