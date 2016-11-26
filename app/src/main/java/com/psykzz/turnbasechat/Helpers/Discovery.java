package com.psykzz.turnbasechat.Helpers;

/**
 * Created by PsyKzz on 25/11/2016.
 */

public class Discovery {
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

    public String getmEndpointId() {
        return mEndpointId;
    }

    public String getMdeviceId() {
        return mdeviceId;
    }

    public String getmServiceId() {
        return mServiceId;
    }

    public String getmEndpointName() {
        return mEndpointName;
    }
}
