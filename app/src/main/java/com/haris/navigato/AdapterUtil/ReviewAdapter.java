package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haris.navigato.JsonUtil.PlaceDetailUtil.Review;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.ExpandableTextView;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hp on 5/23/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> reviewArrays = new ArrayList<>();
    private ArrayList<Object> Review = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2;


    public ReviewAdapter(Context context, ArrayList<Object> ReviewArrays) {
        this.context = context;
        this.reviewArrays = ReviewArrays;
    }

    @Override
    public int getItemViewType(int position) {
       /* if (reviewArrays.get(position) instanceof EmptyState)
            return NO_INTERNET_VIEW;
        else*/
        if (reviewArrays.get(position) instanceof Review)
            return AVAILABLE_DATA_VIEW;

        return NO_INTERNET_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

      /*  if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_state_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else*/
        if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail_item_layout, parent, false);
            viewHolder = new ReviewHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ReviewHolder) {

            ReviewHolder emptyHolder = (ReviewHolder) holder;
            Review emptyState = (Review) reviewArrays.get(position);

            emptyHolder.rating.setRating(emptyState.getRating());
            emptyHolder.txtPersonName.setText(emptyState.getAuthorName());
            emptyHolder.txtReviewDesc.setText(emptyState.getText());
            Picasso.with(context).load(emptyState.getProfilePhotoUrl()).into(emptyHolder.imagePeople);

        }


    }

    @Override
    public int getItemCount() {
        return reviewArrays.size();
    }


    protected class ReviewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imagePeople;
        private UbuntuMediumTextview txtPersonName;
        private AppCompatRatingBar rating;
        private ExpandableTextView txtReviewDesc;

        public ReviewHolder(View view) {
            super(view);
            imagePeople = (RoundedImageView) view.findViewById(R.id.image_people);
            txtPersonName = (UbuntuMediumTextview) view.findViewById(R.id.txt_person_name);
            rating = (AppCompatRatingBar) view.findViewById(R.id.rating);
            txtReviewDesc = (ExpandableTextView) view.findViewById(R.id.txt_review_desc);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }


}