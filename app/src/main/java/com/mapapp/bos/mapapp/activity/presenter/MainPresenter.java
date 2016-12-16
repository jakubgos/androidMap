package com.mapapp.bos.mapapp.activity.presenter;

import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.mapapp.bos.mapapp.Data.LocationManagerImpl;
import com.mapapp.bos.mapapp.Data.MapManagerImpl;

import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.common.LocationE;

/**
 * Created by Bos on 2016-12-04.
 */
public class MainPresenter implements Mvp_inter.PresenterOps,MainModel.ModelMapResult,MainModel.ModelLocationResult {

    private WeakReference<Mvp_inter.ViewOps> mView;
    private Mvp_inter.PresenterToModel mModel;
    private Handler handler;


    public MainPresenter(Mvp_inter.ViewOps view) {
        mView = new WeakReference<>(view);
        handler = new Handler();
        mModel = new MainModel(new MapManagerImpl(getView().getSupportMapFragment()), new LocationManagerImpl(getView().getContext() ));
    }


    private Mvp_inter.ViewOps  getView() throws NullPointerException {
        if ( mView != null )
            return mView.get();
        else
        throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onStartup(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            mModel.prepareMapData(this);
        }
    }

    @Override
    public void permissionGranted() {
        mModel.startGps(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        getView().setupMap(map);
        mModel.startGps(this);

    }


    @Override
    public void onLocationChange(LocationE location) {
        LatLng latLng = new LatLng(location.lat, location.lng);
        getView().printMarketAndMoveCamera(latLng,"You");
    }

    @Override
    public void onLocationSetupFailed() {
        getView().requestGpsPermission();
    }
}
