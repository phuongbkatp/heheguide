package com.haris.navigato.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.haris.navigato.ActivityUtil.PlaceDetail;
import com.haris.navigato.AdapterUtil.FavouriteAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.ObjectUtil.NearbyPlaces;
import com.haris.navigato.R;
import com.haris.navigato.Utility.Utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Favourite extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
    private RecyclerView recyclerViewFavourite;
    private FavouriteAdapter favouriteAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> searchArraylist = new ArrayList<>();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private ImageView imgClose;
    private EditText editSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favourite, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
        initAD(view); //Initialize Admob Banner Ads
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());


        txtMenu = (TextView) view.findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.FAVOURITE_TEXT);

        imageBack = (ImageView) view.findViewById(R.id.image_back);
        imageBack.setVisibility(View.GONE);

        imgClose = (ImageView) view.findViewById(R.id.img_close);
        editSearch = (EditText) view.findViewById(R.id.edit_search);

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);

        recyclerViewFavourite = (RecyclerView) view.findViewById(R.id.recycler_view_favourite);
        recyclerViewFavourite.setLayoutManager(gridLayoutManager);

        favouriteAdapter = new FavouriteAdapter(getActivity(), objectArrayList) {
            @Override
            public void onFavouriteClick(int position) {

                DatabaseQueryObject databaseQueryObject = (DatabaseQueryObject) objectArrayList.get(position);
                NearbyPlaces nearbyPlaces = new NearbyPlaces("0", databaseQueryObject.getPlaceId(), databaseQueryObject.getPlaceName()
                        , databaseQueryObject.getPlaceAddress(), databaseQueryObject.getPlaceRating(), databaseQueryObject.getPlaceLatitude(), databaseQueryObject.getPlaceLongitude()
                        , false, null, databaseQueryObject.getPlaceType(), databaseQueryObject.getPriceLevel());

                Intent intent = new Intent(getActivity(), PlaceDetail.class);
                intent.putExtra(Constant.IntentKey.PLACE_DETAIL, (Serializable) nearbyPlaces);
                startActivity(intent);

            }
        };
        recyclerViewFavourite.setAdapter(favouriteAdapter);

        imageBack.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        editSearch.setOnEditorActionListener(this);
        editSearch.addTextChangedListener(this);

    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */
    private void initAD(View view) {
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build();
        mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.GONE);

    }


    /**
     * <p>It trigger by Search & find required search terms</p>
     */
    private void performSearch(String searchText) {

        if (objectArrayList.size() > 0) {
            if (objectArrayList.get(0) instanceof ErrorObject) {
                return;
            }
        }

        objectArrayList.clear();

        for (int i = 0; i < searchArraylist.size(); i++) {

            DatabaseQueryObject favourite = (DatabaseQueryObject) searchArraylist.get(i);

            if (favourite.getPlaceName().toLowerCase().contains(searchText.toLowerCase())) {
                objectArrayList.add(favourite);
            }
        }

        favouriteAdapter.notifyDataSetChanged();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utility.isEmptyString(s.toString()))
            imgClose.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {

        }
        if (v == imgClose) {
            imgClose.setVisibility(View.GONE);
            editSearch.setText(null);
            onResume();

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        objectArrayList.clear();
        objectArrayList.addAll(management.getDataFromDb(Constant.DATABASE_EVENT.RETRIEVE, Constant.DATABASE_FUNCTION.FAVOURITE, null, null));
        searchArraylist.addAll(objectArrayList);

        if (objectArrayList.size() <= 0) {
            objectArrayList.add(new ErrorObject(Constant.ImportantMessages.no_favourite, Constant.ImportantMessages.no_favourite_tagline, R.drawable.no_favourite));
        }

        favouriteAdapter.notifyDataSetChanged();

    }

}