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

public class FitnessChallengeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<FitnessChallenge> mDataSource;

    public FitnessChallengeAdapter(Context context, ArrayList<FitnessChallenge> items) {
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
        FitnessChallenge fitnessChallenge = (FitnessChallenge) getItem(i);
        return  fitnessChallenge.getId();
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

        FitnessChallenge fitnessChallenge = (FitnessChallenge) getItem(i);


        titleTextView.setText(fitnessChallenge.getName());
        subtitleTextView.setText(fitnessChallenge.getDescription());
//        detailTextView.setText(meal.label);

        Picasso.with(mContext).load(fitnessChallenge.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        return rowView;
    }
}
