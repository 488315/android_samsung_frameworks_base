package android.hardware.contexthub;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public interface HubEndpointMessageCallback {
    void onMessageReceived(HubEndpointSession hubEndpointSession, HubMessage hubMessage);
}
