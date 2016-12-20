package com.example.admin.pagination.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.pagination.*;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.Embassy;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DataHelper dataHelper;

    private Locale mNewLocale;
    MenuItem item;
    ActionBar actionBar;
    TextView news,eaeu,hotLine,embassy,diaspora,abroad,ht,prohibition,employ,faq;
    int lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar;
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        Log.e("WERTYUIKOLKJHGFDFJK",width+"  "+height);
        news = (TextView) findViewById(R.id.news);
        eaeu = (TextView) findViewById(R.id.eaeu);
        hotLine = (TextView) findViewById(R.id.hot_line);
        embassy = (TextView) findViewById(R.id.embassy);
        diaspora = (TextView) findViewById(R.id.diaspora);
        abroad = (TextView) findViewById(R.id.abroad);
        ht = (TextView) findViewById(R.id.ht);
        prohibition = (TextView) findViewById(R.id.prohibition);
        employ = (TextView) findViewById(R.id.employ);
        faq = (TextView) findViewById(R.id.faq);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        dataHelper=new DataHelper(this);
        dataHelper.deleteSize();
        dataHelper.insertSize(width,height);






    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_faq:
                startActivity(new Intent(MainActivity.this, FAQActivity.class));
                break;
            case R.id.bt_diaspory:
                startActivity(new Intent(MainActivity.this, DiasporyActivity.class));
                break;
            case R.id.bt_zapret_v_RF:
                startActivity(new Intent(MainActivity.this,ProhibitionRFActivity.class));
                break;
            case  R.id.bt_news:
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
                break;
            case R.id.bt_tel_doveriya:
                startActivity(new Intent(MainActivity.this,HotLineCountryActivity.class));
                break;
            case R.id.bt_konsulstva_diaspory:
                startActivity(new Intent(MainActivity.this, EmbassyActivity.class));
                break;
            case R.id.bt_instruction:
                startActivity(new Intent(MainActivity.this,EAEUActivity.class));
                break;
            case R.id.bt_rules_of_migration:
                startActivity(new Intent(MainActivity.this,AbroadActivity.class));
                break;
            case R.id.bt_torgovlya:
                startActivity(new Intent(MainActivity.this,HumanTraffickingActivity.class));
            break;
            case R.id.bt_trudoustiystvo:
                startActivity(new Intent(MainActivity.this,EmployementActivity.class));
                break;

        }
    }
    void setLocale(String mLang) {
        mNewLocale = new Locale(mLang);
        Locale.setDefault(mNewLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = mNewLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        updateTextView();
    }

    private void updateTextView() {
        embassy.setText(R.string.bt_embassy);
        employ.setText(R.string.bt_employment);
        eaeu.setText(R.string.bt_eaeu);
        abroad.setText(R.string.bt_abroad);
        diaspora.setText(R.string.bt_diaspora);
        ht.setText(R.string.bt_ht);
        hotLine.setText(R.string.bt_hot_line);
        prohibition.setText(R.string.bt_prohibition);
        faq.setText(R.string.bt_faq);
        news.setText(R.string.bt_news);
        actionBar.setTitle(R.string.app_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        item = menu.findItem(R.id.menu_item_language);
        Cursor cursor=dataHelper.getDataLanguage();
        if (cursor.getCount()>0)
        {

            cursor.moveToFirst();
            lang=cursor.getInt(cursor.getColumnIndex(DataHelper.LANGUAGE_COLUMN));

        }
        else {lang=0;

        }



        if (lang==0){
            item.setTitle("kg");
            setLocale("ru");
        }

        else{
            setLocale("ky");
            item.setTitle("ru");
        }


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();



        // Операции для выбранного пункта меню
        switch (id) {

            case R.id.about_project:
                startActivity(new Intent(MainActivity.this, AboutProjectActivity.class));
                return true;
            case R.id.menu_item_language:
                Cursor cursor=dataHelper.getDataLanguage();
                if (cursor.getCount()>0)
                {

                    cursor.moveToFirst();
                    lang=cursor.getInt(cursor.getColumnIndex(DataHelper.LANGUAGE_COLUMN));

                }
                else {lang=0;

                }

                lang=(lang+1)%2;
                dataHelper.deleteLanguage();
                dataHelper.insertLanguage(lang);
                if (lang==0){ item.setTitle("kg");
                setLocale("ru");}
                else{   item.setTitle("ru");
                setLocale("ky");}




                    dataHelper.updateDate("ss",1+"");

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}