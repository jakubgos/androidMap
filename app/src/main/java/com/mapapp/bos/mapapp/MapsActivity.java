package com.mapapp.bos.mapapp;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mapapp.bos.mapapp.Data.CustomList;
import com.mapapp.bos.mapapp.Data.OkHttpImpl;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.activity.presenter.MainPresenter;
import com.mapapp.bos.mapapp.common.LocationE;
import com.mapapp.bos.mapapp.gson.Place;
import com.mapapp.bos.mapapp.gson.PlacesResult;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements Mvp_inter.ViewOps, MaterialSearchBar.OnSearchActionListener {

    private Mvp_inter.PresenterOps mPresenter;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    ProgressBar progressBar;
    ListView listView;
    private MaterialSearchBar searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        setupMVP();

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);

        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        FadingCircle doubleBounce = new FadingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);


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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        }
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
    public void hideMap() {
        findViewById(R.id.map).setVisibility(View.GONE);
    }

    @Override
    public void showMap() {
        findViewById(R.id.map).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.spin_kit).setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.spin_kit).setVisibility(View.VISIBLE);
    }


    @Override
    public void hideListView() {
        findViewById(R.id.listView).setVisibility(View.GONE);
    }

    @Override
    public void showListView() {
        findViewById(R.id.listView).setVisibility(View.VISIBLE);
    }


    @Override
    public void hideSearchBar() {
        findViewById(R.id.searchBar).setVisibility(View.GONE);
    }

    @Override
    public void showSearchBar() {
        findViewById(R.id.searchBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlaceList(PlacesResult placesResult) {


        listView = (ListView) this.findViewById(R.id.listView);


        List<String> list = new ArrayList<String>();

        for ( Place place : placesResult ) {
            list.add(place.getName());
        }

        CustomList adapter = new
                CustomList(getContext(), list.toArray(new String[0]) );
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos,   long id) {
                mPresenter.onPlaceSelected(pos);
            }
        });
    }

    @Override
    public void showMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("...","onRequestPermissionsResult");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPresenter.permissionGranted();
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence charSequence) {
                mPresenter.onSearchConfirmed(charSequence);
    }

    @Override
    public void onSearchStateChanged(boolean b) {
        mPresenter.onSearchStateChanged(b);
    }



    @Override
    public void onButtonClicked(int buttonCode) {
    }
}
