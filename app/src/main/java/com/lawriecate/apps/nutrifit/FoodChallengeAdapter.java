package com.lawriecate.apps.nutrifit;

/**
 * Created by lawrie on 18/11/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lawrie on 15/11/2016.
 */

public class FoodChallengeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FoodChallenge> mDataSource;

    public FoodChallengeAdapter(Context context, ArrayList<FoodChallenge> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        FoodChallenge foodChallenge = (FoodChallenge) getItem(i);
        return  foodChallenge.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.list_item_challenge, viewGroup, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.challenge_list_title);
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.challenge_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.challenge_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.challenge_list_thumbnail);

        FoodChallenge foodChallenge = (FoodChallenge) getItem(i);


        titleTextView.setText(foodChallenge.getName());
        subtitleTextView.setText(foodChallenge.getDescription());
//        detailTextView.setText(meal.label);

        Picasso.with(mContext).load(foodChallenge.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        return rowView;
    }
}
