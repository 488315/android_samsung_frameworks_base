package com.android.server.policy;

import android.hardware.hdmi.HdmiPlaybackClient;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PhoneWindowManager$HdmiControl$1
        implements HdmiPlaybackClient.OneTouchPlayCallback {
    public final void onComplete(int i) {
        if (i != 0) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "One touch play failed: ", "WindowManager");
        }
    }
}
