package com.mapapp.bos.mapapp.activity;

import android.content.res.Resources;
import android.os.Bundle;

import com.mapapp.bos.mapapp.activity.model.MainModel;


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

    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface PresenterOps {
        void onStartup(Bundle savedInstanceState);
    }



    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface PresenterToModel {
        void downloadWeatherData(String city, MainModel.ModelResult callBack);
    }
}
