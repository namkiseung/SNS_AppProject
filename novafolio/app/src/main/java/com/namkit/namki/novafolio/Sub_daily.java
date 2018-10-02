package com.namkit.namki.novafolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Sub_daily extends AppCompatActivity implements View.OnClickListener {
     EditText title;
     EditText content;
  //  Daily_Adapter_List adapter;
   // List<Daily_MainItem> data;
  ArrayList<String> getset;
    Daily_MainItem daily_mainItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daily);

        //Intent arrInfo = getIntent();
        //getset = (ArrayList<String>) arrInfo.getSerializableExtra("배열");

        title = (EditText)findViewById(R.id.daily_title_edit);

        content = (EditText)findViewById(R.id.daily_content_edit);

        Button button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daily_title_edit:
                break;
            case R.id.daily_content_edit:
                break;
            case R.id.btn_save:
                Daily daily = new Daily();
                //getset = new Daily_MainItem(name, des);

                String name = title.getText().toString();
                String des = content.getText().toString();

                Daily_MainItem temp = new Daily_MainItem(name, des);
                daily.data.add(temp);
                daily.adapter.notifyDataSetChanged();

                Intent intent = new Intent(Sub_daily.this, Daily.class);
                startActivity(intent);



               // login.putExtra("이름과나이", data);//인텐트에 정보를 담아서 보낸다




             /*   Intent login = new Intent(Sub_daily.this, Daily.class);
                Daily_Adapter_List data = new Daily_Adapter_List(name, age);

                login.putExtra("이름과나이", data);//인텐트에 정보를 담아서 보낸다
                startActivity(login);*/
                break;
        }
    }
}


