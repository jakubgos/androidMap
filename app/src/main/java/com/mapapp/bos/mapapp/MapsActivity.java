package com.mapapp.bos.mapapp;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapapp.bos.mapapp.Data.OkHttpImpl;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.activity.presenter.MainPresenter;

public class MapsActivity extends FragmentActivity implements Mvp_inter.ViewOps {

    private Mvp_inter.PresenterOps mPresenter;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        setupMVP();
        mPresenter.onStartup(savedInstanceState);
    }

    private void setupMVP() {

        MainPresenter presenter = new MainPresenter(this);
        mPresenter = presenter;

    }

    @Override
    public Resources getViewResources() {
        return null;
    }

    @Override
    public void setupMap(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void printMarketAndMoveCamera(LatLng latLng, String text) {
        if (mMap != null) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(text));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));}
        else{ throw new NullPointerException("mMap is unavailable");}

    }

    @Override
    public SupportMapFragment getSupportMapFragment() {
        return mapFragment;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestGpsPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1
        );
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("...","onRequestPermissionsResult");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPresenter.permissionGranted();
        }
    }

}
