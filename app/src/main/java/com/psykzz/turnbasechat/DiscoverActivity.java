package com.psykzz.turnbasechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.gms.common.api.GoogleApiClient;
import com.psykzz.turnbasechat.Helpers.LocalDiscovery;

/*
Discovery new clients and show a list of clients ready to talk to.
 */
public class DiscoverActivity extends Activity  {

    private GoogleApiClient mGoogleApiClient;
    private LocalDiscovery mLocalDiscovery;
    private boolean mIsHost;

    private Button btnRefresh;

    private ListView mListView;
    private Notification mNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNotifications = new Notification(this);
        mLocalDiscovery = new LocalDiscovery(this);
        setContentView(R.layout.discover);


        // Enable discoverable mode
        Switch switchDiscoverable = (Switch) findViewById(R.id.upper_setting_switch);
        switchDiscoverable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLocalDiscovery.setDiscoverable(isChecked);
            }
        });

        // Click a chat
        mListView = (ListView) findViewById(R.id.discovered_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) mListView.getItemAtPosition(position);

                // Show Alert
                mNotifications.toast("Position :" + itemPosition + "  ListItem : " + itemValue);
            }
        });
        syncDiscoveries();

        // refresh the list
        Button btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocalDiscovery.startDiscovery();
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();

            }
        });
    }

    public void syncDiscoveries() {
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.discover, mLocalDiscovery.getRecentDiscoveries()));
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
