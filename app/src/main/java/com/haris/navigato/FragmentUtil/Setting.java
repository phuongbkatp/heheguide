package com.haris.navigato.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.haris.navigato.ActivityUtil.History;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ManagementUtil.Management;
import com.haris.navigato.ObjectUtil.PrefObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;
import com.haris.navigato.Utility.Utility;
import com.xw.repo.BubbleSeekBar;

public class Setting extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, BubbleSeekBar.OnProgressChangedListener {

    private BubbleSeekBar seekbarRadius;
    private UbuntuRegularTextview txtHistory;
    private LinearLayout layoutBackup;
    private UbuntuRegularTextview txtBackupHeading;
    private UbuntuRegularTextview txtBackupDesc;
    private Switch switchHud;
    private UbuntuRegularTextview txtShare;
    private UbuntuRegularTextview txtRate;
    private Switch switchAd;
    private Switch switchAr;
    private TextView txtMenu;
    private Management management;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_setting, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
        initAD(view);  //Initialize Admob Banner Ad

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Constant.MenuText.SETTING_TEXT);

        seekbarRadius = (BubbleSeekBar) view.findViewById(R.id.seekbar_radius);

        txtHistory = (UbuntuRegularTextview) view.findViewById(R.id.txt_history);
        txtBackupHeading = (UbuntuRegularTextview) view.findViewById(R.id.txt_backup_heading);
        txtBackupDesc = (UbuntuRegularTextview) view.findViewById(R.id.txt_backup_desc);
        txtShare = (UbuntuRegularTextview) view.findViewById(R.id.txt_share);
        txtRate = (UbuntuRegularTextview) view.findViewById(R.id.txt_rate);
        layoutBackup = (LinearLayout) view.findViewById(R.id.layout_backup);

        switchHud = (Switch) view.findViewById(R.id.switch_hud);
        switchAd = (Switch) view.findViewById(R.id.switch_ad);
        switchAr = (Switch) view.findViewById(R.id.switch_ar);

        switchHud.setChecked(management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.HUD_VIEW)).condition);
        switchAr.setChecked(management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.AR_NAV)).condition);
        switchAd.setChecked(management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.REMOVE_AD)).condition);
        seekbarRadius.setProgress(Float.parseFloat(management.getDataFromPref(new PrefObject(Constant.SHARED_PREF.COVERAGE)).result));


        switchHud.setOnCheckedChangeListener(this);
        switchAr.setOnCheckedChangeListener(this);
        switchAd.setOnCheckedChangeListener(this);
        txtRate.setOnClickListener(this::onClick);
        txtShare.setOnClickListener(this::onClick);
        txtHistory.setOnClickListener(this::onClick);
        seekbarRadius.setOnProgressChangedListener(this);


    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */
    private void initAD(View view) {
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build();
        mAdView.loadAd(adRequest);


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == switchHud) {

            if (isChecked) {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.HUD_VIEW, false, false, true));
            } else {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.HUD_VIEW, false, false, false));

            }

        }
        if (buttonView == switchAd) {

            if (isChecked) {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.REMOVE_AD, true, false, false));
            } else {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.REMOVE_AD, false, false, false));

            }

        }
        if (buttonView == switchAr) {

            if (isChecked) {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.AR_NAV, false, true, false));
            } else {
                management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.AR_NAV, false, false, false));

            }

        }

    }


    @Override
    public void onClick(View v) {
        if (v == txtRate) {
            Utility.rateApp(getActivity());
        }
        if (v == txtShare) {
            Utility.shareApp(getActivity());
        }
        if (v == txtHistory) {
            startActivity(new Intent(getActivity(), History.class));
        }

    }

    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        management.saveDataIntoPref(new PrefObject(Constant.SHARED_PREF.COVERAGE, String.valueOf(progress)));
    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }


}
