package com.psykzz.kingdom.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AppIdentifier;
import com.google.android.gms.nearby.connection.AppMetadata;
import com.google.android.gms.nearby.connection.Connections;
import com.psykzz.kingdom.DiscoverActivity;
import com.psykzz.kingdom.Notification;
import com.psykzz.kingdom.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LocalDiscovery implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Connections.ConnectionRequestListener,
        Connections.MessageListener,
        Connections.EndpointDiscoveryListener {

    private final Logger Log = Logger.getLogger(this.toString());

    private DiscoverActivity mActivity;
    private Notification mNotifications;

    public ArrayList<Discovery> mRecentDiscoveries = new ArrayList<>();

    public GoogleApiClient mGoogleApiClient;

    private static int[] NETWORK_TYPES = {ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_ETHERNET};
    private boolean mDiscoverable;

    public LocalDiscovery(DiscoverActivity activity) {
        mActivity = activity;
        mNotifications = new Notification(activity);

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Nearby.CONNECTIONS_API)
                .build();
    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager connManager =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (int networkType : NETWORK_TYPES) {
            NetworkInfo info = connManager.getNetworkInfo(networkType);
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public void stopAdvertising() {
        Nearby.Connections.stopAdvertising(mGoogleApiClient);
        Log.info("Nearby.Connections:: Stopping advertising");
        mNotifications.toast("Stopping advertising.");
    }

    public void startAdvertising() {
        if (!isConnectedToNetwork()) {
            mNotifications.toast("Networking is not available.");
            return;
        }

        // Advertising with an AppIdentifer lets other devices on the
        // network discover this application and prompt the user to
        // install the application.
        List<AppIdentifier> appIdentifierList = new ArrayList<>();
        appIdentifierList.add(new AppIdentifier(mActivity.getPackageName()));
        AppMetadata appMetadata = new AppMetadata(appIdentifierList);

        // The advertising timeout is set to run indefinitely
        // Positive values represent timeout in milliseconds
        long NO_TIMEOUT = 0L;

        String name = null;
        Nearby.Connections.startAdvertising(mGoogleApiClient, name, appMetadata, NO_TIMEOUT,
                this).setResultCallback(new ResultCallback<Connections.StartAdvertisingResult>() {
            @Override
            public void onResult(Connections.StartAdvertisingResult result) {
                if (result.getStatus().isSuccess()) {
                    mNotifications.toast("Device is advertising.");
                    Log.info("Nearby.Connections:: Device is advertising");
                } else {
                    int statusCode = result.getStatus().getStatusCode();
                    Log.info("Nearby.Connections:: Advertising failed - see statusCode for more details");
                    Log.info("Nearby.Connections:: Advertising failed - " + statusCode);
                    mNotifications.toast("Advertising failed - see statusCode for more details.");
                }
            }
        });
    }
    public void startDiscovery() {
        if (!isConnectedToNetwork()) {
            mNotifications.toast("Networking is not available.");
            return;
        }
        String serviceId = mActivity.getString(R.string.service_id);

        // Set an appropriate timeout length in milliseconds
        long DISCOVER_TIMEOUT = 1000L;

        // Discover nearby apps that are advertising with the required service ID.
        Nearby.Connections.startDiscovery(mGoogleApiClient, serviceId, DISCOVER_TIMEOUT, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mNotifications.toast("Searching nearby");
                            Log.info("Nearby.Connections:: Device is discovering nearby devices");
                        } else {
                            int statusCode = status.getStatusCode();
                            mNotifications.toast("Advertising failed - see statusCode for more details" + statusCode);
                            Log.info("Nearby.Connections:: Advertising failed - see statusCode for more details" + statusCode);
                        }
                    }
                });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.info("onConnected - we are connected");
        startDiscovery();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.info("onConnectionSuspended - Connection was suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.info("onConnectionFailed - Connection failed for some reason");
    }

    @Override
    public void onConnectionRequest(String s, String s1, String s2, byte[] bytes) {
        Log.info("onConnectionRequest - new request for conn");
    }

    @Override
    public void onEndpointFound(final String endpointId, String deviceId,
                                String serviceId, final String endpointName) {
        Discovery disc = new Discovery(endpointId, deviceId, serviceId, endpointName);
        for(Discovery oDisc : mRecentDiscoveries) {
            if(oDisc.equals(disc)) {
                return;
            }
        }
        mRecentDiscoveries.add(disc);
        mActivity.mAdapter.notifyDataSetChanged();

        Log.info("onEndpointFound - found new endpoint");
        Log.info("onEndpointFound - " + endpointId);
        Log.info("onEndpointFound - " + deviceId);
        Log.info("onEndpointFound - " + serviceId);
        Log.info("onEndpointFound - " + endpointName);
        Log.info("onEndpointFound - ---");
    }

    @Override
    public void onEndpointLost(String s) {
        Log.info("onEndpointLost - lost an endpoint");
    }

    @Override
    public void onMessageReceived(String s, byte[] bytes, boolean b) {
        Log.info("onMessageReceived - got message");
    }

    @Override
    public void onDisconnected(String s) {
        Log.info("onDisconnected - Disconnected");
    }

    public void setDiscoverable(boolean discoverable) {
        this.mDiscoverable = discoverable;
        if(mDiscoverable)
            startAdvertising();
        else
            stopAdvertising();
    }

    public void stopAll() {
        Nearby.Connections.stopAllEndpoints(mGoogleApiClient);
    }

    public void connectTo(Discovery discovery) {
        connectTo(discovery.getEndpointId(), null);
    }

    public void connectTo(Discovery discovery, byte[] payload) {
        connectTo(discovery.getEndpointId(), payload);
    }

    public void connectTo(String endpointId) {
        connectTo(endpointId, null);
    }

    public void connectTo(String endpointId, byte[] payload) {
        // Send a connection request to a remote endpoint. By passing 'null' for
        // the name, the Nearby Connections API will construct a default name
        // based on device model such as 'LGE Nexus 5'.
        Nearby.Connections.sendConnectionRequest(mGoogleApiClient, null,
                endpointId, payload, new Connections.ConnectionResponseCallback() {
                    @Override
                    public void onConnectionResponse(String remoteEndpointId, Status status,
                                                     byte[] bytes) {
                        if (status.isSuccess()) {
                            // Successful connection
                        } else {
                            // Failed connection
                        }
                    }
                }, this);
    }


}
