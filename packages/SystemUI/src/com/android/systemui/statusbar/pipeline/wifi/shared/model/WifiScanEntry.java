package com.android.systemui.statusbar.pipeline.wifi.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("WifiScanEntry(ssid="), this.ssid, ")");
    }
}
