package com.haris.navigato.ConnectionUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Unit;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.FontUtil.Font;
import com.haris.navigato.InterfaceUtil.ConnectivityCallback;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;
import com.haris.navigato.InterfaceUtil.MapBoxRouteCallback;
import com.haris.navigato.InterfaceUtil.WeatherCallback;
import com.haris.navigato.JsonUtil.NearbyUtil.NearbyJson;
import com.haris.navigato.JsonUtil.PlaceDetailUtil.PlaceDetailJson;
import com.haris.navigato.JsonUtil.TopPlaceUtil.TopPlaceJson;
import com.haris.navigato.JsonUtil.WikiUtil.WikiJson;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.ObjectUtil.WikiObject;
import com.haris.navigato.Utility.Utility;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.Locale;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 5/20/2018.
 */

public class ConnectionBuilder {


    /**
     * <p>It is used to prepare & send Nearby request to server</p>
     *
     * @param context
     * @param request
     * @param loadingText
     * @param serverUrl
     * @param type
     * @param directoryCallback
     */
    @SuppressLint("StaticFieldLeak")
    public ConnectionBuilder(Context context, final Constant.REQUEST request, String loadingText, String serverUrl, String type, final DirectoryCallback directoryCallback, ConnectivityCallback connectivityCallback) {

        if (!Utility.checkConnection(context)) {

            if (connectivityCallback != null)
                connectivityCallback.onInternetFailure();
            else
                Utility.showNoInternetDialog(context);
            return;
        }

        Utility.Logger("Serverl Url", serverUrl);

        final Gson gson = new Gson();

        new InitRequest(context, serverUrl, loadingText, type) {
            @Override
            public void onSuccess(String data, ACProgressFlower dialog) {

                if (dialog != null)
                    if (dialog.isShowing())
                        dialog.dismiss();

                Object object = null;
                ArrayList<Object> objectArrayList = new ArrayList<>();
                String placeType;
                String priceLevel;
                String nextPageToken;
                boolean openingHours;

                if (request == Constant.REQUEST.NEARBY) {

                    NearbyJson nearbyJson = gson.fromJson(data, NearbyJson.class);

                    nextPageToken = nearbyJson.getNextPageToken() == null ? "null" : nearbyJson.getNextPageToken();

                    for (int i = 0; i < nearbyJson.getResults().size(); i++) {

                        ArrayList<Object> photoArrayList = new ArrayList<>();

                        if (nearbyJson.getResults().get(i).getPhotos() == null)
                            continue;

                        Utility.Logger("Size of pic", String.valueOf(nearbyJson.getResults().get(i).getPhotos().size()));

                        if (nearbyJson.getResults().get(i).getPhotos().size() > 0)
                            photoArrayList = new ArrayList<Object>(nearbyJson.getResults().get(i).getPhotos());


                        placeType = nearbyJson.getResults().get(i).getTypes() == null ? "Not Available" : nearbyJson.getResults().get(i).getTypes().size() > 0 ? nearbyJson.getResults().get(i).getTypes().get(0) : "not available";
                        priceLevel = nearbyJson.getResults().get(i).getPriceLevel() == null ? "2" : String.valueOf(nearbyJson.getResults().get(i).getPriceLevel());
                        openingHours = nearbyJson.getResults().get(i).getOpeningHours() == null ? false : nearbyJson.getResults().get(i).getOpeningHours().getOpenNow();


                        Utility.Logger("Price Level", priceLevel + " " + photoArrayList.size() + " ");

                        objectArrayList.add(new NearbyPlaces(nearbyJson.getResults().get(i).getId(), nearbyJson.getResults().get(i).getPlaceId()
                                , nearbyJson.getResults().get(i).getName(), nearbyJson.getResults().get(i).getVicinity(), String.valueOf(nearbyJson.getResults().get(i).getRating())
                                , nearbyJson.getResults().get(i).getGeometry().getLocation().getLat(), nearbyJson.getResults().get(i).getGeometry().getLocation().getLng()
                                , openingHours, photoArrayList, placeType, priceLevel, nextPageToken));


                    }

                } else if (request == Constant.REQUEST.PLACE_DETAIL) {

                    PlaceDetailJson placeDetailJson = gson.fromJson(data, PlaceDetailJson.class);
                    ArrayList<Object> photoArrayList = new ArrayList<>();
                    ArrayList<Object> reviewArrayList = new ArrayList<>();
                    if (placeDetailJson.getResult().getPhotos() != null)
                        photoArrayList.addAll(placeDetailJson.getResult().getPhotos());
                    if (placeDetailJson.getResult().getReviews() != null)
                        reviewArrayList.addAll(placeDetailJson.getResult().getReviews());

                    String url = Utility.isEmptyString(placeDetailJson.getResult().getUrl()) ? "Not Available" : placeDetailJson.getResult().getUrl();

                    /*objectArrayList.add(new NearbyPlaces(placeDetailJson.getResult().getFormattedAddress(), placeDetailJson.getResult().getInternationalPhoneNumber()
                            , url, placeDetailJson.getResult().getWebsite(), reviewArrayList, photoArrayList));
*/
                    placeType = placeDetailJson.getResult().getTypes() == null ? "Not Available" : placeDetailJson.getResult().getTypes().size() > 0 ? placeDetailJson.getResult().getTypes().get(0) : "not available";
                    priceLevel = "2";
                    openingHours = placeDetailJson.getResult().getOpeningHours() == null ? false : placeDetailJson.getResult().getOpeningHours().getOpenNow();


                    objectArrayList.add(new NearbyPlaces(placeDetailJson.getResult().getFormattedAddress(), placeDetailJson.getResult().getInternationalPhoneNumber()
                            , url, placeDetailJson.getResult().getWebsite(), reviewArrayList, photoArrayList, placeDetailJson.getResult().getId(), placeDetailJson.getResult().getPlaceId()
                            , placeDetailJson.getResult().getName(), placeDetailJson.getResult().getVicinity(), String.valueOf(placeDetailJson.getResult().getRating())
                            , placeDetailJson.getResult().getGeometry().getLocation().getLat(), placeDetailJson.getResult().getGeometry().getLocation().getLng()
                            , openingHours, placeType, priceLevel, null));


                } else if (request == Constant.REQUEST.TOP_PLACES) {

                    TopPlaceJson topPlaceJson = gson.fromJson(data, TopPlaceJson.class);
                    nextPageToken = topPlaceJson.getNextPageToken() == null ? "null" : topPlaceJson.getNextPageToken();


                    for (int i = 0; i < topPlaceJson.getResults().size(); i++) {

                        ArrayList<Object> photoArrayList = new ArrayList<>();

                        if (topPlaceJson.getResults().get(i).getPhotos() == null)
                            continue;

                        Utility.Logger("Size of pic", String.valueOf(topPlaceJson.getResults().get(i).getPhotos().size()));

                        if (topPlaceJson.getResults().get(i).getPhotos().size() > 0)
                            photoArrayList = new ArrayList<Object>(topPlaceJson.getResults().get(i).getPhotos());


                        placeType = topPlaceJson.getResults().get(i).getTypes() == null ? "Not Available" : topPlaceJson.getResults().get(i).getTypes().size() > 0 ? topPlaceJson.getResults().get(i).getTypes().get(0) : "not available";
                        priceLevel = "2";
                        if (topPlaceJson.getResults().get(i).getOpeningHours() == null)
                            openingHours = false;
                        else {
                            Utility.Logger("Opening Hours", topPlaceJson.getResults().get(i).getOpeningHours() + " " + topPlaceJson.getResults().get(i).getOpeningHours().getOpenNow());
                            openingHours = topPlaceJson.getResults().get(i).getOpeningHours().getOpenNow() == null ? false : topPlaceJson.getResults().get(i).getOpeningHours().getOpenNow();
                        }


                        Utility.Logger("Price Level", priceLevel + " " + photoArrayList.size() + " ");

                        objectArrayList.add(new NearbyPlaces(topPlaceJson.getResults().get(i).getId(), topPlaceJson.getResults().get(i).getPlaceId()
                                , topPlaceJson.getResults().get(i).getName(), topPlaceJson.getResults().get(i).getFormattedAddress(), String.valueOf(topPlaceJson.getResults().get(i).getRating())
                                , topPlaceJson.getResults().get(i).getGeometry().getLocation().getLat(), topPlaceJson.getResults().get(i).getGeometry().getLocation().getLng()
                                , openingHours, photoArrayList, placeType, priceLevel, nextPageToken));


                    }


                } else if (request == Constant.REQUEST.WIKI) {

                    WikiJson wikiJson = gson.fromJson(data, WikiJson.class);

                    for (int i = 0; i < wikiJson.getQuery().getGeosearch().size(); i++) {

                        objectArrayList.add(new WikiObject(wikiJson.getQuery().getGeosearch().get(i).getPageid(), wikiJson.getQuery().getGeosearch().get(i).getTitle()
                                , wikiJson.getQuery().getGeosearch().get(i).getLat(), wikiJson.getQuery().getGeosearch().get(i).getLon()
                                , wikiJson.getQuery().getGeosearch().get(i).getDist()));

                    }


                } else if (request == Constant.REQUEST.NEARBY_PLACES_ONLY) {


                    NearbyJson nearbyJson = gson.fromJson(data, NearbyJson.class);

                    nextPageToken = nearbyJson.getNextPageToken() == null ? "null" : nearbyJson.getNextPageToken();

                    for (int i = 0; i < nearbyJson.getResults().size(); i++) {

                        ArrayList<Object> photoArrayList = new ArrayList<>();

                        if (nearbyJson.getResults().get(i).getPhotos() == null)
                            photoArrayList = new ArrayList<>();


                        placeType = nearbyJson.getResults().get(i).getTypes() == null ? "Not Available" : nearbyJson.getResults().get(i).getTypes().size() > 0 ? nearbyJson.getResults().get(i).getTypes().get(0) : "not available";
                        priceLevel = nearbyJson.getResults().get(i).getPriceLevel() == null ? "2" : String.valueOf(nearbyJson.getResults().get(i).getPriceLevel());
                        if (nearbyJson.getResults().get(i).getOpeningHours() == null)
                            openingHours = false;
                        else {
                            openingHours = nearbyJson.getResults().get(i).getOpeningHours().getOpenNow() == null ? false : nearbyJson.getResults().get(i).getOpeningHours().getOpenNow();
                        }

                        Utility.Logger("Price Level", priceLevel + " " + photoArrayList.size() + " " + placeType);

                        objectArrayList.add(new NearbyPlaces(nearbyJson.getResults().get(i).getId(), nearbyJson.getResults().get(i).getPlaceId()
                                , nearbyJson.getResults().get(i).getName(), nearbyJson.getResults().get(i).getVicinity(), String.valueOf(nearbyJson.getResults().get(i).getRating())
                                , nearbyJson.getResults().get(i).getGeometry().getLocation().getLat(), nearbyJson.getResults().get(i).getGeometry().getLocation().getLng()
                                , openingHours, photoArrayList, placeType, priceLevel, nextPageToken));


                    }

                }


                if (directoryCallback != null)
                    directoryCallback.onSuccess(objectArrayList);


            }
        }.execute();

    }


