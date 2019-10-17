package com.haris.navigato;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.haris.navigato.ConstantUtil.Constant;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mapbox.mapboxsdk.Mapbox;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;



/**
 * Created by hp on 5/20/2018.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;
    Context context;
    private static Picasso built;


    public MyApplication() {
        mInstance = this;
    }

    public MyApplication(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Thread.setDefaultUncaughtExceptionHandler(new ThreadHandeling());

        Mapbox.getInstance(getApplicationContext(), Constant.Credentials.MAP_BOX_API_KEY);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
        mInstance = this;


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public static Picasso getPicasso() {
        return built;
    }


}
