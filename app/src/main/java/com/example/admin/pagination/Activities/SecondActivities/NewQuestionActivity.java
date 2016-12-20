package com.example.admin.pagination.Activities.SecondActivities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.pagination.Activities.IstoriesFromLifeActivity;
import com.example.admin.pagination.DataAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;

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

public class NewQuestionActivity extends AppCompatActivity {
    EditText editTitle;
    DataHelper dataHelper;
    int position;
    String  idUser="d";
    ArrayList<String > studentList;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        studentList=new ArrayList<>();
        position=getIntent().getIntExtra("text",-1);
        studentList.add("Выберите категорию");
        studentList.add("Пасспорт и документы");
        studentList.add("Рабочие моменты");
        studentList.add("Семейные дела");
        studentList.add("Похищение");


        editTitle=(EditText) findViewById(R.id.edit_story_text);
        dataHelper=new DataHelper(this);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner_new_story);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        if (position>-1)
            spinner.setSelection(position);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void onClickNewStory(View view) {
        Cursor cursor=dataHelper.getDataUser();
        if (cursor.getCount()<0){
            startActivity(new Intent(NewQuestionActivity.this, RegisterActivity.class));
        }
        else
        {
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
        }
        if (idUser.equals("d")){
            startActivity(new Intent(this, RegisterActivity.class));
        }
        JSONObject obj = new JSONObject();
        try {
            int pos= spinner.getSelectedItemPosition();
            Log.e("TAG_IDDDDDDD",idUser+"");
            obj.put("user", "/api/v1/user/"+idUser+"/");
            obj.put("category", studentList.get(pos));
            obj.put("question",editTitle.getText().toString());


            Log.e("TAG", "asd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
        new SendJsonDataToServer().execute(String.valueOf(obj));
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
                URL url = new URL("http://176.126.167.231:8000/api/v1/question/");
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
            Log.e("TAG_____________", JsonResponse+"");
            return "";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finish();

        }
    }
}
