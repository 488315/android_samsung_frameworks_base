package com.android.systemui.decor;

import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverViewState {
    public final String contentDescription;
    public final int cornerIndex;
    public final View designatedCorner;
    public final boolean isDotBlocked;
    public final boolean layoutRtl;
    public final int rotation;
    public final boolean systemPrivacyEventIsActive;
    public final boolean viewInitialized;

    public CoverViewState() {
        this(false, false, false, false, 0, 0, null, null, 255, null);
    }

    public static CoverViewState copy$default(CoverViewState coverViewState, boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, View view, String str, int i3) {
        boolean z5 = (i3 & 1) != 0 ? coverViewState.viewInitialized : z;
        boolean z6 = (i3 & 2) != 0 ? coverViewState.systemPrivacyEventIsActive : z2;
        boolean z7 = (i3 & 4) != 0 ? coverViewState.isDotBlocked : z3;
        boolean z8 = (i3 & 8) != 0 ? coverViewState.layoutRtl : z4;
        int i4 = (i3 & 16) != 0 ? coverViewState.rotation : i;
        int i5 = (i3 & 32) != 0 ? coverViewState.cornerIndex : i2;
        View view2 = (i3 & 64) != 0 ? coverViewState.designatedCorner : view;
        String str2 = (i3 & 128) != 0 ? coverViewState.contentDescription : str;
        coverViewState.getClass();
        return new CoverViewState(z5, z6, z7, z8, i4, i5, view2, str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoverViewState)) {
            return false;
        }
        CoverViewState coverViewState = (CoverViewState) obj;
        return this.viewInitialized == coverViewState.viewInitialized && this.systemPrivacyEventIsActive == coverViewState.systemPrivacyEventIsActive && this.isDotBlocked == coverViewState.isDotBlocked && this.layoutRtl == coverViewState.layoutRtl && this.rotation == coverViewState.rotation && this.cornerIndex == coverViewState.cornerIndex && Intrinsics.areEqual(this.designatedCorner, coverViewState.designatedCorner) && Intrinsics.areEqual(this.contentDescription, coverViewState.contentDescription);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.viewInitialized;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.systemPrivacyEventIsActive;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isDotBlocked;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.layoutRtl;
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.cornerIndex, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.rotation, (i6 + (z4 ? 1 : z4 ? 1 : 0)) * 31, 31), 31);
        View view = this.designatedCorner;
        int hashCode = (m42m + (view == null ? 0 : view.hashCode())) * 31;
        String str = this.contentDescription;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CoverViewState(viewInitialized=");
        sb.append(this.viewInitialized);
        sb.append(", systemPrivacyEventIsActive=");
        sb.append(this.systemPrivacyEventIsActive);
        sb.append(", isDotBlocked=");
        sb.append(this.isDotBlocked);
        sb.append(", layoutRtl=");
        sb.append(this.layoutRtl);
        sb.append(", rotation=");
        sb.append(this.rotation);
        sb.append(", cornerIndex=");
        sb.append(this.cornerIndex);
        sb.append(", designatedCorner=");
        sb.append(this.designatedCorner);
        sb.append(", contentDescription=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.contentDescription, ")");
    }

    public CoverViewState(boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, View view, String str) {
        this.viewInitialized = z;
        this.systemPrivacyEventIsActive = z2;
        this.isDotBlocked = z3;
        this.layoutRtl = z4;
        this.rotation = i;
        this.cornerIndex = i2;
        this.designatedCorner = view;
        this.contentDescription = str;
    }

    public /* synthetic */ CoverViewState(boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, View view, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? false : z, (i3 & 2) != 0 ? false : z2, (i3 & 4) != 0 ? false : z3, (i3 & 8) != 0 ? false : z4, (i3 & 16) != 0 ? 0 : i, (i3 & 32) != 0 ? -1 : i2, (i3 & 64) != 0 ? null : view, (i3 & 128) != 0 ? null : str);
    }
}
