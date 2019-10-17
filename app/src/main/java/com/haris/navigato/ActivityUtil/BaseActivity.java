package com.haris.navigato.ActivityUtil;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.FragmentUtil.Favourite;
import com.haris.navigato.FragmentUtil.Home;
import com.haris.navigato.FragmentUtil.Setting;
import com.haris.navigato.FragmentUtil.Tourism;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topGuide;
    private ImageView imageGuide;
    private UbuntuRegularTextview txtHome;
    private ImageView imageFavourite;
    private UbuntuRegularTextview txtFavourite;
    private ImageView layoutAdd;
    private ImageView imageHistory;
    private UbuntuRegularTextview txtHistory;
    private ImageView imageSetting;
    private UbuntuRegularTextview txtSetting;
    private LinearLayout layoutFavourites;
    private LinearLayout layoutHome;
    private LinearLayout layoutHistory;
    private LinearLayout layoutSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        layoutHome = (LinearLayout) findViewById(R.id.layout_home);
        topGuide = (TextView) findViewById(R.id.top_guide);
        imageGuide = (ImageView) findViewById(R.id.image_guide);
        txtHome = (UbuntuRegularTextview) findViewById(R.id.txt_home);
        layoutFavourites = (LinearLayout) findViewById(R.id.layout_favourites);
        imageFavourite = (ImageView) findViewById(R.id.image_favourite);
        txtFavourite = (UbuntuRegularTextview) findViewById(R.id.txt_favourite);
        layoutAdd = (ImageView) findViewById(R.id.layout_add);
        layoutHistory = (LinearLayout) findViewById(R.id.layout_history);
        imageHistory = (ImageView) findViewById(R.id.image_history);
        txtHistory = (UbuntuRegularTextview) findViewById(R.id.txt_history);
        layoutSetting = (LinearLayout) findViewById(R.id.layout_setting);
        imageSetting = (ImageView) findViewById(R.id.image_setting);
        txtSetting = (UbuntuRegularTextview) findViewById(R.id.txt_setting);

        onFragmentSelection(Constant.FRAGMENT_TYPE.HOME);

        layoutHome.setOnClickListener(this);
        layoutFavourites.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutAdd.setOnClickListener(this);

    }


    /**
     * <p>It is used to open fragment</p>
     *
     * @param fragment
     */
    private void openFragment(Fragment fragment) {


        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();

        }

    }


    /**
     * <o>It is used to change colour of selection
     * & open specific fragment</o>
     *
     * @param fragment_type
     */
    private void onFragmentSelection(Constant.FRAGMENT_TYPE fragment_type) {


        if (fragment_type == Constant.FRAGMENT_TYPE.HOME) {

            txtHome.setTextColor(Utility.getColourFromRes(this, R.color.colorPrimaryDark));
            txtFavourite.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtHistory.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtSetting.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));

            imageGuide.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            imageFavourite.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageHistory.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageSetting.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));

            openFragment(new Home());


        } else if (fragment_type == Constant.FRAGMENT_TYPE.FAVOURITES) {

            txtHome.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtFavourite.setTextColor(Utility.getColourFromRes(this, R.color.colorPrimaryDark));
            txtHistory.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtSetting.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));

            imageGuide.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageFavourite.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            imageHistory.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageSetting.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));


            openFragment(new Favourite());


        } else if (fragment_type == Constant.FRAGMENT_TYPE.HISTORY) {


            txtHome.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtFavourite.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtHistory.setTextColor(Utility.getColourFromRes(this, R.color.colorPrimaryDark));
            txtSetting.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));

            imageGuide.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageFavourite.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageHistory.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            imageSetting.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));


            openFragment(new Tourism());
            ///openFragment(new History());

        } else if (fragment_type == Constant.FRAGMENT_TYPE.SETTING) {


            txtHome.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtFavourite.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtHistory.setTextColor(Utility.getColourFromRes(this, R.color.un_selected));
            txtSetting.setTextColor(Utility.getColourFromRes(this, R.color.colorPrimaryDark));


            imageGuide.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageFavourite.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageHistory.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.un_selected));
            imageSetting.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));


            openFragment(new Setting());


        }


    }


    @Override
    public void onClick(View v) {

        if (v == layoutHome) {
            onFragmentSelection(Constant.FRAGMENT_TYPE.HOME);
        }
        if (v == layoutFavourites) {
            onFragmentSelection(Constant.FRAGMENT_TYPE.FAVOURITES);
        }
        if (v == layoutHistory) {
            onFragmentSelection(Constant.FRAGMENT_TYPE.HISTORY);
        }
        if (v == layoutSetting) {
            onFragmentSelection(Constant.FRAGMENT_TYPE.SETTING);
        }
        if (v == layoutAdd) {
            startActivity(new Intent(this, TripPlanning.class));
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_LOCATION && resultCode == RESULT_OK) {
            Utility.Logger("OnSuccess", "Get Permission Successfully");

            if (Constant.getLocationCallback() != null)
                Constant.getLocationCallback().locationAccessGranted();

        }
        if (requestCode == Constant.RequestCode.REQUEST_LOCATION && resultCode == RESULT_CANCELED) {
            Utility.Logger("OnFailure", "Failed to get Permission !");

        }

    }


}