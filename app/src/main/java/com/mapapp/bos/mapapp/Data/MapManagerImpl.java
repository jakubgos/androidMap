package com.mapapp.bos.mapapp.Data;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mapapp.bos.mapapp.activity.Mvp_inter;

/**
 * Created by Bos on 2016-12-14.
 */

public class MapManagerImpl implements Mvp_inter.MapManager {

    SupportMapFragment supportMapFragment;

   public MapManagerImpl(SupportMapFragment supportMapFragment) {
        this.supportMapFragment = supportMapFragment;
    }

    @Override
    public void setupMap(final Mvp_inter.MapManagerResult callBack) {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                callBack.onMapReady(googleMap);
            }
        });
    }

}

