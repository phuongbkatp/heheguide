package com.haris.navigato.ActivityUtil;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.haris.navigato.AdapterUtil.NearbyAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.ObjectUtil.PrefObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;

import java.io.Serializable;
import java.util.ArrayList;


public class SearchPlace extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, DirectoryCallback {
    private UbuntuRegularTextview txtNormal;
    private UbuntuRegularTextview txtHybrid;
    private UbuntuRegularTextview txtSatellite;
    private UbuntuRegularTextview txtTerrain;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng sourceLocation;
    private ValueAnimator lastPulseAnimator;
    private LatLng destinationLocation;
    private TextView txtMenu;
    private ImageView imageBack;
    private String placeTag;
    private boolean isTourist;
    private Management management;
    private RecyclerView recyclerViewPlaces;
    private NearbyAdapter nearbyAdapter;
    private ArrayList<NearbyPlaces> nearbyPlacesArrayList = new ArrayList<>();
    private ArrayList<Object> cardArraylist = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private String pageToken;
    private String menuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        receiveIntentData(); //Receive Intent Data
        initUI(savedInstanceState); //Initialize UI
        searchPlace();  //Search places around them
        Utility.showInterstitialAd(getApplicationContext());  ///Initialize Admob Interstitial Ad

    }


    /**
     * <p>It is used to receive place tags from previous activity</p>
     */
    private void receiveIntentData() {
        placeTag = getIntent().getStringExtra(Constant.IntentKey.PLACE_REQUIRED);
        isTourist = getIntent().getBooleanExtra(Constant.IntentKey.IS_TOURIST, false);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(Bundle savedInstanceState) {

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuText = isTourist ? Constant.MenuText.TOP_PLACES : Constant.MenuText.PLACES_NEAR_YOU;

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(menuText);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        txtNormal = (UbuntuRegularTextview) findViewById(R.id.txt_normal);
        txtHybrid = (UbuntuRegularTextview) findViewById(R.id.txt_hybrid);
        txtSatellite = (UbuntuRegularTextview) findViewById(R.id.txt_satellite);
        txtTerrain = (UbuntuRegularTextview) findViewById(R.id.txt_terrain);

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPlaces = (RecyclerView) findViewById(R.id.recycler_view_places);
        recyclerViewPlaces.setLayoutManager(gridLayoutManager);
        recyclerViewPlaces.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager
                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something

                    Utility.Logger("Scrolling", "Happened" + pageToken);

                    searchPlace(pageToken);

                    loading = true;
                }

            }
        });

        nearbyAdapter = new NearbyAdapter(this, cardArraylist, true) {
            @Override
            public void onNearBySelector(int nearbyPlacePosition) {
                NearbyPlaces nearbyPlaces = (NearbyPlaces) cardArraylist.get(nearbyPlacePosition);
                Intent intent = new Intent(SearchPlace.this, PlaceDetail.class);
                intent.putExtra(Constant.IntentKey.PLACE_DETAIL, (Serializable) nearbyPlaces);
                startActivity(intent);
            }
        };
        recyclerViewPlaces.setAdapter(nearbyAdapter);

        txtNormal.setOnClickListener(this);
        txtHybrid.setOnClickListener(this);
        txtSatellite.setOnClickListener(this);
        txtTerrain.setOnClickListener(this);


        mapView.getMapAsync(this);
        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for finding specific places around them</p>
     */
    private void searchPlace() {
        management = new Management(this);
        if (!isTourist)
            management.sendServerRequest(Constant.REQUEST.NEARBY, new PlaceParameter("", String.valueOf(Constant.getLatitude()), String.valueOf(Constant.getLongitude()), placeTag, management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.COVERAGE)).result, null, this));
        else
            management.sendServerRequest(Constant.REQUEST.TOP_PLACES, new PlaceParameter(placeTag, String.valueOf(Constant.getLatitude()), String.valueOf(Constant.getLongitude()), null, this));

    }


    /**
     * <p>It is used to send request to server for finding next specific places around them
     * using Page Token</p>
     */
    private void searchPlace(String pageToken) {
        if (!isTourist)
            management.sendServerRequest(Constant.REQUEST.NEARBY, new PlaceParameter("", String.valueOf(Constant.getLatitude()), String.valueOf(Constant.getLongitude()), placeTag, "4500", pageToken, null, this));
        else
            management.sendServerRequest(Constant.REQUEST.TOP_PLACES, new PlaceParameter(placeTag, String.valueOf(Constant.getLatitude()), String.valueOf(Constant.getLongitude()), pageToken, null, this));

    }


    @Override
    public void onClick(View v) {
        if (v == txtNormal) {
            onMapTypeSelection(v);
        }
        if (v == txtHybrid) {
            onMapTypeSelection(v);
        }
        if (v == txtSatellite) {
            onMapTypeSelection(v);
        }
        if (v == txtTerrain) {
            onMapTypeSelection(v);
        }
        if (v == imageBack) {
            finish();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        onMapTypeSelection(txtNormal);

        if (Constant.getLatitude() != null) {
            sourceLocation = new LatLng(Constant.getLatitude(), Constant.getLongitude());
            this.googleMap.addMarker(new MarkerOptions().position(sourceLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_pointer))/*.title("Your Current Location").snippet(yourLocation.getText().toString())*/).setTag(-1);

        }


        this.googleMap.setTrafficEnabled(true);

        createRippleEffect(sourceLocation);


    }


    /**
     * <p>It is used to select map type & apply it to map view</p>
     *
     * @param v
     */
    private void onMapTypeSelection(View v) {

        if (v == txtNormal) {
            if (googleMap == null)
                return;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapView.invalidate();

            txtNormal.setBackgroundResource(R.drawable.left_drawable_selected);
            txtHybrid.setBackgroundResource(R.drawable.center_drawables);
            txtSatellite.setBackgroundResource(R.drawable.center_drawables);
            txtTerrain.setBackgroundResource(R.drawable.right_drawable);


        }
        if (v == txtHybrid) {
            if (googleMap == null)
                return;
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mapView.invalidate();

            txtNormal.setBackgroundResource(R.drawable.left_drawable);
            txtHybrid.setBackgroundResource(R.drawable.center_drawables_selected);
            txtSatellite.setBackgroundResource(R.drawable.center_drawables);
            txtTerrain.setBackgroundResource(R.drawable.right_drawable);


        }
        if (v == txtSatellite) {
            if (googleMap == null)
                return;
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mapView.invalidate();

            txtNormal.setBackgroundResource(R.drawable.left_drawable);
            txtHybrid.setBackgroundResource(R.drawable.center_drawables);
            txtSatellite.setBackgroundResource(R.drawable.center_drawables_selected);
            txtTerrain.setBackgroundResource(R.drawable.right_drawable);


        }
        if (v == txtTerrain) {
            if (googleMap == null)
                return;
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mapView.invalidate();

            txtNormal.setBackgroundResource(R.drawable.left_drawable);
            txtHybrid.setBackgroundResource(R.drawable.center_drawables);
            txtSatellite.setBackgroundResource(R.drawable.center_drawables);
            txtTerrain.setBackgroundResource(R.drawable.right_drawable_selected);


        }


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * <p>It is used to create Ripple Effect for specific pointer</p>
     *
     * @param sourceLocation
     */
    private void createRippleEffect(final LatLng sourceLocation) {

        // First ripple effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GroundOverlay groundOverlay11 = SearchPlace.this.googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 2000)
                        .transparency(0.5f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.riple_icon)));
                OverLay(groundOverlay11, 6000);
            }
        }, 0);

        //Second ripple effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GroundOverlay groundOverlay12 = SearchPlace.this.googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 2000)
                        .transparency(0.5f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.riple_icon)));
                OverLay(groundOverlay12, 5000);
            }
        }, 0);

        //Third ripple effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GroundOverlay groundOverlay13 = SearchPlace.this.googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 200)
                        .transparency(0.5f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.riple_icon)));
                OverLay(groundOverlay13, 4000);
            }
        }, 0);

    }


    /**
     * <p>It is used by Ripple effect creator to create & animate the effect</p>
     *
     * @param groundOverlay
     * @param duration
     */
    public void OverLay(final GroundOverlay groundOverlay, int duration) {
        lastPulseAnimator = ValueAnimator.ofInt(0, 2500);
        int r = 99999;
        lastPulseAnimator.setRepeatCount(r);
        //lastPulseAnimator.setIntValues(0, 500);
        lastPulseAnimator.setDuration(duration);
        lastPulseAnimator.setEvaluator(new IntEvaluator());
        lastPulseAnimator.setInterpolator(new LinearInterpolator());
        lastPulseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                Integer i = (Integer) valueAnimator.getAnimatedValue();
                groundOverlay.setDimensions(i);
                groundOverlay.setTransparency(animatedFraction);
            }
        });
        lastPulseAnimator.start();
    }


    @Override
    public void onSuccess(ArrayList<Object> result) {

        if (result.size() > 0) {
            pageToken = ((NearbyPlaces) result.get(0)).getNextPageToken();
            if (!Utility.isEmptyString(pageToken))
                loading = false;
            Utility.Logger("Token", pageToken + " ");
        } else {
            pageToken = null;
        }

        for (int i = 0; i < result.size(); i++) {

            NearbyPlaces nearbyPlaces = (NearbyPlaces) result.get(i);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(nearbyPlaces.getLatitude(), nearbyPlaces.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pointer_light_blue)).snippet((nearbyPlaces.getFormattedAddress())).title(nearbyPlaces.getPlaceName())).setTag(i)/*.setPosition(new LatLng(place.getLatitude(),place.getLongitude()))*/;
            nearbyPlacesArrayList.add(nearbyPlaces);
        }

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(sourceLocation)
                        .bearing(45)
                        .tilt(90)
                        .zoom(14)
                        .build();
        this.googleMap.animateCamera(/*CameraUpdateFactory.newLatLngZoom(sourceLocation, 14)*/CameraUpdateFactory.newCameraPosition(cameraPosition));

        cardArraylist.addAll(result);
        nearbyAdapter.notifyDataSetChanged();


    }


}
