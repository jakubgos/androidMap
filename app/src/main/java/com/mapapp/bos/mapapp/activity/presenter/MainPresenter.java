package com.mapapp.bos.mapapp.activity.presenter;

import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import com.mapapp.bos.mapapp.Data.OkHttpImpl;

import com.mapapp.bos.mapapp.R;
import com.mapapp.bos.mapapp.activity.Mvp_inter;
import com.mapapp.bos.mapapp.activity.model.MainModel;
import com.mapapp.bos.mapapp.common.Weather;

/**
 * Created by Bos on 2016-12-04.
 */
public class MainPresenter implements Mvp_inter.PresenterOps,MainModel.ModelResult {

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and we don't want to create a memory leak
    private WeakReference<Mvp_inter.ViewOps> mView;
    // Model reference
    private Mvp_inter.PresenterToModel mModel;

    private Handler handler;

    /**
     * Presenter Constructor
     * @param view  MainActivity
     */
    public MainPresenter(Mvp_inter.ViewOps view) {
        mView = new WeakReference<>(view);
        handler = new Handler();
        mModel = new MainModel(new OkHttpImpl());
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
           // getView().showMainMenu();
        }

    }

   // @Override
    public void onCitySelected(final int id) {
        //getView().showProgressBar();
        //getView().hideListView();

        //String cityName= getView().getViewResources().getStringArray(R.array.cityName)[id];
       // mModel.downloadWeatherData(cityName, this);
    }

    private void handleWeatherResult(Weather weather) {
       // getView().hideProgressBar();
       // getView().loadResultFragment(weather);
    }


    @Override
    public void onwWeatherReady(final Weather weather) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                handleWeatherResult(weather);
            }
        });
    }

    @Override
    public void onDownloadError(String msg) {
    }
}
