package com.namkit.namki.novafolio.MainMonitoring;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namkit.namki.novafolio.R;

/**
 * Created by namki on 2018-03-19.
 */

public class SingerItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    public SingerItemView(Context context) {
        super(context);

        init(context);
    }

    public SingerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mainsecondgriditem, this, true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setState(String state) {
        textView2.setText(state);
    }

    public void setTime(String time) {
        textView3.setText(String.valueOf(time));
    }
    public void setColor(int color) {
        imageView.setBackgroundColor(color);
    }
  //  public void setImage(int resId) {
     //   imageView.setImageResource(resId);
  //  }
}