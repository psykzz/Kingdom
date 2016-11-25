package com.psykzz.turnbasechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.common.api.GoogleApiClient;

public class DiscoverActivity extends Activity  {

    private GoogleApiClient mGoogleApiClient;
    private String[] participantIds;
    private LocalDiscovery mLocalDiscovery;
    private boolean mIsHost;

    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocalDiscovery = new LocalDiscovery(this);
        setContentView(R.layout.discover);

        Switch switchDiscoverable = (Switch) findViewById(R.id.upper_setting_switch);
        switchDiscoverable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLocalDiscovery.setDiscoverable(isChecked);
            }
        });
        Button btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocalDiscovery.startDiscovery();
            }
        });
    }

    private void showChat() {
        showChat(false, 0);
    }

    private void showChat(Boolean previousChat, int chatId) {
        Intent mainIntent = new Intent(DiscoverActivity.this, ChatActivity.class);
        DiscoverActivity.this.startActivity(mainIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLocalDiscovery.mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocalDiscovery.mGoogleApiClient != null && mLocalDiscovery.mGoogleApiClient.isConnected()) {
            mLocalDiscovery.mGoogleApiClient.disconnect();
        }
    }



}
