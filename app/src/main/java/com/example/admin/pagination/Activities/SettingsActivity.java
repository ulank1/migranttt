package com.example.admin.pagination.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.pagination.Activities.SecondActivities.RegisterActivity;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {
    DataHelper dataHelper;
    TextView tvAccount;
    Button btAccount;
    String idUser="d";
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dataHelper=new DataHelper(this);
        btAccount=(Button) findViewById(R.id.bt_setting_account);
        tvAccount=(TextView) findViewById(R.id.tv_setting_account);





    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor=dataHelper.getDataUser();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
            btAccount.setText("Выйти");
            new ParseTask().execute();
        }else {

            btAccount.setText("Войти");
            tvAccount.setText("");

        }
    }

    public void onClick(View view) {
        if (idUser.equals("d")) {
            startActivity(new Intent(SettingsActivity.this, RegisterActivity.class));
        } else {
            dataHelper.deleteUser();
            btAccount.setText("Войти");
            tvAccount.setText("");
            idUser="d";
        }
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";


        public ParseTask() {

        }

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S", 1 + "");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/user/?id=" + idUser + "&format=json");

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
                Log.e("TAG_S", 3 + "DELETE");

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG_S", 1 + "PIZDEC");
            }

            Log.e("TAG_S", 4 + "");
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            JSONObject dataJsonObject;
            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject menu= menus.getJSONObject(0);
                tvAccount.setText(menu.getString("username"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
