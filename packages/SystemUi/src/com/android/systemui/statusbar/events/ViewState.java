package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewState {
    public final String contentDescription;
    public final int cornerIndex;
    public final View designatedCorner;
    public final Rect landscapeRect;
    public final boolean layoutRtl;
    public final int paddingTop;
    public final Rect portraitRect;
    public final boolean qsExpanded;
    public final int rotation;
    public final Rect seascapeRect;
    public final boolean shadeExpanded;
    public final int stableInsetLeft;
    public final int stableInsetRight;
    public final int statusBarPaddingLeft;
    public final int statusBarPaddingRight;
    public final boolean systemPrivacyEventIsActive;
    public final Rect upsideDownRect;
    public final boolean viewInitialized;

    public ViewState() {
        this(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262143, null);
    }

    public static ViewState copy$default(ViewState viewState, boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, int i3, View view, String str, int i4, int i5, int i6, int i7, int i8) {
        boolean z6 = (i8 & 1) != 0 ? viewState.viewInitialized : z;
        boolean z7 = (i8 & 2) != 0 ? viewState.systemPrivacyEventIsActive : z2;
        boolean z8 = (i8 & 4) != 0 ? viewState.shadeExpanded : z3;
        boolean z9 = (i8 & 8) != 0 ? viewState.qsExpanded : z4;
        Rect rect5 = (i8 & 16) != 0 ? viewState.portraitRect : rect;
        Rect rect6 = (i8 & 32) != 0 ? viewState.landscapeRect : rect2;
        Rect rect7 = (i8 & 64) != 0 ? viewState.upsideDownRect : rect3;
        Rect rect8 = (i8 & 128) != 0 ? viewState.seascapeRect : rect4;
        boolean z10 = (i8 & 256) != 0 ? viewState.layoutRtl : z5;
        int i9 = (i8 & 512) != 0 ? viewState.rotation : i;
        int i10 = (i8 & 1024) != 0 ? viewState.paddingTop : i2;
        int i11 = (i8 & 2048) != 0 ? viewState.cornerIndex : i3;
        View view2 = (i8 & 4096) != 0 ? viewState.designatedCorner : view;
        String str2 = (i8 & 8192) != 0 ? viewState.contentDescription : str;
        int i12 = (i8 & 16384) != 0 ? viewState.statusBarPaddingLeft : i4;
        int i13 = (i8 & 32768) != 0 ? viewState.statusBarPaddingRight : i5;
        int i14 = (i8 & 65536) != 0 ? viewState.stableInsetLeft : i6;
        int i15 = (i8 & 131072) != 0 ? viewState.stableInsetRight : i7;
        viewState.getClass();
        return new ViewState(z6, z7, z8, z9, rect5, rect6, rect7, rect8, z10, i9, i10, i11, view2, str2, i12, i13, i14, i15);
    }

    public final Rect contentRectForRotation(int i) {
        if (i == 0) {
            Rect rect = this.portraitRect;
            Intrinsics.checkNotNull(rect);
            return rect;
        }
        if (i == 1) {
            Rect rect2 = this.landscapeRect;
            Intrinsics.checkNotNull(rect2);
            return rect2;
        }
        if (i == 2) {
            Rect rect3 = this.upsideDownRect;
            Intrinsics.checkNotNull(rect3);
            return rect3;
        }
        if (i != 3) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("not a rotation (", i, ")"));
        }
        Rect rect4 = this.seascapeRect;
        Intrinsics.checkNotNull(rect4);
        return rect4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewState)) {
            return false;
        }
        ViewState viewState = (ViewState) obj;
        return this.viewInitialized == viewState.viewInitialized && this.systemPrivacyEventIsActive == viewState.systemPrivacyEventIsActive && this.shadeExpanded == viewState.shadeExpanded && this.qsExpanded == viewState.qsExpanded && Intrinsics.areEqual(this.portraitRect, viewState.portraitRect) && Intrinsics.areEqual(this.landscapeRect, viewState.landscapeRect) && Intrinsics.areEqual(this.upsideDownRect, viewState.upsideDownRect) && Intrinsics.areEqual(this.seascapeRect, viewState.seascapeRect) && this.layoutRtl == viewState.layoutRtl && this.rotation == viewState.rotation && this.paddingTop == viewState.paddingTop && this.cornerIndex == viewState.cornerIndex && Intrinsics.areEqual(this.designatedCorner, viewState.designatedCorner) && Intrinsics.areEqual(this.contentDescription, viewState.contentDescription) && this.statusBarPaddingLeft == viewState.statusBarPaddingLeft && this.statusBarPaddingRight == viewState.statusBarPaddingRight && this.stableInsetLeft == viewState.stableInsetLeft && this.stableInsetRight == viewState.stableInsetRight;
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
        boolean z3 = this.shadeExpanded;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.qsExpanded;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        Rect rect = this.portraitRect;
        int hashCode = (i8 + (rect == null ? 0 : rect.hashCode())) * 31;
        Rect rect2 = this.landscapeRect;
        int hashCode2 = (hashCode + (rect2 == null ? 0 : rect2.hashCode())) * 31;
        Rect rect3 = this.upsideDownRect;
        int hashCode3 = (hashCode2 + (rect3 == null ? 0 : rect3.hashCode())) * 31;
        Rect rect4 = this.seascapeRect;
        int hashCode4 = (hashCode3 + (rect4 == null ? 0 : rect4.hashCode())) * 31;
        boolean z5 = this.layoutRtl;
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.cornerIndex, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.paddingTop, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.rotation, (hashCode4 + (z5 ? 1 : z5 ? 1 : 0)) * 31, 31), 31), 31);
        View view = this.designatedCorner;
        int hashCode5 = (m42m + (view == null ? 0 : view.hashCode())) * 31;
        String str = this.contentDescription;
        return Integer.hashCode(this.stableInsetRight) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.stableInsetLeft, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.statusBarPaddingRight, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.statusBarPaddingLeft, (hashCode5 + (str != null ? str.hashCode() : 0)) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ViewState(viewInitialized=");
        sb.append(this.viewInitialized);
        sb.append(", systemPrivacyEventIsActive=");
        sb.append(this.systemPrivacyEventIsActive);
        sb.append(", shadeExpanded=");
        sb.append(this.shadeExpanded);
        sb.append(", qsExpanded=");
        sb.append(this.qsExpanded);
        sb.append(", portraitRect=");
        sb.append(this.portraitRect);
        sb.append(", landscapeRect=");
        sb.append(this.landscapeRect);
        sb.append(", upsideDownRect=");
        sb.append(this.upsideDownRect);
        sb.append(", seascapeRect=");
        sb.append(this.seascapeRect);
        sb.append(", layoutRtl=");
        sb.append(this.layoutRtl);
        sb.append(", rotation=");
        sb.append(this.rotation);
        sb.append(", paddingTop=");
        sb.append(this.paddingTop);
        sb.append(", cornerIndex=");
        sb.append(this.cornerIndex);
        sb.append(", designatedCorner=");
        sb.append(this.designatedCorner);
        sb.append(", contentDescription=");
        sb.append(this.contentDescription);
        sb.append(", statusBarPaddingLeft=");
        sb.append(this.statusBarPaddingLeft);
        sb.append(", statusBarPaddingRight=");
        sb.append(this.statusBarPaddingRight);
        sb.append(", stableInsetLeft=");
        sb.append(this.stableInsetLeft);
        sb.append(", stableInsetRight=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.stableInsetRight, ")");
    }

    public ViewState(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, int i3, View view, String str, int i4, int i5, int i6, int i7) {
        this.viewInitialized = z;
        this.systemPrivacyEventIsActive = z2;
        this.shadeExpanded = z3;
        this.qsExpanded = z4;
        this.portraitRect = rect;
        this.landscapeRect = rect2;
        this.upsideDownRect = rect3;
        this.seascapeRect = rect4;
        this.layoutRtl = z5;
        this.rotation = i;
        this.paddingTop = i2;
        this.cornerIndex = i3;
        this.designatedCorner = view;
        this.contentDescription = str;
        this.statusBarPaddingLeft = i4;
        this.statusBarPaddingRight = i5;
        this.stableInsetLeft = i6;
        this.stableInsetRight = i7;
    }

    public /* synthetic */ ViewState(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, int i3, View view, String str, int i4, int i5, int i6, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this((i8 & 1) != 0 ? false : z, (i8 & 2) != 0 ? false : z2, (i8 & 4) != 0 ? false : z3, (i8 & 8) != 0 ? false : z4, (i8 & 16) != 0 ? null : rect, (i8 & 32) != 0 ? null : rect2, (i8 & 64) != 0 ? null : rect3, (i8 & 128) != 0 ? null : rect4, (i8 & 256) != 0 ? false : z5, (i8 & 512) != 0 ? 0 : i, (i8 & 1024) != 0 ? 0 : i2, (i8 & 2048) != 0 ? -1 : i3, (i8 & 4096) != 0 ? null : view, (i8 & 8192) == 0 ? str : null, (i8 & 16384) != 0 ? 10 : i4, (i8 & 32768) == 0 ? i5 : 10, (i8 & 65536) != 0 ? 0 : i6, (i8 & 131072) != 0 ? 0 : i7);
    }
}
