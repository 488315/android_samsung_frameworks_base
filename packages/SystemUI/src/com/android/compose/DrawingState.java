package com.android.compose;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class DrawingState {
    public final float iconWidth;
    public final float indicatorBottom;
    public final float indicatorLeft;
    public final float indicatorRight;
    public final float indicatorTop;
    public final boolean isRtl;
    public final float labelWidth;
    public final float totalHeight;
    public final float totalWidth;

    public DrawingState() {
        this(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 511, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DrawingState)) {
            return false;
        }
        DrawingState drawingState = (DrawingState) obj;
        return this.isRtl == drawingState.isRtl && Float.compare(this.totalWidth, drawingState.totalWidth) == 0 && Float.compare(this.totalHeight, drawingState.totalHeight) == 0 && Float.compare(this.indicatorLeft, drawingState.indicatorLeft) == 0 && Float.compare(this.indicatorTop, drawingState.indicatorTop) == 0 && Float.compare(this.indicatorRight, drawingState.indicatorRight) == 0 && Float.compare(this.indicatorBottom, drawingState.indicatorBottom) == 0 && Float.compare(this.iconWidth, drawingState.iconWidth) == 0 && Float.compare(this.labelWidth, drawingState.labelWidth) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.labelWidth) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.iconWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.indicatorBottom, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.indicatorRight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.indicatorTop, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.indicatorLeft, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.totalHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.totalWidth, Boolean.hashCode(this.isRtl) * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DrawingState(isRtl=");
        sb.append(this.isRtl);
        sb.append(", totalWidth=");
        sb.append(this.totalWidth);
        sb.append(", totalHeight=");
        sb.append(this.totalHeight);
        sb.append(", indicatorLeft=");
        sb.append(this.indicatorLeft);
        sb.append(", indicatorTop=");
        sb.append(this.indicatorTop);
        sb.append(", indicatorRight=");
        sb.append(this.indicatorRight);
        sb.append(", indicatorBottom=");
        sb.append(this.indicatorBottom);
        sb.append(", iconWidth=");
        sb.append(this.iconWidth);
        sb.append(", labelWidth=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.labelWidth, ")", sb);
    }

    public DrawingState(boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.isRtl = z;
        this.totalWidth = f;
        this.totalHeight = f2;
        this.indicatorLeft = f3;
        this.indicatorTop = f4;
        this.indicatorRight = f5;
        this.indicatorBottom = f6;
        this.iconWidth = f7;
        this.labelWidth = f8;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ DrawingState(boolean r2, float r3, float r4, float r5, float r6, float r7, float r8, float r9, float r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r1 = this;
            r12 = r11 & 1
            if (r12 == 0) goto L5
            r2 = 0
        L5:
            r12 = r11 & 2
            r0 = 0
            if (r12 == 0) goto Lb
            r3 = r0
        Lb:
            r12 = r11 & 4
            if (r12 == 0) goto L10
            r4 = r0
        L10:
            r12 = r11 & 8
            if (r12 == 0) goto L15
            r5 = r0
        L15:
            r12 = r11 & 16
            if (r12 == 0) goto L1a
            r6 = r0
        L1a:
            r12 = r11 & 32
            if (r12 == 0) goto L1f
            r7 = r0
        L1f:
            r12 = r11 & 64
            if (r12 == 0) goto L24
            r8 = r0
        L24:
            r12 = r11 & 128(0x80, float:1.8E-43)
            if (r12 == 0) goto L29
            r9 = r0
        L29:
            r11 = r11 & 256(0x100, float:3.59E-43)
            if (r11 == 0) goto L38
            r12 = r0
            r10 = r8
            r11 = r9
            r8 = r6
            r9 = r7
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
            goto L42
        L38:
            r12 = r10
            r11 = r9
            r9 = r7
            r10 = r8
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
        L42:
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.DrawingState.<init>(boolean, float, float, float, float, float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
