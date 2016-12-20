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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.pagination.Adapters.RVFAQAdapter;
import com.example.admin.pagination.Adapters.RVForumAdapter;
import com.example.admin.pagination.Adapters.RVForumQuestionsAndAnswersAdapter;
import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.DateDateDB;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//date 9
public class FAQActivity extends AppCompatActivity {
    String date,dateDB;

    DataHelper dataHelper;

    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVFAQAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Istories> studentList;

    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_faq);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        insert();

     db();



    }

    public void insert(){
        Istories istories=new Istories();
        istories.setText("Если вы стали жертвой торговли людьми, не стыдитесь этого. Ваши права гарантированы законом. Обратитесь за помощью как можно скорее. \n Мигранты также имеют право на защиту государства, юридическую помощь и доступ к социальным услугам. Если вы жертва торговли людьми, вы также можете получить помощь.\n Свяжитесь с уполномоченными людьми, если:\n Вас либо кого-то из ваших знакомых заставляют работать бесплатно!\n Вы лишены свободы передвижения и не можете связаться с родственниками!\n Ваш работодатель причиняет вам моральный либо физический вред!\n Существуют горячие линии, с помощью которых можно сообщить властям и НПО о случаях работорговли и эксплуатации.");
        istories.setNickName("Если вы стали  жертвой  торговли людьми");
        studentList.add(istories);
        istories=new Istories();
        istories.setNickName("Правила пребывания трудящихся мигрантов из КР на территории РФ в рамках Евразийского Экономического Союза");
        istories.setText("При  въезде  в Россию Вам необходимо иметь при себе следующие документы: \n" +
                "- Национальный паспорт (ID – карта);\n" +
                "- Заграничный паспорт.\n" +
                "При въезде в РФ каждый гражданин КР независимо от возраста, обязан заполнить миграционную карту, которая выдается бесплатно  в пункте пропуска через Государственную границу Российской Федерации для проставления отметки о въезде. В ней обязательно указать цель въезда «работа»\n" +
                "Трудящиеся при пребывании до 30 суток находятся на территории РФ без регистрации.\n" +
                "Трудящиеся при пребывании свыше 30 суток  обязаны встать на миграционный учет сроком до 90 суток с даты въезда.\n" +
                "Трудящиеся в течение  90 суток с даты въезда должны заключить трудовой (гражданско-правовой) договор с работодателем.\n" +
                "Срок пребывания трудящегося-мигранта на территории РФ соответственно продлевается до окончания срока действия  трудового договора.\n" +
                "Срок пребывания в дальнейшем может продлеваться по мере продлевания срока действия трудового договора.");
        studentList.add(istories);
        istories=new Istories();
        istories.setText("Для проверки  на наличие запрета въезда в РФ нужно будет обратиться по следующим адресам: \n" +
                "г. Бишкек  Информационно – консультационный  центр  Государственной службы миграции при Правительстве КР по адресу  ул. Токтогула 237. Телефон: 65-02-55.\n" +
                "г. Ош   Информационно – консультационный  центр  Государственной службы миграции при Правительстве КР  по  адресу ул. Ленина – 277. Телефон: (03222)5-50-87.\n" +
                "\n" +
                "Проверка осуществляется строго при наличии паспорта.  \n" +
                "\n" +
                "Заявление по выведению из списка запретников  на въезд  в  РФ, могут  подать граждане КР следующих категорий:\n" +
                "Если близкие родственники (родители, супруг (а), дети, родные братья и сестры имеют гражданство РФ;\n" +
                "Если гражданин КР обучается в ВУЗах РФ;\n" +
                "Если гражданин имеет урегулированный статус;\n" +
                "Если является высококвалифицированным специалистом;\n" +
                "Если гражданин  КР нуждается в безотлагательном лечении в РФ.\n" +
                "\n" +
                "Рассмотрение данных заявлений  осуществляется в  течение  3 - 9 месяцев.");
        istories.setNickName("Где и как можно проверить себя на наличие запрета въезда в РФ?\n" +
                "Методы исключения из списка запретников.");
        studentList.add(istories);
        istories=new Istories();
        istories.setNickName("Информация о легальном трудоустройстве граждан КР за рубежом.");
        istories.setText("Обратиться в Информационно – консультационный центр Государственной службы миграции при Правительстве КР в г.Бишкек по адресу ул.Токтогула - 237 и г.Ош  ул. Ленина- 221 и зарегистрироваться в качестве соискателя работы за рубежом.\n" +
                "            Центр   оказывает содействие  в трудоустройстве за рубежом  на основе организованного набора в  следующих странах:\n" +
                "\n" +
                "Республика Корея - 65 02 51\n" +
                "Республика Казахстан – 64 17 64\n" +
                "Турецкая Республика – 64 17 64\n" +
                "Российская Федерация – 65 02 54\n" +
                "Объединенные Арабские Эмираты – 64-17-64\n" +
                "            Набор осуществляется в рамках государственных программ, межправительственных и официальных соглашений, а также с работодателями, с Частными агентствами занятости  и другими заинтересованными лицами на основе переговоров. \n" +
                "     Также ИКЦ занимается выдачей  разрешения на осуществление трудовой деятельности за рубежом граждан КР. Информация  о легальных ЧАЗах предоставлена на сайте www.oec.kg  раздел Частные агентства.");
        studentList.add(istories);



    }
   public  void  db(){
       Cursor cursor = dataHelper.getDataFaq();
       Log.e("TAG_NEWS",cursor.getCount()+" kol");
       if (cursor != null && cursor.getCount() > 0) {
           while (cursor.moveToNext()) {
               Istories istories=new Istories();
               istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.FAQ_QUESTION_COLUMN)));
               istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.FAQ_ANSWER_COLUMN)));
               studentList.add(istories);
           }
           mAdapter=new RVFAQAdapter(studentList,mRecyclerView,this);
           mRecyclerView.setAdapter(mAdapter);


       }
   }

    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("9");
        if (cursor.getCount()==0){
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                new ParseTask().execute();
                progressBar.setVisibility(View.VISIBLE);
            }
            else {                    Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();

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
            }else db();
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
                dataHelper.updateDate("ss","9");
                ifConnect();

                return true;

            default:  return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);



        return true;
    }
    public class ParseTask extends AsyncTask<Void, Void, String> {

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
            studentList.clear();
            insert();
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Istories student = new Istories();
                    student.setText(menu.getString("answer_ru"));
                    student.setNickName(menu.getString("question_ru"));

                    if (i==0){dataHelper.deleteFAQ();
                    }
                    dataHelper.insertFAQ(student);

                    studentList.add(student);




                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSON_PIZDEC");
            }
            progressBar.setVisibility(View.GONE);
            dataHelper.updateDate(date,"9");

            mAdapter=new RVFAQAdapter(studentList,mRecyclerView,FAQActivity.this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

}