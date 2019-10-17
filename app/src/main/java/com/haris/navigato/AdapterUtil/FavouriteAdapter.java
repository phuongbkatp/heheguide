package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.MyApplication;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.EmptyState;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuLightTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by hp on 5/31/2018.
 */

public abstract class FavouriteAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> favouriteArrays = new ArrayList<>();
    private ArrayList<Object> favourite = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2;


    public FavouriteAdapter(Context context, ArrayList<Object> favouriteArrays) {
        this.context = context;
        this.favouriteArrays = favouriteArrays;
    }

    @Override
    public int getItemViewType(int position) {
        if (favouriteArrays.get(position) instanceof EmptyState)
            return NO_INTERNET_VIEW;
        else if (favouriteArrays.get(position) instanceof DatabaseQueryObject)
            return AVAILABLE_DATA_VIEW;

        return NO_INTERNET_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_state_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item_layout, parent, false);
            viewHolder = new FavouriteHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof FavouriteHolder) {

            final FavouriteHolder favouriteHolder = (FavouriteHolder) holder;
            DatabaseQueryObject databaseQueryObject = (DatabaseQueryObject) favouriteArrays.get(position);


            float rating = Utility.isEmptyString(databaseQueryObject.getPlaceRating()) ? 2f : Float.parseFloat(databaseQueryObject.getPlaceRating());


            favouriteHolder.txtPlaceName.setText(databaseQueryObject.getPlaceName());
            favouriteHolder.txtPlaceDetail.setText(Utility.placeDescriptionCreator(new PlaceParameter("0", databaseQueryObject.getPlaceType(), databaseQueryObject.getPlaceName(), "", databaseQueryObject.getPlaceRating(), databaseQueryObject.getPriceLevel())));
            favouriteHolder.rating.setRating(rating);

            // TODO: 6/14/2018 Picture Url Correction At Favourite
            ////String pictureUrl = String.format(Constant.ServerInformation.GOOGLE_MAP_PHOTO_REFERENCES, databaseQueryObject.getPlacePicture());
            if (!Utility.isEmptyString(databaseQueryObject.getPlacePicture())) {
                if (!(favouriteHolder.imageFavourite.getScaleType() == ImageView.ScaleType.CENTER_CROP))
                    favouriteHolder.imageFavourite.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String pictureUrl = String.format(Constant.ServerInformation.GOOGLE_MAP_PHOTO_REFERENCES, databaseQueryObject.getPlacePicture());
                // TODO: 6/14/2018 Piccass Library
                MyApplication.getPicasso().load(pictureUrl).placeholder(R.drawable.app_icon).into(favouriteHolder.imageFavourite);
            } else {
                favouriteHolder.imageFavourite.setScaleType(ImageView.ScaleType.CENTER_CROP);
                favouriteHolder.imageFavourite.setImageResource(R.drawable.no_picture);
            }
            ///Picasso.with(context).load(pictureUrl).into(favouriteHolder.imageFavourite);
            //Glide.with(context).load(pictureUrl).into(favouriteHolder.imageFavourite);

            favouriteHolder.cardviewFavourite.setTag(position);

            favouriteHolder.cardviewFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) favouriteHolder.cardviewFavourite.getTag();
                    onFavouriteClick(pos);

                }
            });


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            ErrorObject errorObject = (ErrorObject) favouriteArrays.get(position);

            emptyHolder.txtErrorTitle.setText(errorObject.getTitle());
            emptyHolder.txtErrorTagline.setText(errorObject.getTagline());
            emptyHolder.imageEmpty.setImageResource(errorObject.getErrorImage());


        }


    }

    @Override
    public int getItemCount() {
        return favouriteArrays.size();
    }


    public abstract void onFavouriteClick(int position);


    protected class FavouriteHolder extends RecyclerView.ViewHolder {
        private CardView cardviewFavourite;
        private RoundedImageView imageFavourite;
        private LemonMilkTextview txtPlaceName;
        private AppCompatRatingBar rating;
        private UbuntuLightTextview txtPlaceDetail;

        public FavouriteHolder(View view) {
            super(view);
            imageFavourite = (RoundedImageView) view.findViewById(R.id.image_favourite);
            txtPlaceName = (LemonMilkTextview) view.findViewById(R.id.txt_place_name);
            rating = (AppCompatRatingBar) view.findViewById(R.id.rating);
            txtPlaceDetail = (UbuntuLightTextview) view.findViewById(R.id.txt_place_detail);
            cardviewFavourite = (CardView) view.findViewById(R.id.cardview_favourite);
        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutNotesState;
        private ImageView imageEmpty;
        private TextView txtErrorTitle;
        private UbuntuRegularTextview txtErrorTagline;

        public EmptyHolder(View view) {
            super(view);
            layoutNotesState = (LinearLayout) view.findViewById(R.id.layout_notes_state);
            imageEmpty = (ImageView) view.findViewById(R.id.image_empty);
            txtErrorTitle = (TextView) view.findViewById(R.id.txt_error_title);
            txtErrorTagline = (UbuntuRegularTextview) view.findViewById(R.id.txt_error_tagline);
        }
    }


}
