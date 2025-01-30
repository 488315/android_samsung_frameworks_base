package com.android.systemui.shade.carrier;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public final int hashCode() {
        boolean z = this.visible;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.mobileSignalIconId, r1 * 31, 31);
        String str = this.contentDescription;
        int hashCode = (m42m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.typeContentDescription;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        boolean z2 = this.roaming;
        return hashCode2 + (z2 ? 1 : z2 ? 1 : 0);
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.roaming, ")");
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
