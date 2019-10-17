package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.JsonUtil.NearbyUtil.Photo;
import com.haris.navigato.MyApplication;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuLightTextview;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;

/**
 * Created by hp on 6/13/2018.
 */

public abstract class TourismAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> homeArrays = new ArrayList<>();
    private ArrayList<Object> object = new ArrayList<>();
    private int SEARCH_VIEW = 1, EXPLORE_VIEW = 2, TO_DO_VIEW = 3, PROGRESS_VIEW = 4;
    private ArrayList<Object> nearbyObject = new ArrayList<>();
    private String photoRef;


    public TourismAdapter(Context context, ArrayList<Object> homeArrays) {
        this.context = context;
        this.homeArrays = homeArrays;
    }

    @Override
    public int getItemViewType(int position) {

        /*if (homeArrays.get(position) instanceof EmptyState) {
            return PROGRESS_VIEW;
        } else*/
        if (position == 0)
            return SEARCH_VIEW;
        else if (position == 1)
            return EXPLORE_VIEW;
        else if (homeArrays.get(position) instanceof NearbyPlaces)
            return TO_DO_VIEW;

        else
            return PROGRESS_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == SEARCH_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false);
            viewHolder = new SearchHolder(view);

        } else if (viewType == TO_DO_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_activities_item_layout, parent, false);
            viewHolder = new FavouriteHolder(view);

        } else if (viewType == EXPLORE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item_layout, parent, false);
            viewHolder = new ExploreHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_progress_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof SearchHolder) {

            SearchHolder searchHolder = (SearchHolder) holder;
            searchHolder.cardviewSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearch();
                }
            });


        } else if (holder instanceof FavouriteHolder) {


            final FavouriteHolder favouriteHolder = (FavouriteHolder) holder;
            NearbyPlaces databaseQueryObject = (NearbyPlaces) homeArrays.get(position);


            float rating = Utility.isEmptyString(databaseQueryObject.getPlaceRating()) ? 2f : Float.parseFloat(databaseQueryObject.getPlaceRating());


            favouriteHolder.txtPlaceName.setText(databaseQueryObject.getPlaceName());
            favouriteHolder.txtPlaceDetail.setText(Utility.placeDescriptionCreator(new PlaceParameter("0", databaseQueryObject.getPlaceType(), databaseQueryObject.getPlaceName(), "", databaseQueryObject.getPlaceRating(), databaseQueryObject.getPriceLevel())));
            favouriteHolder.rating.setRating(rating);


            photoRef = (databaseQueryObject.getPhotoArrayList().get(0)) instanceof Photo ? ((Photo) (databaseQueryObject.getPhotoArrayList().get(0))).getPhotoReference()
                    : ((com.haris.navigato.JsonUtil.TopPlaceUtil.Photo) (databaseQueryObject.getPhotoArrayList().get(0))).getPhotoReference();

            // TODO: 6/14/2018 Toursim Adapter Picture Correction
            String pictureUrl = String.format(Constant.ServerInformation.GOOGLE_MAP_PHOTO_REFERENCES, photoRef);

            favouriteHolder.txtRating.setText("(" + rating + ")");

            // TODO: 6/14/2018 Picasso Use
            MyApplication.getPicasso().load(pictureUrl).placeholder(Constant.PlaceHolder.place_holder).into(favouriteHolder.imageFavourite);

            favouriteHolder.cardviewFavourite.setTag(position);

            favouriteHolder.cardviewFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) favouriteHolder.cardviewFavourite.getTag();
                    onTouristAttraction(pos);

                }
            });


        } else if (holder instanceof ExploreHolder) {


            final ExploreHolder featureplacesHolder = (ExploreHolder) holder;
            object = (ArrayList<Object>) homeArrays.get(position);


            featureplacesHolder.txtNearbyPlaces.setText("Places");
            featureplacesHolder.txtNearest.setText("Explore Top");

            featureplacesHolder.txtActivities.setText("Attractions");
            featureplacesHolder.txtRecommended.setText("Tourist");
            ////featureplacesHolder.txtNearest.setVisibility(View.GONE);

            featureplacesHolder.placeAdapter = new PlaceAdapter(context, object, true) {
                @Override
                public void onFeaturePlaceSelect(int featurePlacePosition) {
                    onPlaceSelector(Constant.EVENTS.PLACE, position, featurePlacePosition);
                }
            };
            featureplacesHolder.recyclerViewPlaces.setAdapter(featureplacesHolder.placeAdapter);



          /*  featureplacesHolder.layoutMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) featureplacesHolder.layoutMore.getTag();
                    onFeatureMoreView();
                }
            });*/


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;

        }


    }

    @Override
    public int getItemCount() {
        return homeArrays.size();
    }

    public abstract void onPlaceSelector(Constant.EVENTS events, int parentPosition, int childPosition);

    public abstract void onSearch();

    public abstract void onTouristAttraction(int position);


    protected class SearchHolder extends RecyclerView.ViewHolder {
        private CardView cardviewSearch;

        public SearchHolder(View view) {
            super(view);
            cardviewSearch = (CardView) view.findViewById(R.id.cardview_search);
        }
    }

    protected class ExploreHolder extends RecyclerView.ViewHolder {
        private UbuntuLightTextview txtNearest;
        private UbuntuMediumTextview txtNearbyPlaces;
        private RecyclerView recyclerViewPlaces;
        private StaggeredGridLayoutManager layoutManager;
        private PlaceAdapter placeAdapter;
        private UbuntuMediumTextview txtActivities;
        private UbuntuLightTextview txtRecommended;

        public ExploreHolder(View view) {
            super(view);

            ///layoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false);
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);

            txtNearest = (UbuntuLightTextview) view.findViewById(R.id.txt_nearest);
            txtNearbyPlaces = (UbuntuMediumTextview) view.findViewById(R.id.txt_nearby_places);
            txtRecommended = (UbuntuLightTextview) view.findViewById(R.id.txt_recommended);
            txtActivities = (UbuntuMediumTextview) view.findViewById(R.id.txt_activities);

            recyclerViewPlaces = (RecyclerView) view.findViewById(R.id.recycler_view_explore);
            recyclerViewPlaces.setLayoutManager(layoutManager);


        }
    }

    protected class FavouriteHolder extends RecyclerView.ViewHolder {
        private CardView cardviewFavourite;
        private RoundedImageView imageFavourite;
        private LemonMilkTextview txtPlaceName;
        private AppCompatRatingBar rating;
        private UbuntuLightTextview txtPlaceDetail;
        private TextView txtRating;

        public FavouriteHolder(View view) {
            super(view);

            imageFavourite = (RoundedImageView) view.findViewById(R.id.image_favourite);
            txtPlaceName = (LemonMilkTextview) view.findViewById(R.id.txt_place_name);
            rating = (AppCompatRatingBar) view.findViewById(R.id.rating);
            txtPlaceDetail = (UbuntuLightTextview) view.findViewById(R.id.txt_place_detail);
            cardviewFavourite = (CardView) view.findViewById(R.id.cardview_favourite);
            txtRating = view.findViewById(R.id.txt_rating);


        }
    }

    protected class ToDoHolder extends RecyclerView.ViewHolder {

        private UbuntuLightTextview txtTop;
        private UbuntuMediumTextview txtPlaces;
        private LinearLayout layoutMore;
        private UbuntuMediumTextview txtMore;
        private RecyclerView recyclerViewNearby;
        private GridLayoutManager layoutManager;
        private NearbyAdapter nearbyAdapter;

        public ToDoHolder(View view) {
            super(view);

            layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);


            txtTop = (UbuntuLightTextview) view.findViewById(R.id.txt_top);
            txtPlaces = (UbuntuMediumTextview) view.findViewById(R.id.txt_places);
            layoutMore = (LinearLayout) view.findViewById(R.id.layout_more);
            txtMore = (UbuntuMediumTextview) view.findViewById(R.id.txt_more);

            recyclerViewNearby = (RecyclerView) view.findViewById(R.id.recycler_view_to_do);
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
