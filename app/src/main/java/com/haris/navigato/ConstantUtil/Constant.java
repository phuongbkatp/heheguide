package com.haris.navigato.ConstantUtil;

import com.haris.navigato.InterfaceUtil.LocationCallback;
import com.haris.navigato.ObjectUtil.UniversalVariable;
import com.haris.navigato.R;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

/**
 * Created by hp on 5/20/2018.
 */

public class Constant {

    static Double latitude;
    static Double longitude;
    static String cityName;
    static String weather;
    static DirectionsRoute directionsRoute;
    static UniversalVariable universalVariable;
    static LocationCallback locationCallback;


    public static enum REQUEST {NEARBY, DIRECTION, TOP_PLACES, PLACE_DETAIL, WIKI, NEARBY_PLACES_ONLY, SNAP_TO_ROAD}

    public static enum EVENTS {FEATURE_PLACE, PLACE}

    public static enum DATABASE_EVENT {INSERT, RETRIEVE, DELETE, SINGLE}

    public static enum DATABASE_FUNCTION {FAVOURITE, HISTORY}

    public static enum FRAGMENT_TYPE {HOME, FAVOURITES, HISTORY, SETTING}

    public static enum LOCATION_SELECTOR {SOURCE_LOCATION, DESTINATION_LOCATION}

    public static enum SHARED_PREF {REMOVE_AD, AR_NAV, HUD_VIEW, COVERAGE}


    /**
     * <p>It contain all Server Url</p>
     */
    public static class ServerInformation {

        public static String NEARBY_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%s&type=%s&key=" + Credentials.GOOGLE_API_KEY;
        public static String NEARBY_PLACES_KEYWORD_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%s&type=%s&key=" + Credentials.GOOGLE_API_KEY + "&pagetoken=%s";
        public static String NEARBY_PLACES_URL_FOR_NEARBY = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%s&key=" + Credentials.GOOGLE_API_KEY;
        public static String GOOGLE_DRIVE_LINK = "https://docs.google.com/uc?id=";
        public static String GOOGLE_MAP_PHOTO_REFERENCES = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=%s&key=" + Credentials.GOOGLE_API_KEY_PHOTO;
        public static String PLACE_DETAIL_URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=" + Credentials.GOOGLE_API_KEY;
        public static String TOP_PLACES = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s&key=" + Credentials.GOOGLE_API_KEY_TOP_PLACES;
        public static String TOP_PLACES_NEXT_TOKEN = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s&key=" + Credentials.GOOGLE_API_KEY_TOP_PLACES + "&pagetoken=%s";
        public static String WIKI_API_LINK = "https://en.wikipedia.org/w/api.php?format=json&action=query&list=geosearch&gscoord=%s%s%s&gsradius=10000&gslimit=40";
        public static String WIKIPEDIA_LINK = "http://en.wikipedia.org/?curid=%s";
        public static String googleLink = "http://maps.google.com/maps?q=%s,%s&ll=%s,%s&z=17";
        public static String googleMapLink = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=%s";

    }


    /**
     * <p>It contain list of place holder icons</p>
     */
    public static class PlaceHolder {
        public static int place_holder = R.drawable.place_holder_icon;
    }


    /**
     * <p>It contain all of the API keys from different Services</p>
     */
    public static class Credentials {
        public static String GOOGLE_API_KEY = "AIzaSyAGW6xw4MhGjqbe8QTj4CWVhe2SCIQeLok";
        public static String GOOGLE_API_KEY_PHOTO = "AIzaSyAGW6xw4MhGjqbe8QTj4CWVhe2SCIQeLok";
        public static String GOOGLE_API_KEY_TOP_PLACES = "AIzaSyAGW6xw4MhGjqbe8QTj4CWVhe2SCIQeLok";
        public static String GOOGLE_SERVER_KEY = "AIzaSyAGW6xw4MhGjqbe8QTj4CWVhe2SCIQeLok";

        public static String ADMOB_TEST_DEVICE_ID = "ADMOB_TEST_DEVICE_ID";
        public static String ADMOB_APP_ID = "ADMOB_APP_ID";
        public static String ADMOB_INTERSTITIAL_ID = "ADMOB_INTERSTITIAL_ID";

