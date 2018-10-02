package com.namkit.namki.novafolio.JSONWakeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.namkit.namki.novafolio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Wakeup_JSON extends AppCompatActivity implements WakeupAdapter_JSON.OnItemClickListener{
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_TIMERS = "timers";

    private RecyclerView mRecyclerView;
    private WakeupAdapter_JSON mWakeupAdapter;
    private ArrayList<WakeupItem_JSON> mWakeupList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup__json);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        mWakeupList = new ArrayList<>();
        
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
            @Override
                    public void onResponse(JSONObject response){
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for(int i =0; i<jsonArray.length(); i++){
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String creatorName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int addTimer = hit.getInt("likes");

                        mWakeupList.add(new WakeupItem_JSON(imageUrl, creatorName, addTimer));
                    }
                    mWakeupAdapter = new WakeupAdapter_JSON(Wakeup_JSON.this, mWakeupList);
                    mRecyclerView.setAdapter(mWakeupAdapter);
                    mWakeupAdapter.setOnItemClickListener(Wakeup_JSON.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
                    @Override
            public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        WakeupItem_JSON clickItem = mWakeupList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickItem.getmCreator());
        detailIntent.putExtra(EXTRA_TIMERS, clickItem.getmTimes());

        startActivity(detailIntent);
    }
}
