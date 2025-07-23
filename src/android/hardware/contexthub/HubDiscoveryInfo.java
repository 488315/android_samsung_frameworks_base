package android.hardware.contexthub;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public class HubDiscoveryInfo {
    private final HubEndpointInfo mEndpointInfo;
    private final HubServiceInfo mServiceInfo;

    public HubDiscoveryInfo(HubEndpointInfo hubEndpointInfo) {
        this.mEndpointInfo = hubEndpointInfo;
        this.mServiceInfo = null;
    }

    public HubDiscoveryInfo(HubEndpointInfo hubEndpointInfo, HubServiceInfo hubServiceInfo) {
        this.mEndpointInfo = hubEndpointInfo;
        this.mServiceInfo = hubServiceInfo;
    }

    public HubEndpointInfo getHubEndpointInfo() {
        return this.mEndpointInfo;
    }

    public HubServiceInfo getHubServiceInfo() {
        return this.mServiceInfo;
    }
}