        public static String DARK_SKY_API_KEY = "a404322d97c00e56a5c4479945a39325";
        public static String MAP_BOX_API_KEY = "pk.eyJ1Ijoibmd1eWVudGh1eWxpbmhtaW45MiIsImEiOiJjazF1eXZ1YjEwNHpoM2pwNHFzNTU4N3g5In0.fhBB0-_N0VTn1zTpvK-KNg";
        public static String UBER_CLIENT_ID = "uber_client_id";
        public static String UBER_CLIENT_SECRET = "uber_client_secret";
        public static String UBER_SERVER_TOKEN = "uber_server_token";


    }


    /**
     * <p>It contain all of the Important Messages</p>
     */
    public static class ImportantMessages {
        public static final String CONNECTION_ERROR = "Connection Error";
        public static String top = "Top";
        public static String place = "Places";
        public static String restaurant = "Restaurants";
        public static String thing = "Things";
        public static String ToDo = "To Do";
        public static String briefly = "Briefly";
        public static String explore = "Explore City";
        public static String nearest = "Nearest";
        public static String more = "More";
        public static String nearby_places = "Nearest Places";
        public static String loading = "Loading";
        public static String place_detail = "Place History";
        public static String top_place = "Loading Places";
        public static String miles = "Km";
        public static String nearby = "Away";
        public static String no_review = "There is no review available";
        public static String price_level_label = "Price Level";
        public static String litre_label = "Litre";
        public static String car_label = "Car";
        public static String bicycle_label = "Bicycle";
        public static String walk_label = "Walking";
        public static String no_favourite = "No Favourites";
        public static String no_favourite_tagline = "You did not favourites anything until yet\nBookmark your favourite places";
        public static String no_history = "No Location";
        public static String no_history_tagline = "You did not travel anywhere until yet \n Start travelling now! ";
        public static String celcius_symbol = "\u00b0C";
        public static String LOADING_WEATHER = "Loading Weather";
        public static String wiki_loading = "Loading Wikipedia";
    }


    /**
     * <p>It contain all of the Toast messages</p>
     */
    public static class ToastMessage {
        public static String NO_INTERNET_MESSAGE = "No Internet Connection";
        public static String EMPTY_BOX = "Kindly write any word";
        public static String NO_DATA = "No Result";
        public static String LOCATION_FROM_HISTORY = "";
        public static String SELECT_DESTINATION = "First , Select destination";
        public static String WEATHER_ERROR = "There is an error while retrieving Weather Report";
        public static String TURN_ON_AR = "Turn on Augmented Navigation from Setting";
        public static String TURN_ON_GPS = "Turn on Gps first";
        public static String CAMERA_REQUIRE_PERMISSION = "You did not allow Camera permission";
        public static String EXTERNAL_REQUIRE_PERMISSION = "You did not allow Sd Card permisssion";
        public static String LOCATION_REQUIRE_PERMISSION = "You did not allow Location permission";
        public static String RES_START_APP = "Restart app , there is a problem in app";
        public static String DID_NOT_SUPPORT_VERSION = "Augmented Reality did not support below Lollipop version";
        public static String DID_NOT_HAVE_COMPASS = "It did not have Compass feature";
    }


    /**
     * <p>It contain all of the Menu labels</p>
     */
    public static class MenuText {
        public static String HOME_MENU_TEXT = "Dictionary4u";
        public static String PLACE_DETAIL_TEXT = "Place Detail";
        public static String REVIEW_TEXT = "List of Reviews";
        public static String HOME_TEXT = "Home";
        public static String FAVOURITE_TEXT = "Favourites";
        public static String HISTORY_TEXT = "History";
        public static String AR_NAVIGATION_TEXT = "Ar Navigation";
        public static String AR_POI_BROWSER = "Ar Poi Browser";
        public static String SETTING_TEXT = "Setting";
        public static String TOURISM_TEXT = "Tourism";
        public static String PLACES_NEAR_YOU = "Nearby Places";
        public static String ROUTES = "Route & Directions";
        public static String NAVIGATION = "Navigation";
        public static String LIST_OF_PLACES = "List of Places";
        public static String FROM_LABEL = "From";
        public static String TO_LABEL = "To";
        public static String DISTANCE_LABEL = "Distance";
        public static String TIME_LABEL = "Duration";
        public static String PETROL_LABEL = "Petrol";
        public static String NAVIGATION_LABEL = "Start Navigation";
        public static String ROUTE_LABEL = "Route";
        public static String TRIP_PLANING = "Plan your trip";
        public static String NEARBY_WIKI = "Nearby Encylopedia";
        public static String TOP_PLACES = "Top Places";
    }


