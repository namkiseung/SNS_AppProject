package com.namkit.namki.novafolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class FindMember extends AppCompatActivity{
 SharedPreferences preferences;
    TextView displayId, displaypw, displayEmail;
    Button  Gobutton, Idbutton, Pwbutton;
    int falsecount=0, falsecount2=0; //카운트세기 (버튼비활성화)
    EditText etname, etid, etemail;
    String aa, aaa;
    int intenabled, intenabled2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_member);
          //check = getSharedPreferences("btnrecycle",0);

        preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);
Log.d("FindMember","onCreate에서 preferences의 네임! 출력 : "+ preferences);
        displayId = (TextView)findViewById(R.id.FindIdDisplay);
        displaypw = (TextView)findViewById(R.id.FindPwDisplay);
        displayEmail = (TextView)findViewById(R.id.FindEmailDisplay);
Log.d("FindMember","onCreate에서 TextView 들이 제대로 들어왔나?(xml의 id를 가져온값) displayId 출력 : "+ displayId +"displaypw 출력 : "+displaypw + "displayEmail 출력 : "+displayEmail);
        Idbutton = (Button)findViewById(R.id.btn_Find_ID);
        Pwbutton = (Button)findViewById(R.id.btn_Find_PW);
        Gobutton = (Button)findViewById(R.id.btn_MoveToLogin);
Log.d("FindMember","onCreate에서 Button 들이 제대로 들어왔나?(xml의 id를 가져온값) Idbutton 출력 : "+ Idbutton +"Pwbutton 출력 : "+Pwbutton + "Gobutton 출력 : "+Gobutton);
        etname = (EditText)findViewById(R.id.etFindName);
        etid = (EditText)findViewById(R.id.etFindId_id);
        etemail = (EditText)findViewById(R.id.etFindEmail_Email);
