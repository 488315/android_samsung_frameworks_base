package com.android.systemui.statusbar;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StatusBarState {
    public static String toString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "UNKNOWN: ") : "SHADE_LOCKED" : "KEYGUARD" : "SHADE";
    }
}
