package com.namkit.namki.novafolio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by namki on 2018-03-05.
 */

public class Daily_Adapter_List extends BaseAdapter {
    TextView title;
    TextView description;
    private Context mContext;
    private List<Daily_MainItem> mData;

    public Daily_Adapter_List(Context context, List<Daily_MainItem> data) {                                                    //Context를 받아야한다 그래야 리소스 접근가능 이게 없으면 그냥 단순 클래스가 된다
        mContext = context;
        mData = data;
    }

    //아이템의 갯수
    @Override
    public int getCount() {
        Log.d("Daily_Adapter_List", "int getCount()==");
        return mData.size();
    }

                                                    //해당 포지션에 있는 아이템 리턴해줌
    @Override
    public Object getItem(int i) {                                                     //return 타입이 Object로 모든타입이 리턴가능
        Log.d("Daily_Adapter_List", "Object getItem(int i) 의 i값 =="+i);
        return mData.get(i);                                                    //해당 위치의 데이터를 돌려준다(동작은 내부적으로 첫번째 칸이라고 하면 해당하는것이 올라온다
    }
                                                        //해당 포지션에 있는 아이템의 ID(고유)
    @Override
    public long getItemId(int i) {
        Log.d("Daily_Adapter_List", "long getItemId(int i)=="+i);
        return i;
    }

                                                        //아이템 하나를 표현하는 방법을 정의
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) { //완성된 애를 반환해준다(convertView)만약 같은포지션이면 리턴한 값이 다시 convertView로 들어간다
        Log.d("Daily_Adapter_List", "View getView==");
        if(convertView == null){
                                                                //최초 로드할때(새로만든 daily_item 의 그 하나의 리스트를 가져와서 getView가 호출할때 리턴되도록 한다
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.daily_item, null);  // inflater 한 것은 findViewById랑 역할이 같다(보여지는 액티비티가 아닌데 리소스가져오고 싶으면 항상 이렇게)

                                                                //데이터를 연결 할 View
            title = (TextView)convertView.findViewById(R.id.title_textview);
            description = (TextView)convertView.findViewById(R.id.description_textview);
        }
            title.setText(mData.get(position).getTitle());
            description.setText(mData.get(position).getDescription());
        //title.setText(mData.get(position));
                                                            // 데이터
                                                           // Daily_MainItem item = (Daily_MainItem) getItem(position); //껍데기는 있고, 데이터를 꼽는다

                                                            //데이터 설정
                                                           // title.setText(item.getTitle());
                                                           // description.setText(item.getDescription());
        return convertView;
    }
}
