package com.android.systemui.statusbar.policy;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

public interface DevicePostureController extends CallbackController {

    public interface Callback {
        void onPostureChanged(int i);
    }

    static String devicePostureToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "UNSUPPORTED POSTURE posture=") : "DEVICE_POSTURE_FLIPPED" : "DEVICE_POSTURE_OPENED" : "DEVICE_POSTURE_HALF_OPENED" : "DEVICE_POSTURE_CLOSED" : "DEVICE_POSTURE_UNKNOWN";
    }
}
