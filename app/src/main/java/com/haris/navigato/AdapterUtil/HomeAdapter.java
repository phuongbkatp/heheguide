package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ObjectUtil.EmptyState;
import com.haris.navigato.ObjectUtil.WeatherDetail;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuLightTextview;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;

/**
 * Created by hp on 5/20/2018.
 */

public abstract class HomeAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> homeArrays = new ArrayList<>();
    ArrayList<Object> object = new ArrayList<>();
    private ArrayList<Object> home = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2, NEARBY_DATA_VIEW = 3, PROGRESS_VIEW = 4;
    private ArrayList<Object> nearbyObject = new ArrayList<>();


    public HomeAdapter(Context context, ArrayList<Object> homeArrays) {
        this.context = context;
        this.homeArrays = homeArrays;
    }

    @Override
    public int getItemViewType(int position) {

        if (homeArrays.get(position) instanceof EmptyState) {
            return PROGRESS_VIEW;
        } else if (position == 0)
            return NO_INTERNET_VIEW;
        else if (position == 1)
            return AVAILABLE_DATA_VIEW;
        else if (position == 2)
            return NEARBY_DATA_VIEW;

        return NO_INTERNET_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_layout, parent, false);
            viewHolder = new WeatherHolder(view);

        } else if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_places_item_layout, parent, false);
            viewHolder = new FeaturePlacesHolder(view);

        } else if (viewType == NEARBY_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_places_item_layout, parent, false);
            viewHolder = new NearbyHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_progress_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof WeatherHolder) {

            WeatherHolder weatherHolder = (WeatherHolder) holder;
            WeatherDetail weatheDetail = (WeatherDetail) homeArrays.get(position);

            Constant.setWeather(weatheDetail.getSummary());

            weatherHolder.txtCity.setText(weatheDetail.getPlaceName());
            weatherHolder.txtTemperature.setText(weatheDetail.getSummary() + " : Temperature " + weatheDetail.getTemperature());


        } else if (holder instanceof FeaturePlacesHolder) {

            final FeaturePlacesHolder featureplacesHolder = (FeaturePlacesHolder) holder;
            object = (ArrayList<Object>) homeArrays.get(position);

            featureplacesHolder.txtTop.setText(Constant.ImportantMessages.top);
            featureplacesHolder.txtPlaces.setText(Constant.ImportantMessages.place);
            featureplacesHolder.txtMore.setText(Constant.ImportantMessages.more);

            featureplacesHolder.placeAdapter = new PlaceAdapter(context, object) {
                @Override
                public void onFeaturePlaceSelect(int featurePlacePosition) {
                    onPlaceSelector(Constant.EVENTS.FEATURE_PLACE, position, featurePlacePosition);
                }
            };
            featureplacesHolder.recyclerViewPlaces.setAdapter(featureplacesHolder.placeAdapter);

            featureplacesHolder.layoutMore.setTag(position);
            featureplacesHolder.layoutMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) featureplacesHolder.layoutMore.getTag();
                    onFeatureMoreView();
                }
            });


        } else if (holder instanceof NearbyHolder) {

            NearbyHolder nearbyHolder = (NearbyHolder) holder;
            nearbyObject = (ArrayList<Object>) homeArrays.get(position);

            nearbyHolder.txtNearbyPlaces.setText(Constant.ImportantMessages.restaurant);
            nearbyHolder.txtNearest.setText(Constant.ImportantMessages.top);


            nearbyHolder.nearbyAdapter = new NearbyAdapter(context, nearbyObject) {
                @Override
                public void onNearBySelector(int nearbyPlacePosition) {
                    onPlaceSelector(Constant.EVENTS.PLACE, position, nearbyPlacePosition);
                }
            };
            nearbyHolder.recyclerViewNearby.setAdapter(nearbyHolder.nearbyAdapter);

        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;

        }


    }

    @Override
    public int getItemCount() {
        return homeArrays.size();
    }

    public abstract void onPlaceSelector(Constant.EVENTS events, int parentPosition, int childPosition);

    public abstract void onFeatureMoreView();

    protected class WeatherHolder extends RecyclerView.ViewHolder {

        private LemonMilkTextview txtCity;
        private UbuntuRegularTextview txtTemperature;


        public WeatherHolder(View view) {
            super(view);

            txtCity = (LemonMilkTextview) view.findViewById(R.id.txt_city);
            txtTemperature = (UbuntuRegularTextview) view.findViewById(R.id.txt_temperature);

        }
    }

    protected class FeaturePlacesHolder extends RecyclerView.ViewHolder {
        private UbuntuLightTextview txtTop;
        private UbuntuMediumTextview txtPlaces;
        private LinearLayout layoutMore;
        private UbuntuMediumTextview txtMore;
        private RecyclerView recyclerViewPlaces;
        private StaggeredGridLayoutManager layoutManager;
        private PlaceAdapter placeAdapter;

        public FeaturePlacesHolder(View view) {
            super(view);

            layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);

            txtTop = (UbuntuLightTextview) view.findViewById(R.id.txt_top);
            txtPlaces = (UbuntuMediumTextview) view.findViewById(R.id.txt_places);
            layoutMore = (LinearLayout) view.findViewById(R.id.layout_more);
            txtMore = (UbuntuMediumTextview) view.findViewById(R.id.txt_more);

            recyclerViewPlaces = (RecyclerView) view.findViewById(R.id.recycler_view_places);
            recyclerViewPlaces.setLayoutManager(layoutManager);


        }
    }

    protected class NearbyHolder extends RecyclerView.ViewHolder {
        private UbuntuLightTextview txtNearest;
        private UbuntuMediumTextview txtNearbyPlaces;
        private RecyclerView recyclerViewNearby;
        private GridLayoutManager layoutManager;
        private NearbyAdapter nearbyAdapter;

        public NearbyHolder(View view) {
            super(view);

            layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);

            txtNearest = (UbuntuLightTextview) view.findViewById(R.id.txt_nearest);
            txtNearbyPlaces = (UbuntuMediumTextview) view.findViewById(R.id.txt_nearby_places);

            recyclerViewNearby = (RecyclerView) view.findViewById(R.id.recycler_view_nearby);
            recyclerViewNearby.setLayoutManager(layoutManager);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public EmptyHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);


        }
    }


}