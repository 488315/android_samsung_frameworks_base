package com.android.systemui.statusbar.connectivity;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
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
        int iHashCode = Boolean.hashCode(this.enabled) * 31;
        IconState iconState = this.statusIcon;
        int iHashCode2 = (iHashCode + (iconState == null ? 0 : iconState.hashCode())) * 31;
        IconState iconState2 = this.qsIcon;
        int iM = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((iHashCode2 + (iconState2 == null ? 0 : iconState2.hashCode())) * 31, 31, this.activityIn), 31, this.activityOut);
        String str = this.description;
        int iM2 = TransitionData$$ExternalSyntheticOutline0.m((iM + (str == null ? 0 : str.hashCode())) * 31, 31, this.isTransient);
        String str2 = this.statusLabel;
        return Integer.hashCode(this.inetCondition) + ((iM2 + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        String string;
        String string2;
        StringBuilder sb = new StringBuilder("WifiIndicators[enabled=");
        sb.append(this.enabled);
        sb.append(",statusIcon=");
        String str = "";
        IconState iconState = this.statusIcon;
        if (iconState == null || (string = iconState.toString()) == null) {
            string = "";
        }
        sb.append(string);
        sb.append(",qsIcon=");
        IconState iconState2 = this.qsIcon;
        if (iconState2 != null && (string2 = iconState2.toString()) != null) {
            str = string2;
        }
        sb.append(str);
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
