package com.namkit.namki.novafolio.JSONWakeup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namkit.namki.novafolio.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by namki on 2018-03-22.
 */

public class WakeupAdapter_JSON extends RecyclerView.Adapter<WakeupAdapter_JSON.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<WakeupItem_JSON> mWakeupList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public WakeupAdapter_JSON(Context mContext, ArrayList<WakeupItem_JSON> mWakeupList) {
        this.mContext = mContext;
        this.mWakeupList = mWakeupList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.wakeup_itemjson, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        WakeupItem_JSON currentItem = mWakeupList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String creatorName = currentItem.getmCreator();
        int addTime = currentItem.getmTimes();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewTime.setText("작성시간"+addTime);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mWakeupList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewTime;
        public ExampleViewHolder(View itemview){
            super(itemview);
            mImageView = itemview.findViewById(R.id.image_view);
            mTextViewCreator = itemview.findViewById(R.id.text_view_creator);
            mTextViewTime = itemview.findViewById(R.id.text_view_times);



            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int positon = getAdapterPosition();
                        if(positon != RecyclerView.NO_POSITION){
                            mListener.onItemClick(positon);
                        }
                    }
                }
            });
        }
    }
}
