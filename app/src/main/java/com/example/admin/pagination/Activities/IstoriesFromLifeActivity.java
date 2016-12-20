package com.example.admin.pagination.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.admin.pagination.Helpers.DataHelper;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IstoriesFromLifeActivity extends AppCompatActivity {
    EditText editUsername,editEmail,editPassword,editRePassword;
    String name,username,email,lastName,password,rePassword;
    boolean usernameExist;
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istories_from_life);
        editEmail=(EditText) findViewById(R.id.edit_istories_tel);
        editRePassword=(EditText) findViewById(R.id.edit_istories_re_password);
        editUsername=(EditText) findViewById(R.id.edit_istories_username);
        dataHelper=new DataHelper(this);
        editPassword=(EditText) findViewById(R.id.edit_istories_password);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Регистрация");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    public void onClick(View view) {
        lastName=name="";
        usernameExist=false;
        boolean bool = true;


         email = editEmail.getText().toString();
        if (!isValidEmail(email)) {
            editEmail.setError("Неверный адрес электронной почты");
            bool = false;
        }



        username= editUsername.getText().toString();
        if (!isValidUserName(username)) {
            editUsername.setError("Заполните поле");
            bool = false;
        }


        password= editPassword.getText().toString();
        if (!isValidName(password)) {
            editPassword.setError("Заполните поле");
            bool = false;
        }
        rePassword=editRePassword.getText().toString();
        if(!isValidRePassword(rePassword,password)){
            editRePassword.setError("Пароли не совпадают");
            bool = false;
        }

        if (bool){
          new ParseTask(username).execute();
        }

    }
    private boolean isValidEmail(String email) {
        if(email.length()==0) return true;
        else {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

    }
    private  boolean isValidName(String name){
        if (name.length()==0) return false;
        else return true;
    }
    private  boolean isValidRePassword(String name,String password){
        if (!name.equals(password)) return false;
        else return true;
    }
    private  boolean isValidUserName(String name){
        if (name.length()==0) return false;
        else{

            return true;
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
                URL url = new URL("http://176.126.167.231:8000/api/v1/user/");
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
            new ParseTask2(username).execute();
        }
    }
    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";
        String username;
        public ParseTask(String username){
            this.username=username;
        }
        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/user/?username="+username+"&format=json");

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
            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                int totalCount=meta.getInt("total_count");
                Log.e("TAG_TOTAL",totalCount+"");
                if (totalCount>0) usernameExist=false;
                else  usernameExist=true;



                if (usernameExist){
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("username",username);
                        obj.put("email",email);
                        obj.put("password",password);



                        Log.e("TAG", "asd");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SendJsonDataToServer().execute(String.valueOf(obj));
                }else {
                    editUsername.setError("Это имя уже занято");
                }




            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }
            Log.e("TAG_1","NORM");





        }
    }
    public class ParseTask2 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";
        String username;
        public ParseTask2(String username){
            this.username=username;
        }
        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/user/?username="+username+"&format=json");

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
            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                int totalCount=meta.getInt("total_count");
                Log.e("TAG_TOTAL",totalCount+"");

                JSONObject menu=menus.getJSONObject(0);
                String  id=menu.getString("id");
                Log.e("TAGGGGGGG",id);
                dataHelper.deleteUser();
                Log.e("TAGGGGGGG","1");

                dataHelper.insertUser(id);
                dataHelper.readDataUser();
                Log.e("TAGGGGGGG","3");
                setResult(RESULT_OK);
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }
            Log.e("TAG_1","NORM");





        }
    }
}
