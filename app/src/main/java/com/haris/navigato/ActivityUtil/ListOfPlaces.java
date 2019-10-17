package com.haris.navigato.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.haris.navigato.AdapterUtil.LocationTypeAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ObjectUtil.LocationTypeObject;
import com.haris.navigato.R;
import com.haris.navigato.Utility.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ListOfPlaces extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
    private RecyclerView recyclerViewPlaces;
    private LocationTypeAdapter locationTypeAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> searchArraylist = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imgSearch;
    private ImageView imgClose;
    private EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_places);

        initPlaces(); //Initialize Places
        initUI(); //Initialize UI
        initAD();  //Initialize Admob Banner

    }


    /**
     * <p>It is used to add all of the location types into Arraylist</p>
     */
    private void initPlaces() {

        Object locationTypes = new Constant.LocationTypes();
        String locationName;

        for (Field field : locationTypes.getClass().getDeclaredFields()) {


            try {
                if (field.getType().equals(String.class)) {

                    locationName = field.getName().replaceAll("_", " ");
                    Utility.Logger("Name of Place", field.getName() + " " + locationName);
                    objectArrayList.add(new LocationTypeObject(Utility.capitalize(locationName.toLowerCase()), (String) field.get(locationTypes)));
                } else
                    continue;

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        searchArraylist.addAll(objectArrayList);

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.LIST_OF_PLACES);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgClose = (ImageView) findViewById(R.id.img_close);
        editSearch = (EditText) findViewById(R.id.edit_search);

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);

        recyclerViewPlaces = (RecyclerView) findViewById(R.id.recycler_view_places);
        recyclerViewPlaces.setLayoutManager(gridLayoutManager);

        locationTypeAdapter = new LocationTypeAdapter(this, objectArrayList) {
            @Override
            public void onLocationSelection(int position) {
                LocationTypeObject locationTypeObject = (LocationTypeObject) objectArrayList.get(position);

                Intent intent = new Intent(ListOfPlaces.this, SearchPlace.class);
                intent.putExtra(Constant.IntentKey.PLACE_REQUIRED, locationTypeObject.getPlaceTag());
                startActivity(intent);

            }
        };
        recyclerViewPlaces.setAdapter(locationTypeAdapter);


        imageBack.setOnClickListener(this);
        editSearch.setOnEditorActionListener(this);
        editSearch.addTextChangedListener(this);
        imgClose.setOnClickListener(this);

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
     * <p>It trigger by Search & find required search terms</p>
     */
    private void performSearch(String searchText) {

        objectArrayList.clear();

        for (int i = 0; i < searchArraylist.size(); i++) {

            LocationTypeObject locationTypeObject = (LocationTypeObject) searchArraylist.get(i);

            if (locationTypeObject.getPlaceName().toLowerCase().contains(searchText.toLowerCase())) {
                objectArrayList.add(locationTypeObject);
            }
        }

        locationTypeAdapter.notifyDataSetChanged();

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
            finish();
        }
        if (v == imgClose) {
            imgClose.setVisibility(View.GONE);
            objectArrayList.clear();
            editSearch.setText(null);
            objectArrayList.addAll(searchArraylist);
            locationTypeAdapter.notifyDataSetChanged();
        }
    }

}
