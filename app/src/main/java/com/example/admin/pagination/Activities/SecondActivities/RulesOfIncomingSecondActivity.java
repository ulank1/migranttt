package com.example.admin.pagination.Activities.SecondActivities;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.admin.pagination.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class RulesOfIncomingSecondActivity extends AppCompatActivity {

    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView=new ScrollView(this);
        LinearLayout linLayout = new LinearLayout(this);
        // установим вертикальную ориентацию
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setPadding(10,10,10,10);
        scrollView.addView(linLayout);
        // создаем LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(scrollView, linLayoutParam);
        s='"'+getIntent().getStringExtra("text")+'"';


        for(Element element : Jsoup.parse(s).select("*")) {
            if (element.tagName().equals("p")) {
                Log.e("TAG", element.text());
                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(this);
                tv.setText(element.text());
                tv.setLayoutParams(lpView);
                linLayout.addView(tv);

            } else if(element.tagName().equals("img")) {
                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Log.e("TAG_ROI",element.attr("src"));
                ImageView btn = new ImageView(this);

                Picasso.with(this).load("http://176.126.167.231:8000/"+element.attr("src")).placeholder(R.drawable.send_icon).into(btn);
                linLayout.addView(btn, lpView);
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
