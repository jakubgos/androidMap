package com.mapapp.bos.mapapp.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.common.LocationE;


/**
 * Created by Bos on 2016-12-04.
 */

public interface Mvp_inter {


    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      Presenter to View
     */
    interface ViewOps {
       // Context getAppContext();
        //Context getActivityContext();

        Resources getViewResources();
        void setupMap(GoogleMap map);
        void printMarketAndMoveCamera(LatLng latLng, String text);

        SupportMapFragment getSupportMapFragment();


        Context getContext();

        void requestGpsPermission();
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface PresenterOps {
        void onStartup(Bundle savedInstanceState);

        void permissionGranted();
    }



    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface PresenterToModel {
        void prepareMapData(MainModel.ModelMapResult callBack);

        void startGps(MainModel.ModelLocationResult result);
    }

    interface MapManager {
        void setupMap(Mvp_inter.MapManagerResult result);
    }
    interface MapManagerResult {
       void  onMapReady(GoogleMap map);


    }


    interface LocationManager {
        void initGps(Mvp_inter.LocationManagerResult result);
    }
    interface LocationManagerResult {
        void onLocationChange(LocationE location);

        void onLocationSetupFailed();
    }
}
