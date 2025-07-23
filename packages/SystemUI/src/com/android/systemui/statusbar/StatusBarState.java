package com.android.systemui.statusbar;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarState {
    public static String toString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "UNKNOWN: ") : "SHADE_LOCKED" : "KEYGUARD" : "SHADE";
    }
}
