package com.android.systemui.statusbar.pipeline.wifi.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class WifiScanEntry {
    public final String ssid;

    public WifiScanEntry(String str) {
        this.ssid = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof WifiScanEntry) && Intrinsics.areEqual(this.ssid, ((WifiScanEntry) obj).ssid);
    }

    public final int hashCode() {
        return this.ssid.hashCode();
    }

    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("WifiScanEntry(ssid="), this.ssid, ")");
    }
}
