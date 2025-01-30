package com.android.systemui.statusbar.policy;

import android.support.v4.media.AbstractC0000x2c234b15;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface DevicePostureController extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onPostureChanged(int i);
    }

    static String devicePostureToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? AbstractC0000x2c234b15.m0m("UNSUPPORTED POSTURE posture=", i) : "DEVICE_POSTURE_FLIPPED" : "DEVICE_POSTURE_OPENED" : "DEVICE_POSTURE_HALF_OPENED" : "DEVICE_POSTURE_CLOSED" : "DEVICE_POSTURE_UNKNOWN";
    }
}
