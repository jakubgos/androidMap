package com.mapapp.bos.mapapp.activity.model;


import com.google.android.gms.maps.GoogleMap;
import com.mapapp.bos.mapapp.Data.NetworkInterface;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.common.LocationE;

/**
 * Created by Bos on 2016-12-04.
 */
public class MainModel implements Mvp_inter.PresenterToModel {

    Mvp_inter.MapManager mapManagerImpl;
    Mvp_inter.LocationManager mLocationManagerImpl;




    public MainModel(Mvp_inter.MapManager mapManagerImpl ,  Mvp_inter.LocationManager  mLocationManagerImpl) {
        this.mapManagerImpl = mapManagerImpl;
        this.mLocationManagerImpl= mLocationManagerImpl;
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

    public interface ModelMapResult {
        void onMapReady(GoogleMap map);
    }

    public interface ModelLocationResult {
        void onLocationChange(LocationE location);

        void onLocationSetupFailed();
    }

}