    /**
     * <p>It contain all of the Key of Share Preferences</p>
     */
    public static class SharedPref {
        public static String PREF_NAME = "Data";
        public static String REMOVE_AD = "REMOVE_AD";
        public static String AR_NAV = "AR_NAV";
        public static String HUD_VIEW = "HUD_VIEW";
        public static String COVERAGE = "COVERAGE";
    }


    /**
     * <p>It contain all of the Request Code</p>
     */
    public static class RequestCode {
        public static int LOCATION_REQUEST_CODE = 1;
        public final static int REQUEST_LOCATION = 2;
        public static int PLACE_AUTOCOMPLETE_REQUEST_CODE_SOURCE = 3;
        public static int PLACE_AUTOCOMPLETE_REQUEST_CODE_DEST = 4;
        public static int SEARCH_SPECIFIC_PLACE = 5;
    }


    /**
     * <p>It contain all of the Key of Share Preferences</p>
     */
    public static class IntentKey {
        public static final String IS_TOURIST = "IS_TOURIST";
        public static String PLACE_DETAIL = "PLACE_DETAIL";
        public static String PLACE_RATING = "PLACE_RATING";
        public static String PLACE_REQUIRED = "PLACE_REQUIRED";
        public static String DESTINATION_LONGITUDE = "DESTINATION_LONGITUDE";
        public static String DESTINATION_LATITUDE = "DESTINATION_LATITUDE";
        public static String NAVIGATION_DETAIL = "NAVIGATION_DETAIL";
        public static String DESTINATION_NAME = "DESTINATION_NAME";
        public static String HISTORY_LOCATION = "HISTORY_LOCATION";
        public static String SOURCE_LONGITUDE = "SOURCE_LONGITUDE";
        public static String SOURCE_LATITUDE = "SOURCE_LATITUDE";
        public static String CONVEYANCE = "CONVEYANCE";
        public static String AR_DIRECTION = "AR_DIRECTION";
        public static String IS_SEARCHED_PLACE = "IS_SEARCHED_PLACE";
        public static String SEARCH_PLACE_ID = "SEARCH_PLACE_ID";
        public static String DISTANCE = "DISTANCE";
        public static String DURATION = "DURATION";
        public static String DRIVING_PROFILE = "DRIVING_PROFILE";
    }


    /**
     * <p>It contain all of the database columns</p>
     */
    public static class DatabaseColumn {
        public static final String TAG = "Database";
        public static String TABLE_NAME = "Favourites";
        public static String ID_COLUMN = "id";
        public static String PLACE_ID = "place_id";
        public static String PLACE_PICTURE = "place_picture";
        public static String PLACE_NAME = "place_name";
        public static String PRICE_LEVEL = "price_level";
        public static String PLACE_RATING = "place_rating";
        public static String PLACE_TYPE = "place_type";
        public static String PLACE_ADDRESS = "place_address";
        public static String PLACE_LATITUDE = "place_latitude";
        public static String PLACE_LONGITUDE = "place_longitude";

        public static String HISTORY_TABLE_NAME = "History";
        public static String HISTORY_ID_COLUMN = "id";
        public static String SOURCE_NAME_COLUMN = "source";
        public static String DESTINATION_NAME_COLUMN = "destination";
        public static String SOURCE_LATITUDE_COLUMN = "source_latitude";
        public static String SOURCE_LONGITUDE_COLUMN = "source_longitude";
        public static String DESTINATION_LATITUDE_COLUMN = "destination_latitude";
        public static String DESTINATION_LONGITUDE_COLUMN = "destination_longitude";
        public static String DISTANCE_COLUMN = "distance";
        public static String DURATION_COLUMN = "duration";
        public static String PETROL_COLUMN = "petrol";
        public static String BY_COLUMN = "conveyance";
        public static String ROUTE_COLUMN = "route";

    }


