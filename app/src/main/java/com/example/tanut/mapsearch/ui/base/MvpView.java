package com.example.tanut.mapsearch.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by tanut on 10/18/2017.
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();


}
