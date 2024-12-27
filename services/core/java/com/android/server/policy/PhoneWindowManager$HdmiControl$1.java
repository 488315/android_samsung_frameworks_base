package com.android.server.policy;

import android.hardware.hdmi.HdmiPlaybackClient;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;

public final class PhoneWindowManager$HdmiControl$1
        implements HdmiPlaybackClient.OneTouchPlayCallback {
    public final void onComplete(int i) {
        if (i != 0) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "One touch play failed: ", "WindowManager");
        }
    }
}
