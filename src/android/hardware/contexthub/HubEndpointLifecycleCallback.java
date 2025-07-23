package android.hardware.contexthub;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public interface HubEndpointLifecycleCallback {
    void onSessionClosed(HubEndpointSession hubEndpointSession, int i);

    HubEndpointSessionResult onSessionOpenRequest(HubEndpointInfo hubEndpointInfo, String str);

    void onSessionOpened(HubEndpointSession hubEndpointSession);
}