    /**
     * <p>It is used to send request for getting direction between locations</p>
     *
     * @param context
     * @param request
     * @param source
     * @param destination
     * @param avoidType
     * @param transportMode
     * @param isAlternative
     * @param waypoint
     * @param directionCallback
     * @param connectivityCallback
     */
    public ConnectionBuilder(Context context, Constant.REQUEST request, LatLng source, LatLng destination, String avoidType, String transportMode, boolean isAlternative, ArrayList<LatLng> waypoint, DirectionCallback directionCallback, ConnectivityCallback connectivityCallback) {

        if (!Utility.checkConnection(context)) {

            if (connectivityCallback != null)
                connectivityCallback.onInternetFailure();
            else
                Utility.showNoInternetDialog(context);
            return;
        }


        GoogleDirection.withServerKey(Constant.Credentials.GOOGLE_SERVER_KEY)
                .from(source)
                .to(destination)
                .waypoints(waypoint)
                .avoid(avoidType)
                .transportMode(transportMode)
                .alternativeRoute(isAlternative)
                .execute(directionCallback);


    }


    /**
     * <p>It is used to send request for getting direction between locations using MapBox Sdk</p>
     *
     * @param context
     * @param request
     * @param source
     * @param destination
     * @param avoidType
     * @param transportMode
     * @param isAlternative
     * @param waypoint
     * @param directionCallback
     * @param connectivityCallback
     */
    public ConnectionBuilder(Context context, Constant.REQUEST request, LatLng source, LatLng destination, String avoidType, String transportMode, boolean isAlternative, ArrayList<LatLng> waypoint, MapBoxRouteCallback directionCallback, ConnectivityCallback connectivityCallback) {

        if (!Utility.checkConnection(context)) {

            if (connectivityCallback != null)
                connectivityCallback.onInternetFailure();
            else
                Utility.showNoInternetDialog(context);
            return;
        }


        NavigationRoute.builder(context)
                .accessToken(Mapbox.getAccessToken())
                .origin(Point.fromLngLat(source.longitude, source.latitude))
                .destination(Point.fromLngLat(destination.longitude, destination.latitude))
                .alternatives(isAlternative)
                .profile(transportMode)
                .language(Locale.ENGLISH)
                .voiceUnits(DirectionsCriteria.METRIC)
                .build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                if (response.isSuccessful()) {


                    if (directionCallback != null)
                        directionCallback.getDirectionRoutes(response.body().routes());


                } else {
                    Utility.Toaster(context, response.errorBody().toString(), Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Utility.Toaster(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });

    }


    /**
     * <p>It is used to send request to Dark sky server for getting weather update</p>
     *
     * @param context
     * @param loadingText
     * @param darkSkyApiKey
     * @param latitude
     * @param longitude
     * @param weatherCallback
     * @param connectivityCallback
     */
    public ConnectionBuilder(Context context, String loadingText, String darkSkyApiKey, double latitude, double longitude, final WeatherCallback weatherCallback, ConnectivityCallback connectivityCallback) {

        if (!Utility.checkConnection(context)) {

            if (connectivityCallback != null)
                connectivityCallback.onInternetFailure();
            else
                Utility.showNoInternetDialog(context);

            return;
        }

        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(loadingText)
                .textTypeface(Font.ubuntu_medium_font(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);


        ForecastConfiguration configuration =
                new ForecastConfiguration.Builder(darkSkyApiKey)
                        .setDefaultUnit(Unit.SI)
                        /*.setCacheDirectory(getCacheDir())*/
                        .build();
        ForecastClient.create(configuration);

        dialog.show();

        ForecastClient.getInstance()
                .getForecast(latitude, longitude, new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, retrofit2.Response<Forecast> response) {

                        if (!response.isSuccessful())
                            return;

                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();

                        if (weatherCallback != null)
                            weatherCallback.onWeatherSuccess(Utility.getRoundedValue(response.body().getCurrently().getTemperature()) + " " + Constant.ImportantMessages.celcius_symbol, response.body().getCurrently().getSummary());

                    }

                    @Override
                    public void onFailure(Call<Forecast> forecastCall, Throwable t) {

                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();

                        if (weatherCallback != null) {
                            weatherCallback.onWeatherFailure(t.getMessage());
                        }
                    }

                });

    }


