package com.example.admin.pagination.Activities.SecondActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.admin.pagination.Activities.IstoriesFromLifeActivity;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;

public class RegisterActivity extends AppCompatActivity {
    EditText editPasword,editUsername;
    String username,password;
    String TAG="TAG";
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Авторизация");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        editPasword=(EditText) findViewById(R.id.edit_register_password);
        editUsername=(EditText) findViewById(R.id.edit_register_username);
        dataHelper=new DataHelper(this);
    }

    public void onClickAvtorizaciya(View view) {
        Log.e("TAGGGGGGGGGGGGG","PPPPPPPPPPPPPPp");
        if(editPasword.getText().length()>0&&editUsername.getText().length()>0){
            password=editPasword.getText().toString();
            username=editUsername.getText().toString();
            new ParseTask().execute();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
    public void onClickRegistraciya(View view) {
        startActivityForResult(new Intent(this, IstoriesFromLifeActivity.class),1);

    }
    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        public ParseTask(){

        }
        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/user/?username="+username+"&offset=0&limit=15&format=json");

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

            Log.e("TAG", json);
            int total_count;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");
                Log.e(TAG+"Total",total_count+"");
                JSONObject menu;
                String id;
                String passwordJson;
                if (total_count==1) {
                    dataHelper.deleteUser();
                    menu = menus.getJSONObject(0);
                    passwordJson=menu.getString("password");
                    id=menu.getString("id");
                    dataHelper.insertUser(id);

                    finish();

                }
                else editUsername.setError("Такого пользователя не существует");


                Log.d(TAG, "NET NET dsfsadadsgf: ");
            } catch (JSONException e) {
                e.printStackTrace();



                Log.d(TAG, "JSON_PIZDEC");
            }
            Log.e("TAG_1","NORM");





        }
        public String MD5(String md5) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                byte[] array = md.digest(md5.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
                }
                return sb.toString();
            } catch (java.security.NoSuchAlgorithmException e) {
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        finish();
    }
}
