package com.namkit.namki.novafolio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class News extends AppCompatActivity implements View.OnClickListener{
    Button button;
    SeekBar seekbar;
    MediaPlayer music;

    int id = -1;

    ListView listSinhVien;
    EditText editTen;
    EditText editSDT;
    Button btnSua, btnXoa, btnclear;
    android.support.design.widget.FloatingActionButton btnThem;
    ArrayList<News_MainItem> arrSinhVien;
    News_CustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        arrSinhVien = new ArrayList<News_MainItem>();
        arrSinhVien.add(new News_MainItem(R.mipmap.ic_launcher," 판 벤 손1", "01094065290"));

        myadapter = new News_CustomAdapter(this, R.layout.news_item, arrSinhVien);
        listSinhVien.setAdapter(myadapter);

        listSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("News클래스","onItemClick 클릭했을때 position값 == "+position);
                Log.d("News클래스","onItemClick 클릭했을때 id값 == "+id);
                id=position;

                editTen.setText(arrSinhVien.get(position).getTenSinhVien());
                editSDT.setText(arrSinhVien.get(position).getSdtSinhVien());

            }
        });
        if(savedInstanceState != null) {
            editTen.setText(savedInstanceState.getString("message"));
            editSDT.setText(savedInstanceState.getString("message"));
        }
        listSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                arrSinhVien.remove(position);
                myadapter.notifyDataSetChanged();

                return false;
            }
        });
    }
    private void init(){
        listSinhVien = (ListView)findViewById(R.id.list_view);
        editTen =(EditText)findViewById(R.id.edit_ten);
        editSDT =(EditText)findViewById(R.id.edit_sdt);
        btnThem = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        btnSua = (Button)findViewById(R.id.btn_sua);
        btnclear = (Button)findViewById(R.id.btn_record_delete);

        // btnXoa = (Button)findViewById(R.id.btn_xoa);
        btnclear.setOnClickListener(this);
        btnThem.setOnClickListener(this);
        btnSua.setOnClickListener(this);
        // btnXoa.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                String ten = editTen.getText().toString();
                String sdt = editSDT.getText().toString();
                if(ten == "" || sdt == ""){
                    Toast.makeText(News.this, "입력하시오", Toast.LENGTH_SHORT).show();
                }else {
                    News_MainItem temp = new News_MainItem(R.mipmap.ic_launcher, ten, sdt);
                    arrSinhVien.add(temp);
                    myadapter.notifyDataSetChanged();
                    Toast.makeText(News.this, "작성한 글이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    editTen.setText("");
                    editSDT.setText("");
                }
                break;
            case R.id.btn_sua:
                String tenDaSua = editTen.getText().toString();
                String sdtDaSua = editSDT.getText().toString();

                News_MainItem svDaSua = new News_MainItem(R.mipmap.ic_launcher, tenDaSua, sdtDaSua);
                arrSinhVien.set(id, svDaSua);
                id = -1;
                myadapter.notifyDataSetChanged();
                editTen.setText("");
                editSDT.setText("");
                Toast.makeText(News.this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_record_delete:




                /*try{arrSinhVien.remove(id);}catch (Exception e){Toast.makeText(News.this, "삭제할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();}
                myadapter.notifyDataSetChanged();
                //확인 버튼이 눌렸을 때 토스트를 띄워줍니다.
                Toast.makeText(News.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                editTen.setText("");
                editSDT.setText("");
                myadapter.notifyDataSetChanged();*/
                break;

        }
    }
        /*Log.i("번 News Acitivty", "onCreate()");
        music = MediaPlayer.create(this, R.raw.darling); // raw에 있는 음악 파일가져오기
        music.setLooping(false); //Looping 을 false로 설정

        button = (Button) findViewById(R.id.button1); //재생버튼
        seekbar = (SeekBar) findViewById(R.id.seekBar1); //재생되는 위치 식바

        seekbar.setMax(music.getDuration()); //get으로 음악의 총길이가져온다

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //식바 변할때 리스너 붙혀주기
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//사용자가 건들면 true
                // TODO Auto-generated method stub
                if (fromUser) //사용자가 seek움직일때만 실행
                    music.seekTo(progress); //seekTo로 재생 위치 변경
            }
        });
}
    public void button(View v){
        if(music.isPlaying()){ //재생중이면 true반환 - 재생중이면 정지 재생중 아니면 재생
// 재생중이면 실행될 작업 (정지)
            music.stop();
            try {
                music.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            music.seekTo(0);
            button.setText(R.string.start);
            seekbar.setProgress(0);
        }else{
// 재생중이 아니면 실행될 작업 (재생)
            music.start();
            button.setText(R.string.stop);
            Thread();
        }
    }*/
  /*  public void Thread(){
        Runnable task = new Runnable(){
            public void run(){ //1초마다 SeekBar의 재생위치가 옮겨진다
                /**
                 * while문을 돌려서 음악이 실행중일때 게속 돌아가게 합니다
                 */
                /*while(music.isPlaying()){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    /**
                     * music.getCurrentPosition()은 현재 음악 재생 위치를 가져오는 구문 입니다
                     */
                   /* seekbar.setProgress(music.getCurrentPosition()); //재생중인 위치 파악
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }*/
    public void onPause(){
        super.onPause();
//        music.stop();
        Log.i("번 News Acitivty", "onPause()");
    }
    public void onStop(){
        super.onStop();
        Log.i("번 News Acitivty", "onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("번 News Acitivty", "onDestroy()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("번 News Acitivty", "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("번 News Acitivty", "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("News Acitivty", "onResume()");
    }


}

