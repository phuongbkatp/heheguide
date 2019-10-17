package com.haris.navigato.ObjectUtil;

import com.haris.navigato.ConstantUtil.Constant;

public class PrefObject {

    private Constant.SHARED_PREF sharedPref;
    private boolean isRemoveAd;
    private boolean isArNav;
    private boolean isHud;
    private String coverage;


    public PrefObject(Constant.SHARED_PREF sharedPref) {
        this.sharedPref = sharedPref;
    }

    public PrefObject(Constant.SHARED_PREF sharedPref, String coverage) {
        this.sharedPref = sharedPref;
        this.coverage = coverage;
    }

    public PrefObject(Constant.SHARED_PREF sharedPref, boolean isRemoveAd, boolean isArNav, boolean isHud) {
        this.sharedPref = sharedPref;
        this.isRemoveAd = isRemoveAd;
        this.isArNav = isArNav;
        this.isHud = isHud;
    }


    public Constant.SHARED_PREF getSharedPref() {
        return sharedPref;
    }

    public void setSharedPref(Constant.SHARED_PREF sharedPref) {
        this.sharedPref = sharedPref;
    }

    public boolean isRemoveAd() {
        return isRemoveAd;
    }

    public void setRemoveAd(boolean removeAd) {
        isRemoveAd = removeAd;
    }

    public boolean isArNav() {
        return isArNav;
    }

    public void setArNav(boolean arNav) {
        isArNav = arNav;
    }

    public boolean isHud() {
        return isHud;
    }

    public void setHud(boolean hud) {
        isHud = hud;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}
