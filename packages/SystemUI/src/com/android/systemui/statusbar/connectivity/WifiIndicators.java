package com.android.systemui.statusbar.connectivity;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WifiIndicators {
    public final boolean activityIn;
    public final boolean activityOut;
    public final String description;
    public final boolean enabled;
    public final int inetCondition;
    public final boolean isTransient;
    public final IconState qsIcon;
    public final IconState statusIcon;
    public final String statusLabel;

    public WifiIndicators(boolean z, IconState iconState, IconState iconState2, boolean z2, boolean z3, String str, boolean z4, String str2, int i) {
        this.enabled = z;
        this.statusIcon = iconState;
        this.qsIcon = iconState2;
        this.activityIn = z2;
        this.activityOut = z3;
        this.description = str;
        this.isTransient = z4;
        this.statusLabel = str2;
        this.inetCondition = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WifiIndicators)) {
            return false;
        }
        WifiIndicators wifiIndicators = (WifiIndicators) obj;
        return this.enabled == wifiIndicators.enabled && Intrinsics.areEqual(this.statusIcon, wifiIndicators.statusIcon) && Intrinsics.areEqual(this.qsIcon, wifiIndicators.qsIcon) && this.activityIn == wifiIndicators.activityIn && this.activityOut == wifiIndicators.activityOut && Intrinsics.areEqual(this.description, wifiIndicators.description) && this.isTransient == wifiIndicators.isTransient && Intrinsics.areEqual(this.statusLabel, wifiIndicators.statusLabel) && this.inetCondition == wifiIndicators.inetCondition;
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.enabled) * 31;
        IconState iconState = this.statusIcon;
        int hashCode2 = (hashCode + (iconState == null ? 0 : iconState.hashCode())) * 31;
        IconState iconState2 = this.qsIcon;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (iconState2 == null ? 0 : iconState2.hashCode())) * 31, 31, this.activityIn), 31, this.activityOut);
        String str = this.description;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m((m + (str == null ? 0 : str.hashCode())) * 31, 31, this.isTransient);
        String str2 = this.statusLabel;
        return Integer.hashCode(this.inetCondition) + ((m2 + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WifiIndicators[enabled=");
        sb.append(this.enabled);
        sb.append(",statusIcon=");
        IconState iconState = this.statusIcon;
        sb.append(iconState != null ? iconState.toString() : "");
        sb.append(",qsIcon=");
        IconState iconState2 = this.qsIcon;
        sb.append(iconState2 != null ? iconState2.toString() : "");
        sb.append(",activityIn=");
        sb.append(this.activityIn);
        sb.append(",activityOut=");
        sb.append(this.activityOut);
        sb.append(",qsDescription=");
        sb.append(this.description);
        sb.append(",isTransient=");
        sb.append(this.isTransient);
        sb.append(",statusLabel=");
        sb.append(this.statusLabel);
        sb.append(",inetcondition=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.inetCondition, ']');
    }
}
