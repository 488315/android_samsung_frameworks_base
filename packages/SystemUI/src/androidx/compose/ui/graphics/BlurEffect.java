package androidx.compose.ui.graphics;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.TileMode;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        return renderEffect == null ? android.graphics.RenderEffect.createBlurEffect(f, f2, AndroidTileMode_androidKt.m449toAndroidTileMode0vamqd0(i)) : android.graphics.RenderEffect.createBlurEffect(f, f2, renderEffect.asAndroidRenderEffect(), AndroidTileMode_androidKt.m449toAndroidTileMode0vamqd0(i));
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
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radiusY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radiusX, (renderEffect != null ? renderEffect.hashCode() : 0) * 31, 31), 31);
        TileMode.Companion companion = TileMode.Companion;
        return Integer.hashCode(this.edgeTreatment) + iM;
    }

    public final String toString() {
        return "BlurEffect(renderEffect=" + this.renderEffect + ", radiusX=" + this.radiusX + ", radiusY=" + this.radiusY + ", edgeTreatment=" + ((Object) TileMode.m502toStringimpl(this.edgeTreatment)) + ')';
    }

    private BlurEffect(RenderEffect renderEffect, float f, float f2, int i) {
        super(null);
        this.renderEffect = renderEffect;
        this.radiusX = f;
        this.radiusY = f2;
        this.edgeTreatment = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public BlurEffect(RenderEffect renderEffect, float f, float f2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        f2 = (i2 & 4) != 0 ? f : f2;
        if ((i2 & 8) != 0) {
            TileMode.Companion.getClass();
            i = 0;
        }
        this(renderEffect, f, f2, i, null);
    }
}
