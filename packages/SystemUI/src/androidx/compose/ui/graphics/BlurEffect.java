package androidx.compose.ui.graphics;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.TileMode;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BlurEffect extends RenderEffect {
    public final int edgeTreatment;
    public final float radiusX;
    public final float radiusY;
    public final RenderEffect renderEffect;

    public /* synthetic */ BlurEffect(RenderEffect renderEffect, float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(renderEffect, f, f2, i);
    }

    @Override // androidx.compose.ui.graphics.RenderEffect
    public final android.graphics.RenderEffect createRenderEffect() {
        RenderEffectVerificationHelper.INSTANCE.getClass();
        float f = this.radiusX;
        float f2 = this.radiusY;
        if (f == 0.0f && f2 == 0.0f) {
            return android.graphics.RenderEffect.createOffsetEffect(0.0f, 0.0f);
        }
        RenderEffect renderEffect = this.renderEffect;
        int i = this.edgeTreatment;
        return renderEffect == null ? android.graphics.RenderEffect.createBlurEffect(f, f2, AndroidTileMode_androidKt.m447toAndroidTileMode0vamqd0(i)) : android.graphics.RenderEffect.createBlurEffect(f, f2, renderEffect.asAndroidRenderEffect(), AndroidTileMode_androidKt.m447toAndroidTileMode0vamqd0(i));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlurEffect)) {
            return false;
        }
        BlurEffect blurEffect = (BlurEffect) obj;
        if (this.radiusX == blurEffect.radiusX && this.radiusY == blurEffect.radiusY) {
            int i = blurEffect.edgeTreatment;
            TileMode.Companion companion = TileMode.Companion;
            return this.edgeTreatment == i && Intrinsics.areEqual(this.renderEffect, blurEffect.renderEffect);
        }
        return false;
    }

    public final int hashCode() {
        RenderEffect renderEffect = this.renderEffect;
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radiusY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radiusX, (renderEffect != null ? renderEffect.hashCode() : 0) * 31, 31), 31);
        TileMode.Companion companion = TileMode.Companion;
        return Integer.hashCode(this.edgeTreatment) + m;
    }

    public final String toString() {
        return "BlurEffect(renderEffect=" + this.renderEffect + ", radiusX=" + this.radiusX + ", radiusY=" + this.radiusY + ", edgeTreatment=" + ((Object) TileMode.m500toStringimpl(this.edgeTreatment)) + ')';
    }

    private BlurEffect(RenderEffect renderEffect, float f, float f2, int i) {
        super(null);
        this.renderEffect = renderEffect;
        this.radiusX = f;
        this.radiusY = f2;
        this.edgeTreatment = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BlurEffect(androidx.compose.ui.graphics.RenderEffect r1, float r2, float r3, int r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 4
            if (r6 == 0) goto L5
            r3 = r2
        L5:
            r5 = r5 & 8
            if (r5 == 0) goto Lf
            androidx.compose.ui.graphics.TileMode$Companion r4 = androidx.compose.ui.graphics.TileMode.Companion
            r4.getClass()
            r4 = 0
        Lf:
            r5 = 0
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.BlurEffect.<init>(androidx.compose.ui.graphics.RenderEffect, float, float, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
