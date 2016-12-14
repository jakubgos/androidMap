package com.mapapp.bos.mapapp.Data;

import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.mapapp.bos.mapapp.common.Weather;

/**
 * Created by Bos on 2016-12-04.
 */

public class OkHttpImpl implements NetworkInterface {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/find?q=%s";

    @Override
    public void getWeather(final String city, final NetworkInterfaceResult result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                OkHttpClient client = new OkHttpClient();
                try {
                    url = new URL(String.format(OPEN_WEATHER_MAP_API, city));

                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("x-api-key", "d32a0bb9c5d13290bb3dcca5f2b3883a")
                            .build();

                    Response response = client.newCall(request).execute();
                    JSONObject data = new JSONObject(response.body().string());


                    Log.d("myLog", "response from api: " + data);

                    Weather resultWeather = new Weather();

                    resultWeather.temp = data.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp") - 272.15; //double
                    resultWeather.cityName = data.getJSONArray("list").getJSONObject(0).getString("name");//string
                    resultWeather.pressure = String.format("%.2f hPa", data.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("pressure"));//string
                    resultWeather.country = data.getJSONArray("list").getJSONObject(0).getJSONObject("sys").getString("country");
                    resultWeather.description = data.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");

                    result.onResultReady(resultWeather);

                } catch (Exception e) {
                    e.printStackTrace();
                    result.onResultError(e.getMessage());
                }
            }
        });
        thread.start();
    }
}