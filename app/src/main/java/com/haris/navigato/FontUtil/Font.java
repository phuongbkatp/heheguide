package com.haris.navigato.FontUtil;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by hp on 5/20/2018.
 */

public class Font {

    static String ubuntu_regular = "Fonts/Ubuntu-Regular.ttf";
    static String ubuntu_light = "Fonts/Ubuntu-Light.ttf";
    static String ubuntu_medium = "Fonts/Ubuntu-Medium.ttf";
    static String ubuntu_bold = "Fonts/Ubuntu-Bold.ttf";
    static String lemon_milk = "Fonts/LemonMilk.otf";
    static String prime_reg = "Fonts/Prime Regular.otf";
    static String hi_reg = "Fonts/Hi.otf";

    public static Typeface ubuntu_regular_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_regular);
    }

    public static Typeface ubuntu_medium_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_medium);
    }

    public static Typeface ubuntu_bold_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_bold);
    }

    public static Typeface ubuntu_light_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_light);
    }

    public static Typeface lemon_milk_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), lemon_milk);
    }


    public static Typeface prime_reg_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), prime_reg);
    }

    public static Typeface hi_reg_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), hi_reg);
    }

}
