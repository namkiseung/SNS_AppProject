package com.namkit.namki.novafolio.JSONWakeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.namkit.namki.novafolio.R;
import com.squareup.picasso.Picasso;

import static com.namkit.namki.novafolio.JSONWakeup.Wakeup_JSON.EXTRA_CREATOR;
import static com.namkit.namki.novafolio.JSONWakeup.Wakeup_JSON.EXTRA_TIMERS;
import static com.namkit.namki.novafolio.JSONWakeup.Wakeup_JSON.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int timer = intent.getIntExtra(EXTRA_TIMERS, 0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewTimers = findViewById(R.id.text_view_timer_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(creatorName);
        textViewTimers.setText("등록시간 : "+timer);


    }
}
