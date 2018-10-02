package com.namkit.namki.novafolio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.namkit.namki.novafolio.models.Bookmark;

public class Record extends AppCompatActivity implements View.OnClickListener{
    private EditText et_url; //입력받는 EditText
    private Button btn_add, btn_edit, btn_del; //추가 버튼
    private ListView lv_bookmark; //리스트뷰
    Bookmark bookmark; //GETTER 와 SETTER와 생성자를 포함한 클래스
    int pos=0; //입력된 순서를 세는 변수
    private BookmarkAdapter bookmarkAdapter = new BookmarkAdapter();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
    }
    public void init(){
        Log.d("pos의 값","onCreate 호출되었을때 pos값==================="+pos);
        et_url = (EditText)findViewById(R.id.ed_url);
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_del = (Button)findViewById(R.id.btn_del);
        lv_bookmark = (ListView)findViewById(R.id.lv_bookmark);

        lv_bookmark.setAdapter(bookmarkAdapter);
        prefs = getSharedPreferences("setting", MODE_PRIVATE);
        btn_add.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("pos의 값","onStop에서 pos가 저장되기 직전================="+pos);
        editor = prefs.edit();
        editor.putInt("pos",pos);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.pos = prefs.getInt("pos", 0);
        Log.d("pos의 값","onStart의 pos값=========="+pos+"prefs.getInt(\"pos\", 0)값은 ====="+prefs.getInt("pos", 0));
       /* for(int i=0; i<pos; i++) {

        }*/
        Log.d("pos의 값","onStart의 for문 들어가기 직전======================"+pos);
        for(int i=1; i<=pos; i++) {
            String newgeturl = prefs.getString(String.valueOf(i), ""); //setting이라는 파일을 가져오는데, 해당 파일안에 내용없으면 "" 반환
            Log.d("pos의 값","onStart의 for문 진입==================="+pos+"그리고======================"+prefs.getString(String.valueOf(i), ""));
            bookmark = new Bookmark(newgeturl);//키으로 저장된 횟수 만큼 데이터를 넣으면 된다.
            bookmarkAdapter.addBookmark(bookmark);         //정상 추가 되었을때
        }
        /**
         * "1" ,"2", "3", "4" Key 값으로 데이터를 저장하였다.
         * 가져오는 방법
         * 해당 키값을 순차적으로 bookmark라는 객체에 추가하여 어댑터를 돌린다 => 이후 자동으로 리스트뷰에 생성
         *
         * */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                final String url = et_url.getText().toString();       //editText에 입력한 텍스트를 담는다.
                //추가한횟수를 센다. why? 저장한값의 횟수를 사용하기 위해서
                // if(isUrl(url)){
                bookmark = new Bookmark(url);
                if (bookmarkAdapter.hasDuplicate(bookmark)){     //중복되었을때
                    Toast.makeText(getApplicationContext(), "이미 등록된 주소입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    et_url.setText("");
                    pos++;
                    //Log.d("pos의 값","onClick으로 리스트에 데이터 추가할때 pos값==================="+pos);
                    bookmarkAdapter.addBookmark(bookmark);         //정상 추가 되었을때
                    editor = prefs.edit();  //editor를 사용하기 위해 edit() 함수를 가져온다
                    editor.putString(String.valueOf(pos), url);  // editor 사용준비가 되어서 키,값("url",url)쌍을 넣자. => "1"첫번째 "2" 번째 "3"번째 ....입력될 것이다.
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                // }else{
                //     showToast(getString(R.string.wrong));
                // }
                break;
         /*   case R.id.btn_edit:
                //delBookmark();
                break;
*/
        }
    }
    /* public boolean isUrl(String url){
        String urlRegex = "^(file|gopher|news|nntp|telnet|https?|ftps?|sftp)://([a-z0-9-]+.)+[a-z0-9]{2,4}.*$";
        return url.matches(urlRegex);
    }*/

}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }*/
        /*if(savedInstanceState != null) {
            editTen.setText(savedInstanceState.getString("message"));
            editSDT.setText(savedInstanceState.getString("message"));
        }*/
      /*  listSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(Record.this);
                //다이얼로그의 내용을 설정합니다.
                alertdialog.setTitle("공지");
                alertdialog.setMessage("해당 항목을 삭제하시겠습니까?");

                //확인 버튼
                alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrSinhVien.remove(position);
                        myadapter.notifyDataSetChanged();
                        //확인 버튼이 눌렸을 때 토스트를 띄워줍니다.
                        Toast.makeText(Record.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                //취소 버튼
                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소 버튼이 눌렸을 때 토스트를 띄워줍니다.
                        //Toast.makeText(Record.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert = alertdialog.create();
                alert.show();
                return false;
            }
        });*/


  /*  protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        for(int i=0; i<=arrSinhVien.size(); i++) {
            init();
            listSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    id=position;

                    editTen.setText(arrSinhVien.get(position).getTenSinhVien());
                    editSDT.setText(arrSinhVien.get(position).getSdtSinhVien());

                }
            });
            btnThem.setOnClickListener(this);
            onClick(btnThem);
        }
        outState.putString("message", ""+editTen);
        outState.putString("message2", ""+editSDT);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        editTen.setText(savedInstanceState.getString("message"));
        editSDT.setText(savedInstanceState.getString("message2"));
    }*/