    /**
     * <p>Used to initialize network connection</p>
     */
    public static class CreateConnection {
        String serverUrl;
        String loadingText;
        Context context;
        String type;
        Constant.REQUEST request;
        DirectoryCallback dictionaryCallback;
        ConnectivityCallback connectivityCallback;

        public CreateConnection setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public CreateConnection setLoadingText(String loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        public CreateConnection setContext(Context context) {
            this.context = context;
            return this;
        }

        public CreateConnection setDictionaryCallback(DirectoryCallback dictionaryCallback) {
            this.dictionaryCallback = dictionaryCallback;
            return this;
        }

        public CreateConnection setType(String type) {
            this.type = type;
            return this;
        }

        public CreateConnection setRequest(Constant.REQUEST request) {
            this.request = request;
            return this;
        }

        public CreateConnection setConnectivityCallback(ConnectivityCallback connectivityCallback) {
            this.connectivityCallback = connectivityCallback;
            return this;
        }

        public ConnectionBuilder onCreate() {
            return new ConnectionBuilder(context, request, loadingText, serverUrl, type, dictionaryCallback, connectivityCallback);
        }

    }


    /**
     * <p>It is used to initialize weather connection request</p>
     */
    public static class CreateWeatherConnection {
        private Context context;
        private String loadingText;
        private double latitude;
        private double longitude;
        private String darkSkyApi;
        private WeatherCallback weatherCallback;
        private ConnectivityCallback connectivityCallback;


