package com.psykzz.kingdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.psykzz.kingdom.Helpers.Discovery;
import com.psykzz.kingdom.Helpers.LocalDiscovery;
import com.psykzz.kingdom.Helpers.RecyclerEmptyView;

/*
Discovery new clients and show a list of clients ready to talk to.
 */
public class DiscoverActivity extends Activity  {

//    private FirebaseAnalytics mFirebaseAnalytics
    // view information
    private RecyclerEmptyView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // end -

    public LocalDiscovery mLocalDiscovery;
    private boolean mIsHost;

    private Notification mNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover);

//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Add our other classes
        mNotifications = new Notification(this);
        mLocalDiscovery = new LocalDiscovery(this);

        mRecyclerView = (RecyclerEmptyView) findViewById(R.id.discovered_list);
        mRecyclerView.setHasFixedSize(true); // perf improvements

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(findViewById(R.id.list_empty));

        mAdapter = new DiscoveryAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);

        // Setup switch for enabling local advertising
        Switch switchDiscoverable = (Switch) findViewById(R.id.upper_setting_switch);
        switchDiscoverable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLocalDiscovery.setDiscoverable(isChecked);
            }
        });

        // Refresh button to discover other devices
        Button btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocalDiscovery.startDiscovery();
            }
        });
    }

    public void showChat(Discovery disc) {
        showChat(disc, false);
    }

    public void showChat(Discovery disc, boolean previousChat) {
        Intent mainIntent = new Intent(DiscoverActivity.this, ChatActivity.class);
        DiscoverActivity.this.startActivity(mainIntent);
    }

    public void onDiscoveryClicked(View view) {
        Discovery disc = (Discovery) view.getTag();
        showChat(disc);
        Log.d("TAGME", "Loading chat :: " + disc.getEndpointId());
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

    @Override
    protected void onResume() {
        super.onResume();

        if (mLocalDiscovery.mGoogleApiClient.isConnected() != true) {
            mLocalDiscovery.mGoogleApiClient.connect();
        }

        Switch switchDiscoverable = (Switch) findViewById(R.id.upper_setting_switch);
        if (switchDiscoverable.isChecked()) {
            mLocalDiscovery.startAdvertising();
        }
    }
}
