package com.android.systemui.statusbar.pipeline.mobile.ui.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DeXStatusBarIconModel {
    public final int activityId;
    public final boolean isVisible;
    public final int netwotkTypeId;
    public final int roamingId;
    public final boolean showTriangle;
    public final int slotId;
    public final int strengthId;
    public final int subId;

    public DeXStatusBarIconModel(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6) {
        this.isVisible = z;
        this.slotId = i;
        this.subId = i2;
        this.strengthId = i3;
        this.netwotkTypeId = i4;
        this.showTriangle = z2;
        this.activityId = i5;
        this.roamingId = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeXStatusBarIconModel)) {
            return false;
        }
        DeXStatusBarIconModel deXStatusBarIconModel = (DeXStatusBarIconModel) obj;
        return this.isVisible == deXStatusBarIconModel.isVisible && this.slotId == deXStatusBarIconModel.slotId && this.subId == deXStatusBarIconModel.subId && this.strengthId == deXStatusBarIconModel.strengthId && this.netwotkTypeId == deXStatusBarIconModel.netwotkTypeId && this.showTriangle == deXStatusBarIconModel.showTriangle && this.activityId == deXStatusBarIconModel.activityId && this.roamingId == deXStatusBarIconModel.roamingId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.roamingId) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.activityId, TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.netwotkTypeId, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.strengthId, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.subId, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.slotId, Boolean.hashCode(this.isVisible) * 31, 31), 31), 31), 31), 31, this.showTriangle), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeXStatusBarIconModel(isVisible=");
        sb.append(this.isVisible);
        sb.append(", slotId=");
        sb.append(this.slotId);
        sb.append(", subId=");
        sb.append(this.subId);
        sb.append(", strengthId=");
        sb.append(this.strengthId);
        sb.append(", netwotkTypeId=");
        sb.append(this.netwotkTypeId);
        sb.append(", showTriangle=");
        sb.append(this.showTriangle);
        sb.append(", activityId=");
        sb.append(this.activityId);
        sb.append(", roamingId=");
        return Anchor$$ExternalSyntheticOutline0.m(this.roamingId, ")", sb);
    }

    public /* synthetic */ DeXStatusBarIconModel(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, i, i2, i3, (i7 & 16) != 0 ? 0 : i4, z2, (i7 & 64) != 0 ? 0 : i5, (i7 & 128) != 0 ? 0 : i6);
    }
}
