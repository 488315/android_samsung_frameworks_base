package com.android.systemui.statusbar.connectivity;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileDataIndicators {
    public final boolean activityIn;
    public final boolean activityOut;
    public final CharSequence qsDescription;
    public final IconState qsIcon;
    public final int qsType;
    public final boolean roaming;
    public final boolean showTriangle;
    public final IconState statusIcon;
    public final int statusType;
    public final int subId;
    public final CharSequence typeContentDescription;
    public final CharSequence typeContentDescriptionHtml;

    public MobileDataIndicators(IconState iconState, IconState iconState2, int i, int i2, boolean z, boolean z2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i3, boolean z3, boolean z4) {
        this.statusIcon = iconState;
        this.qsIcon = iconState2;
        this.statusType = i;
        this.qsType = i2;
        this.activityIn = z;
        this.activityOut = z2;
        this.typeContentDescription = charSequence;
        this.typeContentDescriptionHtml = charSequence2;
        this.qsDescription = charSequence3;
        this.subId = i3;
        this.roaming = z3;
        this.showTriangle = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileDataIndicators)) {
            return false;
        }
        MobileDataIndicators mobileDataIndicators = (MobileDataIndicators) obj;
        return Intrinsics.areEqual(this.statusIcon, mobileDataIndicators.statusIcon) && Intrinsics.areEqual(this.qsIcon, mobileDataIndicators.qsIcon) && this.statusType == mobileDataIndicators.statusType && this.qsType == mobileDataIndicators.qsType && this.activityIn == mobileDataIndicators.activityIn && this.activityOut == mobileDataIndicators.activityOut && Intrinsics.areEqual(this.typeContentDescription, mobileDataIndicators.typeContentDescription) && Intrinsics.areEqual(this.typeContentDescriptionHtml, mobileDataIndicators.typeContentDescriptionHtml) && Intrinsics.areEqual(this.qsDescription, mobileDataIndicators.qsDescription) && this.subId == mobileDataIndicators.subId && this.roaming == mobileDataIndicators.roaming && this.showTriangle == mobileDataIndicators.showTriangle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        IconState iconState = this.statusIcon;
        int hashCode = (iconState == null ? 0 : iconState.hashCode()) * 31;
        IconState iconState2 = this.qsIcon;
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.qsType, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.statusType, (hashCode + (iconState2 == null ? 0 : iconState2.hashCode())) * 31, 31), 31);
        boolean z = this.activityIn;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m + i) * 31;
        boolean z2 = this.activityOut;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        CharSequence charSequence = this.typeContentDescription;
        int hashCode2 = (i4 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.typeContentDescriptionHtml;
        int hashCode3 = (hashCode2 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        CharSequence charSequence3 = this.qsDescription;
        int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.subId, (hashCode3 + (charSequence3 != null ? charSequence3.hashCode() : 0)) * 31, 31);
        boolean z3 = this.roaming;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (m42m2 + i5) * 31;
        boolean z4 = this.showTriangle;
        return i6 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MobileDataIndicators[statusIcon=");
        IconState iconState = this.statusIcon;
        sb.append(iconState != null ? iconState.toString() : "");
        sb.append(",qsIcon=");
        IconState iconState2 = this.qsIcon;
        sb.append(iconState2 != null ? iconState2.toString() : "");
        sb.append(",statusType=");
        sb.append(this.statusType);
        sb.append(",qsType=");
        sb.append(this.qsType);
        sb.append(",activityIn=");
        sb.append(this.activityIn);
        sb.append(",activityOut=");
        sb.append(this.activityOut);
        sb.append(",typeContentDescription=");
        sb.append(this.typeContentDescription);
        sb.append(",typeContentDescriptionHtml=");
        sb.append(this.typeContentDescriptionHtml);
        sb.append(",description=");
        sb.append(this.qsDescription);
        sb.append(",subId=");
        sb.append(this.subId);
        sb.append(",roaming=");
        sb.append(this.roaming);
        sb.append(",showTriangle=");
        sb.append(this.showTriangle);
        sb.append(']');
        return sb.toString();
    }
}
