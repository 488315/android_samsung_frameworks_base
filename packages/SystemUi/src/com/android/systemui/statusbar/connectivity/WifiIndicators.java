package com.android.systemui.statusbar.connectivity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.enabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        IconState iconState = this.statusIcon;
        int hashCode = (i2 + (iconState == null ? 0 : iconState.hashCode())) * 31;
        IconState iconState2 = this.qsIcon;
        int hashCode2 = (hashCode + (iconState2 == null ? 0 : iconState2.hashCode())) * 31;
        boolean z2 = this.activityIn;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (hashCode2 + i3) * 31;
        boolean z3 = this.activityOut;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        String str = this.description;
        int hashCode3 = (i6 + (str == null ? 0 : str.hashCode())) * 31;
        boolean z4 = this.isTransient;
        int i7 = (hashCode3 + (z4 ? 1 : z4 ? 1 : 0)) * 31;
        String str2 = this.statusLabel;
        return Integer.hashCode(this.inetCondition) + ((i7 + (str2 != null ? str2.hashCode() : 0)) * 31);
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
        sb.append(this.inetCondition);
        sb.append(']');
        return sb.toString();
    }
}
