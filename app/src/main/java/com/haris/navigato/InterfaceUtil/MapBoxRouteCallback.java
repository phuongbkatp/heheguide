package com.haris.navigato.InterfaceUtil;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.util.List;

public interface MapBoxRouteCallback {

    public void getDirectionRoutes(List<DirectionsRoute> directionsRouteList);

}
