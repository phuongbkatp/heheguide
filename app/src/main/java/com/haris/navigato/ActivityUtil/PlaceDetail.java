package com.haris.navigato.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.haris.navigato.AdapterUtil.PlaceDetailAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.FontUtil.Font;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;
import com.haris.navigato.JsonUtil.NearbyUtil.Photo;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuBoldTextview;
import com.haris.navigato.TextviewUtil.UbuntuLightTextview;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

public class PlaceDetail extends AppCompatActivity implements DirectoryCallback, View.OnClickListener, View.OnLongClickListener, OnMapReadyCallback {
    private RecyclerView recyclerViewImages;
    private LemonMilkTextview txtPlaceName;
    private UbuntuMediumTextview txtDistance;
    private UbuntuLightTextview txtPlaceDetail;
    private AppCompatRatingBar rating;
    private UbuntuMediumTextview labelPriceLevel;
    private UbuntuRegularTextview txtRating;
    private UbuntuBoldTextview txtPriceLevel;
    private TextView txtAddress;
    private TextView txtUrl;
    private UbuntuLightTextview txtPhone;
    private UbuntuLightTextview txtDirections;
    private RecyclerView recyclerViewReviews;
    private NearbyPlaces object;
    private String priceLevel;
    private PlaceDetailAdapter placeDetailAdapter;
    private PlaceDetailAdapter reviewAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager reviewLayoutManager;
    private Management management;
    private String mapDirectionUrl;
    private LinearLayout layoutSeeAll;
    private ArrayList<Object> review = new ArrayList<>();
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageFvrt;
    private MapView mapView;
    private GoogleMap googleMap;
    private LinearLayout layoutDirection;
    private String placeRating;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String photoRef;
    private boolean isSearchedPlace;
    private String searchPlaceId;
    private double latitude = 0.0;
    private double longitude;
    private NearbyPlaces nearbyPlace;
    private ImageView imageShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        getIntentData();  //Get Intent Data
        initUI(savedInstanceState); //Initialize UI
        initAD();  ///Initialize Admob Banner Ad


    }


    /**
     * <p>It is used to receive Intent Data </p>
     */
    private void getIntentData() {

        object = (NearbyPlaces) getIntent().getSerializableExtra(Constant.IntentKey.PLACE_DETAIL);
        isSearchedPlace = getIntent().getBooleanExtra(Constant.IntentKey.IS_SEARCHED_PLACE, false);
        searchPlaceId = getIntent().getStringExtra(Constant.IntentKey.SEARCH_PLACE_ID);

    }


    /**
     * <p>It initialize the UI & send request to Server</p>
     */
    private void initUI(Bundle savedInstanceState) {

        if (!isSearchedPlace)
            searchPlaceId = object.getPlaceId();

        management = new Management(this);
        management.sendServerRequest(Constant.REQUEST.PLACE_DETAIL, new PlaceParameter(searchPlaceId, null, null, null, null, null, this));

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.PLACE_DETAIL_TEXT);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        imageShare = findViewById(R.id.image_share);
        imageShare.setVisibility(View.VISIBLE);


        imageFvrt = (ImageView) findViewById(R.id.image_fvrt);

        layoutDirection = (LinearLayout) findViewById(R.id.layout_direction);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        txtPlaceName = (LemonMilkTextview) findViewById(R.id.txt_place_name);
        txtDistance = (UbuntuMediumTextview) findViewById(R.id.txt_distance);
        txtPlaceDetail = (UbuntuLightTextview) findViewById(R.id.txt_place_detail);
        rating = (AppCompatRatingBar) findViewById(R.id.rating);
        labelPriceLevel = (UbuntuMediumTextview) findViewById(R.id.label_price_level);
        txtPriceLevel = (UbuntuBoldTextview) findViewById(R.id.txt_price_level);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtPhone = (UbuntuLightTextview) findViewById(R.id.txt_phone);
        txtDirections = (UbuntuLightTextview) findViewById(R.id.txt_directions);
        txtUrl = (TextView) findViewById(R.id.txt_link);
        txtRating = (UbuntuRegularTextview) findViewById(R.id.txt_rating);
        layoutSeeAll = (LinearLayout) findViewById(R.id.layout_see_all);

        txtAddress.setTypeface(Font.ubuntu_light_font(getApplicationContext()));

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        recyclerViewImages = (RecyclerView) findViewById(R.id.recycler_view_images);
        recyclerViewImages.setLayoutManager(gridLayoutManager);


        recyclerViewReviews = (RecyclerView) findViewById(R.id.recycler_view_reviews);
        recyclerViewReviews.setLayoutManager(reviewLayoutManager);


        labelPriceLevel.setText(Constant.ImportantMessages.price_level_label);

        if (!isSearchedPlace) {

            populateDate(object);

        }

        objectArrayList.addAll(management.getDataFromDb(Constant.DATABASE_EVENT.SINGLE, Constant.DATABASE_FUNCTION.FAVOURITE, new DatabaseQueryObject(searchPlaceId, 0), null));
        if (objectArrayList.size() > 0)
            imageFvrt.setImageResource(R.drawable.bookmarked);
        else {
            imageFvrt.setImageResource(R.drawable.not_bookmark);
        }


        layoutSeeAll.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        layoutDirection.setOnClickListener(this);
        imageFvrt.setOnClickListener(this);
        imageShare.setOnClickListener(this);
        txtUrl.setOnClickListener(this::onClick);
        txtPhone.setOnClickListener(this::onClick);
        txtAddress.setOnLongClickListener(this);
        mapView.getMapAsync(this);

    }


    /**
     * <p>It is used to popular data </p>
     *
     * @param object
     */
    private void populateDate(NearbyPlaces object) {

        priceLevel = Integer.parseInt(object.getPriceLevel()) == 1 ? "$" : Integer.parseInt(object.getPriceLevel()) == 2 ? "$$" : Integer.parseInt(object.getPriceLevel()) == 3 ?
                "$$$" : Integer.parseInt(object.getPriceLevel()) == 4 ? "$$$$" : "$$";

        placeRating = Utility.isEmptyString(object.getPlaceRating()) ? "2" : object.getPlaceRating();

        txtPlaceName.setText(object.getPlaceName());


        //txtDistance.setText(Utility.convertMeterIntoKm(String.valueOf(Utility.distance(Utility.DoubleToFloat(Constant.getLatitude()), Utility.DoubleToFloat(Constant.getLongitude()), Utility.DoubleToFloat(object.getLatitude()), Utility.DoubleToFloat(object.getLongitude())))) + " " + Constant.ImportantMessages.miles + " " + Constant.ImportantMessages.nearby);
        txtDistance.setText(Utility.convertMeterIntoKm(String.valueOf(SphericalUtil.computeDistanceBetween(new LatLng(Constant.getLatitude(), Constant.getLongitude()), new LatLng(object.getLatitude(), object.getLongitude())))) + " " + Constant.ImportantMessages.miles + " " + Constant.ImportantMessages.nearby);
        txtPlaceDetail.setText(Utility.placeDescriptionCreator(new PlaceParameter("0", object.getPlaceType(), object.getPlaceName(), "", object.getPlaceRating(), object.getPriceLevel())));
        rating.setRating(Float.parseFloat(placeRating));
        txtRating.setText(placeRating);

        txtPriceLevel.setText(priceLevel);
        txtAddress.setText(object.getPlaceAddress());


    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */

    private void initAD() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build();
        mAdView.loadAd(adRequest);

    }


    /**
     * <p>It is trigger after the successful completion of request sent to network
     * & get its response for working on UI</p>
     *
     * @param result
     */
    @Override
    public void onSuccess(ArrayList<Object> result) {

        NearbyPlaces nearbyPlaces = (NearbyPlaces) result.get(0);

        txtPhone.setText(Utility.isEmptyString(nearbyPlaces.getPhone()) ? "Not Available" : nearbyPlaces.getPhone());
        mapDirectionUrl = nearbyPlaces.getMapUrl();
        txtUrl.setText(Utility.isEmptyString(nearbyPlaces.getWebUrl()) ? "Not Available" : nearbyPlaces.getWebUrl());

        if (isSearchedPlace) {


            object = nearbyPlaces;
            populateDate(nearbyPlaces);

            if (googleMap != null && latitude == 0.0)
                setLocationOnMap(nearbyPlaces);


        }

        ArrayList<Object> pictureArraylist = new ArrayList<>();
        if (nearbyPlaces.getPhotoArrayList().size() > 0)
            pictureArraylist.addAll(nearbyPlaces.getPhotoArrayList());
        else
            pictureArraylist.add(new ErrorObject(Utility.getStringFromRes(getApplicationContext(), R.string.no_picture_label),
                    Utility.getStringFromRes(getApplicationContext(), R.string.no_picture_tagline), R.drawable.no_picture));

        placeDetailAdapter = new PlaceDetailAdapter(this, pictureArraylist, false);
        recyclerViewImages.setAdapter(placeDetailAdapter);

        if (nearbyPlaces.getReviewArraylist().size() > 0) {
            review.addAll(nearbyPlaces.getReviewArraylist());
            layoutSeeAll.setVisibility(View.VISIBLE);
        } else {
            review.add(new ErrorObject(Constant.ImportantMessages.no_review));
            layoutSeeAll.setVisibility(View.GONE);
        }


        reviewAdapter = new PlaceDetailAdapter(this, review, true);
        recyclerViewReviews.setAdapter(reviewAdapter);


    }


    @Override
    public void onClick(View v) {
        if (v == txtUrl) {
            if (!(txtUrl.getText().toString().equalsIgnoreCase("Not Available"))) {
                Utility.openWebUrl(getApplicationContext(), txtUrl.getText().toString());
            } else
                Utility.Toaster(getApplicationContext(), Utility.getStringFromRes(getApplicationContext(), R.string.url_not_available), Toast.LENGTH_SHORT);

        }
        if (v == txtPhone) {
            if (!(txtPhone.getText().toString().equalsIgnoreCase("Not Available"))) {
                Utility.openDialer(getApplicationContext(), txtPhone.getText().toString());
            } else
                Utility.Toaster(getApplicationContext(), Utility.getStringFromRes(getApplicationContext(), R.string.phone_not_available), Toast.LENGTH_SHORT);

        }
        if (v == layoutSeeAll) {
            Intent intent = new Intent(this, ListOfReviews.class);
            intent.putExtra(Constant.IntentKey.PLACE_RATING, review);
            startActivity(intent);
        }
        if (v == imageBack) {
            finish();
        }
        if (v == layoutDirection) {

            Intent intent = new Intent(this, PlaceDirection.class);
            intent.putExtra(Constant.IntentKey.DESTINATION_LATITUDE, object.getLatitude());
            intent.putExtra(Constant.IntentKey.DESTINATION_LONGITUDE, object.getLongitude());
            intent.putExtra(Constant.IntentKey.DESTINATION_NAME, object.getPlaceName());
            startActivity(intent);

        }
        if (v == imageShare) {

            String address = Utility.isEmptyString(txtAddress.getText().toString()) ? "null" : txtAddress.getText().toString();
            String name = Utility.isEmptyString(txtPlaceName.getText().toString()) ? "null" : txtPlaceName.getText().toString();

            Constant.ServerInformation.googleLink = String.format(Constant.ServerInformation.googleLink, object.getLatitude(), object.getLongitude(), object.getLatitude(), object.getLongitude());
            Utility.shareLocation(this, "Share Location", "Name :" + name + "\nAddress : " + address + " \nGoogle Map Url :" + Constant.ServerInformation.googleLink);


        }
        if (v == imageFvrt) {

            objectArrayList.clear();
            objectArrayList.addAll(management.getDataFromDb(Constant.DATABASE_EVENT.SINGLE, Constant.DATABASE_FUNCTION.FAVOURITE, new DatabaseQueryObject(object.getPlaceId(), 0), null));

            if (objectArrayList.size() > 0) {

                imageFvrt.setImageResource(R.drawable.not_bookmark);
                management.getDataFromDb(Constant.DATABASE_EVENT.DELETE, Constant.DATABASE_FUNCTION.FAVOURITE, null, new HistoryQueryObject(((DatabaseQueryObject) (objectArrayList.get(0))).getId()));

            } else {


                if (object.getPhotoArrayList().size() > 0) {

                    photoRef = (object.getPhotoArrayList().get(0)) instanceof Photo ? ((Photo) (object.getPhotoArrayList().get(0))).getPhotoReference()
                            : (object.getPhotoArrayList().get(0)) instanceof com.haris.navigato.JsonUtil.TopPlaceUtil.Photo ? ((com.haris.navigato.JsonUtil.TopPlaceUtil.Photo) (object.getPhotoArrayList().get(0))).getPhotoReference()
                            : ((com.haris.navigato.JsonUtil.PlaceDetailUtil.Photo) (object.getPhotoArrayList().get(0))).getPhotoReference();

                } else
                    photoRef = "null";


                imageFvrt.setImageResource(R.drawable.bookmarked);
                management.getDataFromDb(Constant.DATABASE_EVENT.INSERT, Constant.DATABASE_FUNCTION.FAVOURITE, new DatabaseQueryObject("0", photoRef, object.getPlaceId(), object.getPlaceName()
                        , object.getPriceLevel(), object.getPlaceRating(), object.getPlaceType(), object.getPlaceAddress(), object.getLatitude(), object.getLongitude()), null);
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (isSearchedPlace && object != null)
            setLocationOnMap(object);
        else if (!isSearchedPlace && object != null)
            setLocationOnMap(object);

    }


    /**
     * <p>It is used to set location on Map</p>
     *
     * @param nearbyPlaces
     */
    private void setLocationOnMap(NearbyPlaces nearbyPlaces) {

        /*latitude = isSearchedPlace ? nearbyPlaces.getLatitude() : object.getLatitude();
        longitude = isSearchedPlace ? nearbyPlaces.getLongitude() : object.getLongitude();*/

        Utility.Logger("Location", String.valueOf(object.getLatitude()));

        latitude = object.getLatitude();
        longitude = object.getLongitude();

        MarkerOptions markerOption = new MarkerOptions().position(new LatLng(latitude, longitude));
        final Marker location_marker = googleMap.addMarker(markerOption);
        location_marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_pointer_light_blue));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));

    }


    @Override
    public boolean onLongClick(View v) {
        if (v == txtAddress) {
            if (!(txtAddress.getText().toString().equalsIgnoreCase("Not Available"))) {
                Utility.copyData(getApplicationContext(), txtAddress.getText().toString());
                Utility.Toaster(getApplicationContext(), Utility.getStringFromRes(getApplicationContext(), R.string.address_copied), Toast.LENGTH_SHORT);
            }
        }
        return true;
    }


}

