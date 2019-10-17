package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.JsonUtil.PlaceDetailUtil.Photo;
import com.haris.navigato.JsonUtil.PlaceDetailUtil.Review;
import com.haris.navigato.MyApplication;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hp on 5/22/2018.
 */

public class PlaceDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> homeArrays = new ArrayList<>();
    ArrayList<Object> object = new ArrayList<>();
    private ArrayList<Object> home = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2, NEARBY_DATA_VIEW = 3, SINGLE_IMAGE_VIEW = 4, NO_REVIEWS = 5, NO_PICTURE = 6;
    private ArrayList<Object> nearbyObject = new ArrayList<>();
    private boolean isReview;


    public PlaceDetailAdapter(Context context, ArrayList<Object> homeArrays, boolean isReview) {
        this.context = context;
        this.homeArrays = homeArrays;
        this.isReview = isReview;
    }

    @Override
    public int getItemViewType(int position) {


        if (!isReview) {
            if (getItemCount() == 1 && !(homeArrays.get(position) instanceof ErrorObject))
                return SINGLE_IMAGE_VIEW;
            else if (getItemCount() > 1)
                return NO_INTERNET_VIEW;
            else if (getItemCount() == 1 && homeArrays.get(position) instanceof ErrorObject)
                return NO_PICTURE;
        } else {

            if (position < 4 && homeArrays.get(position) instanceof Review)
                return AVAILABLE_DATA_VIEW;
            else if (position >= 4 && homeArrays.get(position) instanceof Review)
                return NEARBY_DATA_VIEW;
            else if (homeArrays.get(position) instanceof ErrorObject)
                return NO_REVIEWS;


        }

        return NO_INTERNET_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_picture_item_layout, parent, false);
            viewHolder = new PlacePictureHolder(view);

        } else if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_picture_item_layout, parent, false);
            viewHolder = new ReviewPlaceHolder(view);

        } else if (viewType == NEARBY_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_text_item_layout, parent, false);
            viewHolder = new ReviewTextPlaceHolder(view);

        } else if (viewType == SINGLE_IMAGE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_item_layout, parent, false);
            viewHolder = new PlacePictureHolder(view);

        } else if (viewType == NO_REVIEWS) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_reviews_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == NO_PICTURE) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_picture_item_layout, parent, false);
            viewHolder = new NoPictureHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof PlacePictureHolder) {

            PlacePictureHolder placePictureHolder = (PlacePictureHolder) holder;
            Photo photo = (Photo) homeArrays.get(position);
            // TODO: 6/14/2018 Place Detail Adapter Picture Correction
            String pictureUrl = String.format(Constant.ServerInformation.GOOGLE_MAP_PHOTO_REFERENCES, photo.getPhotoReference());
            Utility.Logger("Picture Url", pictureUrl);

            // TODO: 6/14/2018 Picasso Use
            MyApplication.getPicasso().load(pictureUrl).placeholder(Constant.PlaceHolder.place_holder).into(placePictureHolder.roundedImageView);



        } else if (holder instanceof ReviewPlaceHolder) {


            ReviewPlaceHolder placePictureHolder = (ReviewPlaceHolder) holder;
            Review photo = (Review) homeArrays.get(position);

            Picasso.with(context).load(photo.getProfilePhotoUrl()).into(placePictureHolder.imageProfile);


        } else if (holder instanceof ReviewTextPlaceHolder) {

            ReviewTextPlaceHolder placePictureHolder = (ReviewTextPlaceHolder) holder;
            placePictureHolder.txtReviewCount.setText((getItemCount() - 1) + "+");

        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            ErrorObject errorObject = (ErrorObject) homeArrays.get(position);

            emptyHolder.txtNoReview.setText(errorObject.getError());


        } else if (holder instanceof NoPictureHolder) {

            NoPictureHolder noPictureHolder = (NoPictureHolder) holder;
            ErrorObject errorObject = (ErrorObject) homeArrays.get(position);

            noPictureHolder.txtErrorTitle.setText(errorObject.getTitle());
            noPictureHolder.txtErrorTagline.setText(errorObject.getTagline());
            noPictureHolder.imageNoPic.setImageResource(errorObject.getErrorImage());

        }


    }

    @Override
    public int getItemCount() {
        if (!isReview)
            return homeArrays.size();
        else {
            if (homeArrays.size() > 4)
                return homeArrays.size() - (homeArrays.size() - 5);
            else
                return homeArrays.size();
        }
    }


    protected class PlacePictureHolder extends RecyclerView.ViewHolder {
        private RoundedImageView roundedImageView;

        public PlacePictureHolder(View view) {
            super(view);

            roundedImageView = (RoundedImageView) view.findViewById(R.id.image_place);


        }
    }

    protected class ReviewPlaceHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageProfile;

        public ReviewPlaceHolder(View view) {
            super(view);

            imageProfile = (RoundedImageView) view.findViewById(R.id.image_people);

        }
    }

    protected class ReviewTextPlaceHolder extends RecyclerView.ViewHolder {
        private LemonMilkTextview txtReviewCount;

        public ReviewTextPlaceHolder(View view) {
            super(view);

            txtReviewCount = (LemonMilkTextview) view.findViewById(R.id.txt_review_count);

        }
    }

    protected class NoPictureHolder extends RecyclerView.ViewHolder {
        private TextView txtErrorTagline;
        private TextView txtErrorTitle;
        private ImageView imageNoPic;

        public NoPictureHolder(View view) {
            super(view);

            txtErrorTagline = (TextView) view.findViewById(R.id.txt_error_tagline);
            txtErrorTitle = (TextView) view.findViewById(R.id.txt_error_title);
            imageNoPic = (ImageView) view.findViewById(R.id.image_no_pic);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private final UbuntuRegularTextview txtNoReview;

        public EmptyHolder(View view) {
            super(view);

            txtNoReview = (UbuntuRegularTextview) view.findViewById(R.id.txt_no_review);

        }
    }


}