package com.example.admin.pagination.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVEAEUAdapter;
import com.example.admin.pagination.Adapters.RVEmbassyAdapter;
import com.example.admin.pagination.Adapters.RVFAQAdapter;
import com.example.admin.pagination.Adapters.RVProhibitionAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.DateDateDB;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Diaspora;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.Embassy;
import com.example.admin.pagination.Serializables.Employment;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.Istories;
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
import java.util.Calendar;

public class SynchronizeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    DataHelper dataHelper;
    String date,dateDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize);
        dataHelper=new DataHelper(this);
        progressBar=(ProgressBar) findViewById(R.id.progress);
        ifConnect();
    }
    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("1");

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
            }else  startActivity(new Intent(SynchronizeActivity.this,MainActivity.class));
        }
    }
    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country/?format=json");

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


            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteAbroad();
                    dataHelper.insertAbroad(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");

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

                URL url = new URL("http://176.126.167.249/api/v1/rules_of_incoming/?limit=0&format=json");

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
            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();


                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    if (i==0){dataHelper.deleteROM();
                        Log.e("TAG_NEWS","DELETE");
                    }
                    JSONObject country=menu.getJSONObject("country");
                    String id=country.getString("id");
                    dataHelper.insertROM(student,id);






                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }




           new ParseTaskDiaspora().execute();
        }
    }

    public class ParseTaskDiaspora extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country_diaspora/?format=json");

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


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteDias();
                    dataHelper.insertDias(eaeu);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");
            new ParseTaskDiaspora2().execute();



        }
    }
    public class ParseTaskDiaspora2 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/diaspora/?offset=0&limit=0&format=json");

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

            JSONObject dataJsonObject;
            JSONObject country;
            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Diaspora student = new Diaspora();

                    if (i==0){dataHelper.deleteDiaspora();
                    }


                    student.setNumber(menu.getString("phone_number"));

                    student.setCity(menu.getString("city"));
                    student.setManager(menu.getString("manager"));
                    student.setAddress(menu.getString("address"));
                    student.setEmail(menu.getString("email"));
                    student.setPlace(menu.getString("place"));
                    country=menu.getJSONObject("country");
                    String id=country.getString("id");
                    Log.e("TAG___ID",id);

                    dataHelper.insertDiaspora(student,id);






                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("df", "JSON_PIZDEC");
            }






            new ParseTaskEAEU().execute();



        }
    }

    public class ParseTaskEAEU extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country_eaes/?&limit=0&format=json");

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


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteEAEU();
                    dataHelper.insertEAEU(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            Log.e("TAG_1","NORM");

            new ParseTaskEAEU1().execute();


        }
    }
    public class ParseTaskEAEU1 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";


        @Override
        protected String doInBackground(Void... params) {


            try {

                URL url = new URL("http://176.126.167.249/api/v1/rules_of_incoming_eaes/?limit=0&format=json");

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

            Log.e("TAG", json);
            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");



                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();


                    student.setText(menu.getString("text_ru"));
                    student.setTitle("   "+menu.getString("title_ru"));
                    JSONObject country=menu.getJSONObject("country");
                    String id=country.getString("id");
                    Log.e("TAG___ID",id);
                    if (i==0){dataHelper.deleteROI();
                    }
                    dataHelper.insertROI(student,id);






                }

                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("TAG","JSON_PIZDEC");
            }
         new ParseTaskEmbassy().execute();





        }
    }

    public class ParseTaskEmbassy extends AsyncTask<Void, Void, String> {

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

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");



                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Embassy student = new Embassy();
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





                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAg", "JSON_PIZDEC_EMBASSY");
            }


            new ParseTaskEmmbassy1().execute();


        }
    }

    public class ParseTaskEmmbassy1 extends AsyncTask<Void, Void, String> {

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
                dataHelper.deleteConsulate();

                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Consulate student = new Consulate();
                    student.setPhoneNumber(menu.getString("phone_number"));

                    student.setRegion(menu.getString("region"));

                    student.setAddress(menu.getString("address"));


                    if (i==0){
                        Log.e("TAG_NEWS","DELETE");
                    }
                    JSONObject country=menu.getJSONObject("embassy");
                    String id=country.getString("id");
                    dataHelper.insertConsulate(student,id);






                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
         new ParseTaskEmploy().execute();
        }
    }


    public class ParseTaskEmploy extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country_employment/?limit=0&format=json");

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

            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    Log.e("!!!!",i+"");
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteEmploy();
                    dataHelper.insertEmploy(eaeu);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            new ParseTaskEmploy1().execute();


        }
    }
    public class ParseTaskEmploy1 extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/employment/?limit=0&format=json");

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


            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");

                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Employment student = new Employment();

                    if (i==0){dataHelper.deleteEmployment();
                    }


                    student.setPhone_number(menu.getString("phone_number"));
                    student.setPhone_number1(menu.getString("phone_number_1"));
                    student.setPhone_number2(menu.getString("phone_number_2"));
                    student.setName(menu.getString("name"));
                    student.setManager(menu.getString("manager"));
                    student.setAdress(menu.getString("address"));
                    JSONObject country=menu.getJSONObject("country");
                    String id=country.getString("id");


                    dataHelper.insertEmployment(student,id);







                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }



            new ParseTaskFAQ().execute();
        }
    }

    public class ParseTaskFAQ extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/faq/?limit=0&format=json");

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

            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Istories student = new Istories();
                    student.setText(menu.getString("answer_ru"));
                    student.setNickName(menu.getString("question_ru"));

                    if (i==0){dataHelper.deleteFAQ();
                    }
                    dataHelper.insertFAQ(student);





                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAg", "JSON_PIZDEC");
            }

           new ParseTaskHotline().execute();

        }
    }

    public class ParseTaskHotline extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country_hotline/?&limit=0&format=json");

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


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteHotCountry();
                    dataHelper.insertHotCountry(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


         new ParseTaskHotLine1().execute();


        }
    }
    public class ParseTaskHotLine1 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/hotline/?limit=0&format=json");

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
                    Hotline student = new Hotline();
                    student.setDescription(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    student.setPhoneNumber(menu.getString("phone_number"));
                    JSONObject country=menu.getJSONObject("country");
                    String id=country.getString("id");
                    if (i==0){dataHelper.deleteHot();
                    }
                    dataHelper.insertHot(student,id);






                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }

                new ParseTaskHumanTraffic().execute();
        }
    }

    public class ParseTaskHumanTraffic extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";


        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/human_traffic/?limit=0&format=json");

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

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();

                    student.setImage("http://176.126.167.249/"+menu.getString("image"));
                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    if (i==0){dataHelper.deleteHT();
                    }
                    dataHelper.insertHT(student);





                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }

            new ParseTaskNews().execute();

        }
    }


    public class ParseTaskNews extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/news2/?limit=0&format=json");

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
            Log.e("TAG", json);

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Istories student = new Istories();
                    student.setImage(menu.getString("image"));
                    student.setText(menu.getString("text_ru"));
                    student.setNickName(menu.getString("title_ru"));

                    if (i==0){dataHelper.delete();
                    }

                    dataHelper.insertNews(student);



                }

            } catch (JSONException e) {
                e.printStackTrace();



            }

            new ParseTaskNKO().execute();
        }
    }

    public class ParseTaskNKO extends AsyncTask<Void, Void, String> {

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

            } catch (Exception e) {
                e.printStackTrace();
            }

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
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("name"));

                    eaeu.setId(menu.getString("id"));
                    if (i==0) dataHelper.deleteNKOCountry();
                    dataHelper.insertNKOCountry(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


            new ParseTaskNKO1().execute();


        }
    }
    public class ParseTaskNKO1 extends AsyncTask<Void, Void, String> {

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

                new ParseTaskProhib().execute();
        }
    }


    public class ParseTaskProhib extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/rf/?limit=0&format=json");

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

            JSONObject user;
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();

                    student.setImage("http://176.126.167.249/"+menu.getString("image"));
                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    if (i==0){dataHelper.deleteProhibition();
                    }
                    dataHelper.insertProhibition(student);



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataHelper.updateDate(date,"1");
            startActivity(new Intent(SynchronizeActivity.this,MainActivity.class));

        }
    }



}
