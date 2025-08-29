package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class InteractionsConfig {
    public final long focusOutlineColor;
    public final float focusOutlineCornerRadius;
    public final float focusOutlinePadding;
    public final float focusOutlineStrokeWidth;
    public final float hoverOverlayAlpha;
    public final long hoverOverlayColor;
    public final float hoverPadding;
    public final float pressedOverlayAlpha;
    public final long pressedOverlayColor;
    public final float pressedPadding;
    public final float surfaceCornerRadius;

    public /* synthetic */ InteractionsConfig(long j, float f, long j2, float f2, long j3, float f3, float f4, float f5, float f6, float f7, float f8, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, f, j2, f2, j3, f3, f4, f5, f6, f7, f8);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InteractionsConfig)) {
            return false;
        }
        InteractionsConfig interactionsConfig = (InteractionsConfig) obj;
        long j = interactionsConfig.hoverOverlayColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.hoverOverlayColor, j) && Float.compare(this.hoverOverlayAlpha, interactionsConfig.hoverOverlayAlpha) == 0 && ULong.m3446equalsimpl0(this.pressedOverlayColor, interactionsConfig.pressedOverlayColor) && Float.compare(this.pressedOverlayAlpha, interactionsConfig.pressedOverlayAlpha) == 0 && ULong.m3446equalsimpl0(this.focusOutlineColor, interactionsConfig.focusOutlineColor) && Dp.m838equalsimpl0(this.focusOutlineStrokeWidth, interactionsConfig.focusOutlineStrokeWidth) && Dp.m838equalsimpl0(this.focusOutlinePadding, interactionsConfig.focusOutlinePadding) && Dp.m838equalsimpl0(this.surfaceCornerRadius, interactionsConfig.surfaceCornerRadius) && Dp.m838equalsimpl0(this.focusOutlineCornerRadius, interactionsConfig.focusOutlineCornerRadius) && Dp.m838equalsimpl0(this.hoverPadding, interactionsConfig.hoverPadding) && Dp.m838equalsimpl0(this.pressedPadding, interactionsConfig.pressedPadding);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressedOverlayAlpha, MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoverOverlayAlpha, Long.hashCode(this.hoverOverlayColor) * 31, 31), 31, this.pressedOverlayColor), 31), 31, this.focusOutlineColor);
        Dp.Companion companion2 = Dp.Companion;
        return Float.hashCode(this.pressedPadding) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoverPadding, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlineCornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.surfaceCornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlinePadding, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlineStrokeWidth, iM, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.hoverOverlayColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.pressedOverlayColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.focusOutlineColor);
        String strM839toStringimpl = Dp.m839toStringimpl(this.focusOutlineStrokeWidth);
        String strM839toStringimpl2 = Dp.m839toStringimpl(this.focusOutlinePadding);
        String strM839toStringimpl3 = Dp.m839toStringimpl(this.surfaceCornerRadius);
        String strM839toStringimpl4 = Dp.m839toStringimpl(this.focusOutlineCornerRadius);
        String strM839toStringimpl5 = Dp.m839toStringimpl(this.hoverPadding);
        String strM839toStringimpl6 = Dp.m839toStringimpl(this.pressedPadding);
        StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("InteractionsConfig(hoverOverlayColor=", strM464toStringimpl, ", hoverOverlayAlpha=");
        sbM.append(this.hoverOverlayAlpha);
        sbM.append(", pressedOverlayColor=");
        sbM.append(strM464toStringimpl2);
        sbM.append(", pressedOverlayAlpha=");
        sbM.append(this.pressedOverlayAlpha);
        sbM.append(", focusOutlineColor=");
        sbM.append(strM464toStringimpl3);
        sbM.append(", focusOutlineStrokeWidth=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM839toStringimpl, ", focusOutlinePadding=", strM839toStringimpl2, ", surfaceCornerRadius=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM839toStringimpl3, ", focusOutlineCornerRadius=", strM839toStringimpl4, ", hoverPadding=");
        return NotificationController$$ExternalSyntheticOutline0.m(sbM, strM839toStringimpl5, ", pressedPadding=", strM839toStringimpl6, ")");
    }

    private InteractionsConfig(long j, float f, long j2, float f2, long j3, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.hoverOverlayColor = j;
        this.hoverOverlayAlpha = f;
        this.pressedOverlayColor = j2;
        this.pressedOverlayAlpha = f2;
        this.focusOutlineColor = j3;
        this.focusOutlineStrokeWidth = f3;
        this.focusOutlinePadding = f4;
        this.surfaceCornerRadius = f5;
        this.focusOutlineCornerRadius = f6;
        this.hoverPadding = f7;
        this.pressedPadding = f8;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public InteractionsConfig(long j, float f, long j2, float f2, long j3, float f3, float f4, float f5, float f6, float f7, float f8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j4;
        long j5;
        long j6;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j4 = Color.Transparent;
        } else {
            j4 = j;
        }
        float f14 = (i & 2) != 0 ? 0.0f : f;
        if ((i & 4) != 0) {
            Color.Companion.getClass();
            j5 = Color.Transparent;
        } else {
            j5 = j2;
        }
        float f15 = (i & 8) == 0 ? f2 : 0.0f;
        if ((i & 16) != 0) {
            Color.Companion.getClass();
            j6 = Color.Transparent;
        } else {
            j6 = j3;
        }
        if ((i & 32) != 0) {
            f9 = 0;
            Dp.Companion companion = Dp.Companion;
        } else {
            f9 = f3;
        }
        if ((i & 64) != 0) {
            f10 = 0;
            Dp.Companion companion2 = Dp.Companion;
        } else {
            f10 = f4;
        }
        if ((i & 128) != 0) {
            f11 = 0;
            Dp.Companion companion3 = Dp.Companion;
        } else {
            f11 = f5;
        }
        if ((i & 256) != 0) {
            f12 = 0;
            Dp.Companion companion4 = Dp.Companion;
        } else {
            f12 = f6;
        }
        if ((i & 512) != 0) {
            f13 = 0;
            Dp.Companion companion5 = Dp.Companion;
        } else {
            f13 = f7;
        }
        this(j4, f14, j5, f15, j6, f9, f10, f11, f12, f13, (i & 1024) != 0 ? f13 : f8, null);
    }
}