        public CreateWeatherConnection setContext(Context context) {
            this.context = context;
            return this;
        }

        public CreateWeatherConnection setLoadingText(String loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        public CreateWeatherConnection setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public CreateWeatherConnection setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public CreateWeatherConnection setDarkSkyApi(String darkSkyApi) {
            this.darkSkyApi = darkSkyApi;
            return this;
        }

        public CreateWeatherConnection setWeatherCallback(WeatherCallback weatherCallback) {
            this.weatherCallback = weatherCallback;
            return this;
        }

        public CreateWeatherConnection setConnectivityCallback(ConnectivityCallback connectivityCallback) {
            this.connectivityCallback = connectivityCallback;
            return this;
        }

        public ConnectionBuilder onCreate() {
            return new ConnectionBuilder(context, loadingText, darkSkyApi, latitude, longitude, weatherCallback, connectivityCallback);
        }


    }


    /**
     * <p>Used to initialize direction connection</p>
     */
    public static class CreateDirectionConnection {
        Context context;
        LatLng source;
        LatLng destination;
        String avoidType;
        String transportMode;
        boolean isAlternative;
        ArrayList<LatLng> waypoint = new ArrayList<>();
        DirectionCallback directionCallback;
        MapBoxRouteCallback mapBoxRouteCallback;
        ConnectivityCallback connectivityCallback;

