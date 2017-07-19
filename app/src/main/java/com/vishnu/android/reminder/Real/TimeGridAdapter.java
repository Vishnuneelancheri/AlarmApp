package com.vishnu.android.reminder.Real;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnu.android.reminder.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by vishnu on 7/18/2017 - 10:50 AM.
 */

public class TimeGridAdapter extends BaseAdapter{
    private Context     mContext;
    private ArrayList<AlarmModel>   mTimeList;
    private OnClickItemDeleted mOnClickItemDeleted;
    private Calendar mCalender;
    private AlarmModel mAlarmModel;
    public TimeGridAdapter(Context context, ArrayList<AlarmModel> time, OnClickItemDeleted onClickItemDeleted){
        mContext    =   context;
        mTimeList    =   time;
        mOnClickItemDeleted =   onClickItemDeleted;
    }

    @Override
    public int getCount(){
        return mTimeList.size();
    }

    @Override
    public Objects getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View converterView, ViewGroup parent){
        View view;
        LayoutInflater inflater =   (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (converterView   ==  null){
            view                =   new View(mContext);
            view                =   inflater.inflate(R.layout.time_grid_item, null);
            TextView textView   =   (TextView)  view.findViewById(R.id.text_view_show_time);
            ImageView imageView =   (ImageView) view.findViewById(R.id.image_view_close);
            if (position %2 == 0)
                view.setBackgroundColor(R.color.black);
            mAlarmModel         =   mTimeList.get(position);
            mCalender           =   mAlarmModel.getMcalender();
            if (!mAlarmModel.ismFlag()){
                /*textView.setVisibility(View.GONE);*/
                imageView.setVisibility(View.GONE);
                view.setBackgroundResource(R.mipmap.add_new);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            else {
                view.setBackgroundResource(R.drawable.grid_time_item_border);
                textView.setText(Integer.toString(mCalender.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(mCalender.get(Calendar.MINUTE)));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickItemDeleted.changeItem(position);
                    }
                });
            }
            RelativeLayout.LayoutParams layoutParams    =   new RelativeLayout.LayoutParams(
                    230, 200
            );
            view.setLayoutParams(layoutParams);

        }
        else
            view                =   (View) converterView;
        return view;
    }

    public interface OnClickItemDeleted{
        void changeItem(int position);
    }

}
