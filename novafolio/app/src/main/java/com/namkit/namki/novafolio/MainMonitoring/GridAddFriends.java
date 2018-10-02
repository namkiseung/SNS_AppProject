package com.namkit.namki.novafolio.MainMonitoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.namkit.namki.novafolio.R;

public class GridAddFriends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_add_friends);

        //SharedPreferences preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);  // 쉐어프리페런스 파일 만듬
        //String userDetails = preferences.getString(stringId + "TEAM_MEMBER_ID", ""); //MYPREFS 파일안에 저장값을 가져와서 (userDetial)와 공유
        //String getUserTo = preferences.getString(newUserName+"NAMETOID", ""); //저장되있던 이름 to 값 //이름을 키 / 아이디가 속성   결국 이름적으면 아이디가 나온다

    }
}
