package com.mapapp.bos.mapapp.Data;

import com.mapapp.bos.mapapp.common.Weather;

/**
 * Created by Bos on 2016-12-04.
 */

public interface NetworkInterface {

    void getWeather(String city, NetworkInterfaceResult networkInterfaceResult);


    interface NetworkInterfaceResult {
        void onResultReady(Weather weather);
        void onResultError(String msg);
    }

}
