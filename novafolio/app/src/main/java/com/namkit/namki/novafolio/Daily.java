package com.namkit.namki.novafolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Daily extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    Intent intent;
    int id = -1;
    private ListView mListView;
    EditText daily_title_edit, daily_content_edit;
    Daily_Adapter_List adapter;
    List<Daily_MainItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);


                                            /** ListView 타입의 메인.xml에 있는 ListView의 ID를 가져와서 mListView로 선언*/
        mListView = (ListView)findViewById(R.id.my_listview);

                                            /** data라는 배열을 인스턴스화*/
        data = new ArrayList<>();
            data.add(new Daily_MainItem("◎ 1주차", "설명입니다."));
        data.add(new Daily_MainItem("       1일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       2일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       3일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       4일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       5일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       6일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       7일 ", "       설명입니다."));
        data.add(new Daily_MainItem("◎ 2주차", "설명입니다."));
        data.add(new Daily_MainItem("       1일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       2일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       3일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       4일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       5일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       6일 ", "       설명입니다."));
        data.add(new Daily_MainItem("       7일 ", "       설명입니다."));


                                            /** Array배열을 담을 수 있는 어댑터 생성*/
        //ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.daily_item, data);
        adapter = new Daily_Adapter_List(getApplicationContext(), data);
                                            /** 데이터는 어댑터에 들어갔으니 어댑터를 리스트뷰에 장착하자*/
        mListView.setAdapter(adapter);

                                            /** 리스트의 아이템 클릭시 이벤트시작*/
        mListView.setOnItemClickListener(this); // 한번 클릭 이벤트
        mListView.setOnItemLongClickListener(this); //롱클릭 이벤트

        //플로팅 버튼 이벤트
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.float_daily_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Daily.this, Sub_daily.class);
                                         Log.d("Daily클래스의 onClick","data"+data);
            //    intent.putExtra("배열", (Serializable) data);//인텐트에 정보를 담아서 보낸다
                startActivity(intent);
            }
        });
    }
                                        /** parent는 mListView를 가르킨다 (리스트뷰를 말한다)
                                         * view는 클릭한 리스트의 한칸을 말한다.
                                         * positon과 id는 비슷(위치를 알린다)
                                         * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Toast.makeText(this, "position"+position, Toast.LENGTH_SHORT).show();
        Log.d("Daily클래스","onItemClick 클릭했을때 position값 == "+position);
        Log.d("Daily클래스","onItemClick 클릭했을때 id값 == "+id);
        id=position;


        daily_title_edit.setText(data.get(position).getTitle());
        daily_content_edit.setText(data.get(position).getDescription());
    }
                                        /**
                                         * 대부분의 boolean 타입은 했는지 안했는지 구별용도
                                         * return false 설정안한게 된다.
                                         * return true 여기서 이벤트를 끝낸다
                                         * */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        data.remove(i);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onClick(View view) {

    }
    /*@Override
    public void onDestroy(){
        super.onDestroy();

    }*/

    /*public void createNote(String subject, String text) {
        Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
                .putExtra(NoteIntents.EXTRA_NAME, subject)
                .putExtra(NoteIntents.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }*/

}
