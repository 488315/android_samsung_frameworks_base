package com.android.systemui.keyguard.shared.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.startVelocity, ")");
    }

    public KeyguardSurfaceBehindModel(float f, float f2, float f3, float f4, float f5) {
        this.alpha = f;
        this.animateFromAlpha = f2;
        this.translationY = f3;
        this.animateFromTranslationY = f4;
        this.startVelocity = f5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ KeyguardSurfaceBehindModel(float r4, float r5, float r6, float r7, float r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L6
            r4 = 1065353216(0x3f800000, float:1.0)
        L6:
            r10 = r9 & 2
            if (r10 == 0) goto Lc
            r10 = r4
            goto Ld
        Lc:
            r10 = r5
        Ld:
            r5 = r9 & 4
            r0 = 0
            if (r5 == 0) goto L14
            r1 = r0
            goto L15
        L14:
            r1 = r6
        L15:
            r5 = r9 & 8
            if (r5 == 0) goto L1b
            r2 = r1
            goto L1c
        L1b:
            r2 = r7
        L1c:
            r5 = r9 & 16
            if (r5 == 0) goto L21
            goto L22
        L21:
            r0 = r8
        L22:
            r5 = r3
            r6 = r4
            r7 = r10
            r8 = r1
            r9 = r2
            r10 = r0
            r5.<init>(r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.shared.model.KeyguardSurfaceBehindModel.<init>(float, float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
