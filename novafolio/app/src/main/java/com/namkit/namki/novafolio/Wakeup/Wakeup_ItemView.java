package com.namkit.namki.novafolio.Wakeup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.namkit.namki.novafolio.R;

/**
 * Created by namki on 2018-03-07.
 */

public class Wakeup_ItemView extends LinearLayout {
    /*TextView textView;
    TextView textView2;
    TextView textView3;*/
    ImageView imageView;
    public Wakeup_ItemView(Context context) {
        super(context);
        init(context);
    }

    public Wakeup_ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wakeup_item, this, true);

        /*textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);*/
        imageView = (ImageView) findViewById(R.id.wake_view);
    }
    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }

}
