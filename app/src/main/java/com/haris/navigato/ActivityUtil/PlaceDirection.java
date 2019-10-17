package com.haris.navigato.ActivityUtil;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.util.DirectionConverter;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.haris.navigato.AdapterUtil.TravelingAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.MapBoxRouteCallback;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.DirectionParameter;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;
import com.haris.navigato.ObjectUtil.InstructionObject;
import com.haris.navigato.ObjectUtil.PrefObject;
import com.haris.navigato.ObjectUtil.RouteDataObject;
import com.haris.navigato.ObjectUtil.SpinnerItem;
import com.haris.navigato.ObjectUtil.StepDataObject;
import com.haris.navigato.ObjectUtil.TimeParameter;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestDeeplink;
import com.uber.sdk.core.client.SessionConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PlaceDirection extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnPolylineClickListener, MapBoxRouteCallback {
    private LatLng destinationLocation;
    private LatLng sourceLocation;
    private Double latitude;
    private Double longitude;
    private UbuntuRegularTextview txtNormal;
    private UbuntuRegularTextview txtHybrid;
    private UbuntuRegularTextview txtSatellite;
    private UbuntuRegularTextview txtTerrain;
    private MapView mapView;
    private GoogleMap googleMap;
    private ValueAnimator lastPulseAnimator;
    private TextView txtMenu;
    private ImageView imageBack;
    private String placeTag;
    private Management management;
    private PolylineOptions[] polylineOptions;
    private Polyline[] polyLines;
    private UbuntuRegularTextview labelFrom;
    private UbuntuMediumTextview txtFrom;
    private UbuntuRegularTextview labelTo;
    private UbuntuMediumTextview txtTo;
    private UbuntuMediumTextview txtDistance;
    private UbuntuRegularTextview labelDistance;
    private UbuntuMediumTextview txtTime;
    private UbuntuRegularTextview labelTime;
    private UbuntuMediumTextview txtPrice;
    private UbuntuRegularTextview labelPrice;
    private TextView txtRoute;
    private CardView cardStartOrg;
    private UbuntuMediumTextview labelNavigation;
    private String destinationAddress;
    private String destinationName;
    private ArrayList<ArrayList<LatLng>> pointerArraylist = new ArrayList<>();
    private ArrayList<RouteDataObject> routeDataArrayList = new ArrayList<>();
    private ArrayList<StepDataObject> stepDataArrayList = new ArrayList<>();
    private ArrayList<ArrayList<InstructionObject>> instructionObjects = new ArrayList<>();
    private int selectedRoute;
    private AppCompatSpinner spinnerTraveling;
    private SpinnerAdapter spinnerAdapter;
    private ArrayList<SpinnerItem> spinnerItemArrayList = new ArrayList<>();
    private List<DirectionsRoute> mapBoxRoute = new ArrayList<>();
    private String transportMode;
    private String transportSelected;
    private boolean isDetail;
    private int conveyance;
    private CardView cardViewTraffic;
    private ImageView imageTrafficCar;
    private int traffic = 0;
    private Class classObject;
    private ImageView imageShare;
    private RideRequestDeeplink deeplink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_direction);

        getIntentData();  //Receive Intent Data
        initUI(savedInstanceState); //Initialize UI
        initTaxiSdk();  //Initialize Uber Ride Sdk


    }


    /**
     * <p>It is used to receive Intent Data </p>
     */
    private void getIntentData() {

        latitude = getIntent().getDoubleExtra(Constant.IntentKey.DESTINATION_LATITUDE, 0.0);
        longitude = getIntent().getDoubleExtra(Constant.IntentKey.DESTINATION_LONGITUDE, 0.0);
        destinationName = getIntent().getStringExtra(Constant.IntentKey.DESTINATION_NAME);

        isDetail = getIntent().getBooleanExtra(Constant.IntentKey.HISTORY_LOCATION, false);
        if (isDetail) {
            sourceLocation = new LatLng(getIntent().getDoubleExtra(Constant.IntentKey.SOURCE_LATITUDE, 0.0)
                    , getIntent().getDoubleExtra(Constant.IntentKey.SOURCE_LONGITUDE, 0.0));
            conveyance = getIntent().getIntExtra(Constant.IntentKey.CONVEYANCE, 0);
        }

        destinationLocation = new LatLng(latitude, longitude);

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(Bundle savedInstanceState) {

        if (sourceLocation == null)
            sourceLocation = new LatLng(Constant.getLatitude(), Constant.getLongitude());

        //transportMode = TransportMode.DRIVING;
        transportMode = DirectionsCriteria.PROFILE_DRIVING_TRAFFIC;

        management = new Management(this);
        //management.sendRequestForDirections(Constant.REQUEST.DIRECTION, new DirectionParameter(sourceLocation, destinationLocation, AvoidType.TOLLS, transportMode, true, this, null));


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.ROUTES);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        imageShare = findViewById(R.id.image_share);
        imageShare.setImageResource(R.drawable.car_taxi_icon);
        imageShare.setVisibility(View.VISIBLE);

        txtNormal = (UbuntuRegularTextview) findViewById(R.id.txt_normal);
        txtHybrid = (UbuntuRegularTextview) findViewById(R.id.txt_hybrid);
        txtSatellite = (UbuntuRegularTextview) findViewById(R.id.txt_satellite);
        txtTerrain = (UbuntuRegularTextview) findViewById(R.id.txt_terrain);

        labelFrom = (UbuntuRegularTextview) findViewById(R.id.label_from);
        txtFrom = (UbuntuMediumTextview) findViewById(R.id.txt_from);
        labelTo = (UbuntuRegularTextview) findViewById(R.id.label_to);
        txtTo = (UbuntuMediumTextview) findViewById(R.id.txt_to);
        txtDistance = (UbuntuMediumTextview) findViewById(R.id.txt_distance);
        labelDistance = (UbuntuRegularTextview) findViewById(R.id.label_distance);
        txtTime = (UbuntuMediumTextview) findViewById(R.id.txt_time);
        labelTime = (UbuntuRegularTextview) findViewById(R.id.label_time);
        txtPrice = (UbuntuMediumTextview) findViewById(R.id.txt_price);
        labelPrice = (UbuntuRegularTextview) findViewById(R.id.label_price);
        txtRoute = (TextView) findViewById(R.id.txt_route);
        cardStartOrg = (CardView) findViewById(R.id.card_start_org);
        labelNavigation = (UbuntuMediumTextview) findViewById(R.id.label_navigation);

        cardViewTraffic = (CardView) findViewById(R.id.card_view_traffic);
        imageTrafficCar = (ImageView) findViewById(R.id.image_traffic_car);

        spinnerItemArrayList.addAll(Utility.getSpinnerItems());

        spinnerTraveling = (AppCompatSpinner) findViewById(R.id.spinner_traveling);
        spinnerAdapter = new TravelingAdapter(this, spinnerItemArrayList);
        spinnerTraveling.setAdapter(spinnerAdapter);

        if (isDetail) {
            spinnerTraveling.setSelection(conveyance);
            cardStartOrg.setVisibility(View.GONE);
        } else {
            spinnerTraveling.setSelection(1);
            cardStartOrg.setVisibility(View.VISIBLE);
        }


        labelFrom.setText(Constant.MenuText.FROM_LABEL);
        labelTo.setText(Constant.MenuText.TO_LABEL);
        labelDistance.setText(Constant.MenuText.DISTANCE_LABEL);
        labelTime.setText(Constant.MenuText.TIME_LABEL);
        labelPrice.setText(Constant.MenuText.PETROL_LABEL);
        labelNavigation.setText(Constant.MenuText.NAVIGATION_LABEL);
        txtRoute.setText(Constant.MenuText.ROUTE_LABEL);

        txtTo.setText(destinationName);
        txtFrom.setText(Utility.getCityNameByCoordinates(this, sourceLocation.latitude, sourceLocation.longitude));


        txtNormal.setOnClickListener(this);
        txtHybrid.setOnClickListener(this);
        txtSatellite.setOnClickListener(this);
        txtTerrain.setOnClickListener(this);
        cardStartOrg.setOnClickListener(this);


        mapView.getMapAsync(this);
        imageBack.setOnClickListener(this);
        spinnerTraveling.setOnItemSelectedListener(this);
        cardViewTraffic.setOnClickListener(this::onClick);
        imageShare.setOnClickListener(this::onClick);

    }


    /**
     * <p>It is used to initialize Uber Taxi Sdk for request Ride</p>
     */
    private void initTaxiSdk() {


        RideParameters rideParams = new RideParameters.Builder()
                .setPickupLocation(sourceLocation.latitude, sourceLocation.longitude, Utility.getCityNameByCoordinates(getApplicationContext(), sourceLocation.latitude, sourceLocation.longitude), Utility.getAddressByCoordinates(getApplicationContext(), sourceLocation.latitude, sourceLocation.longitude))
                .setDropoffLocation(destinationLocation.latitude, destinationLocation.longitude, destinationName, Utility.getAddressByCoordinates(getApplicationContext(), destinationLocation.latitude, destinationLocation.longitude)) // Price estimate will only be provided if this is provided.
                ///.setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d") // Optional. If not provided, the cheapest product will be used.
                .build();

        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(Constant.Credentials.UBER_CLIENT_ID)
                .setClientSecret(Constant.Credentials.UBER_CLIENT_SECRET)
                .setServerToken(Constant.Credentials.UBER_SERVER_TOKEN)
                ///.setEnvironment(SessionConfiguration.Environment.SANDBOX) //Useful for testing your app in the sandbox environment
                .build();


        deeplink = new RideRequestDeeplink.Builder(this)
                .setSessionConfiguration(config)
                .setRideParameters(rideParams)
                .build();

        ///Utility.Logger("UBer Link", String.valueOf(deeplink.getUri()));


    }


    @Override
    public void getDirectionRoutes(List<DirectionsRoute> directionsRouteList) {

        mapBoxRoute.clear();
        mapBoxRoute.addAll(directionsRouteList);

        this.googleMap.addMarker(new MarkerOptions().position(sourceLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_pointer)));


        Utility.Logger("Routes", String.valueOf(directionsRouteList.size()));


        for (int i = 0; i < directionsRouteList.size(); i++) {


            LineString lineString = LineString.fromPolyline(directionsRouteList.get(i).geometry(), Constants.PRECISION_6);
            ArrayList<LatLng> routeArraylist = new ArrayList<>();
            List<Point> coordinates = new ArrayList<>();


            coordinates.addAll(lineString.coordinates());
            ////LatLng[] points = new LatLng[coordinates.size()];

            for (int j = 0; j < coordinates.size(); j++) {

                //if (j == 0)
                //routeArraylist.add(new LatLng(Constant.getLatitude(), Constant.getLongitude()));

                routeArraylist.add(new LatLng(
                        coordinates.get(j).latitude(),
                        coordinates.get(j).longitude()));
            }


            String fDis = String.valueOf(Utility.convertMeterIntoKm(String.valueOf(directionsRouteList.get(i).distance()))) + " km";
            String fDur = Utility.secondIntoTime(new TimeParameter(directionsRouteList.get(i).duration().intValue(), true));
            String fDistInMet = String.valueOf(directionsRouteList.get(i).distance());
            String fDurInSec = String.valueOf(directionsRouteList.get(i).duration());


            routeDataArrayList.add(new RouteDataObject(fDis, fDur, fDistInMet, fDurInSec));

            pointerArraylist.add(routeArraylist);


        }

        Utility.Logger("Pointer Array", String.valueOf(pointerArraylist.size()));

        drawRoute(pointerArraylist);


        boolean isDirection;
        String bannerInstruction = null;
        int speed = 0;

        for (int i = 0; i < directionsRouteList.size(); i++) {

            ArrayList<InstructionObject> instructionObjects = new ArrayList<>();

            for (int j = 0; j < directionsRouteList.get(i).legs().size(); j++) {

                for (int k = 0; k < directionsRouteList.get(i).legs().get(j).steps().size(); k++) {


                    LineString lineString = LineString.fromPolyline(directionsRouteList.get(i).legs().get(j).steps().get(k).geometry(), Constants.PRECISION_6);
                    ArrayList<LatLng> routeArraylist = new ArrayList<>();
                    List<Point> coordinates = new ArrayList<>();

                    coordinates.addAll(lineString.coordinates());

                    for (int l = 0; l < coordinates.size(); l++) {

                        if (l == 0)
                            isDirection = true;
                        else
                            isDirection = false;

                        if (directionsRouteList.get(i).legs().get(j).steps().get(k).bannerInstructions() != null
                                && directionsRouteList.get(i).legs().get(j).steps().get(k).bannerInstructions().size() > 0)
                            bannerInstruction = directionsRouteList.get(i).legs().get(j).steps().get(k).bannerInstructions().get(0).primary().text();
                        else
                            bannerInstruction = "null";


                        instructionObjects.add(new InstructionObject(directionsRouteList.get(i).legs().get(j).steps().get(k).maneuver().instruction(),
                                directionsRouteList.get(i).legs().get(j).steps().get(k).distance(), directionsRouteList.get(i).legs().get(j).steps().get(k).duration(),
                                coordinates.get(l).latitude(), coordinates.get(l).longitude(), isDirection,
                                directionsRouteList.get(i).legs().get(j).steps().get(k).maneuver().type(),
                                directionsRouteList.get(i).legs().get(j).steps().get(k).maneuver().modifier(),
                                coordinates.get(0).latitude(), coordinates.get(0).longitude(),
                                coordinates.get(coordinates.size() - 1).latitude(), coordinates.get(coordinates.size() - 1).longitude()
                                , bannerInstruction));

                        Utility.Logger("Coordinates", "Value : " + l);


                    }


                    Utility.Logger("Instructions " + i + " Leg : " + j + " Step : " + k, directionsRouteList.get(i).legs().get(j).steps().get(k).maneuver().instruction() + " \t" + " Instruction : " + bannerInstruction +
                            " \n");


                }


            }

            this.instructionObjects.add(instructionObjects);

        }


        ///drawRouteTesting(instructionObjects);


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
        if (v == imageShare) {
            if (deeplink != null)
                alertTaxiConfirmationDialog(this);
        }
        if (v == cardViewTraffic) {

            traffic++;

            if (traffic == 1) {
                googleMap.setTrafficEnabled(true);
                cardViewTraffic.setCardBackgroundColor(Utility.getColourFromRes(this, R.color.lightBlue));
                imageTrafficCar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
            } else if (traffic == 2) {
                googleMap.setTrafficEnabled(false);
                cardViewTraffic.setCardBackgroundColor(Utility.getColourFromRes(this, R.color.colorPrimaryDark));
                imageTrafficCar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                traffic = 0;
            }

        }
        if (v == cardStartOrg) {
            onNavigationSelector(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setOnPolylineClickListener(this);

        onMapTypeSelection(txtNormal);


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
     * <p>It is used to raw route on map</p>
     *
     * @param pointList
     */
    private void drawRoute(ArrayList<ArrayList<LatLng>> pointList) {


        // For showing a move to my location button

        polylineOptions = new PolylineOptions[pointList.size()];
        polyLines = new Polyline[pointList.size()];

        for (int i = 0; i < pointList.size(); i++) {

            if (i == 0) {
                polylineOptions[i] = DirectionConverter.createPolyline(getApplicationContext(), pointList.get(i), 10, Utility.getColourFromRes(this, R.color.colorPrimary));
                setData(i);


            } else
                polylineOptions[i] = DirectionConverter.createPolyline(getApplicationContext(), pointList.get(i), 7, Utility.getColourFromRes(this, R.color.orange));

            polyLines[i] = googleMap.addPolyline(polylineOptions[i]);
            polyLines[i].setTag(i);
            polyLines[i].setClickable(true);

        }

        googleMap.addMarker(new MarkerOptions().position(destinationLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pointer_light_blue))).setTitle("Your Destination");

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(sourceLocation)
                        .bearing(43)
                        .tilt(90)
                        .zoom(14.5f)
                        .build();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        createRippleEffect(sourceLocation);

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
    public void onPolylineClick(Polyline polyline) {
        changePolyLineColour((int) polyline.getTag());
        setData((int) polyline.getTag());
    }


    /**
     * <p>It is used to Change Polyline Colour , when we click</p>
     *
     * @param value
     */
    private void changePolyLineColour(int value) {


        for (int i = 0; i < polyLines.length; i++) {
            if (value == i)
                polyLines[i].setColor(Utility.getColourFromRes(this, R.color.colorPrimary));
            else
                polyLines[i].setColor(Utility.getColourFromRes(this, R.color.orange));
        }

    }


    /**
     * <p>It is used to populate data like Time,Distance,Route & Petrol</p>
     *
     * @param value
     */
    private void setData(int value) {
        selectedRoute = value;
        txtDistance.setText(routeDataArrayList.get(value).getDistance());
        txtTime.setText(routeDataArrayList.get(value).getDuration());
        if (transportMode.equals(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC) || transportMode.equals(DirectionsCriteria.PROFILE_DRIVING))
            txtPrice.setText(Utility.calculatePetrol(Double.parseDouble(routeDataArrayList.get(value).getDistanceInMeter())) + " " + Constant.ImportantMessages.litre_label);
        txtRoute.setText(Constant.MenuText.ROUTE_LABEL + " " + (value + 1));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (!(position == 0)) {


            if (spinnerItemArrayList.get(position).getTitle().contains(Constant.ImportantMessages.car_label)) {

                //transportMode = TransportMode.DRIVING;
                transportMode = DirectionsCriteria.PROFILE_DRIVING_TRAFFIC;
                transportSelected = "Travelling by Car";

            } else if (spinnerItemArrayList.get(position).getTitle().contains(Constant.ImportantMessages.bicycle_label)) {

                //transportMode = TransportMode.BICYCLING;
                transportMode = DirectionsCriteria.PROFILE_CYCLING;
                txtPrice.setText("- " + Constant.ImportantMessages.litre_label);
                transportSelected = "Travelling by Bicycle";

            } else if (spinnerItemArrayList.get(position).getTitle().contains(Constant.ImportantMessages.walk_label)) {

                //transportMode = TransportMode.WALKING;
                transportMode = DirectionsCriteria.PROFILE_WALKING;
                txtPrice.setText("- " + Constant.ImportantMessages.litre_label);
                transportSelected = "Travelling by Walking";
            }

            routeDataArrayList.clear();
            pointerArraylist.clear();
            stepDataArrayList.clear();
            //management.sendRequestForDirections(Constant.REQUEST.DIRECTION, new DirectionParameter(sourceLocation, destinationLocation, AvoidType.TOLLS, transportMode, true, this, null));
            management.sendRequestForMapboxDirections(Constant.REQUEST.DIRECTION, new DirectionParameter(sourceLocation, destinationLocation, AvoidType.TOLLS, transportMode, true, this, null, false));
            if (googleMap != null)
                googleMap.clear();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * <p>It trigger Dialog for navigation selection </p>
     *
     * @param context
     */
    private void onNavigationSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_navigation_layout);

        LinearLayout layout_step = (LinearLayout) dialog.findViewById(R.id.layout_step);
        layout_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 7/8/2018 Modification of Navigation

                if (management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.HUD_VIEW, false, false, true)).condition) {
                    //classObject = NavigationHud.class;
                } else {
                    //classObject = MapBoxNavigation.class;
                }

                proceedToNavigationActivity(classObject);

                dialog.dismiss();
            }
        });

        LinearLayout layout_ar = (LinearLayout) dialog.findViewById(R.id.layout_ar);
        layout_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 7/8/2018 Ar Navigation

                if (management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.AR_NAV, false, true, false)).condition) {
                    ///classObject = ArNavigation.class;

                } else {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.TURN_ON_AR, Toast.LENGTH_SHORT);
                    return;
                }

                if (!(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
                    Utility.Toaster(context, Constant.ToastMessage.DID_NOT_SUPPORT_VERSION, Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utility.checkIfSensorsAvailable(context)) {
                    Utility.Toaster(context, Constant.ToastMessage.DID_NOT_HAVE_COMPASS, Toast.LENGTH_SHORT);
                    return;
                }

                proceedToNavigationActivity(classObject);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    /**
     * <p>It is used to start new activity</p>
     *
     * @param classObject
     */
    private void proceedToNavigationActivity(Class classObject) {


        management.getDataFromDb(Constant.DATABASE_EVENT.INSERT, Constant.DATABASE_FUNCTION.HISTORY, null
                , new HistoryQueryObject("0", Utility.getTextFromView(txtRoute), transportSelected, Utility.getTextFromView(txtFrom), Utility.getTextFromView(txtTo)
                        , Utility.getTextFromView(txtDistance), Utility.getTextFromView(txtTime), Utility.getTextFromView(txtPrice), Constant.getLatitude(), Constant.getLongitude()
                        , latitude, longitude));

        Constant.setDirectionsRoute(mapBoxRoute.get(selectedRoute));


        String URL = String.format(Constant.ServerInformation.googleMapLink, destinationLocation.latitude + "," + destinationLocation.longitude);
        Uri location = Uri.parse(URL);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);

    }


    /**
     * <p>It show alert to User about either they want Taxi ride or not</p>
     *
     * @param context
     */
    private void alertTaxiConfirmationDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.taxi_confirmation_dialog);

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView txt_description = (TextView) dialog.findViewById(R.id.txt_description);


        LinearLayout layout_close = (LinearLayout) dialog.findViewById(R.id.layout_cancel);
        LinearLayout layout_done = (LinearLayout) dialog.findViewById(R.id.layout_done);

        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.openWebUrl(context, String.valueOf(deeplink.getUri()));

                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
