package com.haris.navigato.ActivityUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.haris.navigato.AdapterUtil.ReviewAdapter;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;

import java.util.ArrayList;
import java.util.Collection;

public class ListOfReviews extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewReviews;
    private ReviewAdapter reviewAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private TextView txtMenu;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_reviews);

        initUI(); //Initialize UI
        initAD();  //Initialize Admob Banner Ad

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        objectArrayList = (ArrayList<Object>) getIntent().getSerializableExtra(Constant.IntentKey.PLACE_RATING);

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.REVIEW_TEXT);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);

        recyclerViewReviews = (RecyclerView) findViewById(R.id.recycler_view_reviews);
        recyclerViewReviews.setLayoutManager(gridLayoutManager);


        reviewAdapter = new ReviewAdapter(this, objectArrayList);
        recyclerViewReviews.setAdapter(reviewAdapter);

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