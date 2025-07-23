package android.telecom;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes4.dex */
public interface CallEventCallback {
    void onAvailableCallEndpointsChanged(List<CallEndpoint> list);

    void onCallEndpointChanged(CallEndpoint callEndpoint);

    void onCallStreamingFailed(int i);

    void onEvent(String str, Bundle bundle);

    void onMuteStateChanged(boolean z);

    default void onVideoStateChanged(int i) {
    }
}
