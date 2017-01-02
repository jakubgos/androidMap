package com.mapapp.bos.mapapp.activity.model;


import com.google.android.gms.maps.GoogleMap;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.common.LocationE;
import com.mapapp.bos.mapapp.common.PlaceE;
import com.mapapp.bos.mapapp.gson.PlacesResult;

/**
 * Created by Bos on 2016-12-04.
 */
public class MainModel implements Mvp_inter.PresenterToModel {

    Mvp_inter.MapManager mapManagerImpl;
    Mvp_inter.LocationManager mLocationManagerImpl;
    Mvp_inter.PlaceFinder mPlaceFinderImpl;


    public MainModel(Mvp_inter.MapManager mapManagerImpl ,  Mvp_inter.LocationManager  mLocationManagerImpl, Mvp_inter.PlaceFinder mPlaceFinderImpl) {
        this.mapManagerImpl = mapManagerImpl;
        this.mLocationManagerImpl= mLocationManagerImpl;
        this.mPlaceFinderImpl= mPlaceFinderImpl;
    }


    @Override
    public void prepareMapData(final ModelMapResult result) {
        mapManagerImpl.setupMap(new Mvp_inter.MapManagerResult() {
            @Override
            public void onMapReady(GoogleMap map) {
                result.onMapReady(map);
            }
        });
    }

    @Override
    public void startGps(final ModelLocationResult result) {
        mLocationManagerImpl.initGps(new Mvp_inter.LocationManagerResult() {
            @Override
            public void onLocationChange(LocationE location) {
                result.onLocationChange(location);
            }

            @Override
            public void onLocationSetupFailed() {
                result.onLocationSetupFailed();
            }
        });
    }

    @Override
    public void downloadPlaceData(CharSequence charSequence, LocationE currentLoc, final ModelPlacesResult results) {
        mPlaceFinderImpl.getPlaceData(charSequence, currentLoc, new Mvp_inter.PlaceFinderResult(){

            @Override
            public void onPlacesResult(PlacesResult result) {
                results.onPlacesResult(result);
            }
        } );
    }

    public interface ModelMapResult {
        void onMapReady(GoogleMap map);
    }

    public interface ModelLocationResult {
        void onLocationChange(LocationE location);
        void onLocationSetupFailed();
    }

    public interface ModelPlacesResult {

        void onPlacesResult(PlacesResult result);
    }

}
