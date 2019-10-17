package com.haris.navigato.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.haris.navigato.AdapterUtil.HistoryAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.R;

import java.util.ArrayList;

public class History extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewHistory;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUI(); //Initialize UI
        initAD();  //Initialize Admob Banner UI
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.HISTORY_TEXT);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        management = new Management(getApplicationContext());
        objectArrayList.addAll(management.getDataFromDb(Constant.DATABASE_EVENT.RETRIEVE, Constant.DATABASE_FUNCTION.HISTORY, null, null));


        if (objectArrayList.size() <= 0) {
            objectArrayList.add(new ErrorObject(Constant.ImportantMessages.no_history, Constant.ImportantMessages.no_history_tagline, R.drawable.no_location));
        }

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);

        recyclerViewHistory = (RecyclerView) findViewById(R.id.recycler_view_history);
        recyclerViewHistory.setLayoutManager(gridLayoutManager);

        historyAdapter = new HistoryAdapter(getApplicationContext(), objectArrayList) {
            @Override
            public void onItemSelected(int position) {

                /*HistoryQueryObject historyQueryObject = (HistoryQueryObject) objectArrayList.get(position);

                int conveyance = historyQueryObject.getConveyanceName().contains("Car") ? 1
                        : historyQueryObject.getConveyanceName().contains("Bicycle") ? 2
                        : historyQueryObject.getConveyanceName().contains("Walk") ? 3 : 0;

                Intent intent = new Intent(getApplicationContext(), PlaceDirection.class);
                intent.putExtra(Constant.IntentKey.HISTORY_LOCATION, true);
                intent.putExtra(Constant.IntentKey.CONVEYANCE, conveyance);
                intent.putExtra(Constant.IntentKey.DESTINATION_NAME, historyQueryObject.getDestinationName());
                intent.putExtra(Constant.IntentKey.DESTINATION_LATITUDE, historyQueryObject.getDestinationLatitude());
                intent.putExtra(Constant.IntentKey.DESTINATION_LONGITUDE, historyQueryObject.getDestinationLongitude());
                intent.putExtra(Constant.IntentKey.SOURCE_LATITUDE, historyQueryObject.getSourceLatitude());
                intent.putExtra(Constant.IntentKey.SOURCE_LONGITUDE, historyQueryObject.getSourceLongitude());
                startActivity(intent);*/

            }
        };
        recyclerViewHistory.setAdapter(historyAdapter);

        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */

    private void initAD() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
    }

}