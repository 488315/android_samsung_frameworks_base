package com.android.systemui.statusbar.pipeline.wifi.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class WifiNetworkModel implements Diffable {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getMIN_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    public final class HotspotDeviceType {
        public static final /* synthetic */ HotspotDeviceType[] $VALUES;
        public static final HotspotDeviceType AUTO;
        public static final HotspotDeviceType INVALID;
        public static final HotspotDeviceType LAPTOP;
        public static final HotspotDeviceType NONE;
        public static final HotspotDeviceType PHONE;
        public static final HotspotDeviceType TABLET;
        public static final HotspotDeviceType UNKNOWN;
        public static final HotspotDeviceType WATCH;

        static {
            HotspotDeviceType hotspotDeviceType = new HotspotDeviceType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = hotspotDeviceType;
            HotspotDeviceType hotspotDeviceType2 = new HotspotDeviceType("UNKNOWN", 1);
            UNKNOWN = hotspotDeviceType2;
            HotspotDeviceType hotspotDeviceType3 = new HotspotDeviceType("PHONE", 2);
            PHONE = hotspotDeviceType3;
            HotspotDeviceType hotspotDeviceType4 = new HotspotDeviceType("TABLET", 3);
            TABLET = hotspotDeviceType4;
            HotspotDeviceType hotspotDeviceType5 = new HotspotDeviceType("LAPTOP", 4);
            LAPTOP = hotspotDeviceType5;
            HotspotDeviceType hotspotDeviceType6 = new HotspotDeviceType("WATCH", 5);
            WATCH = hotspotDeviceType6;
            HotspotDeviceType hotspotDeviceType7 = new HotspotDeviceType("AUTO", 6);
            AUTO = hotspotDeviceType7;
            HotspotDeviceType hotspotDeviceType8 = new HotspotDeviceType("INVALID", 7);
            INVALID = hotspotDeviceType8;
            HotspotDeviceType[] hotspotDeviceTypeArr = {hotspotDeviceType, hotspotDeviceType2, hotspotDeviceType3, hotspotDeviceType4, hotspotDeviceType5, hotspotDeviceType6, hotspotDeviceType7, hotspotDeviceType8};
            $VALUES = hotspotDeviceTypeArr;
            EnumEntriesKt.enumEntries(hotspotDeviceTypeArr);
        }

        private HotspotDeviceType(String str, int i) {
        }

        public static HotspotDeviceType valueOf(String str) {
            return (HotspotDeviceType) Enum.valueOf(HotspotDeviceType.class, str);
        }

        public static HotspotDeviceType[] values() {
            return (HotspotDeviceType[]) $VALUES.clone();
        }
    }

    public final class Inactive extends WifiNetworkModel {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (((WifiNetworkModel) obj) instanceof Inactive) {
                return;
            }
            logFull(tableRowLoggerImpl);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Inactive");
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return "WifiNetwork.Inactive";
        }
    }

    public final class Invalid extends WifiNetworkModel {
        public final String invalidReason;

        public Invalid(String str) {
            super(null);
            this.invalidReason = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Invalid) && Intrinsics.areEqual(this.invalidReason, ((Invalid) obj).invalidReason);
        }

        public final int hashCode() {
            return this.invalidReason.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj;
            if (!(wifiNetworkModel instanceof Invalid)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            String str = ((Invalid) wifiNetworkModel).invalidReason;
            String str2 = this.invalidReason;
            if (Intrinsics.areEqual(str2, str)) {
                return;
            }
            tableRowLoggerImpl.logChange("type", "Unavailable " + str2);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable " + this.invalidReason);
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("WifiNetwork.Invalid["), this.invalidReason, "]");
        }
    }

    public final class Unavailable extends WifiNetworkModel {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
            super(null);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (((WifiNetworkModel) obj) instanceof Unavailable) {
                return;
            }
            logFull(tableRowLoggerImpl);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable");
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return "WifiNetwork.Unavailable";
        }
    }

    public final class WifiNetworkType {
        public static final /* synthetic */ WifiNetworkType[] $VALUES;
        public static final WifiNetworkType FIVEG;
        public static final WifiNetworkType NONE;
        public static final WifiNetworkType SEVENG;
        public static final WifiNetworkType SIXG;
        public static final WifiNetworkType SIXGE;

        static {
            WifiNetworkType wifiNetworkType = new WifiNetworkType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = wifiNetworkType;
            WifiNetworkType wifiNetworkType2 = new WifiNetworkType("FIVEG", 1);
            FIVEG = wifiNetworkType2;
            WifiNetworkType wifiNetworkType3 = new WifiNetworkType("SIXGE", 2);
            SIXGE = wifiNetworkType3;
            WifiNetworkType wifiNetworkType4 = new WifiNetworkType("SIXG", 3);
            SIXG = wifiNetworkType4;
            WifiNetworkType wifiNetworkType5 = new WifiNetworkType("SEVENG", 4);
            SEVENG = wifiNetworkType5;
            WifiNetworkType[] wifiNetworkTypeArr = {wifiNetworkType, wifiNetworkType2, wifiNetworkType3, wifiNetworkType4, wifiNetworkType5};
            $VALUES = wifiNetworkTypeArr;
            EnumEntriesKt.enumEntries(wifiNetworkTypeArr);
        }

        private WifiNetworkType(String str, int i) {
        }

        public static WifiNetworkType valueOf(String str) {
            return (WifiNetworkType) Enum.valueOf(WifiNetworkType.class, str);
        }

        public static WifiNetworkType[] values() {
            return (WifiNetworkType[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    private WifiNetworkModel() {
    }

    public /* synthetic */ WifiNetworkModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final class Active extends WifiNetworkModel {
        public final HotspotDeviceType hotspotDeviceType;
        public final boolean isOnlineSignUpForPasspointAccessPoint;
        public final boolean isPasspointAccessPoint;
        public boolean isValidated;
        public final int level;
        public final int networkId;
        public final String passpointProviderFriendlyName;
        public int receivedInetCondition;
        public final String ssid;
        public final WifiNetworkType wifiNetworkType;

        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static /* synthetic */ void getMAX_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
            }
        }

        static {
            new Companion(null);
        }

        public /* synthetic */ Active(int i, boolean z, int i2, String str, HotspotDeviceType hotspotDeviceType, boolean z2, boolean z3, String str2, WifiNetworkType wifiNetworkType, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i4 & 2) != 0 ? false : z, i2, (i4 & 8) != 0 ? null : str, (i4 & 16) != 0 ? HotspotDeviceType.NONE : hotspotDeviceType, (i4 & 32) != 0 ? false : z2, (i4 & 64) != 0 ? false : z3, (i4 & 128) != 0 ? null : str2, (i4 & 256) != 0 ? WifiNetworkType.NONE : wifiNetworkType, (i4 & 512) != 0 ? -1 : i3);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return this.networkId == active.networkId && this.isValidated == active.isValidated && this.level == active.level && Intrinsics.areEqual(this.ssid, active.ssid) && this.hotspotDeviceType == active.hotspotDeviceType && this.isPasspointAccessPoint == active.isPasspointAccessPoint && this.isOnlineSignUpForPasspointAccessPoint == active.isOnlineSignUpForPasspointAccessPoint && Intrinsics.areEqual(this.passpointProviderFriendlyName, active.passpointProviderFriendlyName) && this.wifiNetworkType == active.wifiNetworkType && this.receivedInetCondition == active.receivedInetCondition;
        }

        public final int hashCode() {
            int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.level, TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.networkId) * 31, 31, this.isValidated), 31);
            String str = this.ssid;
            int m2 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.hotspotDeviceType.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31, 31, this.isPasspointAccessPoint), 31, this.isOnlineSignUpForPasspointAccessPoint);
            String str2 = this.passpointProviderFriendlyName;
            return Integer.hashCode(this.receivedInetCondition) + ((this.wifiNetworkType.hashCode() + ((m2 + (str2 != null ? str2.hashCode() : 0)) * 31)) * 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj;
            if (!(wifiNetworkModel instanceof Active)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            Active active = (Active) wifiNetworkModel;
            int i = active.networkId;
            int i2 = this.networkId;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, "networkId");
            }
            boolean z = active.isValidated;
            boolean z2 = this.isValidated;
            if (z != z2) {
                tableRowLoggerImpl.logChange("isValidated", z2);
            }
            int i3 = active.level;
            int i4 = this.level;
            if (i3 != i4) {
                tableRowLoggerImpl.logChange(i4, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
            String str = active.ssid;
            String str2 = this.ssid;
            if (!Intrinsics.areEqual(str, str2)) {
                tableRowLoggerImpl.logChange("ssid", str2);
            }
            HotspotDeviceType hotspotDeviceType = active.hotspotDeviceType;
            HotspotDeviceType hotspotDeviceType2 = this.hotspotDeviceType;
            if (hotspotDeviceType != hotspotDeviceType2) {
                tableRowLoggerImpl.logChange("hotspot", hotspotDeviceType2.name());
            }
            boolean z3 = active.isPasspointAccessPoint;
            boolean z4 = this.isPasspointAccessPoint;
            if (z3 != z4) {
                tableRowLoggerImpl.logChange("isPasspointAccessPoint", z4);
            }
            boolean z5 = active.isOnlineSignUpForPasspointAccessPoint;
            boolean z6 = this.isOnlineSignUpForPasspointAccessPoint;
            if (z5 != z6) {
                tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", z6);
            }
            String str3 = active.passpointProviderFriendlyName;
            String str4 = this.passpointProviderFriendlyName;
            if (!Intrinsics.areEqual(str3, str4)) {
                tableRowLoggerImpl.logChange("passpointProviderFriendlyName", str4);
            }
            WifiNetworkType wifiNetworkType = active.wifiNetworkType;
            WifiNetworkType wifiNetworkType2 = this.wifiNetworkType;
            if (wifiNetworkType != wifiNetworkType2) {
                tableRowLoggerImpl.logChange("networkType", wifiNetworkType2.toString());
            }
            int i5 = active.receivedInetCondition;
            int i6 = this.receivedInetCondition;
            if (i5 != i6) {
                tableRowLoggerImpl.logChange(i6, "receivedInetCondition");
            }
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Active");
            tableRowLoggerImpl.logChange(this.networkId, "networkId");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", this.isValidated);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", this.ssid);
            tableRowLoggerImpl.logChange("hotspot", this.hotspotDeviceType.name());
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", this.isPasspointAccessPoint);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", this.isOnlineSignUpForPasspointAccessPoint);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", this.passpointProviderFriendlyName);
            tableRowLoggerImpl.logChange("networkType", this.wifiNetworkType.toString());
            tableRowLoggerImpl.logChange(this.receivedInetCondition, "receivedInetCondition");
        }

        public final String toString() {
            String sb;
            String str = this.passpointProviderFriendlyName;
            boolean z = this.isOnlineSignUpForPasspointAccessPoint;
            boolean z2 = this.isPasspointAccessPoint;
            if (z2 || z || str != null) {
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(", isPasspointAp=", ", isOnlineSignUpForPasspointAp=", ", passpointName=", z2, z);
                m.append(str);
                sb = m.toString();
            } else {
                sb = "";
            }
            boolean z3 = this.isValidated;
            StringBuilder sb2 = new StringBuilder("WifiNetworkModel.Active(networkId=");
            sb2.append(this.networkId);
            sb2.append(", isValidated=");
            sb2.append(z3);
            sb2.append(", level=");
            sb2.append(this.level);
            sb2.append(", ssid=");
            return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb2, this.ssid, sb, ")");
        }

        public Active(int i, boolean z, int i2, String str, HotspotDeviceType hotspotDeviceType, boolean z2, boolean z3, String str2, WifiNetworkType wifiNetworkType, int i3) {
            super(null);
            this.networkId = i;
            this.isValidated = z;
            this.level = i2;
            this.ssid = str;
            this.hotspotDeviceType = hotspotDeviceType;
            this.isPasspointAccessPoint = z2;
            this.isOnlineSignUpForPasspointAccessPoint = z3;
            this.passpointProviderFriendlyName = str2;
            this.wifiNetworkType = wifiNetworkType;
            this.receivedInetCondition = i3;
            if (i2 < 0 || i2 >= 5) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "0 <= wifi level <= 4 required; level was ").toString());
            }
        }
    }

    public final class CarrierMerged extends WifiNetworkModel {
        public final int level;
        public final int networkId;
        public final int numberOfLevels;
        public final int subscriptionId;

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public CarrierMerged(int r1, int r2, int r3, int r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
            /*
                r0 = this;
                r5 = r5 & 8
                if (r5 == 0) goto Lb
                com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository$Companion r4 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion
                r4.getClass()
                int r4 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS
            Lb:
                r0.<init>(r1, r2, r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.CarrierMerged.<init>(int, int, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CarrierMerged)) {
                return false;
            }
            CarrierMerged carrierMerged = (CarrierMerged) obj;
            return this.networkId == carrierMerged.networkId && this.subscriptionId == carrierMerged.subscriptionId && this.level == carrierMerged.level && this.numberOfLevels == carrierMerged.numberOfLevels;
        }

        public final int hashCode() {
            return Integer.hashCode(this.numberOfLevels) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.level, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.subscriptionId, Integer.hashCode(this.networkId) * 31, 31), 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj;
            if (!(wifiNetworkModel instanceof CarrierMerged)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            CarrierMerged carrierMerged = (CarrierMerged) wifiNetworkModel;
            int i = carrierMerged.networkId;
            int i2 = this.networkId;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, "networkId");
            }
            int i3 = carrierMerged.subscriptionId;
            int i4 = this.subscriptionId;
            if (i3 != i4) {
                tableRowLoggerImpl.logChange(i4, "subscriptionId");
            }
            int i5 = carrierMerged.level;
            int i6 = this.level;
            if (i5 != i6) {
                tableRowLoggerImpl.logChange(i6, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
            int i7 = carrierMerged.numberOfLevels;
            int i8 = this.numberOfLevels;
            if (i7 != i8) {
                tableRowLoggerImpl.logChange(i8, "maxLevel");
            }
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "CarrierMerged");
            tableRowLoggerImpl.logChange(this.networkId, "networkId");
            tableRowLoggerImpl.logChange(this.subscriptionId, "subscriptionId");
            tableRowLoggerImpl.logChange("isValidated", true);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange(this.numberOfLevels, "maxLevel");
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierMerged(networkId=");
            sb.append(this.networkId);
            sb.append(", subscriptionId=");
            sb.append(this.subscriptionId);
            sb.append(", level=");
            sb.append(this.level);
            sb.append(", numberOfLevels=");
            return Anchor$$ExternalSyntheticOutline0.m(this.numberOfLevels, ")", sb);
        }

        public CarrierMerged(int i, int i2, int i3, int i4) {
            super(null);
            this.networkId = i;
            this.subscriptionId = i2;
            this.level = i3;
            this.numberOfLevels = i4;
            if (i3 < 0 || i3 > i4) {
                throw new IllegalArgumentException(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i4, i3, "0 <= wifi level <= ", " required; level was ").toString());
            }
            if (i2 == -1) {
                throw new IllegalArgumentException("subscription ID cannot be invalid".toString());
            }
        }
    }
}
