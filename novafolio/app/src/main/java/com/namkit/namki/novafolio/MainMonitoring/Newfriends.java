package com.namkit.namki.novafolio.MainMonitoring;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by namki on 2018-03-21.
 */

public class Newfriends extends AsyncTask<Integer, String, Integer> {

    private ProgressDialog mDlg;
    private Context mContext;

    public Newfriends(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDlg = new ProgressDialog(mContext);
        mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDlg.setMessage("Start");
        mDlg.show();

        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... params) {

        final int taskCnt = params[0];
        publishProgress("max", Integer.toString(taskCnt));

        for (int i = 0; i < taskCnt; ++i) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 작업이 진행되면서 호출하며 화면의 업그레이드를 담당하게 된다
            publishProgress("progress", Integer.toString(i),
                    "주소록 동기화 중 " + Integer.toString(i)+" %");
        }

        // 수행이 끝나고 리턴하는 값은 다음에 수행될 onProgressUpdate 의 파라미터가 된다
        return taskCnt;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        if (progress[0].equals("progress")) {
            mDlg.setProgress(Integer.parseInt(progress[1]));
            mDlg.setMessage(progress[2]);
        } else if (progress[0].equals("max")) {
            mDlg.setMax(Integer.parseInt(progress[1]));
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        mDlg.dismiss();
        Toast.makeText(mContext, " 현재 친구목록은 최신버전 입니다. ",
                Toast.LENGTH_SHORT).show(); //Integer.toString(result)총 값
    }
}