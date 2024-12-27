package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.view.Surface;

@SystemApi
public interface VirtualCameraCallback {
    void onStreamClosed(int i);

    void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4);

    default void onProcessCaptureRequest(int streamId, long frameId) {}
}
