package com.mapapp.bos.mapapp.Data;

import android.util.Log;
import android.widget.Toast;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.common.LocationE;
import com.mapapp.bos.mapapp.common.PlaceE;
import com.mapapp.bos.mapapp.common.Weather;
import com.mapapp.bos.mapapp.gson.Place;
import com.mapapp.bos.mapapp.gson.PlacesResult;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Bos on 2016-12-28.
 */
public class PlaceFinderImpl implements Mvp_inter.PlaceFinder {

    private static final String OPEN_WEATHER_MAP_API =
    "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=5000&keyword=%s&language=pl&key=AIzaSyB6O9TqVUCR5n6UQjmP_but4vtRYO9zyEs";
    //"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=5000&keyword=%s&language=pl&key=AIzaSyAgLnKcIO-yMTeF5ixXk-VS3vUkIG9Pyjo";


    private static final String LOCATION_PATTERN = "%f,%f";
    @Override
    public void getPlaceData(final CharSequence charSequence, final LocationE currentLoc, final Mvp_inter.PlaceFinderResult placeFinderResult) {

        Thread thread = new Thread(new Runnable() {
            public Gson gson;

            @Override
            public void run() {
                URL url = null;
                OkHttpClient client = new OkHttpClient();
                PlaceE placeResult = new PlaceE();
                try {

                    url = new URL(String.format(Locale.US,OPEN_WEATHER_MAP_API,currentLoc.lat,currentLoc.lng,charSequence.toString()));

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("myLog", "Req to api: " + request.toString());
                    //Log.d("myLog", "response from api: " + response.body().string());

                    GsonBuilder gb = new GsonBuilder( );
                    gb.setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES );
                    this.gson = gb.create( );

                    PlacesResult res = this.gson.fromJson( response.body().string() , PlacesResult.class );

                    placeFinderResult.onPlacesResult(res);



                } catch (Exception e) {
                    e.printStackTrace();
                    //result.onResultError(e.getMessage());
                }
            }
        });

        thread.start();


    }
}
