package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model;

import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface FakeWifiEventModel {

    public final class CarrierMerged implements FakeWifiEventModel {
        public final int activity;
        public final int level;
        public final int numberOfLevels;
        public final int subscriptionId;

        public CarrierMerged(int i, int i2, int i3, int i4) {
            this.subscriptionId = i;
            this.level = i2;
            this.numberOfLevels = i3;
            this.activity = i4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CarrierMerged)) {
                return false;
            }
            CarrierMerged carrierMerged = (CarrierMerged) obj;
            return this.subscriptionId == carrierMerged.subscriptionId && this.level == carrierMerged.level && this.numberOfLevels == carrierMerged.numberOfLevels && this.activity == carrierMerged.activity;
        }

        public final int hashCode() {
            return Integer.hashCode(this.activity) + ReorderTile$$ExternalSyntheticOutline0.m(this.numberOfLevels, ReorderTile$$ExternalSyntheticOutline0.m(this.level, Integer.hashCode(this.subscriptionId) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierMerged(subscriptionId=");
            sb.append(this.subscriptionId);
            sb.append(", level=");
            sb.append(this.level);
            sb.append(", numberOfLevels=");
            sb.append(this.numberOfLevels);
            sb.append(", activity=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.activity, ")", sb);
        }
    }

    public final class WifiDisabled implements FakeWifiEventModel {
        public static final WifiDisabled INSTANCE = new WifiDisabled();

        private WifiDisabled() {
        }
    }

    public final class Wifi implements FakeWifiEventModel {
        public final int activity;
        public final WifiNetworkModel.HotspotDeviceType hotspotDeviceType;
        public final Integer level;
        public final String ssid;
        public final Boolean validated;

        public Wifi(Integer num, int i, String str, Boolean bool, WifiNetworkModel.HotspotDeviceType hotspotDeviceType) {
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
            if (!(obj instanceof Wifi)) {
                return false;
            }
            Wifi wifi = (Wifi) obj;
            return Intrinsics.areEqual(this.level, wifi.level) && this.activity == wifi.activity && Intrinsics.areEqual(this.ssid, wifi.ssid) && Intrinsics.areEqual(this.validated, wifi.validated) && this.hotspotDeviceType == wifi.hotspotDeviceType;
        }

        public final int hashCode() {
            Integer num = this.level;
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.activity, (num == null ? 0 : num.hashCode()) * 31, 31);
            String str = this.ssid;
            int iHashCode = (iM + (str == null ? 0 : str.hashCode())) * 31;
            Boolean bool = this.validated;
            return this.hotspotDeviceType.hashCode() + ((iHashCode + (bool != null ? bool.hashCode() : 0)) * 31);
        }

        public final String toString() {
            return "Wifi(level=" + this.level + ", activity=" + this.activity + ", ssid=" + this.ssid + ", validated=" + this.validated + ", hotspotDeviceType=" + this.hotspotDeviceType + ")";
        }

        public /* synthetic */ Wifi(Integer num, int i, String str, Boolean bool, WifiNetworkModel.HotspotDeviceType hotspotDeviceType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(num, i, str, bool, (i2 & 16) != 0 ? WifiNetworkModel.HotspotDeviceType.NONE : hotspotDeviceType);
        }
    }
}
