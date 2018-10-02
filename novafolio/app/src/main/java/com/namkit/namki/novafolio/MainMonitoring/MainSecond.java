package com.namkit.namki.novafolio.MainMonitoring;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.namkit.namki.novafolio.MainActivity;
import com.namkit.namki.novafolio.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainSecond extends AppCompatActivity implements CustomDialog.ExampleDialogListener {
    //private static long START_TIME_IN_MILLIS=0;
    EditText editText;
    static int k = 0;
    boolean plus = true;
    private AsyncTask<Integer, String, Integer> mProgressDlg;
    GridView gridView;
    SingerAdapter adapter;
    SharedPreferences prefs;
    //Random sc, sctime;
    long inputNumber=0;
    Button renewer;
    long mEndTime = (System.currentTimeMillis()/1000-1521336357) + inputNumber; // 입력받은 시간과 끝시간 계산
    int endcolor;
    TimeHandler timers = new TimeHandler(this);
    ImageView mimageview;
    TextView mtitle, mdesc, mdtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second);
        prefs = getSharedPreferences("NowUser", 0);
        Toast.makeText(this, "프로필을 누르면 상태를 바꿀 수 있습니다. ", Toast.LENGTH_SHORT).show();
        //내프로필;
        mimageview = (ImageView)findViewById(R.id.myimageView);
        mtitle = (TextView)findViewById(R.id.mytextView);
        mdesc = (TextView)findViewById(R.id.mytextView2);
        mdtime = (TextView)findViewById(R.id.mytextView3);
        renewer= (Button) findViewById(R.id.Renewerfriends);
        mtitle.setText("아이디 : "+prefs.getString("currentdisplayId",""));
        mdesc.setText("상태메시지 : 부재중");

        mimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog exampleDialog = new CustomDialog();
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }
        });
        /**
         * [이미지]
         * 1. 이미지를 누르면 다이얼로그가 뜬다
         * 2. 다이얼로그에는 4가지 색상과 텍스트를 입력할 수 있다
         * 3. 텍스트를 작성 후 (작성안해도됨) 한가지 색상을 선택한다
         * 4. 작성한 텍스트는 상태메시지에 표시된다
         * 5. 그리고 타이머가 증가하기 시작한다
         * */
        //그리드뷰
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new SingerAdapter();
       // adapter.addItem(new SingerItem("루이", "부재중 입니다", "00 : 00", -16777216));
       // adapter.addItem(new SingerItem("존", "부재중 입니다", "00 : 00", -16777216));
       // adapter.addItem(new SingerItem("엘버른", "부재중 입니다", "00 : 00", -16777216));
       // adapter.addItem(new SingerItem("윌스미스", "부재중 입니다", "00 : 00", -16777216));
       // adapter.addItem(new SingerItem("데이먼", "부재중 입니다", "00 : 00", -16777216));
       // adapter.addItem(new SingerItem("에일리", "부재중 입니다", "00 : 00", -16777216));
        gridView.setAdapter(adapter);


        editText = (EditText) findViewById(R.id.editText);
        /*Button btngridadd = (Button) findViewById(R.id.btn_add_friends);
        btngridadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSecond.this, GridAddFriends.class);;
            }
        });*/

        Button button = (Button) findViewById(R.id.button);
        renewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDlg = new Newfriends(MainSecond.this).execute(100);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = (int) (Math.random() * 8);
                String name = editText.getText().toString();
            if(name != "") {
                String time = "00 : 00";
                String state;
                if (a == 1) {
                    state = "집중이 되지않네요~~";
                    //adapter.addItem(new SingerItem(name, state, time, a));//-256
                    adapter.addItem(new SingerItem(name, state, time, -256));
                    adapter.notifyDataSetChanged();
                } else if (a == 2) {
                     state = "쉬고있습니다.";
                    adapter.addItem(new SingerItem(name, state, time, -16711936));
                    //adapter.addItem(new SingerItem(name, state, time, a));//-16711936
                    adapter.notifyDataSetChanged();
                } else if (a == 3) {
                     state = "작업중입니다....";
                    adapter.addItem(new SingerItem(name, state, time, -65536));
                    //adapter.addItem(new SingerItem(name, state, time, a));//-65536
                    adapter.notifyDataSetChanged();
                } else {
                     state = "부재중 입니다";
                    adapter.addItem(new SingerItem(name, state, time, -16777216));
                    adapter.notifyDataSetChanged();
                }
                editText.setText("");
            }else{
                Toast.makeText(MainSecond.this, "친구를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :  " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainSecond.this, "정상 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.items.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
    protected void countalarm() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Novafolio 모니터링 알림")
                .setContentText("설정하신 시간이 만료되었습니다.").setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_VIBRATE);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT); //PendingIntent은 원격으로 뭔갈 할 수 있는것 즉 지금은 앱이 꺼져도 Result를 킬수있는것
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }
    @Override
    public void applyTexts(String userdesc, int endcolor, String usertime, String usertimesec) {
        mdesc.setText("상태메시지 : "+userdesc);
//        START_TIME_IN_MILLIS= Long.parseLong(usertime); //값 부여
        int defaultcolor = endcolor;
        if(defaultcolor == -65536 || defaultcolor == -256 || defaultcolor== -16711936 || defaultcolor== -16777216){
            mimageview.setBackgroundColor(endcolor);
        }else{
            mimageview.setBackgroundColor(-16777216);
        }
        if(usertime==""||usertime==null){
            usertime = "0";
        }
        if(usertimesec==""||usertimesec==null){
            usertimesec = "0";
        }

            String input = usertime;
            String inputsec = usertimesec;
        inputNumber = Integer.parseInt(input)*60+Integer.parseInt(inputsec);
        mdtime.setText(usertime);
        mEndTime = Long.parseLong((System.currentTimeMillis()/1000-1521336357) + usertime); //받아온값으로 mEndTime 세팅
        ///////
        if(input.equals("")){
            Toast.makeText(this, "시간을 설정해주세요", Toast.LENGTH_SHORT).show();
        }else {
           /* if(inputNumber==0) {
                System.out.println("plus상태"+plus);
                plus = true;
                System.out.println("plus상태2"+plus);
                //return;
            }else */
           ///////////////////0일떄 시간 증가
             /*   if(plus){
                /////////////////////////////시간설정안했을때
                final TimeHandler handler2 = new TimeHandler(this);
                Runnable task2 = new Runnable(){
                    @Override
                    public void run(){
                        while(plus){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {}
                            ++inputNumber;
                            handler2.sendEmptyMessage(3); //핸들러에 메시지 send 가능
                            Message message2= Message.obtain(); //.obtain을 이용해서 sendMessage할 때 필요한 msg 만들 수 있다.
                            message2.what = 2;
                            handler2.sendMessage(message2);
                            if(inputNumber==30000000){//5시간까지
                                break;
                            }
                        }
                    }
                };
                Thread thread2 = new Thread(task2);
                thread2.start();

            }*/
            /////////////////////////////시간설정했을때
            final TimeHandler handler = new TimeHandler(this);
            Runnable task = new Runnable(){
                @Override
                public void run(){
                    while(inputNumber > 0){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                        --inputNumber;
                        handler.sendEmptyMessage(1); //핸들러에 메시지 send 가능
                        Message message= Message.obtain(); //.obtain을 이용해서 sendMessage할 때 필요한 msg 만들 수 있다.
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
        ///////

    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setState(item.getState());
            view.setTime(item.getTime());
            view.setColor(item.getColor());
            //int a = (int) (Math.random() * 8);
            //String state;
           /* if (item.getColor() == 1) {
                state = "집중이 되지않네요~~";
                view.setState(state);
                System.out.println("item.getColor()item.getColor()item.getColor()"+item.getColor());
                view.setColor(-256);
            } else if (item.getColor() == 2) {
                 state = "쉬고있습니다.";
                view.setState(state);

            } else if (item.getColor() == 3) {
                 state = "작업중입니다....";
                view.setState(state);
                view.setColor(-65536);
            } else {
                 state = "부재중 입니다";
                view.setState(state);
                view.setColor(Color.RED);
            }
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa 색@@@@@@@@@@@@"+item.getColor());*/


          //  view.setImage(item.getResId());

            int numColumns = gridView.getNumColumns();
            int rowIndex = position / numColumns;
            int columnIndex = position % numColumns;
            Log.d("SingerAdapter", "index : " + rowIndex + ", " + columnIndex);

            return view;
        }
    }
    /*@Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", inputNumber);
        outState.putInt("statecolor", endcolor);
        outState.putLong("endTime", mEndTime);
        //outState.putBoolean("timerRunning", mTimerRunning);
        mEndTime = System.currentTimeMillis() + inputNumber;
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        inputNumber = savedInstanceState.getLong("millisLeft");
        endcolor = savedInstanceState.getInt("statecolor", endcolor);
        mimageview.setBackgroundColor(endcolor);
            mEndTime = savedInstanceState.getLong("endTime");
            inputNumber = mEndTime - (System.currentTimeMillis()/1000-1521336357);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefsTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", inputNumber);
        editor.putInt("statecolor", endcolor);
        editor.putLong("endTime", mEndTime);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences("prefsTime", MODE_PRIVATE);
      //  inputNumber = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        endcolor = prefs.getInt("statecolor", endcolor);
        this.mimageview.setBackgroundColor(endcolor);
        //  updateCountDownText();
            mEndTime = prefs.getLong("endTime", 0);
            inputNumber = mEndTime - (System.currentTimeMillis()/1000-1521336357);

        final TimeHandler handler = new TimeHandler(this);
        Runnable task2 = new Runnable(){
            @Override
            public void run(){
                while(inputNumber > 0){
                    try {
                        Thread.sleep(1000);
                       // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+(System.currentTimeMillis()/1000-1521336357));
                    } catch (InterruptedException e) {}
                    --inputNumber;
                    handler.sendEmptyMessage(1); //핸들러에 메시지 send 가능
                    Message message= Message.obtain(); //.obtain을 이용해서 sendMessage할 때 필요한 msg 만들 수 있다.
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        };
        Thread thread2 = new Thread(task2);
        thread2.start();
    }*/
}
class TimeHandler extends Handler {
    private final WeakReference<MainSecond> mActivity;

    public TimeHandler(MainSecond activity) {
        mActivity = new WeakReference<MainSecond>(activity);
    }

    @Override
    public void handleMessage(Message msg) { //여기에서 TextView에 직접 접근 가능
        MainSecond activity = mActivity.get();
        if (activity != null) {
            /**
             * 넘겨받은 what값을 이용해 실행할 작업을 분류합니다
             */
            if(msg.what==1){
                Log.d("What Number : ", "What is 1");
            }else if(msg.what==2){
                Log.d("What Number : ", "What is 2");
            }else if(msg.what==3){
                Log.d("What Number : ", "What is 3");
            }else {
                Log.d("What Number : ", "What is 그이상");
            }
            int hour = (int)activity.inputNumber / 3600;
            int min = (int)activity.inputNumber / 60;
            int sec = (int)activity.inputNumber % 60;
            String strTime = String.format("%02d 시 %02d 분 %02d 초", hour, min, sec);
            activity.mdtime.setText(""+strTime);
            if(activity.inputNumber==0){
                activity.countalarm();
                activity.mimageview.setBackgroundColor(-16777216);
                activity.mdesc.setText("상태메시지 : 부재중");
            }/*else if(activity.inputNumber<0){
                activity.mimageview.setBackgroundColor(Color.BLACK);
                activity.mdesc.setText("상태메시지 : 오프라인");
            }*/
        }
    }
}