    /**
     * <p>Contain list of Locations along with their tags which is used
     * while sending request to Google server when requesting nearbny
     * places</p>
     */
    public static class LocationTypes {
        public String ACCOUNTING = "accounting";
        public String AIRPORT = "airport";
        public String AMUSEMENT_PARK = "amusement_park";
        public String AQUARIUM = "aquarium";
        public String ART_GALLERY = "art_gallery";
        public String ATM = "atm";
        public String BAKERY = "bakery";
        public String BANK = "bank";
        public String BAR = "bar";
        public String BEAUTY_SALON = "beauty_salon";
        public String BICYCLE_STORE = "bicycle_store";
        public String BOOK_STORE = "book_store";
        public String BOWLING_ALLEY = "bowling_alley";
        public String BUS_STATION = "bus_station";
        public String CAFE = "cafe";
        public String CAMPGROUND = "campground";
        public String CAR_DEALER = "car_dealer";
        public String CAR_RENTAL = "car_rental";
        public String CAR_REPAIR = "car_repair";
        public String CAR_WASH = "car_wash";
        public String CASINO = "casino";
        public String CEMETERY = "cemetery";
        public String CITY_HALL = "city_hall";
        public String CLOTHING_STORE = "clothing_store";
        public String CONVENIENCE_STORE = "convenience_store";
        public String COURTHOUSE = "courthouse";
        public String DENTIST = "dentist";
        public String DEPARTMENT_STORE = "department_store";
        public String DOCTOR = "doctor";
        public String ELECTRICIAN = "electrician";
        public String ELECTRONICS_STORE = "electronics_store";
        public String EMBASSY = "embassy";
        public String FINANCE = "finance";
        public String FIRE_STATION = "fire_station";
        public String FLORIST = "florist";
        public String FUNERAL_HOME = "funeral_home";
        public String FURNITURE_STORE = "furniture_store";
        public String GAS_STATION = "gas_station";
        public String GYM = "gym";
        public String HAIR_CARE = "hair_care";
        public String HARDWARE_STORE = "hardware_store";
        public String HOME_GOODS_STORE = "home_goods_store";
        public String HOSPITAL = "hospital";
        public String INSURANCE_AGENCY = "insurance_agency";
        public String JEWELRY_STORE = "jewelry_store";
        public String LAUNDRY = "laundry";
        public String LAWYER = "lawyer";
        public String LIBRARY = "library";
        public String LIQUOR_STORE = "liquor_store";
        public String LOCAL_GOVERNMENT_OFFICE = "local_government_office";
        public String LOCKSMITH = "locksmith";
        public String LODGING = "lodging";
        public String MEAL_DELIVERY = "meal_delivery";
        public String MEAL_TAKEAWAY = "meal_takeaway";
        public String MOSQUE = "mosque";
        public String MOVIE_RENTAL = "movie_rental";
        public String MOVIE_THEATER = "movie_theater";
        public String MOVING_COMPANY = "moving_company";
        public String MUSEUM = "museum";
        public String NIGHT_CLUB = "night_club";
        public String PAINTER = "painter";
        public String PARK = "park";
        public String PARKING = "parking";
        public String PET_STORE = "pet_store";
        public String PHARMACY = "pharmacy";
        public String PHYSIOTHERAPIST = "physiotherapist";
        public String PLUMBER = "plumber";
        public String POLICE = "police";
        public String POST_OFFICE = "post_office";
        public String REAL_ESTATE_AGENCY = "real_estate_agency";
        public String RESTAURANT = "restaurant";
        public String ROOFING_CONTRACTOR = "roofing_contractor";
        public String RV_PARK = "rv_park";
        public String SCHOOL = "school";
        public String SHOE_STORE = "shoe_store";
        public String SHOPPING_MALL = "shopping_mall";
        public String SPA = "spa";
        public String STADIUM = "stadium";
        public String STORAGE = "storage";
        public String STORE = "store";
        public String SUBWAY_STATION = "subway_station";
        public String SYNAGOGUE = "synagogue";
        public String TAXI_STAND = "taxi_stand";
        public String TRAIN_STATION = "train_station";
        public String TRANSIT_STATION = "transit_station";
        public String TRAVEL_AGENCY = "travel_agency";
        public String UNIVERSITY = "university";
        public String VETERINARY_CARE = "veterinary_care";
        public String ZOO = "zoo";


    }


    public static Double getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        Constant.latitude = latitude;
    }

    public static Double getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        Constant.longitude = longitude;
    }

    public static String getCityName() {
        return cityName;
    }

    public static void setCityName(String cityName) {
        Constant.cityName = cityName;
    }

    public static DirectionsRoute getDirectionsRoute() {
        return directionsRoute;
    }

    public static void setDirectionsRoute(DirectionsRoute directionsRoute) {
        Constant.directionsRoute = directionsRoute;
    }

    public static UniversalVariable getUniversalVariable() {
        return universalVariable;
    }

    public static void setUniversalVariable(UniversalVariable universalVariable) {
        Constant.universalVariable = universalVariable;
    }

    public static LocationCallback getLocationCallback() {
        return locationCallback;
    }

    public static void setLocationCallback(LocationCallback locationCallback) {
        Constant.locationCallback = locationCallback;
    }

    public static String getWeather() {
        return weather;
    }

    public static void setWeather(String weather) {
        Constant.weather = weather;
    }


}
