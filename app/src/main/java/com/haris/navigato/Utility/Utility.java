package com.haris.navigato.Utility;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.MyApplication;
import com.haris.navigato.ObjectUtil.PlaceData;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.ObjectUtil.SpinnerItem;
import com.haris.navigato.ObjectUtil.TimeParameter;
import com.haris.navigato.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 5/20/2018.
 */

public class Utility {
    static int i = 0;
    static double EarthRadius = 6371.0d;

    /**
     * 1
     * <p>Show the Toast in Activity</p>
     *
     * @param context context of activity or either Fragment
     * @param message your message you want to show in Toast
     * @param length  length of Toast
     */
    public static void Toaster(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void Logger(String tag, String message) {
        //Log.e(tag, message);
    }


    public static void extraData(String TAG, String message) {
        int maxLogSize = 2000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            //android.util.Log.e(TAG, message.substring(start, end));
        }
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out " + getStringFromRes(context, R.string.app_name) + " app at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>It is used to open playstore app link for rating</p>
     *
     * @param context
     */
    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    /**
     * <p>It is used to open share location dialog using Intent</p>
     *
     * @param context
     * @param subject
     * @param body
     */
    public static void shareLocation(Context context, String subject, String body) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    /**
     * <p>Check the Connection status either it is available or not</p>
     *
     * @param context reference from activity or either fragment
     * @return true if internet connection available
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) { // connected to the internet
            ////Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }


    /**
     * <p>Check any specific text either it's null or not</p>
     *
     * @param text text about which we want to know about
     * @return true if text is Empty
     */
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }


