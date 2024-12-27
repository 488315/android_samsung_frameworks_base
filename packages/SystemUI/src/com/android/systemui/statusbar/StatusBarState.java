package com.android.systemui.statusbar;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

public final class StatusBarState {
    public static String toString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "UNKNOWN: ") : "SHADE_LOCKED" : "KEYGUARD" : "SHADE";
    }
}
