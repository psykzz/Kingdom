package com.psykzz.kingdom;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by PsyKzz on 25/11/2016.
 */

public class Notification {

    private DiscoverActivity mActivity;

    public Notification(DiscoverActivity activity) {
        mActivity = activity;
    }

    public void toast(String message) {
        toast(message, true);
     }

    void toast(String message, Boolean isLong ) {
        int duration = Snackbar.LENGTH_SHORT;
        if (isLong){
            duration = Snackbar.LENGTH_LONG;
        }
        Snackbar snackbar = Snackbar
                .make(mActivity.findViewById(android.R.id.content), message, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        snackbar.show();
    }
}
