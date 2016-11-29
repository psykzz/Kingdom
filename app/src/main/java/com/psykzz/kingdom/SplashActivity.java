package com.psykzz.kingdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, DiscoverActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(SplashActivity.this.getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                SplashActivity.this.startActivity(intent, bundle);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}