package com.android.systemui.statusbar.connectivity;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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

    public final int hashCode() {
        IconState iconState = this.statusIcon;
        int hashCode = (iconState == null ? 0 : iconState.hashCode()) * 31;
        IconState iconState2 = this.qsIcon;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.qsType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.statusType, (hashCode + (iconState2 == null ? 0 : iconState2.hashCode())) * 31, 31), 31), 31, this.activityIn), 31, this.activityOut);
        CharSequence charSequence = this.typeContentDescription;
        int hashCode2 = (m + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.typeContentDescriptionHtml;
        int hashCode3 = (hashCode2 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        CharSequence charSequence3 = this.qsDescription;
        return Boolean.hashCode(this.showTriangle) + TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.subId, (hashCode3 + (charSequence3 != null ? charSequence3.hashCode() : 0)) * 31, 31), 31, this.roaming);
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
        return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.showTriangle, ']');
    }
}
