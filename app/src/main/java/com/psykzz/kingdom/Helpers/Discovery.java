package com.psykzz.kingdom.Helpers;

/**
 * Created by PsyKzz on 25/11/2016.
 */

public class Discovery extends Object {
    private String mEndpointId;
    private String mdeviceId;
    private String mServiceId;
    private String mEndpointName;

    Discovery(String endpoint, String deviceId, String serviceId, String endpointName) {
        mEndpointId = endpoint;
        mdeviceId = deviceId;
        mServiceId = serviceId;
        mEndpointName = endpointName;
    }

    public boolean equals(Discovery other) {
        return mdeviceId == other.getDeviceId();
    }

    public String getEndpointId() {
        return mEndpointId;
    }

    public String getDeviceId() {
        return mdeviceId;
    }

    public String getServiceId() {
        return mServiceId;
    }

    public String getEndpointName() {
        return mEndpointName;
    }
}
