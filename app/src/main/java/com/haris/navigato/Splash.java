package com.haris.navigato;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.haris.navigato.ActivityUtil.BaseActivity;
import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.Utility.Utility;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    private Handler handler;
    private Runnable runnable;
    private String TAG = Splash.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initUI();  ///Initialize UI

    }


    /**
     * <p>It is used to initialize UI </p>
     */
    private void initUI() {

        MobileAds.initialize(this, Constant.Credentials.ADMOB_APP_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {

                requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, Constant.RequestCode.LOCATION_REQUEST_CODE);

            } else {
                if (Utility.isLocationProviderAvailable(this, true)) {

                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), BaseActivity.class));
                            finish();
                        }
                    };

                    handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);

                } else {
                    triggerLocationSettingAlert();
                }
            }
        } else {

            if (Utility.isLocationProviderAvailable(this, true)) {

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), BaseActivity.class));
                        finish();
                    }
                };

                handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);

            } else {
                triggerLocationSettingAlert();
            }
        }


    }


    /**
     * <p>It is used to trigger location Setting Alert</p>
     */
    private void triggerLocationSettingAlert() {
        Utility.Logger("Working", "Alert Working");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(Splash.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                Utility.Logger("OnSuccess", locationSettingsResponse.getLocationSettingsStates().isLocationPresent() + " " + locationSettingsResponse.getLocationSettingsStates().isLocationUsable());

                if (locationSettingsResponse.getLocationSettingsStates().isGpsUsable() ||
                        locationSettingsResponse.getLocationSettingsStates().isNetworkLocationUsable() ||
                        locationSettingsResponse.getLocationSettingsStates().isLocationUsable()
                        ) {

                    startActivity(new Intent(getApplicationContext(), BaseActivity.class));
                    finish();

                }
            }


        });
        task.addOnCompleteListener(Splash.this, new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

            }
        });
        task.addOnFailureListener(Splash.this, new OnFailureListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(@NonNull Exception e) {

                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Utility.Logger(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                "location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            ResolvableApiException rae = (ResolvableApiException) e;
                            rae.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sie) {
                            Utility.Logger(TAG, "PendingIntent unable to execute request.");
                        }


                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;

                            resolvable.startResolutionForResult(Splash.this,
                                    Constant.RequestCode.REQUEST_LOCATION);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                            sendEx.printStackTrace();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings.";
                        Utility.Logger(TAG, errorMessage);

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;

                            resolvable.startResolutionForResult(Splash.this,
                                    Constant.RequestCode.REQUEST_LOCATION);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                            sendEx.printStackTrace();
                        }

                        break;
                    //Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }

            }


        });


    }


    /**
     * <p>It is used to create location request</p>
     *
     * @return
     */
    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constant.RequestCode.REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        // All required changes were successfully made
                        Utility.Logger("Permission", "Success");

                        startActivity(new Intent(getApplicationContext(), BaseActivity.class));
                        finish();

                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        Utility.Logger("Permission", "Failure");
                        //triggerLocationSettingAlert();

                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == Constant.RequestCode.LOCATION_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.CAMERA_REQUIRE_PERMISSION, Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.EXTERNAL_REQUIRE_PERMISSION, Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.EXTERNAL_REQUIRE_PERMISSION, Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.LOCATION_REQUIRE_PERMISSION, Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(getApplicationContext(), Constant.ToastMessage.LOCATION_REQUIRE_PERMISSION, Toast.LENGTH_SHORT);
                    return;
                }


                if (Utility.isLocationProviderAvailable(this, true)) {
                    startActivity(new Intent(Splash.this, BaseActivity.class));
                    finish();
                } else
                    triggerLocationSettingAlert();


            }


        }


    }


}
