package com.android.systemui.statusbar.pipeline.wifi.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class WifiNetworkModel implements Diffable {

    public final class CarrierMerged extends WifiNetworkModel {
        public static final Companion Companion = new Companion(null);
        public final int level;
        public final int numberOfLevels;
        public final int subscriptionId;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public /* synthetic */ CarrierMerged(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CarrierMerged)) {
                return false;
            }
            CarrierMerged carrierMerged = (CarrierMerged) obj;
            return this.subscriptionId == carrierMerged.subscriptionId && this.level == carrierMerged.level && this.numberOfLevels == carrierMerged.numberOfLevels;
        }

        public final int hashCode() {
            return Integer.hashCode(this.numberOfLevels) + ReorderTile$$ExternalSyntheticOutline0.m(this.level, Integer.hashCode(this.subscriptionId) * 31, 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
            if (!(wifiNetworkModel instanceof CarrierMerged)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            CarrierMerged carrierMerged = (CarrierMerged) wifiNetworkModel;
            int i = carrierMerged.subscriptionId;
            int i2 = this.subscriptionId;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, "subscriptionId");
            }
            int i3 = carrierMerged.level;
            int i4 = this.level;
            if (i3 != i4) {
                tableRowLoggerImpl.logChange(i4, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
            int i5 = carrierMerged.numberOfLevels;
            int i6 = this.numberOfLevels;
            if (i5 != i6) {
                tableRowLoggerImpl.logChange(i6, "maxLevel");
            }
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "CarrierMerged");
            tableRowLoggerImpl.logChange(this.subscriptionId, "subscriptionId");
            tableRowLoggerImpl.logChange("isValidated", true);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange(this.numberOfLevels, "maxLevel");
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierMerged(subscriptionId=");
            sb.append(this.subscriptionId);
            sb.append(", level=");
            sb.append(this.level);
            sb.append(", numberOfLevels=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.numberOfLevels, ")", sb);
        }

        private CarrierMerged(int i, int i2, int i3) {
            super(null);
            this.subscriptionId = i;
            this.level = i2;
            this.numberOfLevels = i3;
            Companion.getClass();
            if (i2 == -1 || i2 < 0 || i2 > i3) {
                throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(ListImplementation$$ExternalSyntheticOutline0.m(i3, i2, "Wifi network was carrier merged but had invalid level. 0 <= wifi level <= ", " required; level was "), ". This should only be an issue if the caller incorrectly used `copy` to get a new instance. Please use the `of` method instead.").toString());
            }
            if (i == -1) {
                throw new IllegalArgumentException("Wifi network was carrier merged but had invalid sub ID. This should only be an issue if the caller incorrectly used `copy` to get a new instance. Please use the `of` method instead.");
            }
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getMIN_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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
        public final String inactiveReason;

        /* JADX WARN: Multi-variable type inference failed */
        public Inactive() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Inactive) && Intrinsics.areEqual(this.inactiveReason, ((Inactive) obj).inactiveReason);
        }

        public final int hashCode() {
            String str = this.inactiveReason;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
            if (!(wifiNetworkModel instanceof Inactive)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            String str = ((Inactive) wifiNetworkModel).inactiveReason;
            String str2 = this.inactiveReason;
            if (Intrinsics.areEqual(str2, str)) {
                return;
            }
            tableRowLoggerImpl.logChange("type", "Inactive[reason=" + str2 + "]");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Inactive[reason=" + this.inactiveReason + "]");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("WifiNetwork.Inactive[reason="), this.inactiveReason, "]");
        }

        public /* synthetic */ Inactive(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str);
        }

        public Inactive(String str) {
            super(null);
            this.inactiveReason = str;
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
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
            if (!(wifiNetworkModel instanceof Invalid)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            String str = ((Invalid) wifiNetworkModel).invalidReason;
            String str2 = this.invalidReason;
            if (Intrinsics.areEqual(str2, str)) {
                return;
            }
            tableRowLoggerImpl.logChange("type", "Unavailable[reason=" + str2 + "]");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable[reason=" + this.invalidReason + "]");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("WifiNetwork.Invalid[reason="), this.invalidReason, "]");
        }
    }

    public final class Unavailable extends WifiNetworkModel {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
            super(null);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (((WifiNetworkModel) diffable) instanceof Unavailable) {
                return;
            }
            logFull(tableRowLoggerImpl);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("hotspot", (String) null);
        }

        public final String toString() {
            return "WifiNetwork.Unavailable";
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    public /* synthetic */ WifiNetworkModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final class Active extends WifiNetworkModel {
        public static final Companion Companion = new Companion(null);
        public final HotspotDeviceType hotspotDeviceType;
        public boolean isValidated;
        public final int level;
        public int receivedInetCondition;
        public final String ssid;
        public final WifiNetworkType wifiNetworkType;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public static /* synthetic */ void getMAX_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
            }
        }

        public /* synthetic */ Active(boolean z, int i, String str, HotspotDeviceType hotspotDeviceType, WifiNetworkType wifiNetworkType, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? false : z, i, str, hotspotDeviceType, (i3 & 16) != 0 ? WifiNetworkType.NONE : wifiNetworkType, (i3 & 32) != 0 ? -1 : i2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return this.isValidated == active.isValidated && this.level == active.level && Intrinsics.areEqual(this.ssid, active.ssid) && this.hotspotDeviceType == active.hotspotDeviceType && this.wifiNetworkType == active.wifiNetworkType && this.receivedInetCondition == active.receivedInetCondition;
        }

        public final int hashCode() {
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.level, Boolean.hashCode(this.isValidated) * 31, 31);
            String str = this.ssid;
            return Integer.hashCode(this.receivedInetCondition) + ((this.wifiNetworkType.hashCode() + ((this.hotspotDeviceType.hashCode() + ((iM + (str == null ? 0 : str.hashCode())) * 31)) * 31)) * 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
            if (!(wifiNetworkModel instanceof Active)) {
                logFull(tableRowLoggerImpl);
                return;
            }
            Active active = (Active) wifiNetworkModel;
            boolean z = active.isValidated;
            boolean z2 = this.isValidated;
            if (z != z2) {
                tableRowLoggerImpl.logChange("isValidated", z2);
            }
            int i = active.level;
            int i2 = this.level;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, ActionResults.RESULT_SET_VOLUME_SUCCESS);
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
            WifiNetworkType wifiNetworkType = active.wifiNetworkType;
            WifiNetworkType wifiNetworkType2 = this.wifiNetworkType;
            if (wifiNetworkType != wifiNetworkType2) {
                tableRowLoggerImpl.logChange("networkType", wifiNetworkType2.toString());
            }
            int i3 = active.receivedInetCondition;
            int i4 = this.receivedInetCondition;
            if (i3 != i4) {
                tableRowLoggerImpl.logChange(i4, "receivedInetCondition");
            }
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Active");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", this.isValidated);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", this.ssid);
            tableRowLoggerImpl.logChange("hotspot", this.hotspotDeviceType.name());
            tableRowLoggerImpl.logChange("networkType", this.wifiNetworkType.toString());
            tableRowLoggerImpl.logChange(this.receivedInetCondition, "receivedInetCondition");
        }

        public final String toString() {
            boolean z = this.isValidated;
            int i = this.receivedInetCondition;
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("Active(isValidated=", ", level=", z);
            sbM.append(this.level);
            sbM.append(", ssid=");
            sbM.append(this.ssid);
            sbM.append(", hotspotDeviceType=");
            sbM.append(this.hotspotDeviceType);
            sbM.append(", wifiNetworkType=");
            sbM.append(this.wifiNetworkType);
            sbM.append(", receivedInetCondition=");
            sbM.append(i);
            sbM.append(")");
            return sbM.toString();
        }

        private Active(boolean z, int i, String str, HotspotDeviceType hotspotDeviceType, WifiNetworkType wifiNetworkType, int i2) {
            super(null);
            this.isValidated = z;
            this.level = i;
            this.ssid = str;
            this.hotspotDeviceType = hotspotDeviceType;
            this.wifiNetworkType = wifiNetworkType;
            this.receivedInetCondition = i2;
            Companion.getClass();
            if (i == -1 || i < 0 || i >= 5) {
                throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wifi network was active but had invalid level. 0 <= wifi level <= 4 required; level was "), ". This should only be an issue if the caller incorrectly used `copy` to get a new instance. Please use the `of` method instead.").toString());
            }
        }
    }

    private WifiNetworkModel() {
    }
}
