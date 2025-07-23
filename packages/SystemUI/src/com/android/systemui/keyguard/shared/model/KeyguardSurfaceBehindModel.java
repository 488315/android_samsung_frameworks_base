package com.android.systemui.keyguard.shared.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindModel {
    public final float alpha;
    public final float animateFromAlpha;
    public final float animateFromTranslationY;
    public final float startVelocity;
    public final float translationY;

    public KeyguardSurfaceBehindModel() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardSurfaceBehindModel)) {
            return false;
        }
        KeyguardSurfaceBehindModel keyguardSurfaceBehindModel = (KeyguardSurfaceBehindModel) obj;
        return Float.compare(this.alpha, keyguardSurfaceBehindModel.alpha) == 0 && Float.compare(this.animateFromAlpha, keyguardSurfaceBehindModel.animateFromAlpha) == 0 && Float.compare(this.translationY, keyguardSurfaceBehindModel.translationY) == 0 && Float.compare(this.animateFromTranslationY, keyguardSurfaceBehindModel.animateFromTranslationY) == 0 && Float.compare(this.startVelocity, keyguardSurfaceBehindModel.startVelocity) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.startVelocity) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.animateFromTranslationY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.translationY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.animateFromAlpha, Float.hashCode(this.alpha) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KeyguardSurfaceBehindModel(alpha=");
        sb.append(this.alpha);
        sb.append(", animateFromAlpha=");
        sb.append(this.animateFromAlpha);
        sb.append(", translationY=");
        sb.append(this.translationY);
        sb.append(", animateFromTranslationY=");
        sb.append(this.animateFromTranslationY);
        sb.append(", startVelocity=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.startVelocity, ")", sb);
    }

    public KeyguardSurfaceBehindModel(float f, float f2, float f3, float f4, float f5) {
        this.alpha = f;
        this.animateFromAlpha = f2;
        this.translationY = f3;
        this.animateFromTranslationY = f4;
        this.startVelocity = f5;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ KeyguardSurfaceBehindModel(float r2, float r3, float r4, float r5, float r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
        /*
            r1 = this;
            r8 = r7 & 1
            if (r8 == 0) goto L6
            r2 = 1065353216(0x3f800000, float:1.0)
        L6:
            r8 = r7 & 2
            if (r8 == 0) goto Lb
            r3 = r2
        Lb:
            r8 = r7 & 4
            r0 = 0
            if (r8 == 0) goto L11
            r4 = r0
        L11:
            r8 = r7 & 8
            if (r8 == 0) goto L16
            r5 = r4
        L16:
            r7 = r7 & 16
            if (r7 == 0) goto L21
            r8 = r0
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
            goto L27
        L21:
            r8 = r6
            r7 = r5
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
        L27:
            r3.<init>(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.shared.model.KeyguardSurfaceBehindModel.<init>(float, float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
