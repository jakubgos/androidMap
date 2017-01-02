package com.mapapp.bos.mapapp.activity.presenter;

import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.mapapp.bos.mapapp.Data.LocationManagerImpl;
import com.mapapp.bos.mapapp.Data.MapManagerImpl;

import com.mapapp.bos.mapapp.Data.PlaceFinderImpl;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.common.LocationE;
import com.mapapp.bos.mapapp.gson.PlacesResult;

/**
 * Created by Bos on 2016-12-04.
 */
public class MainPresenter implements Mvp_inter.PresenterOps,MainModel.ModelMapResult,MainModel.ModelLocationResult, MainModel.ModelPlacesResult {

    private WeakReference<Mvp_inter.ViewOps> mView;
    private Mvp_inter.PresenterToModel mModel;
    private Handler handler;
    LocationE currentLoc = new LocationE(51.759445, 19.457216); //lodz
    PlacesResult placesResult;
    boolean isFirstMove = true;

    public MainPresenter(Mvp_inter.ViewOps view) {
        mView = new WeakReference<>(view);
        handler = new Handler();
        mModel = new MainModel(new MapManagerImpl(getView().getSupportMapFragment()),
                new LocationManagerImpl(getView().getContext()),
                new PlaceFinderImpl() );
    }


    private Mvp_inter.ViewOps  getView() throws NullPointerException {
        if ( mView != null )
            return mView.get();
        else
        throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onStartup(Bundle savedInstanceState) {
            getView().showSearchBar();
            getView().hideMap();
            getView().showProgressBar();
            getView().hideListView();
            mModel.prepareMapData(this);

    }

    @Override
    public void permissionGranted() {
        mModel.startGps(this);
    }

    @Override
    public void onSearchConfirmed(CharSequence charSequence) {
        if(charSequence.length() <= 2) {
            getView().showMsg("Too short");
            return;
        }

        getView().hideMap();
        getView().showProgressBar();
        mModel.downloadPlaceData(charSequence, currentLoc, this);
    }

    @Override
    public void onSearchStateChanged(boolean b) {

    }

    @Override
    public void onPlaceSelected(int pos) {
        getView().hideListView();
        getView().showMap();
        getView().showSearchBar();

        getView().printMarketAndMoveCamera(new LatLng(placesResult.asList().get(pos).getGeometry().getLocation().getLat(),
                placesResult.asList().get(pos).getGeometry().getLocation().getLng()),
                placesResult.asList().get(pos).getName());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        getView().hideProgressBar();
        getView().showMap();
        getView().setupMap(map);
        mModel.startGps(this);

    }


    @Override
    public void onLocationChange(LocationE location) {
        currentLoc = location;
        LatLng latLng = new LatLng(location.lat, location.lng);
        if (isFirstMove) {
            getView().printMarketAndMoveCamera(latLng, "You");
            isFirstMove = false;
        }
    }

    @Override
    public void onLocationSetupFailed() {
        getView().requestGpsPermission();
    }

    @Override
    public void onPlacesResult(final PlacesResult result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().hideProgressBar();
                if(result.isOkay()) {
                    placesResult = result;
                    getView().hideSearchBar();
                    getView().showPlaceList(result);
                    getView().showListView();
                }
                else
                {
                    getView().showMap();
                    getView().showMsg("Error:" + result.getStatus());
                }
            }
        });


    }
}
