package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class FakeWifiEventModel$Wifi {
    public final int activity;
    public final WifiNetworkModel.HotspotDeviceType hotspotDeviceType;
    public final Integer level;
    public final String ssid;
    public final Boolean validated;

    public FakeWifiEventModel$Wifi(Integer num, int i, String str, Boolean bool, WifiNetworkModel.HotspotDeviceType hotspotDeviceType) {
        this.level = num;
        this.activity = i;
        this.ssid = str;
        this.validated = bool;
        this.hotspotDeviceType = hotspotDeviceType;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FakeWifiEventModel$Wifi)) {
            return false;
        }
        FakeWifiEventModel$Wifi fakeWifiEventModel$Wifi = (FakeWifiEventModel$Wifi) obj;
        return Intrinsics.areEqual(this.level, fakeWifiEventModel$Wifi.level) && this.activity == fakeWifiEventModel$Wifi.activity && Intrinsics.areEqual(this.ssid, fakeWifiEventModel$Wifi.ssid) && Intrinsics.areEqual(this.validated, fakeWifiEventModel$Wifi.validated) && this.hotspotDeviceType == fakeWifiEventModel$Wifi.hotspotDeviceType;
    }

    public final int hashCode() {
        Integer num = this.level;
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.activity, (num == null ? 0 : num.hashCode()) * 31, 31);
        String str = this.ssid;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        Boolean bool = this.validated;
        return this.hotspotDeviceType.hashCode() + ((hashCode + (bool != null ? bool.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "Wifi(level=" + this.level + ", activity=" + this.activity + ", ssid=" + this.ssid + ", validated=" + this.validated + ", hotspotDeviceType=" + this.hotspotDeviceType + ")";
    }

    public /* synthetic */ FakeWifiEventModel$Wifi(Integer num, int i, String str, Boolean bool, WifiNetworkModel.HotspotDeviceType hotspotDeviceType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(num, i, str, bool, (i2 & 16) != 0 ? WifiNetworkModel.HotspotDeviceType.NONE : hotspotDeviceType);
    }
}