        public CreateDirectionConnection setContext(Context context) {
            this.context = context;
            return this;
        }

        public CreateDirectionConnection setSource(LatLng source) {
            this.source = source;
            return this;
        }

        public CreateDirectionConnection setDestination(LatLng destination) {
            this.destination = destination;
            return this;
        }

        public CreateDirectionConnection setAvoidType(String avoidType) {
            this.avoidType = avoidType;
            return this;
        }

        public CreateDirectionConnection setTransportMode(String transportMode) {
            this.transportMode = transportMode;
            return this;
        }

        public CreateDirectionConnection setAlternative(boolean alternative) {
            isAlternative = alternative;
            return this;
        }

        public CreateDirectionConnection setDirectoryCallback(DirectionCallback directionCallback) {
            this.directionCallback = directionCallback;
            return this;
        }

        public CreateDirectionConnection setWaypoint(ArrayList<LatLng> waypoint) {
            this.waypoint = waypoint;
            return this;
        }

        public CreateDirectionConnection setConnectivityCallback(ConnectivityCallback connectivityCallback) {
            this.connectivityCallback = connectivityCallback;
            return this;
        }

        public CreateDirectionConnection setMapBoxRouteCallback(MapBoxRouteCallback mapBoxRouteCallback) {
            this.mapBoxRouteCallback = mapBoxRouteCallback;
            return this;
        }

        public ConnectionBuilder onCreate() {

            if (mapBoxRouteCallback == null)
                return new ConnectionBuilder(context, Constant.REQUEST.DIRECTION, source, destination, avoidType, transportMode, isAlternative, waypoint, directionCallback, connectivityCallback);
            else if (mapBoxRouteCallback != null)
                return new ConnectionBuilder(context, Constant.REQUEST.DIRECTION, source, destination, avoidType, transportMode, isAlternative, waypoint, mapBoxRouteCallback, connectivityCallback);

            return null;
        }
    }


    /**
     * <p>It is the background processing and send request to Server in Background</p>
     */
    private abstract class InitRequest extends AsyncTask<String, String, String> {
        public String serverUrl, loadingMessage, type;
        public ACProgressFlower dialog;
        public Context context;

        public InitRequest(Context context, String serverUrl, String loadingMessage, String type) {
            this.serverUrl = serverUrl;
            this.loadingMessage = loadingMessage;
            this.type = type;
            this.context = context;

            Utility.Logger("Data in One", "Server " + serverUrl + " Loading Message " + loadingMessage + " Type " + type);


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text(loadingMessage)
                    .textTypeface(Font.ubuntu_medium_font(context))
                    .fadeColor(Color.DKGRAY).build();
            dialog.setCanceledOnTouchOutside(false);
            if (context != null)
                dialog.show();

            Utility.Logger("Data in Constructor", "Server " + serverUrl + " Loading Message " + loadingMessage + " Type " + type);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Utility.Logger("Data Getting Data", s + " ");

            onSuccess(s, dialog);

            /*if (dialog.isShowing())
                dialog.dismiss();*/
        }

        @Override
        protected String doInBackground(String... params) {


            return Connection.makeRequest(serverUrl, type);
        }

        public abstract void onSuccess(String data, ACProgressFlower dialog);
    }


}

