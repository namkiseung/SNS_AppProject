package com.namkit.namki.novafolio.Signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.namkit.namki.novafolio.Login;
import com.namkit.namki.novafolio.R;

import static com.namkit.namki.novafolio.R.id.btn_finish;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    EditText userId, userpw, userrepwd, username, userage, useremail;
    Button btn_idEqual;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userId = (EditText)findViewById(R.id.etNewId);
        userpw = (EditText)findViewById(R.id.etNewPwd);
        userrepwd = (EditText)findViewById(R.id.etNewRePwd);
        username = (EditText)findViewById(R.id.etNewName);
        userage = (EditText)findViewById(R.id.etNewAge);
        useremail = (EditText)findViewById(R.id.etNewEmail);
        btn_idEqual = (Button) findViewById(R.id.btn_idEqual);


        btn_idEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringId = userId.getText().toString(); //Edit에 입력한 Id값
                SharedPreferences preferences = getSharedPreferences("NOVAMEMBER", MODE_PRIVATE);  // 쉐어프리페런스 파일 만듬
                String userDetails = preferences.getString(stringId + "TEAM_MEMBER_ID", ""); //MYPREFS 파일안에 저장값을 가져와서 (userDetial)와 공유
                SharedPreferences.Editor editor = preferences.edit();
                //Log.d("회원가입","아이디 중복체크 지금 입력한 값  stringId : "+stringId);
                //Log.d("회원가입","아이디 중복체크 기존에 저장되있던 값  userDetails : "+userDetails);
            if (!stringId.equals("")) {
                if(stringId.length()>3 && stringId.length()<11) { //4글자 부터 최대 10글자

                    if (stringId.equals(userDetails)) {  //아이디에 입력한 값과  그 값이 이미 저장되어 있는지 확인
                        Toast.makeText(Signup.this, "이미 가입된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        check = true;
                    }
                }else{
                    Toast.makeText(Signup.this, "아이디는 4글자 이상 10글지 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Signup.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
          }
        });
        ImageButton button = (ImageButton)findViewById(btn_finish);
        button.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.btn_signupcommit);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_finish:
                finish();
                break;
            case R.id.btn_signupcommit:
                //비밀번호 맞는지 확인구문 예시
                Log.d("회원가입","첫번째 if문 이름부터 ~ 이메일까지 빈칸있는지 체크 [userId.getText().toString().equals(\"\")]  여기서 userId.getText().toString() 값은 ? = "+userId.getText().toString());
                if(userId.getText().toString().equals("")||userpw.getText().toString().equals("")||userrepwd.getText().toString().equals("")||username.getText().toString().equals("")||userage.getText().toString().equals("")||useremail.getText().toString().equals("")) {
                    Toast.makeText(Signup.this, "아직 입력되지 않은 회원정보가 있습니다", Toast.LENGTH_SHORT).show();
                }else{
                    if(check==true) { // 사용자가 EditText ID에 입력한 텍스트가 이미 존재하는 아이디와 같은지 비교

                        Log.d("회원가입","세번째 if문 userpw.getText().toString()와  userrepwd.getText().toString()가 같은가?  userpw.getText().toString() 값은 ? = "+userpw.getText().toString()+"userrepwd.getText().toString()의 값은 ?"+userrepwd.getText().toString());
                        if (userpw.getText().toString().equals(userrepwd.getText().toString())) {
                            Log.d("회원가입","몇글자까지 입력이 될까 ? 4글자 부터 8글자");
                            if (userpw.length() > 3 && userpw.length() < 9) {
                                SharedPreferences preferences = getSharedPreferences("NOVAMEMBER", Context.MODE_PRIVATE);  // 쉐어프리페런스 파일 만듬
                                String newUser = userId.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 userId.getText().toString() = "+newUser);
                                String newPwd = userpw.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 userpw.getText().toString() = "+newPwd);
                                String newRePwd = userrepwd.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 userrepwd.getText().toString() = "+newRePwd);
                                String newName = username.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 username.getText().toString() = "+newName);
                                String newAge = userage.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 userage.getText().toString() = "+newAge);
                                String newEmail = useremail.getText().toString();
                                Log.d("회원가입","EditText에 입력값이 잘들어왔나 useremail.getText().toString() = "+newEmail);
                                String tempName = newName; // 이름을 키값으로 아이디를 뽑기 위해서 tempName이라는 변수 생성

                                SharedPreferences.Editor editor = preferences.edit(); //수정 기능 사용 선언
                                editor.putString(newUser+"TEAM_MEMBER_ID", newUser);
                                editor.putString(newUser+"TEAM_MEMBER_PW", newPwd);
                                editor.putString(newUser+"TEAM_MEMBER_NAME", newName);
                                    editor.putString(tempName+"NAMETOID", newUser);//이름을 키 / 아이디가 속성
                                editor.putString(newUser+"TEAM_MEMBER_AGE", newAge);
                                editor.putString(newUser+"TEAM_MEMBER_EMAIL", newEmail);
                                editor.commit();
                                Log.d("회원가입","입력한 아이디 호출 TEAM_MEMBER_ID = "+preferences.getString(newUser+"TEAM_MEMBER_ID", ""));
                                Log.d("회원가입","입력한 비번 호출TEAM_MEMBER_PW = "+preferences.getString(newUser+"TEAM_MEMBER_PW", ""));
                                Log.d("회원가입","입력한 이름 호출TEAM_MEMBER_NAME = "+preferences.getString(newUser+"TEAM_MEMBER_NAME", ""));
                                Log.d("회원가입","이름입력 아이디 호출NAMETOID = "+preferences.getString(newName+"NAMETOID", ""));
                                Log.d("회원가입","입력한 나이 호출TEAM_MEMBER_AGE = "+preferences.getString(newUser+"TEAM_MEMBER_AGE", ""));
                                Log.d("회원가입","입력한 이메일 호출TEAM_MEMBER_EMAIL = "+preferences.getString(newUser+"TEAM_MEMBER_EMAIL", ""));

                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(Signup.this, "비밀번호를 4~8자리로 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Signup.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Signup.this, "ID 중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    @Override
    public void onStop(){
        super.onStop();

    }

}
