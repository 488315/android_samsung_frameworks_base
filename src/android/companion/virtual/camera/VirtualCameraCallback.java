package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.view.Surface;

@SystemApi
/* loaded from: classes.dex */
public interface VirtualCameraCallback {
    default void onProcessCaptureRequest(int i, long j) {
    }

    void onStreamClosed(int i);

    void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4);
}
