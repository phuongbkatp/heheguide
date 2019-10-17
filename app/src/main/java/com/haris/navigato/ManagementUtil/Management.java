package com.haris.navigato.ManagementUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import com.haris.navigato.ConnectionUtil.ConnectionBuilder;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.DatabaseUtil.DatabaseHandler;
import com.haris.navigato.DatabaseUtil.QueryFactory;
import com.haris.navigato.DatabaseUtil.QueryRunner;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.DirectionParameter;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.ObjectUtil.PrefObject;
import com.haris.navigato.ObjectUtil.UniversalVariable;
import com.haris.navigato.ObjectUtil.WeatherParameter;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

/**
 * Created by hp on 5/21/2018.
 */

public class Management {
    Context context;
    SQLiteOpenHelper sqLiteOpenHelper;
    QueryRunner queryRunner;
    SharedPreferences sharedPreferences;

    public Management(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constant.SharedPref.PREF_NAME, Context.MODE_PRIVATE);
        sqLiteOpenHelper = new DatabaseHandler(context);
        queryRunner = new QueryRunner();

    }


    /**
     * <p>It is used to prepare request for Sending to Server</p>
     *
     * @param request
     * @param placeParameter
     */
    public void sendServerRequest(Constant.REQUEST request, PlaceParameter placeParameter) {
        String loadingText = null;
        String serverUrl = null;
        String nearByUrl = null;

        if (request == Constant.REQUEST.NEARBY) {

            Constant.setLatitude(Double.valueOf(placeParameter.getLatitude()));
            Constant.setLongitude(Double.valueOf(placeParameter.getLongitude()));


            if (Utility.isEmptyString(placeParameter.getNextPageToken()))
                serverUrl = String.format(Constant.ServerInformation.NEARBY_PLACES_URL, placeParameter.getLatitude(), placeParameter.getLongitude(), placeParameter.getRadius(), placeParameter.getPlaceType());
            else {
                serverUrl = String.format(Constant.ServerInformation.NEARBY_PLACES_KEYWORD_URL, placeParameter.getLatitude(), placeParameter.getLongitude(), placeParameter.getRadius(), placeParameter.getPlaceType(), placeParameter.getNextPageToken());

            }
            loadingText = Constant.ImportantMessages.nearby_places;

        } else if (request == Constant.REQUEST.PLACE_DETAIL) {

            serverUrl = String.format(Constant.ServerInformation.PLACE_DETAIL_URL, placeParameter.getPlaceId());
            loadingText = Constant.ImportantMessages.place_detail;

        } else if (request == Constant.REQUEST.TOP_PLACES) {

            Constant.setLatitude(Double.valueOf(placeParameter.getLatitude()));
            Constant.setLongitude(Double.valueOf(placeParameter.getLongitude()));

            if (Utility.isEmptyString(placeParameter.getNextPageToken()))
                serverUrl = String.format(Constant.ServerInformation.TOP_PLACES, placeParameter.getPlaceName());
            else
                serverUrl = String.format(Constant.ServerInformation.TOP_PLACES_NEXT_TOKEN, placeParameter.getPlaceName(), placeParameter.getNextPageToken());

            loadingText = Constant.ImportantMessages.top_place;

        } else if (request == Constant.REQUEST.WIKI) {

            serverUrl = String.format(Constant.ServerInformation.WIKI_API_LINK, placeParameter.getLatitude(), "%7C", placeParameter.getLongitude());
            loadingText = Constant.ImportantMessages.wiki_loading;

        } else if (request == Constant.REQUEST.NEARBY_PLACES_ONLY) {


            if (Utility.isEmptyString(placeParameter.getNextPageToken()))
                serverUrl = String.format(Constant.ServerInformation.NEARBY_PLACES_URL_FOR_NEARBY, placeParameter.getLatitude(), placeParameter.getLongitude(), placeParameter.getRadius());
            /*else {
                serverUrl = String.format(Constant.ServerInformation.NEARBY_PLACES_KEYWORD_URL, placeParameter.getLatitude(), placeParameter.getLongitude(), placeParameter.getRadius(), placeParameter.getPlaceType(), placeParameter.getNextPageToken());

            }*/
            loadingText = Constant.ImportantMessages.nearby_places;

        }


        new ConnectionBuilder.CreateConnection()
                .setContext(context)
                .setRequest(request)
                .setServerUrl(serverUrl)
                .setLoadingText(loadingText)
                .setType("GET")
                .setDictionaryCallback(placeParameter.getCallback())
                .setConnectivityCallback(placeParameter.getConnectivityCallback())
                .onCreate();


    }


    /**
     * <p>It is used to prepare request for sending direction request to Mapbox Server</p>
     *
     * @param request
     * @param directionParameter
     */
    public void sendRequestForMapboxDirections(Constant.REQUEST request, DirectionParameter directionParameter) {

        if (request == Constant.REQUEST.DIRECTION) {

            new ConnectionBuilder.CreateDirectionConnection()
                    .setContext(context)
                    .setSource(directionParameter.getSource())
                    .setDestination(directionParameter.getDestination())
                    .setAvoidType(directionParameter.getAvoidType())
                    .setTransportMode(directionParameter.getTrasportMode())
                    .setConnectivityCallback(directionParameter.getConnectivityCallback())
                    .setMapBoxRouteCallback(directionParameter.getMapBoxRouteCallback())
                    .setAlternative(directionParameter.isAlternative())
                    .setWaypoint(directionParameter.getWaypoint())
                    .onCreate();

        }


    }


    /**
     * <p>It is used to store & retrieve data from Database</p>
     *
     * @param database_event
     * @param databaseQueryObject
     * @return
     */
    public ArrayList<Object> getDataFromDb(Constant.DATABASE_EVENT database_event, Constant.DATABASE_FUNCTION database_function, DatabaseQueryObject databaseQueryObject, HistoryQueryObject historyQueryObject) {

        ArrayList<Object> objectArrayList = new ArrayList<>();
        String query = QueryFactory.getQuery(database_event, database_function, databaseQueryObject, historyQueryObject);

        if (database_event == Constant.DATABASE_EVENT.RETRIEVE || database_event == Constant.DATABASE_EVENT.SINGLE) {

            objectArrayList.addAll(queryRunner.getAll(database_function, query, sqLiteOpenHelper));

        } else if (database_event == Constant.DATABASE_EVENT.DELETE) {

            queryRunner.getStatus(query, sqLiteOpenHelper);

        } else if (database_event == Constant.DATABASE_EVENT.INSERT) {

            queryRunner.getStatus(query, sqLiteOpenHelper);

        }

        return objectArrayList;
    }


    /**
     * <p>It is used to send weather request for finding specific place weather</p>
     */
    public void sendWeatherRequest(WeatherParameter weatherParameter) {

        String loadingText = Constant.ImportantMessages.LOADING_WEATHER;
        String darkSkyApiKey = Constant.Credentials.DARK_SKY_API_KEY;

        new ConnectionBuilder.CreateWeatherConnection()
                .setContext(context)
                .setLatitude(weatherParameter.getLatitude())
                .setLongitude(weatherParameter.getLongitude())
                .setLoadingText(loadingText)
                .setDarkSkyApi(darkSkyApiKey)
                .setConnectivityCallback(weatherParameter.getConnectivityCallback())
                .setWeatherCallback(weatherParameter.getWeatherCallback())
                .onCreate();


    }


    /**
     * <p>It is used to save Data into Shared Preference</p>
     *
     * @param prefObject
     */
    public void saveDataIntoPref(PrefObject prefObject) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (prefObject.getSharedPref() == Constant.SHARED_PREF.REMOVE_AD) {
            editor.putBoolean(Constant.SharedPref.REMOVE_AD, prefObject.isRemoveAd());
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.AR_NAV) {
            editor.putBoolean(Constant.SharedPref.AR_NAV, prefObject.isArNav());
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.HUD_VIEW) {
            editor.putBoolean(Constant.SharedPref.HUD_VIEW, prefObject.isHud());
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.COVERAGE) {
            editor.putString(Constant.SharedPref.COVERAGE, prefObject.getCoverage());
        }

        editor.commit();

    }


    /**
     * <p>It is used to retrieve Data from Shared Preference</p>
     *
     * @param prefObject
     * @return
     */
    public UniversalVariable getDataFromPref(PrefObject prefObject) {

        UniversalVariable result = new UniversalVariable();

        if (prefObject.getSharedPref() == Constant.SHARED_PREF.REMOVE_AD) {
            result.condition = sharedPreferences.getBoolean(Constant.SharedPref.REMOVE_AD, false);
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.AR_NAV) {
            result.condition = sharedPreferences.getBoolean(Constant.SharedPref.AR_NAV, false);
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.HUD_VIEW) {
            result.condition = sharedPreferences.getBoolean(Constant.SharedPref.HUD_VIEW, false);
        } else if (prefObject.getSharedPref() == Constant.SHARED_PREF.COVERAGE) {
            result.result = sharedPreferences.getString(Constant.SharedPref.COVERAGE, "4500");
        }

        Utility.Logger("Condition", result.condition + " ");

        return result;
    }


}
