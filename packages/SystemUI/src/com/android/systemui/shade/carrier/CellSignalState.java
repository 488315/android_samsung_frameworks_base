package com.android.systemui.shade.carrier;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class CellSignalState {
    public final String contentDescription;
    public final int mobileSignalIconId;
    public final boolean roaming;
    public final String typeContentDescription;
    public final boolean visible;

    public CellSignalState() {
        this(false, 0, null, null, false, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellSignalState)) {
            return false;
        }
        CellSignalState cellSignalState = (CellSignalState) obj;
        return this.visible == cellSignalState.visible && this.mobileSignalIconId == cellSignalState.mobileSignalIconId && Intrinsics.areEqual(this.contentDescription, cellSignalState.contentDescription) && Intrinsics.areEqual(this.typeContentDescription, cellSignalState.typeContentDescription) && this.roaming == cellSignalState.roaming;
    }

    public final int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mobileSignalIconId, Boolean.hashCode(this.visible) * 31, 31);
        String str = this.contentDescription;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.typeContentDescription;
        return Boolean.hashCode(this.roaming) + ((hashCode + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CellSignalState(visible=");
        sb.append(this.visible);
        sb.append(", mobileSignalIconId=");
        sb.append(this.mobileSignalIconId);
        sb.append(", contentDescription=");
        sb.append(this.contentDescription);
        sb.append(", typeContentDescription=");
        sb.append(this.typeContentDescription);
        sb.append(", roaming=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.roaming, ")");
    }

    public CellSignalState(boolean z, int i, String str, String str2, boolean z2) {
        this.visible = z;
        this.mobileSignalIconId = i;
        this.contentDescription = str;
        this.typeContentDescription = str2;
        this.roaming = z2;
    }

    public /* synthetic */ CellSignalState(boolean z, int i, String str, String str2, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? null : str, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? false : z2);
    }
}
