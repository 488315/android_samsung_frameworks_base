package android.hardware.contexthub;

import android.annotation.SystemApi;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public interface HubEndpointDiscoveryCallback {
    void onEndpointsStarted(List<HubDiscoveryInfo> list);

    void onEndpointsStopped(List<HubDiscoveryInfo> list, int i);
}
