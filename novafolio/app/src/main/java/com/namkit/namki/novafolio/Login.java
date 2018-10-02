package com.namkit.namki.novafolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.namkit.namki.novafolio.Signup.Signup;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private BackPressCloseHandler backPressCloseHandler;

    EditText etId, etPwd;
    //CheckBox Auto_LogIn;
    //CheckBox Auto_MemoId; // 체크박스
    Button button, button2, button3;
    //Boolean loginCheck=false;
    String tempID, tempPW;
    SharedPreferences preferences;//= getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);  // 쉐어프리페런스 파일 만듬
    SharedPreferences.Editor editor;// = preferences.edit(); //에디터사용
    // displayId, displaypwd;
    ProgressBar circle_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면 세로 고정
        button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.btn_signup);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.btn_signupcommit);
        button3.setOnClickListener(this);
        etId = (EditText) findViewById(R.id.edtext_id);
        etPwd = (EditText) findViewById(R.id.edtext_pwd);
      //  Auto_LogIn = (CheckBox) findViewById(R.id.Auto_LogIn);
       // Auto_MemoId = (CheckBox) findViewById(R.id.Auto_MemoId);
        circle_bar = (ProgressBar) findViewById(R.id.progressBar);
        circle_bar.setVisibility(View.GONE);

/*if(preferences.getBoolean("autoID", false)){
    etId.setText(preferences.getString("currentdisplayId", ""));
    Auto_MemoId.setChecked(true);
}*/


       /* if(preferences.getBoolean("saveloginsavelogin", false))
            Auto_LogIn.setChecked(true);
        else
            Auto_LogIn.setChecked(false);

            etId.setText(preferences.getString(etId + "TEAM_MEMBER_ID", ""));
            etPwd.setText(preferences.getString(etId + "TEAM_MEMBER_PW", ""));*/
            //etId.addTextChangedListener(this);
            //etPwd.addTextChangedListener(this);
       // preferences = getSharedPreferences("temp",0);
      //  editor = preferences.edit();
        //아이디 자동 저장할때 체크박스 조건 이다
       // Auto_MemoId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          //  @Override
           // public void onCheckedChanged(CompoundButton compoundButton, boolean saveid) {
            //    if(saveid) {

                    //etId.setText(preferences.getString(etId+"TEAM_MEMBER_ID", ""));
                    //editor = preferences.edit();
                    //editor.putBoolean("save_Id",true); //ID 저장하는거 액티비티 종료시에 저장
                  //  editor.apply();
                   // loginCheck=true;

                   // String checkclickid = etId.getText().toString();

                   // editor.putString("TempId", checkclickid);
                   // editor.commit();

            //    }else {
                 //   loginCheck=false;
                 //   editor.remove("TempId");
                 //   editor.commit();
                   // editor.remove("currentdisplayId");
                  //  editor.commit();

                    /*etId.setText("");
                    editor = preferences.edit();
                    editor.putBoolean("save_Id",false); //ID 저장하는거 액티비티 종료시에 저장
                    editor.apply();*/
           //     }
          //  }
     //   });
        /*if(loginCheck){
            etId.setText(preferences.getString("TempId", "null"));
        }else{
            editor.remove("TempId");
            editor.commit();
        }
        */
       /* setting = getSharedPreferences("setting", MODE_PRIVATE);
            editor = setting.edit();
        etId = (EditText) findViewById(R.id.edtext_id);
        etPwd = (EditText) findViewById(R.id.edtext_pwd);
        //자동로그인을 설정하면 껏다켜도 유지되어야 하는 조건
        if(setting.getBoolean("Auto_Login_enabled", false)) { //처음 실행시 설정 값 없음. 기본값 false반환
            etId.setText(setting.getString("ID", "")); //ID와 PW 불러와서 에디트텍스트에 setText로 적용
            etPwd.setText(setting.getString("PW", ""));
            Auto_LogIn.setChecked(true);
        }
        Auto_LogIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                    String ID = etId.getText().toString();
                    String PW = etPwd.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                }else{*/
        /**
         * remove로 지우는것은 부분삭제
         * clear로 지우는것은 전체 삭제 입니다
         */
					/*editor.remove("ID");
					editor.remove("PW");
					editor.remove("Auto_Login_enabled");
                    //editor.clear();
                    editor.commit();//이코드가 없다면 실제로 변경된 내용이 반영되지 않는다
                }
            }
        });*/
        preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                tempID = etId.getText().toString();
                Log.d("로그인 액티비티", "아이디 입력하는 곳에 사용자가 입력한 tempID 값 =" + tempID);
                tempPW = etPwd.getText().toString();
                Log.d("로그인 액티비티", "비밀번호 입력하는 곳에 사용자가 입력한 tempPW 값 =" + tempPW);
               // preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);
               // System.out.println(preferences.getString(tempID + "TEAM_MEMBER_ID", ""));
                String displayId = preferences.getString(tempID + "TEAM_MEMBER_ID", "");
                //Log.d("로그인 액티비티","아이디 입력하는 곳에 사용자가 입력한 tempID 값 =" + tempID);
                //Log.d("로그인 액티비티", "사용자가 입력한 tempID 값을 '키'로 ID 불러오기 =" + displayId);
                String displaypwd = preferences.getString(tempID + "TEAM_MEMBER_PW", "");

                System.out.println(displayId+"@@@@@@@@@@@@@@@@@@@와 @@@@@@@@@@@@@@@"+displaypwd);
                //Log.d("로그인 액티비티","비밀번호 입력하는 곳에 사용자가 입력한 tempPW 값 =" + tempPW);
               // Log.d("로그인 액티비티", "사용자가 입력한 tempPW 값을 '키'로 ID 불러오기 =" + displaypwd);
                if (!tempID.equals("") || !tempPW.equals("")) {//아이디와 비번 공백이 넘어올때의 처리
                    if (tempID.equals(displayId) && tempPW.equals(displaypwd) || tempID.equals("user")) {//아이디와 비번이 맞는지
                        if (tempID.equals(displayId) || tempID.equals("user")) { //가입이 되어있는지 확인

                            Intent login = new Intent(Login.this, MainActivity.class);
                            login.putExtra("currentdisplayId", displayId);
                            CountTask count = new CountTask();  //AsyncTask상속받은 클래스를 사용하자
                            count.execute();//count 객체 실행
                            etId.setText("");
                            etPwd.setText("");
                            startActivity(login);
                        } else {//가입이 안되었을때
                            Toast.makeText(Login.this, "가입된 사용자가 아닙니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "ID와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "ID와 PW 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_signup:
                Intent signup = new Intent(Login.this, Signup.class);
                startActivity(signup);

                break;
            case R.id.btn_signupcommit:
                Intent signfind = new Intent(Login.this, FindMember.class);
                startActivity(signfind);
                break;
        }
    }

    //뒤로가기 버튼 2번클릭에 대한 메서드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressCloseHandler = new BackPressCloseHandler(Login.this);//뒤로가기 버튼 2번클릭 객체
        backPressCloseHandler.onBackPressed();
    }

    /*@Override
    public void onPause() {
        super.onPause();

        //Toast.makeText(Login.this, "onpause", Toast.LENGTH_SHORT).show();
    }*/
    @Override
    public void onStop() {
        super.onStop();

    }

   /* @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }
    private void managePrefs(){
        if(Auto_LogIn.isChecked()){
            System.out.println(Auto_LogIn+"aaaaaaaaaaaaaaaaaamanagePrefs()진입aaaaaaaaaaa");
            editor.putBoolean("saveloginsavelogin", true);
            editor.putString("currentdisplayId", tempID);
            editor.putString("currentdisplaypw",tempPW);
            editor.apply();
        }else{
            editor.putBoolean("saveloginsavelogin", false);
            editor.remove("currentdisplayId");
            editor.remove("currentdisplaypw");
            editor.apply();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }*/

   /* @Override
    public void onDestroy(){
        super.onDestroy();

       // finish();
    }*/
   /* @Override
    public void onResume() {
        super.onResume();
        //wificheck();

    }*/

    /*public void wificheck(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE); //활성화 한지 와이파이 체크 하기 위함
        manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE); //와이파이 연결 체크
//NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//와이파이 연결 체크
// wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected()) {
            Log.i("연결됨", "현재 데이터 WIFI로 연결중 입니다."); //와이파이 연결 되있을 때 구분 구문
            Toast.makeText(getApplicationContext(), "WIFI 정상 이용중", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "WIFI 연결되어있지 않으면 데이터로 연결됩니다.", Toast.LENGTH_SHORT).show();
/////////활성화 되지 않았다면 활성화 코드 추가
            wifiManager.setWifiEnabled(true); //와이파이 활성화
        }
    }*/

   /* public void successlogin(){
        preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);

        EditText name = (EditText)findViewById(R.id.edtext_name);
        String Logonname = preferences.getString(newUser+"TEAM_MEMBER_NAME","");
        String teamname = name.getText().toString();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Novafolio")
                .setAutoCancel(true)
                .setContentText(teamname+"님이 로그인 하였 습니다.").setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_VIBRATE);
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT); //PendingIntent은 원격으로 뭔갈 할 수 있는것 즉 지금은 앱이 꺼져도 Result를 킬수있는것
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }*/

        class CountTask extends AsyncTask<Integer, Integer, Void> { //AsyncTask가 제네릭 클래스이기 때문에 래퍼값으로 int 대신에 Integer다 혹은 double 사용
            //AsyncTask<Integer, Integer, Void> 인자는 꼭 3개
            //doInBackground을 제외한 나머지 3개는 MainThread에서 처리하게 된다.
            @Override
            protected Void doInBackground(Integer... integers) { //Thread를 처리하고 싶은 코드를 작성하는 곳
                //for(int i=0; i<100; i++){ //100000번 돌면서
                //     if(i%10 == 0 ){ //10000번 마다 TextView에 표시
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //    }
                // }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) { //새로운 Thread가 진행 중 실행하고 싶은 경우
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPreExecute() { //doInBackground가 실행되기 전에 실행하고 싶은 경우
                super.onPreExecute();
                circle_bar.setVisibility(View.VISIBLE);
                //text.setText(text.getText().toString()+"\n"+"Count를 시작하겠습니다.");
            }

            @Override
            protected void onPostExecute(Void aVoid) { //doInBackground의 수행이 끝난 뒤 실행하고 싶은경우
                super.onPostExecute(aVoid);
                //text.setText(text.getText().toString()+"\n"+"Count가 끝났습니다."); //카운터가 끝나면 나오는 텍스트
                circle_bar.setVisibility(View.GONE);
            }

        }
    }

