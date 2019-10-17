package com.haris.navigato.ObjectUtil;

import com.akexorcist.googledirection.DirectionCallback;
import com.google.android.gms.maps.model.LatLng;
import com.haris.navigato.InterfaceUtil.ConnectivityCallback;
import com.haris.navigato.InterfaceUtil.MapBoxRouteCallback;

import java.util.ArrayList;

/**
 * Created by hp on 5/25/2018.
 */

public class DirectionParameter {
    LatLng source;
    LatLng destination;
    String avoidType;
    String trasportMode;
    boolean isAlternative;
    ArrayList<LatLng> waypoint = new ArrayList<>();
    DirectionCallback directionCallback;
    ConnectivityCallback connectivityCallback;
    MapBoxRouteCallback mapBoxRouteCallback;

    public DirectionParameter(LatLng source, LatLng destination, String avoidType, String trasportMode, boolean isAlternative, DirectionCallback directionCallback, ConnectivityCallback connectivityCallback) {
        this.source = source;
        this.destination = destination;
        this.avoidType = avoidType;
        this.trasportMode = trasportMode;
        this.isAlternative = isAlternative;
        this.directionCallback = directionCallback;
        this.connectivityCallback = connectivityCallback;
    }


    public DirectionParameter(LatLng source, LatLng destination, String avoidType, String trasportMode, boolean isAlternative, MapBoxRouteCallback mapBoxRouteCallback, ConnectivityCallback connectivityCallback, boolean isMapbox) {
        this.source = source;
        this.destination = destination;
        this.avoidType = avoidType;
        this.trasportMode = trasportMode;
        this.isAlternative = isAlternative;
        this.mapBoxRouteCallback = mapBoxRouteCallback;
        this.connectivityCallback = connectivityCallback;
    }


    public LatLng getSource() {
        return source;
    }

    public void setSource(LatLng source) {
        this.source = source;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public String getAvoidType() {
        return avoidType;
    }

    public void setAvoidType(String avoidType) {
        this.avoidType = avoidType;
    }

    public String getTrasportMode() {
        return trasportMode;
    }

    public void setTrasportMode(String trasportMode) {
        this.trasportMode = trasportMode;
    }

    public boolean isAlternative() {
        return isAlternative;
    }

    public void setAlternative(boolean alternative) {
        isAlternative = alternative;
    }

    public DirectionCallback getDirectionCallback() {
        return directionCallback;
    }

    public void setDirectionCallback(DirectionCallback directionCallback) {
        this.directionCallback = directionCallback;
    }

    public ConnectivityCallback getConnectivityCallback() {
        return connectivityCallback;
    }

    public void setConnectivityCallback(ConnectivityCallback connectivityCallback) {
        this.connectivityCallback = connectivityCallback;
    }

    public ArrayList<LatLng> getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(ArrayList<LatLng> waypoint) {
        this.waypoint = waypoint;
    }

    public MapBoxRouteCallback getMapBoxRouteCallback() {
        return mapBoxRouteCallback;
    }

    public void setMapBoxRouteCallback(MapBoxRouteCallback mapBoxRouteCallback) {
        this.mapBoxRouteCallback = mapBoxRouteCallback;
    }
}
