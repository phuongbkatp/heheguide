package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.JsonUtil.NearbyUtil.Photo;
import com.haris.navigato.MyApplication;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuLightTextview;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

/**
 * Created by hp on 5/22/2018.
 */

public abstract class NearbyAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> placeArrays = new ArrayList<>();
    private ArrayList<Object> place = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private boolean isSearchPlace;
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2, TOURISM_VIEW = 3;
    private String photoRef;
    private boolean isTourism;


    public NearbyAdapter(Context context, ArrayList<Object> placeArrays) {
        this.context = context;
        this.placeArrays = placeArrays;
    }

    public NearbyAdapter(Context context, ArrayList<Object> placeArrays, boolean isSearchPlace) {
        this.context = context;
        this.placeArrays = placeArrays;
        this.isSearchPlace = isSearchPlace;
    }

    public NearbyAdapter(Context context, ArrayList<Object> placeArrays, boolean isSearchPlace, boolean isTourism) {
        this.context = context;
        this.placeArrays = placeArrays;
        this.isSearchPlace = isSearchPlace;
        this.isTourism = isTourism;
    }

    @Override
    public int getItemViewType(int position) {

        if (isSearchPlace)
            return NO_INTERNET_VIEW;
        else if (isTourism)
            return TOURISM_VIEW;
        else if (placeArrays.get(position) instanceof NearbyPlaces)
            return AVAILABLE_DATA_VIEW;
        else

            return AVAILABLE_DATA_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_nearby_item_layout, parent, false);
            viewHolder = new PlaceHolder(view);

        } else if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item_layout, parent, false);
            viewHolder = new PlaceHolder(view);

        } else if (viewType == TOURISM_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_tourism_item_layout, parent, false);
            viewHolder = new PlaceHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof PlaceHolder) {

            final PlaceHolder placeHolder = (PlaceHolder) holder;
            NearbyPlaces placeData = (NearbyPlaces) placeArrays.get(position);

            if (placeData.getPhotoArrayList().size() > 0) {

                photoRef = (placeData.getPhotoArrayList().get(0)) instanceof Photo ? ((Photo) (placeData.getPhotoArrayList().get(0))).getPhotoReference()
                        : ((com.haris.navigato.JsonUtil.TopPlaceUtil.Photo) (placeData.getPhotoArrayList().get(0))).getPhotoReference();


                // TODO: 6/14/2018 Picture Url Correction

                String pictureUrl = String.format(Constant.ServerInformation.GOOGLE_MAP_PHOTO_REFERENCES, photoRef);
                // TODO: 6/14/2018 Picasso Use
                MyApplication.getPicasso().load(pictureUrl).placeholder(Constant.PlaceHolder.place_holder).into(placeHolder.imagePlace);


                float rating = Utility.isEmptyString(placeData.getPlaceRating()) ? 2f : Float.parseFloat(placeData.getPlaceRating());

                placeHolder.txtPlaceName.setText(placeData.getPlaceName());
                placeHolder.txtPlaceAddres.setText(placeData.getPlaceAddress());
                placeHolder.txtRating.setText(placeData.getPlaceRating() + " Rating");
                placeHolder.rating.setRating(rating);
                placeHolder.cardviewPlace.setTag(position);
                placeHolder.cardviewPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNearBySelector((Integer) placeHolder.cardviewPlace.getTag());
                    }
                });

            }

        }


    }

    @Override
    public int getItemCount() {
        return placeArrays.size();
    }

    public abstract void onNearBySelector(int nearbyPlacePosition);

    protected class PlaceHolder extends RecyclerView.ViewHolder {
        private CardView cardviewPlace;
        private ImageView imagePlace;
        private UbuntuMediumTextview txtPlaceName;
        private UbuntuLightTextview txtPlaceAddres;
        private AppCompatRatingBar rating;
        private UbuntuLightTextview txtRating;

        public PlaceHolder(View view) {
            super(view);

            imagePlace = (ImageView) view.findViewById(R.id.image_place);
            txtPlaceName = (UbuntuMediumTextview) view.findViewById(R.id.txt_place_name);
            txtPlaceAddres = (UbuntuLightTextview) view.findViewById(R.id.txt_place_addres);
            rating = (AppCompatRatingBar) view.findViewById(R.id.rating);
            txtRating = (UbuntuLightTextview) view.findViewById(R.id.txt_rating);
            cardviewPlace = (CardView) view.findViewById(R.id.cardview_place);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }


}

