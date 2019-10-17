package com.haris.navigato.ActivityUtil;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.ObjectUtil.WikiObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

public class NearbyWiki extends AppCompatActivity implements View.OnClickListener, DirectoryCallback, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private MapView mapView;
    private TextView txtMenu;
    private ImageView imageBack;
    private UbuntuRegularTextview txtNormal;
    private UbuntuRegularTextview txtHybrid;
    private UbuntuRegularTextview txtSatellite;
    private UbuntuRegularTextview txtTerrain;
    private GoogleMap googleMap;
    private ValueAnimator lastPulseAnimator;
    private LatLng sourceLocation;
    private Management management;
    private ArrayList wikiArraylist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_wiki);

        initUI(savedInstanceState); //Initialize UI
        Utility.showInterstitialAd(getApplicationContext());  ///Initialize Admob Interstitial Ad


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(Bundle savedInstanceState) {

        if (sourceLocation == null)
            sourceLocation = new LatLng(Constant.getLatitude(), Constant.getLongitude());

        management = new Management(this);

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.NEARBY_WIKI);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        txtNormal = (UbuntuRegularTextview) findViewById(R.id.txt_normal);
        txtHybrid = (UbuntuRegularTextview) findViewById(R.id.txt_hybrid);
        txtSatellite = (UbuntuRegularTextview) findViewById(R.id.txt_satellite);
        txtTerrain = (UbuntuRegularTextview) findViewById(R.id.txt_terrain);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageBack.setOnClickListener(this);
        txtNormal.setOnClickListener(this);
        txtHybrid.setOnClickListener(this);
        txtSatellite.setOnClickListener(this);
        txtTerrain.setOnClickListener(this);
        mapView.getMapAsync(this);

    }

    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setTrafficEnabled(true);

        this.googleMap.setInfoWindowAdapter(new WikiCustomInfoWindow());
        this.googleMap.setOnInfoWindowClickListener(this);

        onMapTypeSelection(txtNormal);

        googleMap.addMarker(new MarkerOptions().position(sourceLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_pointer))).setTag(-1);
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(sourceLocation)
                        .bearing(43)
                        .tilt(90)
                        .zoom(14.5f)
                        .build();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        createRippleEffect(sourceLocation);

        management.sendServerRequest(Constant.REQUEST.WIKI, new PlaceParameter(null, String.valueOf(sourceLocation.latitude), String.valueOf(sourceLocation.longitude), null, this));

    }


    @Override
    public void onSuccess(ArrayList<Object> result) {


        for (int i = 0; i < result.size(); i++) {

            WikiObject wikiObject = (WikiObject) result.get(i);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(wikiObject.getLatitude(), wikiObject.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pointer_light_blue)).snippet((wikiObject.getPlaceName())).title(wikiObject.getPlaceName())).setTag(i);
            wikiArraylist.add(wikiObject);
        }


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
                final GroundOverlay groundOverlay11 = googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 2000)
                        .transparency(0.6f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.riple_icon)));
                OverLay(groundOverlay11, 6000);
            }
        }, 0);

        //Second ripple effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GroundOverlay groundOverlay12 = googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 2000)
                        .transparency(0.6f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.riple_icon)));
                OverLay(groundOverlay12, 5000);
            }
        }, 0);

        //Third ripple effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GroundOverlay groundOverlay13 = googleMap.addGroundOverlay(new
                        GroundOverlayOptions()
                        .position(sourceLocation, 200)
                        .transparency(0.6f)
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
        lastPulseAnimator = ValueAnimator.ofInt(0, 1500);
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
    public void onInfoWindowClick(Marker marker) {

        int position = (int) marker.getTag();
        WikiObject wikiObject = (WikiObject) wikiArraylist.get(position);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constant.ServerInformation.WIKIPEDIA_LINK, wikiObject.getId()))));


    }


    /**
     * <p>Custom marker Info Window</p>
     */
    protected class WikiCustomInfoWindow implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        private ImageView image_thumb;
        private UbuntuMediumTextview txtDistance;
        private LemonMilkTextview txtPlaceName;

        WikiCustomInfoWindow() {
            myContentsView = getLayoutInflater().inflate(R.layout.custom_wiki_layout, null);

        }

        @Override
        public View getInfoContents(Marker marker) {

            return null;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub

            txtPlaceName = (LemonMilkTextview) myContentsView.findViewById(R.id.txt_place_name);
            txtDistance = (UbuntuMediumTextview) myContentsView.findViewById(R.id.txt_distance);

            int position = (int) marker.getTag();

            if (position == -1)
                return null;

            WikiObject wikiObject = (WikiObject) wikiArraylist.get(position);

            txtPlaceName.setText(wikiObject.getPlaceName());
            txtDistance.setText(Utility.getRoundedValue(Utility.convertMeterIntoKm(String.valueOf(wikiObject.getMeter()))) + " km Away");


            return myContentsView;
        }

    }


}