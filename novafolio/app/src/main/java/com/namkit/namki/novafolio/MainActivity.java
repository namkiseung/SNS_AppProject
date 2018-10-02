package com.namkit.namki.novafolio;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.namkit.namki.novafolio.MainMonitoring.MainSecond;
import com.namkit.namki.novafolio.Wakeup.Wakeup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton call, sms, web, map;
    private DrawerLayout drawerLayout;
    String currentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Main Acitivty", "onCreate()");
        //로그인한 사람의 아이디
        Intent currentloginId = getIntent();
        currentid = currentloginId.getExtras().getString("currentdisplayId");
        System.out.println("현재 로그인한 사람의 아이디는?"+currentid);
        SharedPreferences prefs = getSharedPreferences("NowUser", 0);
        SharedPreferences.Editor logineditor = prefs.edit();
        logineditor.putString("currentdisplayId", currentid);
        logineditor.commit();

        //툴바 생성
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 리스너 붙히기
        toolbar.setOnMenuItemClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.main_navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.first){
                    Toast.makeText(MainActivity.this, "first눌림", Toast.LENGTH_SHORT).show();
                    Log.d("드로우러 레이아웃","첫번째 메뉴 눌렀음");
                    getFragmentManager().beginTransaction().replace(R.id.main_frameLayout,new Nav_first_Fragment()).commit();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //플로팅메뉴(+버튼)
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        call = (FloatingActionButton) findViewById(R.id.floatingActionItem_Call);
        sms = (FloatingActionButton) findViewById(R.id.floatingActionItem_SMS);
        web = (FloatingActionButton) findViewById(R.id.floatingActionItem_Web);
        map = (FloatingActionButton) findViewById(R.id.floatingActionItem_Map);

        call.setOnClickListener(this);
        sms.setOnClickListener(this);
        web.setOnClickListener(this);
        map.setOnClickListener(this);

//xml버튼아이디값 받아와서 리스너 붙혀주기
        Button homebutton = (Button) findViewById(R.id.btn_home_activity);
        homebutton.setOnClickListener(this);
        Button wakeupbutton = (Button) findViewById(R.id.btn_wakeup_activity);
        wakeupbutton.setOnClickListener(this);
        Button dailybutton = (Button) findViewById(R.id.btn_daily_activity);
        dailybutton.setOnClickListener(this);
        Button recordbutton = (Button) findViewById(R.id.btn_record_activity);
        recordbutton.setOnClickListener(this);
        Button newsbutton = (Button) findViewById(R.id.btn_news_activity);
        newsbutton.setOnClickListener(this);


//회원가입했던 아이디, 나이 받아오기
        /*Intent nameage = getIntent();
        SignupData data = (SignupData) nameage.getSerializableExtra("이름과나이");

        TextView txtName = (TextView) findViewById(R.id.user_name_main);
        TextView txtAge = (TextView) findViewById(R.id.user_age_main);

        txtName.setText("* 접속자 : " + data.name);
        txtAge.setText(", (" + String.valueOf(data.age) + ")");*/
       /* txtName.setText(data.name);
        txtAge.setText(String.valueOf(data.age));*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // 기본으로 들어가는 setting 메뉴
        Log.d("onCreateOptionsMenu 진입","옵션메뮤@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //클릭에 대해 뷰 아이디를 받아와서 처리
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_activity:
                Intent moveonhome = getIntent();
                moveonhome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moveonhome);
                break;
            case R.id.btn_wakeup_activity:
                Intent moveonwakeup = new Intent(MainActivity.this, Wakeup.class);
                startActivity(moveonwakeup);
                break;
            case R.id.btn_daily_activity:
                Intent moveondaily = new Intent(MainActivity.this, Daily.class);
                startActivity(moveondaily);
                break;
            case R.id.btn_record_activity:
                Intent moveonrecord = new Intent(MainActivity.this, Record.class);
                startActivity(moveonrecord);
                break;
            case R.id.btn_news_activity:
                Intent moveonnews = new Intent(MainActivity.this, News.class);
                startActivity(moveonnews);
                break;
            case R.id.floatingActionItem_Call: //암시적인텐트 전화
                /*Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:01072552316"));
                //퍼미션체크
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(call);*/
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01072552316"));
                startActivity(call);
                break;
            case R.id.floatingActionItem_SMS: //암시적인텐트 문자
                //Intent sms = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:01072552316"));
                /*Intent sms = new Intent(Intent.ACTION_SEND); //카카오톡
                sms.setType("text/plain");
                sms.putExtra(Intent.EXTRA_SUBJECT, "질문 있습니다!");
                sms.putExtra(Intent.EXTRA_TEXT, "");
                sms.setPackage("com.kakao.talk");*/
                Intent sms = new Intent(Intent.ACTION_SEND);
                sms.setType("text/plain");
                String sendtext = "내용을 입력하세요.";
                sms.putExtra(Intent.EXTRA_TEXT, sendtext);
                Intent chooser = Intent.createChooser(sms, "친구에게 공유하기");
                startActivity(sms);
                break;
            case R.id.floatingActionItem_Web: //암시적인텐트 웹사이트
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.teamnova.co.kr"));
                startActivity(web);
                break;
            case R.id.floatingActionItem_Map: //암시적인텐트 맵
//                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.4828988,126.97395010000002"));
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=팀노바"));
                startActivity(map);
                break;
        }
    }

    //현재시간 명령한 포멧으로 세팅
    private void currentclock() {//현재 시각 가져와서 포멧에 맞춰 setText하기
        //현재시간 띄우기
        long now = System.currentTimeMillis(); //현재시간을 불러온다
        Date date = new Date(now);//Date객체 생성후 현재시간 값 부여
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd \n HH:mm:ss");
        String formatDate = sdfNow.format(date);
        TextView dateNow;
        dateNow = (TextView) findViewById(R.id.text_dateNow);
        dateNow.setText(formatDate);
    }


    //    메인에서 back버튼 누르면 종료 메시지 알림으로 띄우기
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finish() : 이 함수는 이 코드가 속해있는 액티비티를 종료 시킵니다. onDestroy()를 호출한ㄷ.
                // system.exit(0) :  이 함수는 현재 액티비티를 종료 시킨다.
                // android.os.Process.killProcess(android.os.Process.myPid()) : 이 함수는 현재의 프로세스 및 서비스를 종료 시킨다.
                // moveTaskToBack(boolean): 이 함수는 현재 어플을 백그라운드로 넘긴다. 현재 실행되고있는 어플이 하나라면 홈화면으로 바뀌겠지... 하지만 종료된 것은 아니다.
               /* SharedPreferences preferences = getSharedPreferences("setting", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("ID");
                editor.remove("PW");
                editor.commit();*/
                SharedPreferences prefs = getSharedPreferences("NowUser", 0);
                SharedPreferences.Editor logineditor = prefs.edit();
                logineditor.remove("currentdisplayId");
                logineditor.commit();
                /*Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                finish();
            }
        });
        alert.setIcon(R.drawable.ic_launcher);
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setMessage("로그아웃 하시겠습니까?");
        alert.show();
    }

    public void onPause() {
        super.onPause();
        Log.i("Main Acitivty", "onPause()");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.i("Main Acitivty", "onStart()");
        //시간스레드
        Thread t = new Thread() {//현재 시간을 set 해서 1초 마다 스레드
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentclock();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i("Main Acitivty", "onStop()");
        //Toast.makeText(Login.this, "onpause", Toast.LENGTH_SHORT).show();

    }

   /* public void endapp(){
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        Toast.makeText(Login.this, "종료 :"+now, Toast.LENGTH_SHORT).show();
        Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기
        int hour = oCalendar.get(Calendar.HOUR);
        int min = oCalendar.get(Calendar.MINUTE);
        if (oCalendar.get(Calendar.AM_PM) == 0){
            Toast.makeText(this, "이용시간 am "+hour+" 시 "+min+"분", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "이용시간 : pm "+hour+" 시 "+min+"분", Toast.LENGTH_SHORT).show();
        }
    }*/


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Toast.makeText(this, "onMenuItemClick 눌렸습니다.", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.item_main_info:

                Toast.makeText(this, "클릭1", Toast.LENGTH_SHORT).show();
                //openOptionsDialog();
                break;
            case R.id.item_main_monitor:
               // Toast.makeText(getApplicationContext(), "클릭2", Toast.LENGTH_SHORT).show();
                Intent second = new Intent(MainActivity.this, MainSecond.class);
                startActivity(second);
                //openOptionsDialog();
                break;
            case R.id.item_main_community:
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_main_preferences:
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item_main_byebye:
                exitOptionsDialog();
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main Acitivty", "onResume()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Main Acitivty", "onStart()");
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("진입","옵션메뮤@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        switch (item.getItemId()){
            case R.id.item_main_info:
                Toast.makeText(this, "클릭1", Toast.LENGTH_SHORT).show();
                openOptionsDialog();
                break;
            case R.id.item_main_monitor:
                Toast.makeText(getApplicationContext(), "클릭2", Toast.LENGTH_SHORT).show();
                Intent second = new Intent(MainActivity.this, MainSecond.class);
                startActivity(second);
                openOptionsDialog();

                break;
            case R.id.item_main_community:
Log.d("진입","옵션메뮤@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_main_preferences:
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item_main_byebye:
                Toast.makeText(this, "하이", Toast.LENGTH_SHORT).show();
                break;
        }
    return super.onOptionsItemSelected(item);
    }*/
    private void openOptionsDialog() { //컨텍스트 메뉴에서 아이템 하나 클릭했을때 실행되는 메서드
        new AlertDialog.Builder(this)
                .setTitle("내정보")
                .setMessage("정보창")
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Toast.makeText(MainActivity.this, "openOptionsDialog()", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
    }
    private void exitOptionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("계정삭제")
                .setMessage("회원님의 데이터가 삭제되오니, 신중하게 생각하시길 바랍니다.")
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Toast.makeText(MainActivity.this, "exitOptionsDialog() OK", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Toast.makeText(MainActivity.this, "exitOptionsDialog() Die", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).show();
    }

}

