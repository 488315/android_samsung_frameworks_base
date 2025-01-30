package com.android.systemui.statusbar.pipeline.wifi.shared.model;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class WifiNetworkModel implements Diffable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Active extends WifiNetworkModel {
        public final int frequency;
        public final boolean gigaAp;
        public final boolean isOnlineSignUpForPasspointAccessPoint;
        public final boolean isPasspointAccessPoint;
        public boolean isValidated;
        public final int level;
        public final int networkId;
        public final String passpointProviderFriendlyName;
        public int receivedInetCondition;
        public final String ssid;
        public final int wifiNetworkId;
        public final int wifiStandard;

        static {
            new Companion(null);
        }

        public /* synthetic */ Active(int i, boolean z, int i2, String str, boolean z2, boolean z3, String str2, int i3, int i4, int i5, int i6, boolean z4, int i7, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i7 & 2) != 0 ? false : z, i2, (i7 & 8) != 0 ? null : str, (i7 & 16) != 0 ? false : z2, (i7 & 32) != 0 ? false : z3, (i7 & 64) != 0 ? null : str2, (i7 & 128) != 0 ? -1 : i3, (i7 & 256) != 0 ? -1 : i4, (i7 & 512) != 0 ? -1 : i5, i6, z4);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return this.networkId == active.networkId && this.isValidated == active.isValidated && this.level == active.level && Intrinsics.areEqual(this.ssid, active.ssid) && this.isPasspointAccessPoint == active.isPasspointAccessPoint && this.isOnlineSignUpForPasspointAccessPoint == active.isOnlineSignUpForPasspointAccessPoint && Intrinsics.areEqual(this.passpointProviderFriendlyName, active.passpointProviderFriendlyName) && this.wifiStandard == active.wifiStandard && this.frequency == active.frequency && this.receivedInetCondition == active.receivedInetCondition && this.wifiNetworkId == active.wifiNetworkId && this.gigaAp == active.gigaAp;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = Integer.hashCode(this.networkId) * 31;
            boolean z = this.isValidated;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.level, (hashCode + i) * 31, 31);
            String str = this.ssid;
            int hashCode2 = (m42m + (str == null ? 0 : str.hashCode())) * 31;
            boolean z2 = this.isPasspointAccessPoint;
            int i2 = z2;
            if (z2 != 0) {
                i2 = 1;
            }
            int i3 = (hashCode2 + i2) * 31;
            boolean z3 = this.isOnlineSignUpForPasspointAccessPoint;
            int i4 = z3;
            if (z3 != 0) {
                i4 = 1;
            }
            int i5 = (i3 + i4) * 31;
            String str2 = this.passpointProviderFriendlyName;
            int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.wifiNetworkId, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.receivedInetCondition, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.frequency, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.wifiStandard, (i5 + (str2 != null ? str2.hashCode() : 0)) * 31, 31), 31), 31), 31);
            boolean z4 = this.gigaAp;
            return m42m2 + (z4 ? 1 : z4 ? 1 : 0);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
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
            int i5 = active.wifiStandard;
            int i6 = this.wifiStandard;
            if (i5 != i6) {
                tableRowLoggerImpl.logChange(i6, "wifiStandard");
            }
            int i7 = active.frequency;
            int i8 = this.frequency;
            if (i7 != i8) {
                tableRowLoggerImpl.logChange(i8, "frequency");
            }
            int i9 = active.receivedInetCondition;
            int i10 = this.receivedInetCondition;
            if (i9 != i10) {
                tableRowLoggerImpl.logChange(i10, "receivedInetCondition");
            }
            int i11 = active.wifiNetworkId;
            int i12 = this.wifiNetworkId;
            if (i11 != i12) {
                tableRowLoggerImpl.logChange(i12, "wifiNetworkId");
            }
            boolean z7 = active.gigaAp;
            boolean z8 = this.gigaAp;
            if (z7 != z8) {
                tableRowLoggerImpl.logChange("gigaAp", z8);
            }
        }

        @Override // com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel, com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Active");
            tableRowLoggerImpl.logChange(this.networkId, "networkId");
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", this.isValidated);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", this.ssid);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", this.isPasspointAccessPoint);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", this.isOnlineSignUpForPasspointAccessPoint);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", this.passpointProviderFriendlyName);
            tableRowLoggerImpl.logChange(this.wifiStandard, "wifiStandard");
            tableRowLoggerImpl.logChange(this.frequency, "frequency");
            tableRowLoggerImpl.logChange(this.receivedInetCondition, "receivedInetCondition");
            tableRowLoggerImpl.logChange(this.wifiNetworkId, "wifiNetworkId");
            tableRowLoggerImpl.logChange("gigaAp", this.gigaAp);
        }

        public final String toString() {
            String sb;
            String str = this.passpointProviderFriendlyName;
            boolean z = this.isOnlineSignUpForPasspointAccessPoint;
            boolean z2 = this.isPasspointAccessPoint;
            if (z2 || z || str != null) {
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m(", isPasspointAp=", z2, ", isOnlineSignUpForPasspointAp=", z, ", passpointName=");
                m69m.append(str);
                sb = m69m.toString();
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
            return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb2, this.ssid, sb, ")");
        }

        public Active(int i, boolean z, int i2, String str, boolean z2, boolean z3, String str2, int i3, int i4, int i5, int i6, boolean z4) {
            super(null);
            this.networkId = i;
            this.isValidated = z;
            this.level = i2;
            this.ssid = str;
            this.isPasspointAccessPoint = z2;
            this.isOnlineSignUpForPasspointAccessPoint = z3;
            this.passpointProviderFriendlyName = str2;
            this.wifiStandard = i3;
            this.frequency = i4;
            this.receivedInetCondition = i5;
            this.wifiNetworkId = i6;
            this.gigaAp = z4;
            boolean z5 = false;
            if (i2 >= 0 && i2 < 5) {
                z5 = true;
            }
            if (!z5) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("0 <= wifi level <= 4 required; level was ", i2).toString());
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* renamed from: getMAX_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
            public static /* synthetic */ void m212x7e1ae976() {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CarrierMerged extends WifiNetworkModel {
        public final int level;
        public final int networkId;
        public final int numberOfLevels;
        public final int subscriptionId;

        public /* synthetic */ CarrierMerged(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3, (i5 & 8) != 0 ? 4 : i4);
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
            return Integer.hashCode(this.numberOfLevels) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.level, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.subscriptionId, Integer.hashCode(this.networkId) * 31, 31), 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) diffable;
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

        @Override // com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel, com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "CarrierMerged");
            tableRowLoggerImpl.logChange(this.networkId, "networkId");
            tableRowLoggerImpl.logChange(this.subscriptionId, "subscriptionId");
            tableRowLoggerImpl.logChange("isValidated", true);
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange(this.numberOfLevels, "maxLevel");
            tableRowLoggerImpl.logChange("ssid", (String) null);
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.numberOfLevels, ")");
        }

        public CarrierMerged(int i, int i2, int i3, int i4) {
            super(null);
            this.networkId = i;
            this.subscriptionId = i2;
            this.level = i3;
            this.numberOfLevels = i4;
            if (!(i3 >= 0 && i3 <= i4)) {
                throw new IllegalArgumentException(AbstractC0970x34f4116a.m94m("0 <= wifi level <= ", i4, " required; level was ", i3).toString());
            }
            if (!(i2 != -1)) {
                throw new IllegalArgumentException("subscription ID cannot be invalid".toString());
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Inactive extends WifiNetworkModel {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (((WifiNetworkModel) diffable) instanceof Inactive) {
                return;
            }
            logFull(tableRowLoggerImpl);
        }

        @Override // com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel, com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Inactive");
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return "WifiNetwork.Inactive";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            tableRowLoggerImpl.logChange("type", "Unavailable " + str2);
        }

        @Override // com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel, com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable " + this.invalidReason);
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("WifiNetwork.Invalid["), this.invalidReason, "]");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        @Override // com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel, com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "Unavailable");
            tableRowLoggerImpl.logChange("networkId", (String) null);
            tableRowLoggerImpl.logChange("subscriptionId", (String) null);
            tableRowLoggerImpl.logChange("isValidated", false);
            tableRowLoggerImpl.logChange(ActionResults.RESULT_SET_VOLUME_SUCCESS, (String) null);
            tableRowLoggerImpl.logChange("maxLevel", (String) null);
            tableRowLoggerImpl.logChange("ssid", (String) null);
            tableRowLoggerImpl.logChange("isPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("isOnlineSignUpForPasspointAccessPoint", false);
            tableRowLoggerImpl.logChange("passpointProviderFriendlyName", (String) null);
        }

        public final String toString() {
            return "WifiNetwork.Unavailable";
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getMIN_VALID_LEVEL$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m213x36ae964() {
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    public void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
