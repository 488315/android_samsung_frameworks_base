package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewState {
    public final String contentDescription;
    public final PrivacyDotCorner corner;
    public final View designatedCorner;
    public final Point displaySize;
    public final boolean isDotBlocked;
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
        this(false, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, false, null, 1048575, null);
    }

    public static ViewState copy$default(ViewState viewState, boolean z, boolean z2, boolean z3, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z4, int i, int i2, PrivacyDotCorner privacyDotCorner, View view, String str, int i3, int i4, int i5, int i6, Point point, int i7) {
        boolean z5 = (i7 & 1) != 0 ? viewState.viewInitialized : true;
        boolean z6 = (i7 & 2) != 0 ? viewState.systemPrivacyEventIsActive : z;
        boolean z7 = (i7 & 4) != 0 ? viewState.shadeExpanded : z2;
        boolean z8 = (i7 & 8) != 0 ? viewState.qsExpanded : z3;
        Rect rect5 = (i7 & 16) != 0 ? viewState.portraitRect : rect;
        Rect rect6 = (i7 & 32) != 0 ? viewState.landscapeRect : rect2;
        Rect rect7 = (i7 & 64) != 0 ? viewState.upsideDownRect : rect3;
        Rect rect8 = (i7 & 128) != 0 ? viewState.seascapeRect : rect4;
        boolean z9 = (i7 & 256) != 0 ? viewState.layoutRtl : z4;
        int i8 = (i7 & 512) != 0 ? viewState.rotation : i;
        int i9 = (i7 & 1024) != 0 ? viewState.paddingTop : i2;
        PrivacyDotCorner privacyDotCorner2 = (i7 & 2048) != 0 ? viewState.corner : privacyDotCorner;
        View view2 = (i7 & 4096) != 0 ? viewState.designatedCorner : view;
        String str2 = (i7 & 8192) != 0 ? viewState.contentDescription : str;
        int i10 = (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? viewState.statusBarPaddingLeft : i3;
        int i11 = (32768 & i7) != 0 ? viewState.statusBarPaddingRight : i4;
        int i12 = (65536 & i7) != 0 ? viewState.stableInsetLeft : i5;
        int i13 = (131072 & i7) != 0 ? viewState.stableInsetRight : i6;
        boolean z10 = viewState.isDotBlocked;
        Point point2 = (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? viewState.displaySize : point;
        viewState.getClass();
        return new ViewState(z5, z6, z7, z8, rect5, rect6, rect7, rect8, z9, i8, i9, privacyDotCorner2, view2, str2, i10, i11, i12, i13, z10, point2);
    }

    public final Rect contentRectForRotation(int i) {
        if (i == 0) {
            Rect rect = this.portraitRect;
            rect.getClass();
            return rect;
        }
        if (i == 1) {
            Rect rect2 = this.landscapeRect;
            rect2.getClass();
            return rect2;
        }
        if (i == 2) {
            Rect rect3 = this.upsideDownRect;
            rect3.getClass();
            return rect3;
        }
        if (i != 3) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "not a rotation (", ")"));
        }
        Rect rect4 = this.seascapeRect;
        rect4.getClass();
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
        return this.viewInitialized == viewState.viewInitialized && this.systemPrivacyEventIsActive == viewState.systemPrivacyEventIsActive && this.shadeExpanded == viewState.shadeExpanded && this.qsExpanded == viewState.qsExpanded && Intrinsics.areEqual(this.portraitRect, viewState.portraitRect) && Intrinsics.areEqual(this.landscapeRect, viewState.landscapeRect) && Intrinsics.areEqual(this.upsideDownRect, viewState.upsideDownRect) && Intrinsics.areEqual(this.seascapeRect, viewState.seascapeRect) && this.layoutRtl == viewState.layoutRtl && this.rotation == viewState.rotation && this.paddingTop == viewState.paddingTop && this.corner == viewState.corner && Intrinsics.areEqual(this.designatedCorner, viewState.designatedCorner) && Intrinsics.areEqual(this.contentDescription, viewState.contentDescription) && this.statusBarPaddingLeft == viewState.statusBarPaddingLeft && this.statusBarPaddingRight == viewState.statusBarPaddingRight && this.stableInsetLeft == viewState.stableInsetLeft && this.stableInsetRight == viewState.stableInsetRight && this.isDotBlocked == viewState.isDotBlocked && Intrinsics.areEqual(this.displaySize, viewState.displaySize);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.viewInitialized) * 31, 31, this.systemPrivacyEventIsActive), 31, this.shadeExpanded), 31, this.qsExpanded);
        Rect rect = this.portraitRect;
        int hashCode = (m + (rect == null ? 0 : rect.hashCode())) * 31;
        Rect rect2 = this.landscapeRect;
        int hashCode2 = (hashCode + (rect2 == null ? 0 : rect2.hashCode())) * 31;
        Rect rect3 = this.upsideDownRect;
        int hashCode3 = (hashCode2 + (rect3 == null ? 0 : rect3.hashCode())) * 31;
        Rect rect4 = this.seascapeRect;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.paddingTop, ReorderTile$$ExternalSyntheticOutline0.m(this.rotation, TransitionData$$ExternalSyntheticOutline0.m((hashCode3 + (rect4 == null ? 0 : rect4.hashCode())) * 31, 31, this.layoutRtl), 31), 31);
        PrivacyDotCorner privacyDotCorner = this.corner;
        int hashCode4 = (m2 + (privacyDotCorner == null ? 0 : privacyDotCorner.hashCode())) * 31;
        View view = this.designatedCorner;
        int hashCode5 = (hashCode4 + (view == null ? 0 : view.hashCode())) * 31;
        String str = this.contentDescription;
        return this.displaySize.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.stableInsetRight, ReorderTile$$ExternalSyntheticOutline0.m(this.stableInsetLeft, ReorderTile$$ExternalSyntheticOutline0.m(this.statusBarPaddingRight, ReorderTile$$ExternalSyntheticOutline0.m(this.statusBarPaddingLeft, (hashCode5 + (str != null ? str.hashCode() : 0)) * 31, 31), 31), 31), 31), 31, this.isDotBlocked);
    }

    public final String toString() {
        return "ViewState(viewInitialized=" + this.viewInitialized + ", systemPrivacyEventIsActive=" + this.systemPrivacyEventIsActive + ", shadeExpanded=" + this.shadeExpanded + ", qsExpanded=" + this.qsExpanded + ", portraitRect=" + this.portraitRect + ", landscapeRect=" + this.landscapeRect + ", upsideDownRect=" + this.upsideDownRect + ", seascapeRect=" + this.seascapeRect + ", layoutRtl=" + this.layoutRtl + ", rotation=" + this.rotation + ", paddingTop=" + this.paddingTop + ", corner=" + this.corner + ", designatedCorner=" + this.designatedCorner + ", contentDescription=" + this.contentDescription + ", statusBarPaddingLeft=" + this.statusBarPaddingLeft + ", statusBarPaddingRight=" + this.statusBarPaddingRight + ", stableInsetLeft=" + this.stableInsetLeft + ", stableInsetRight=" + this.stableInsetRight + ", isDotBlocked=" + this.isDotBlocked + ", displaySize=" + this.displaySize + ")";
    }

    public ViewState(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, PrivacyDotCorner privacyDotCorner, View view, String str, int i3, int i4, int i5, int i6, boolean z6, Point point) {
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
        this.corner = privacyDotCorner;
        this.designatedCorner = view;
        this.contentDescription = str;
        this.statusBarPaddingLeft = i3;
        this.statusBarPaddingRight = i4;
        this.stableInsetLeft = i5;
        this.stableInsetRight = i6;
        this.isDotBlocked = z6;
        this.displaySize = point;
    }

    public /* synthetic */ ViewState(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, PrivacyDotCorner privacyDotCorner, View view, String str, int i3, int i4, int i5, int i6, boolean z6, Point point, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? false : z, (i7 & 2) != 0 ? false : z2, (i7 & 4) != 0 ? false : z3, (i7 & 8) != 0 ? false : z4, (i7 & 16) != 0 ? null : rect, (i7 & 32) != 0 ? null : rect2, (i7 & 64) != 0 ? null : rect3, (i7 & 128) != 0 ? null : rect4, (i7 & 256) != 0 ? false : z5, (i7 & 512) != 0 ? 0 : i, (i7 & 1024) != 0 ? 0 : i2, (i7 & 2048) != 0 ? null : privacyDotCorner, (i7 & 4096) != 0 ? null : view, (i7 & 8192) == 0 ? str : null, (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? 10 : i3, (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0 ? i4 : 10, (i7 & 65536) != 0 ? 0 : i5, (i7 & 131072) != 0 ? 0 : i6, (i7 & 262144) != 0 ? false : z6, (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? new Point() : point);
    }
}
