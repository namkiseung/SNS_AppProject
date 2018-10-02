package com.namkit.namki.novafolio.MainMonitoring;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.namkit.namki.novafolio.R;

public class CustomDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private EditText editTextUserid;
    private EditText editTextmessage;
    private EditText edittime, edittimesec;
    Button red, green, yello, black;
    int endcolor;
    int temptime;

    TimerActivity timers = new TimerActivity();
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_custom_dialog, null);

        builder.setView(view)
                .setTitle("프로필수정")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //String userid = editTextUserid.getText().toString();
                            String userdesc = editTextmessage.getText().toString();
                            String usertime = edittime.getText().toString();
                            String usertimesec = edittimesec.getText().toString();

                                listener.applyTexts(userdesc, endcolor, usertime, usertimesec);

                        }
                    });

       // editTextUserid = view.findViewById(R.id.edit_userid);
        editTextmessage = view.findViewById(R.id.edit_userdesc);
        edittime = view.findViewById(R.id.edit_dialog_time);
        edittimesec = view.findViewById(R.id.edit_dialog_timesec);
        //버튼의 색상
        red = (Button) view.findViewById(R.id.btn_red);
        green = (Button) view.findViewById(R.id.btn_green);
        yello = (Button) view.findViewById(R.id.btn_yellow);
        black = (Button) view.findViewById(R.id.btn_black);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        yello.setOnClickListener(this);
        black.setOnClickListener(this);
        return builder.create();
    }


    //버튼 눌렀을때 !!!!!
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_red:
                // String red = "-65536";
                endcolor = -65536;
                //Toast.makeText(timers, "바쁜 상태를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                //endcolor=1;
                break;
            case R.id.btn_green:
                // String green = "-16711936";
                endcolor = -16711936;
                //Toast.makeText(timers, "여유있는 상태를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                // endcolor=3;
                break;
            case R.id.btn_yellow:
                //String yellow = "-256";
                endcolor = -256;
                //Toast.makeText(timers, "보통 상태를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                // endcolor=2;
                break;
            case R.id.btn_black:
                // String black = "-16777216";
                //Toast.makeText(timers, "부재중 상태를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                endcolor = -16777216;
                break;
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String password, int endcolor, String usertime, String usertimesec);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "반드시 임플리먼트 ExampleDiallogListener");
        }
    }

    private String emptycheck(String text) {
        if (text.equals("")) {
            Toast.makeText(getContext(), "공백입니다", Toast.LENGTH_SHORT).show();
        } else {
            temptime = Integer.parseInt(text);

            if (temptime == 0) {
                Toast.makeText(getContext(), "0은 입력할수 없습니다", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        return text;
    }
}