    /**
     * <p>It used to show Interstitial Ad of Admob</p>
     *
     * @param context
     */
    public static void showInterstitialAd(Context context) {


        final InterstitialAd mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(Constant.Credentials.ADMOB_INTERSTITIAL_ID);
        mInterstitial.loadAd(new AdRequest.Builder()
                .addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build());
        mInterstitial.show();

        if (!mInterstitial.isLoaded()) {
            AdRequest adRequest1 = new AdRequest.Builder()

                    .addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID)
                    .build();
            mInterstitial.loadAd(adRequest1);
        }
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitial.show();
            }

        });

    }


    /**
     * <p>It show alert to User about Internet Availability</p>
     *
     * @param context
     */
    public static void showNoInternetDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.internet_not_available_dialog);

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


                dialog.dismiss();
            }
        });

        dialog.show();

    }


    /**
     * <p>It is used either Smart phone have Compass feature or not</p>
     *
     * @param context
     * @return
     */
    public static boolean checkIfSensorsAvailable(Context context) {
        boolean result = false;
        PackageManager pm = context.getPackageManager();
        boolean compass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
        boolean accelerometer = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        if (!compass && !accelerometer) {
            result = false;
        } else if (!compass) {
            result = false;
        } else if (!accelerometer) {
            result = false;
        }

        if (compass && accelerometer)
            result = true;

        return result;
    }


    /**
     * <p>It is used to open web url</p>
     *
     * @param context
     * @param url
     */
    public static void openWebUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }


    /**
     * <p>It is used to copy textual data </p>
     *
     * @param context
     * @param text
     */
    public static void copyData(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Place Address", text);
        clipboard.setPrimaryClip(clip);

    }


    /**
     * <p>Get the City Name from Given Coordinates</p>
     *
     * @param context reference from activity or either Fragment
     * @param lat     lattitude of location
     * @param lon     longitude of location
     * @return cityName at given Coordinates
     */
    public static String getCityNameByCoordinates(Context context, double lat, double lon) {
        Geocoder mGeocoder = new Geocoder(context);
        String result;
        List<Address> addresses = null;
        Utility.Logger("Location", "Latitude " + lat + " Longitude " + lon);
        try {
            addresses = mGeocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            result = isEmptyString(addresses.get(0).getFeatureName()) ? addresses.get(0).getSubLocality() + "," + addresses.get(0).getLocality() : addresses.get(0).getFeatureName() + " , " + addresses.get(0).getSubLocality();
            return result;

        }
        return null;
    }


    /**
     * <p>Get the Address from Given Coordinates</p>
     *
     * @param context reference from activity or either Fragment
     * @param lat     lattitude of location
     * @param lon     longitude of location
     * @return cityName at given Coordinates
     */
    public static String getAddressByCoordinates(Context context, double lat, double lon) {
        Geocoder mGeocoder = new Geocoder(context);
        String result;
        List<Address> addresses = null;
        Utility.Logger("Location", "Latitude " + lat + " Longitude " + lon);
        try {
            addresses = mGeocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            result = addresses.get(0).getAddressLine(0);
            ;
            return result;

        }
        return null;
    }


    /**
     * <p>Get the City Name from Given Coordinates</p>
     *
     * @param context reference from activity or either Fragment
     * @param lat     lattitude of location
     * @param lon     longitude of location
     * @return cityName at given Coordinates
     */
    public static String getCityNameByCoordinates(Context context, double lat, double lon, boolean isCityName) {
        Geocoder mGeocoder = new Geocoder(context);
        String result;
        List<Address> addresses = null;
        Utility.Logger("Location", "Latitude " + lat + " Longitude " + lon);
        try {
            addresses = mGeocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            result = addresses.get(0).getLocality();
            return result;

        }
        return null;
    }


    /**
     * <p>It is used to open phone dialer with number</p>
     *
     * @param context
     * @param phone
     */
    public static void openDialer(Context context, String phone) {
        String mobileNo = phone;

        Intent intent = new Intent(Intent.ACTION_DIAL).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + mobileNo));
        context.startActivity(intent);
    }


    /**
     * <p>It is used to create Feature Places List for Quick Over view
     * which is used for showing Feature places</p>
     *
     * @return
     */
    public static ArrayList<PlaceData> getFeaturedPlaces() {
        ArrayList<PlaceData> placeDataArrayList = new ArrayList<>();

        placeDataArrayList.add(new PlaceData("Atm", "atm", R.drawable.atm_bg));
        placeDataArrayList.add(new PlaceData("Clothing Store", "clothing_store", R.drawable.clothing_bg));

        placeDataArrayList.add(new PlaceData("Restaurant", "restaurant", R.drawable.hotel_bg));
        placeDataArrayList.add(new PlaceData("Bakery", "bakery", R.drawable.bakery_bg));
        placeDataArrayList.add(new PlaceData("Doctor", "doctor", R.drawable.doctor_bg));
        placeDataArrayList.add(new PlaceData("Shopping Malls", "shopping_mall", R.drawable.shopping_bg));


        placeDataArrayList.add(new PlaceData("Library", "library", R.drawable.library_bg));
        placeDataArrayList.add(new PlaceData("Bank", "bank", R.drawable.money_bg));
        placeDataArrayList.add(new PlaceData("Parking", "parking", R.drawable.parking_bg));
        placeDataArrayList.add(new PlaceData("Police", "police", R.drawable.polic_bg));
        placeDataArrayList.add(new PlaceData("Cinema", "movie_theater", R.drawable.cinema_bg));

        placeDataArrayList.add(new PlaceData("Saloon", "beauty_salon", R.drawable.salon_bg));


        placeDataArrayList.add(new PlaceData("Petrol Station", "gas_station", R.drawable.petrol_pump_bg));
        placeDataArrayList.add(new PlaceData("School", "school", R.drawable.school_bg));
        placeDataArrayList.add(new PlaceData("Gym", "gym", R.drawable.gym_bg));

        placeDataArrayList.add(new PlaceData("Shoe Store", "shoe_store", R.drawable.shoe_store_bg));
        placeDataArrayList.add(new PlaceData("Casino", "casino", R.drawable.casino_bg));
        placeDataArrayList.add(new PlaceData("Car Wash", "car_wash", R.drawable.car_wash_bg));

        return placeDataArrayList;
    }


    /**
     * <p>Contain list of POI icons for POI Browser</p>
     *
     * @return
     */
    public static HashMap<String, Integer> getPOIIcon() {
        HashMap<String, Integer> directionIcon = new HashMap<>();

        directionIcon.put("restaurant", R.drawable.ar_restaurant_icon);
        directionIcon.put("atm", R.drawable.ar_atm_icon);
        directionIcon.put("hospital", R.drawable.ar_hospital_icon);
        directionIcon.put("lodging", R.drawable.ar_hotel_icon);
        directionIcon.put("Movie Theater", R.drawable.ar_cinema_icon);

        directionIcon.put("cafe", R.drawable.ar_cafe_icon);
        directionIcon.put("bakery", R.drawable.ar_bakery_icon);
        directionIcon.put("Bus Station", R.drawable.ar_bus_station_icon);
        directionIcon.put("Shopping Mall", R.drawable.ar_shopping_center_icon);
        directionIcon.put("pharmacy", R.drawable.ar_pharmacy_icon);

        directionIcon.put("park", R.drawable.ar_park_icon);
        directionIcon.put("Petrol Station", R.drawable.ar_gas_station_icon);
        directionIcon.put("Car Wash", R.drawable.ar_car_wash_icon);
        directionIcon.put("other", R.drawable.ar_location_icon);


        return directionIcon;
    }


    /**
     * <p>It is used to Save Image into App Private Memorys</p>
     *
     * @param bitmapImage
     * @param name
     * @return
     */
    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);

        Utility.Logger("Image Path", mypath.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.toString();
    }


    /**
     * <p>It is used to Calculate distance between two locations in meters</p>
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static float distance(float lat_a, float lng_a, float lat_b, float lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }


    /**
     * <p>Used to convert Double into Float</p>
     *
     * @param d
     * @return
     */
    public static float DoubleToFloat(double d) {
        float value;
        value = Float.parseFloat(Double.toString(d));
        return value;
    }


    /**
     * <p>Used to create place description for showing in detail</p>
     *
     * @param placeParameter
     * @return
     */
    public static String placeDescriptionCreator(PlaceParameter placeParameter) {
        String placeType = placeParameter.getPlaceType().contains("_") ? placeParameter.getPlaceType().replaceAll("_", " ") : placeParameter.getPlaceType();
        String priceLevel = Integer.parseInt(placeParameter.getPlacePriceLevel()) == 1 ? "cheap" : Integer.parseInt(placeParameter.getPlacePriceLevel()) == 2 ? "economic"
                : Integer.parseInt(placeParameter.getPlacePriceLevel()) == 3 ? "little bit expensive" : Integer.parseInt(placeParameter.getPlacePriceLevel()) == 4 ? " expensive" : "Not available";
        String description = placeParameter.getPlaceName() + " is one of the Best " + Utility.capitalize(placeType) + " of the City having the rating of " + placeParameter.getPlaceRating()
                + " star & price level is " + priceLevel;
        return description;
    }


    /**
     * <p>It is used to Capitalize the Word first letter</p>
     *
     * @param capString
     * @return
     */
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


    /**
     * <p>It is used to get Colour from Resource file</p>
     *
     * @param context
     * @param colour
     * @return
     */
    public static int getColourFromRes(Context context, int colour) {
        return context.getResources().getColor(colour);
    }


    /**
     * <p>It is used to get String values from Resource file</p>
     *
     * @param context
     * @param label
     * @return
     */
    public static String getStringFromRes(Context context, int label) {
        return context.getResources().getString(label);
    }


    /**
     * <p>It is used to Calculate Petrol for covering any distance</p>
     *
     * @param meters
     * @return
     */
    public static String calculatePetrol(double meters) {
        double petrol = 8.9;
        double kilometers = 100;
        double perKmConsumption = petrol / kilometers;

        double result = convertMeterIntoKm(String.valueOf(meters)) * perKmConsumption;
        ///double result = meters * perKmConsumption;

        return String.valueOf(getRoundedValue(result));
    }


    /**
     * <p>It is used to convert meter into Km</p>
     *
     * @param meters
     * @return
     */
    public static double convertMeterIntoKm(String meters) {
        Utility.Logger("Meters", meters);
        double result = Double.parseDouble(meters);
        double km = result / 1000;
        return Double.parseDouble(getRoundedValue(km));
    }


    /**
     * <p>Used to round off double value</p>
     *
     * @param value
     * @return
     */
    public static String getRoundedValue(double value) {
        return String.valueOf(Math.round(value * 100.0) / 100.0);
    }


    /**
     * <p>Used to round off String value</p>
     *
     * @param value
     * @return
     */
    public static String getRoundedValue(String value) {
        Double val = Double.valueOf(value);
        return getRoundedValue(val);
    }


    /**
     * <p>It is used to get & popular items into Spinner for
     * the Selection of Conveyance</p>
     *
     * @return
     */
    public static ArrayList<SpinnerItem> getSpinnerItems() {
        ArrayList<SpinnerItem> spinnerItemArrayList = new ArrayList<>();
        spinnerItemArrayList.add(new SpinnerItem(R.drawable.travel_icon, "Select Conveyance"));
        spinnerItemArrayList.add(new SpinnerItem(R.drawable.car_icon, "By Car"));
        spinnerItemArrayList.add(new SpinnerItem(R.drawable.bicycle_icon, "By Bicycle"));
        spinnerItemArrayList.add(new SpinnerItem(R.drawable.walking_icon, "By Walking"));
        return spinnerItemArrayList;
    }


    /**
     * <p>It is used to check Location provider enable or disable</p>
     *
     * @param context
     * @return
     */
    public static boolean isLocationProviderAvailable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (gps_enabled || network_enabled) {
            return true;
        }

        return false;
    }


    /**
     * <p>It is used to check Location provider enable or disable</p>
     *
     * @param context
     * @return
     */
    public static boolean isLocationProviderAvailable(Context context, boolean isGps) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
            Utility.Logger("Error Gps", ex.getMessage());
        }

        Utility.Logger("Gps Status", String.valueOf(gps_enabled));

        if (gps_enabled) {
            return true;
        }

        return false;
    }


    /**
     * <p>It is used to calculate Speed using Coordinates</p>
     *
     * @param currentLocation
     * @param oldLocation
     * @return
     */
    public static double getSpeed(Location currentLocation, Location oldLocation) {
        double newLat = currentLocation.getLatitude();
        double newLon = currentLocation.getLongitude();

        double oldLat = oldLocation.getLatitude();
        double oldLon = oldLocation.getLongitude();

        if (currentLocation.hasSpeed()) {
            return currentLocation.getSpeed();
        } else {
            double radius = 6371000;
            double dLat = Math.toRadians(newLat - oldLat);
            double dLon = Math.toRadians(newLon - oldLon);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(newLat)) * Math.cos(Math.toRadians(oldLat)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double distance = Math.round(radius * c);

            double timeDifferent = currentLocation.getTime() - oldLocation.getTime();
            return Double.parseDouble(getRoundedValue(distance / timeDifferent));
        }
    }


    /**
     * <p>It is used to convert Second into Time</p>
     *
     * @param timeParameter
     * @return
     */
    public static String secondIntoTime(TimeParameter timeParameter) {

        int hours = timeParameter.getTime() / 3600;
        int minutes = (timeParameter.getTime() % 3600) / 60;
        int seconds = timeParameter.getTime() % 60;

        if (timeParameter.isFulltime()) {
            if ((hours > 0))
                return twoDigitString(hours) + " hrs " + twoDigitString(minutes) + " min";
            else
                return minutes + " min";
        }

        return hours + " " + minutes;

    }


    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }


    /**
     * calculates the distance between two locations in MILES
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        Utility.Toaster(MyApplication.getInstance(), "Miles from Method " + dist, Toast.LENGTH_SHORT);

        return dist; // output distance, in MILES
    }


    /**
     * <p>It is used to get Text From Textview</p>
     *
     * @param view
     * @return
     */
    public static String getTextFromView(TextView view) {
        return view.getText().toString();
    }


    /**
     * <p>It is used to format Query for finding top places</p>
     *
     * @param query
     * @return
     */
    public static String formatTopPlaceQuery(String query) {
        String result = null;
        result = query.replaceAll(" ", "+");
        return result;
    }


    /**
     * <p>It is used to get Tourism Data for using in
     * tourism screen</p>
     *
     * @return
     */
    public static ArrayList<PlaceData> getExploration() {
        ArrayList<PlaceData> placeDataArrayList = new ArrayList<>();

        placeDataArrayList.add(new PlaceData("Wikipedia", "Wikipedia", R.drawable.wiki_icon));
        placeDataArrayList.add(new PlaceData("Bus Travel", Utility.formatTopPlaceQuery("Top Bus Travel in " + Constant.getCityName()), R.drawable.travel_icon));
        placeDataArrayList.add(new PlaceData("Hotel", Utility.formatTopPlaceQuery("Best Hotel in " + Constant.getCityName()), R.drawable.doorknob_icon));
        placeDataArrayList.add(new PlaceData("Restaurant", Utility.formatTopPlaceQuery("Best Restaurant in " + Constant.getCityName()), R.drawable.restaurant_icon));
        placeDataArrayList.add(new PlaceData("Bakery", Utility.formatTopPlaceQuery("Best Bakery in " + Constant.getCityName()), R.drawable.bakery_icon));
        placeDataArrayList.add(new PlaceData("Hospital", Utility.formatTopPlaceQuery("Best Hospital of " + Constant.getCityName()), R.drawable.hospital_icon));
        placeDataArrayList.add(new PlaceData("Shopping Malls", Utility.formatTopPlaceQuery("Top Shopping Mall in " + Constant.getCityName()), R.drawable.shopping_mall_icon));
        placeDataArrayList.add(new PlaceData("Gym", Utility.formatTopPlaceQuery("Best Gym in " + Constant.getCityName()), R.drawable.gym_icon));
        placeDataArrayList.add(new PlaceData("Clothing Brand", Utility.formatTopPlaceQuery("Top Clothing brands in " + Constant.getCityName()), R.drawable.bow_icon));


        return placeDataArrayList;
    }


    /**
     * <p>It is used to get Current Time</p>
     *
     * @return
     */
    public static String getCurrentTime() {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        date = simpleDateFormat.format(new Date());
        return date;
    }


}
