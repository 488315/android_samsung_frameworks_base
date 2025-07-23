package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ULong.m3427equalsimpl0(this.hoverOverlayColor, j) && Float.compare(this.hoverOverlayAlpha, interactionsConfig.hoverOverlayAlpha) == 0 && ULong.m3427equalsimpl0(this.pressedOverlayColor, interactionsConfig.pressedOverlayColor) && Float.compare(this.pressedOverlayAlpha, interactionsConfig.pressedOverlayAlpha) == 0 && ULong.m3427equalsimpl0(this.focusOutlineColor, interactionsConfig.focusOutlineColor) && Dp.m836equalsimpl0(this.focusOutlineStrokeWidth, interactionsConfig.focusOutlineStrokeWidth) && Dp.m836equalsimpl0(this.focusOutlinePadding, interactionsConfig.focusOutlinePadding) && Dp.m836equalsimpl0(this.surfaceCornerRadius, interactionsConfig.surfaceCornerRadius) && Dp.m836equalsimpl0(this.focusOutlineCornerRadius, interactionsConfig.focusOutlineCornerRadius) && Dp.m836equalsimpl0(this.hoverPadding, interactionsConfig.hoverPadding) && Dp.m836equalsimpl0(this.pressedPadding, interactionsConfig.pressedPadding);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int m = MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressedOverlayAlpha, MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoverOverlayAlpha, Long.hashCode(this.hoverOverlayColor) * 31, 31), 31, this.pressedOverlayColor), 31), 31, this.focusOutlineColor);
        Dp.Companion companion2 = Dp.Companion;
        return Float.hashCode(this.pressedPadding) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoverPadding, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlineCornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.surfaceCornerRadius, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlinePadding, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusOutlineStrokeWidth, m, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.hoverOverlayColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.pressedOverlayColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.focusOutlineColor);
        String m837toStringimpl = Dp.m837toStringimpl(this.focusOutlineStrokeWidth);
        String m837toStringimpl2 = Dp.m837toStringimpl(this.focusOutlinePadding);
        String m837toStringimpl3 = Dp.m837toStringimpl(this.surfaceCornerRadius);
        String m837toStringimpl4 = Dp.m837toStringimpl(this.focusOutlineCornerRadius);
        String m837toStringimpl5 = Dp.m837toStringimpl(this.hoverPadding);
        String m837toStringimpl6 = Dp.m837toStringimpl(this.pressedPadding);
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("InteractionsConfig(hoverOverlayColor=", m462toStringimpl, ", hoverOverlayAlpha=");
        m.append(this.hoverOverlayAlpha);
        m.append(", pressedOverlayColor=");
        m.append(m462toStringimpl2);
        m.append(", pressedOverlayAlpha=");
        m.append(this.pressedOverlayAlpha);
        m.append(", focusOutlineColor=");
        m.append(m462toStringimpl3);
        m.append(", focusOutlineStrokeWidth=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m837toStringimpl, ", focusOutlinePadding=", m837toStringimpl2, ", surfaceCornerRadius=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m837toStringimpl3, ", focusOutlineCornerRadius=", m837toStringimpl4, ", hoverPadding=");
        return NotificationController$$ExternalSyntheticOutline0.m(m, m837toStringimpl5, ", pressedPadding=", m837toStringimpl6, ")");
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public InteractionsConfig(long r16, float r18, long r19, float r21, long r22, float r24, float r25, float r26, float r27, float r28, float r29, int r30, kotlin.jvm.internal.DefaultConstructorMarker r31) {
        /*
            r15 = this;
            r0 = r30
            r1 = r0 & 1
            if (r1 == 0) goto Le
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Transparent
            goto L10
        Le:
            r1 = r16
        L10:
            r3 = r0 & 2
            r4 = 0
            if (r3 == 0) goto L17
            r3 = r4
            goto L19
        L17:
            r3 = r18
        L19:
            r5 = r0 & 4
            if (r5 == 0) goto L25
            androidx.compose.ui.graphics.Color$Companion r5 = androidx.compose.ui.graphics.Color.Companion
            r5.getClass()
            long r5 = androidx.compose.ui.graphics.Color.Transparent
            goto L27
        L25:
            r5 = r19
        L27:
            r7 = r0 & 8
            if (r7 == 0) goto L2c
            goto L2e
        L2c:
            r4 = r21
        L2e:
            r7 = r0 & 16
            if (r7 == 0) goto L3a
            androidx.compose.ui.graphics.Color$Companion r7 = androidx.compose.ui.graphics.Color.Companion
            r7.getClass()
            long r7 = androidx.compose.ui.graphics.Color.Transparent
            goto L3c
        L3a:
            r7 = r22
        L3c:
            r9 = r0 & 32
            r10 = 0
            if (r9 == 0) goto L45
            float r9 = (float) r10
            androidx.compose.ui.unit.Dp$Companion r11 = androidx.compose.ui.unit.Dp.Companion
            goto L47
        L45:
            r9 = r24
        L47:
            r11 = r0 & 64
            if (r11 == 0) goto L4f
            float r11 = (float) r10
            androidx.compose.ui.unit.Dp$Companion r12 = androidx.compose.ui.unit.Dp.Companion
            goto L51
        L4f:
            r11 = r25
        L51:
            r12 = r0 & 128(0x80, float:1.8E-43)
            if (r12 == 0) goto L59
            float r12 = (float) r10
            androidx.compose.ui.unit.Dp$Companion r13 = androidx.compose.ui.unit.Dp.Companion
            goto L5b
        L59:
            r12 = r26
        L5b:
            r13 = r0 & 256(0x100, float:3.59E-43)
            if (r13 == 0) goto L63
            float r13 = (float) r10
            androidx.compose.ui.unit.Dp$Companion r14 = androidx.compose.ui.unit.Dp.Companion
            goto L65
        L63:
            r13 = r27
        L65:
            r14 = r0 & 512(0x200, float:7.17E-43)
            if (r14 == 0) goto L6d
            float r10 = (float) r10
            androidx.compose.ui.unit.Dp$Companion r14 = androidx.compose.ui.unit.Dp.Companion
            goto L6f
        L6d:
            r10 = r28
        L6f:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L75
            r0 = r10
            goto L77
        L75:
            r0 = r29
        L77:
            r14 = 0
            r16 = r15
            r30 = r0
            r17 = r1
            r19 = r3
            r22 = r4
            r20 = r5
            r23 = r7
            r25 = r9
            r29 = r10
            r26 = r11
            r27 = r12
            r28 = r13
            r31 = r14
            r16.<init>(r17, r19, r20, r22, r23, r25, r26, r27, r28, r29, r30, r31)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.InteractionsConfig.<init>(long, float, long, float, long, float, float, float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
