package com.haris.navigato.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.haris.navigato.ActivityUtil.NearbyWiki;
import com.haris.navigato.ActivityUtil.PlaceDetail;
import com.haris.navigato.ActivityUtil.SearchPlace;
import com.haris.navigato.AdapterUtil.TourismAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.EmptyState;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.ObjectUtil.PlaceData;
import com.haris.navigato.ObjectUtil.PlaceParameter;
import com.haris.navigato.R;
import com.haris.navigato.Utility.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tourism extends Fragment implements View.OnClickListener, DirectoryCallback {
    private RecyclerView recyclerViewTourism;
    private GridLayoutManager gridLayoutManager;
    private TourismAdapter tourismAdapter;
    private TextView txtMenu;
    private Management management;
    private ArrayList<Object> objectArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tourism, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
        Utility.showInterstitialAd(getActivity());  //Initialize Interstitial Ads

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.TOURISM_TEXT);

        objectArrayList.add(new EmptyState());
        objectArrayList.add(Utility.getExploration());

        management = new Management(getActivity());

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewTourism = (RecyclerView) view.findViewById(R.id.recycler_view_tourism);
        recyclerViewTourism.setLayoutManager(gridLayoutManager);

        tourismAdapter = new TourismAdapter(getActivity(), objectArrayList) {
            @Override
            public void onPlaceSelector(Constant.EVENTS events, int parentPosition, int childPosition) {
                if (events == Constant.EVENTS.PLACE) {

                    ArrayList<PlaceData> placeDataArray = (ArrayList<PlaceData>) objectArrayList.get(parentPosition);
                    PlaceData placeData = placeDataArray.get(childPosition);

                    if (placeData.getPlaceName().contains("Wikipedia")) {

                        startActivity(new Intent(getActivity(), NearbyWiki.class));

                    } else {

                        Intent intent = new Intent(getActivity(), SearchPlace.class);
                        intent.putExtra(Constant.IntentKey.PLACE_REQUIRED, placeData.getPlaceTag());
                        intent.putExtra(Constant.IntentKey.IS_TOURIST, true);
                        startActivity(intent);

                    }

                }
            }

            @Override
            public void onSearch() {
                searchSpecificPlace();
            }

            @Override
            public void onTouristAttraction(int position) {

                NearbyPlaces placeData = (NearbyPlaces) objectArrayList.get(position);

                Utility.Logger("Place Id", placeData.getPlaceId());

                Intent intent = new Intent(getActivity(), PlaceDetail.class);
                intent.putExtra(Constant.IntentKey.PLACE_DETAIL, (Serializable) placeData);
                startActivity(intent);
            }
        };
        recyclerViewTourism.setAdapter(tourismAdapter);

        management.sendServerRequest(Constant.REQUEST.TOP_PLACES, new PlaceParameter(Utility.formatTopPlaceQuery("Things to do in " + Constant.getCityName()), String.valueOf(Constant.getLatitude()), String.valueOf(Constant.getLongitude()), null, this/*null, , "restaurant", "2500", null, Home.this*/));


    }

    /**
     * <p>It is used to select select</p>
     */
    private void searchSpecificPlace() {
        int requestCode = Constant.RequestCode.SEARCH_SPECIFIC_PLACE;


        // Initialize Places.
        Places.initialize(getActivity(), Constant.Credentials.GOOGLE_API_KEY);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, placeFields)
                .build(getActivity());
        startActivityForResult(intent, Constant.RequestCode.SEARCH_SPECIFIC_PLACE);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(ArrayList<Object> result) {

        objectArrayList.clear();
        objectArrayList.add(new EmptyState());
        objectArrayList.add(Utility.getExploration());
        objectArrayList.addAll(result);

        tourismAdapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.SEARCH_SPECIFIC_PLACE
                && resultCode == getActivity().RESULT_OK) {

            Place place = Autocomplete.getPlaceFromIntent(data);

            Intent intent = new Intent(getActivity(), PlaceDetail.class);
            intent.putExtra(Constant.IntentKey.SEARCH_PLACE_ID, place.getId());
            intent.putExtra(Constant.IntentKey.IS_SEARCHED_PLACE, true);
            startActivity(intent);


        }

    }


}