Log.d("FindMember","onCreate에서 Button 들이 제대로 들어왔나?(xml의 id를 가져온값) Idbutton 출력 : "+ Idbutton +"Pwbutton 출력 : "+Pwbutton + "Gobutton 출력 : "+Gobutton);



        Idbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.d("버튼 클릭 처음 로직  ","falsecount값 : "+ falsecount);

                String newUserName = etname.getText().toString(); //이름에 입력된 내용
                if(!newUserName.equals("")){ //빈칸인지 확인

                String getUserTo = preferences.getString(newUserName+"NAMETOID", "정확히 입력하세요"); //저장되있던 이름 to 값 //이름을 키 / 아이디가 속성   결국 이름적으면 아이디가 나온다
                String 입력한이름으로아이디가져와서그아이디로아이디찾기 = preferences.getString(getUserTo+"TEAM_MEMBER_ID", "정확히 입력하세요"); //저장되있던 ID 값
//Log.d("FindMember Idbutton","preferences저장된 이름을 키로 하는 값 가져온다 (newUser키 값) 로(NAMETOID) = "+getUserTo);
                String getUserEmail = preferences.getString(getUserTo+"TEAM_MEMBER_EMAIL", "정확히 입력하세요"); //저장되있던 이메일 값
//Log.d("FindMember Idbutton","preferences저장된 이메일을 키로 하는 값 가져온다 (getUserTo키 값) 로(TEAM_MEMBER_EMAIL) = "+preferences.getString(getUserTo+"TEAM_MEMBER_EMAIL", "정보 없음"));

                    if(입력한이름으로아이디가져와서그아이디로아이디찾기 == "정확히 입력하세요"){
                        falsecount++;
                        displayId.setText("");
                        displayEmail.setText("");
                        Toast toast = Toast.makeText(FindMember.this, falsecount+"회 / 3 이상 틀리지 마세요.", Toast.LENGTH_SHORT);
                        int xOffset = 50;
                        int yOffset = 50;
                        toast.setGravity(Gravity.CENTER, xOffset, yOffset);
                        toast.show();
                    }
                    else if(입력한이름으로아이디가져와서그아이디로아이디찾기.equals(getUserTo)){
                        falsecount=0;
                    }
                     try{aa = getUserTo.substring(getUserTo.length()-2, getUserTo.length());//문자열 잘라서 aa에 입력 그리고, aa를 '*'로 치환
                     aaa = getUserTo.replaceAll(aa, "**");}catch (StringIndexOutOfBoundsException e){}
                        displayId.setText(aaa+" ("+getUserTo.length()+" 자리 )");
                        displayEmail.setText("E-mail : "+getUserEmail);
                        etname.setText("");
                    Log.d("setText()공백 처리 후에  ","falsecount값 : "+ falsecount);
                    if(falsecount == 3){
                        Idbutton.setEnabled(false);
                        final Handler handler = new MyHandler(FindMember.this);

                        Runnable task = new Runnable() {
                            @Override
                            public void run() {
                                intenabled=30;
                                Log.d("런어블 시작할때 ","intenabled 값은 : "+intenabled);
                                while (intenabled>0){
                                    try {
                                        Thread.sleep(800);
                                        --intenabled;
                                        handler.sendEmptyMessage(1);

                                        Message msg = Message.obtain();
                                        msg.what = 1;

                                        handler.sendMessage(msg);
                                        Log.d("런어블 안 ","intenabled 값은 : "+intenabled);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                falsecount = 0;
                            }
                        };
                        Thread th = new Thread(task);
                        th.start();
                        Log.d("스레드 스타트할때  ","falsecount값 : "+ falsecount);
                    }else if(falsecount > 3){
                        falsecount = 0;
                    }
                    Log.d("스레드 스타트할때  ","falsecount값 : "+ falsecount);
                }else {
                    Toast.makeText(FindMember.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                Log.d("버튼 클릭 마지막 로직  ","falsecount값 : "+ falsecount);
            }
        });

        Pwbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserId = etid.getText().toString(); //아이디에 입력된 내용
Log.d("FindMember Pwbutton","Edit에 입력한 아이디 호출 newUserId = "+newUserId);
                String newUserEmail = etemail.getText().toString(); //이메일에 입력된 내용
Log.d("FindMember Pwbutton","Edit에 입력한 이메일 호출 newUserEmail = "+newUserEmail);

                String getUserId = preferences.getString(newUserId+"TEAM_MEMBER_ID", "정확히 입력하세요"); //저장되있던 ID 값
Log.d("FindMember Pwbutton","'아이디'를 키로 (TEAM_MEMBER_ID) 저장했던 값 = "+ getUserId);
                String getUserPw = preferences.getString(newUserId+"TEAM_MEMBER_PW", "정확히 입력하세요"); //저장되있던 비밀번호 값
Log.d("FindMember Pwbutton","'아이디'를 키로 (TEAM_MEMBER_PW) 저장했던 값 = "+ getUserPw);
                String getUserEmail = preferences.getString(newUserId+"TEAM_MEMBER_EMAIL", "정확히 입력하세요"); //저장되있던 이메일 값
Log.d("FindMember Pwbutton","'아이디'를 키로 (TEAM_MEMBER_EMAIL) 저장했던 값 = "+ getUserEmail);
                //String temp = preferences.getString(newUser+"TEAM_MEMBER_ID", ""); //저장되있던 ID 값의 속성을 temp에 담는다.


                if(getUserId == "정확히 입력하세요" || newUserEmail == "정확히 입력하세요"){
                    falsecount2++;
                    displayId.setText("");
                    displaypw.setText("");
//System.out.println("if문들어왔네 falsecount2++"+falsecount2);
                    Toast toast = Toast.makeText(FindMember.this, falsecount2+"회 / 3 이상 틀리지 마세요.", Toast.LENGTH_SHORT);
                    int xOffset = 50;
                    int yOffset = 50;
                    toast.setGravity(Gravity.CENTER, xOffset, yOffset);
                    toast.show();
                }
                else if("정확히 입력하세요" != getUserId|| "정확히 입력하세요" != getUserEmail){
//System.out.println("else       if문들어왔네 falsecount2++"+falsecount2);
                    falsecount2=0;
                }

                //Toast.makeText(FindMember.this, falsecount2+"ㅋ", Toast.LENGTH_SHORT).show();
                if(!getUserId.equals("")||!newUserEmail.equals("")){//&&!newUserId.equals(null)&&!newUserEmail.equals(null)
                    etid.setText("");
                    etemail.setText("");
                   /* 저장된것을 불러올 때     아이디 키값으로  아이디를 반환
                    저장된것을 불러올 때     아이디 키값으로  이메일을 반환

                    입력한 이메일이 == 입력한 아이디로 가져온 이메일과 같은지 확인한다.
                    입력한 아이디를 키로 잡은 비밀번호를 가져와서 setText 해준다.*/
                    if(newUserEmail.equals(getUserEmail)||falsecount2 == 3){ //일단은 입력한 이메일이 입력한 아이디로 회원가입했던 이메일과 같은지 비교!!!!!!!저장된 아이디와 입력이 같고, 그 아이디를 키값으로한 이메일과 적은이메일이 맞는지 확인
                        displaypw.setText(getUserPw); //ID 값 리턴
                        etid.setText("");
                        etemail.setText("");
                        if(falsecount2 == 3){
                            falsecount2=0;
                            Pwbutton.setEnabled(false);
                            final Handler handler = new MyHandler(FindMember.this);

                            Runnable task2 = new Runnable() {
                                @Override
                                public void run() {
                                    intenabled2=30;
                                    while (intenabled2>0){
                                        try {
                                            Thread.sleep(800);
                                            --intenabled2;
                                            handler.sendEmptyMessage(2);

                                            Message msg = Message.obtain();
                                            msg.what = 2;
                                            handler.sendMessage(msg);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                            Thread th2 = new Thread(task2);
                            th2.start();
                        }

                    }else{
                     //   Toast.makeText(FindMember.this, "아이디와 이메일이 동일한 회원인지 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FindMember.this, "아이디와 이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindMember.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private static class MyHandler extends Handler{
     private final WeakReference<FindMember> mFindMember;

        private MyHandler(FindMember findMember) {
            mFindMember = new WeakReference<FindMember>(findMember);
        }

        @Override
        public void handleMessage(Message msg) {
            FindMember findMember = mFindMember.get();
            if(mFindMember != null) {
                switch (msg.what) {
                    case 1:
                        findMember.etname.setText("정보찾기 일시금지");
                        findMember.Idbutton.setText(findMember.intenabled+"초");
                        Log.d("핸들러 클래스 ","findMember.intenabled 값은 : "+findMember.intenabled);
                        if(findMember.intenabled==0){
                            findMember.Idbutton.setEnabled(true);
                            findMember.falsecount=0;
                            findMember.etname.setText("");
                            findMember.displayId.setText("");
                            findMember.displayEmail.setText("");
                            findMember.Idbutton.setText("아이디 찾기");
                        }
                        break;
                    case 2:
                        findMember.etid.setText("정보찾기 일시금지");
                        findMember.etemail.setText("정보찾기 일시금지");
                        findMember.Pwbutton.setText(findMember.intenabled2+"초");
                        Log.d("핸들러 클래스 ","findMember.intenabled 값은 : "+findMember.intenabled2);
                        if(findMember.intenabled2==0){
                            findMember.Pwbutton.setText("비밀번호 찾기");
                            findMember.Pwbutton.setEnabled(true);
                            findMember.falsecount2=0;
                            findMember.etid.setText("");
                            findMember.displaypw.setText("");
                            findMember.etemail.setText("");
                        }
                        break;
                }
            }
        }
    }
    protected void onStop(){
        super.onStop();
        /*editor = check.edit();
        editor.putInt("Idpause",intenabled);
        editor.commit();*/
    }
    protected void onStart(){
      super.onStart();
       /*this.intenabled = check.getInt("Idpause",0);*/
    }
   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // 반드시 호출해 주세요.
        String a = Idbutton.getText().toString();
        String b = Pwbutton.getText().toString();
        String c = etname.getText().toString();
        String d = etid.getText().toString();
        String e = etemail.getText().toString();
        String f = displayId.getText().toString();
        String g = displaypw.getText().toString();
        String h = displayEmail.getText().toString();

        outState.putString("counter",a);
        outState.putString("counter",b);
        outState.putString("counter",c);
        outState.putString("counter",d);
        outState.putString("counter",e);
        outState.putString("counter",f);
        outState.putString("counter",g);
        outState.putString("counter",h);

        // 추가로 자료를 저장하는 코드는 여기에 작성 하세요.
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
    }*/
}
