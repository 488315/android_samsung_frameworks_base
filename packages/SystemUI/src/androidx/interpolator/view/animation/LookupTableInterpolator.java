package androidx.interpolator.view.animation;

import android.view.animation.Interpolator;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LookupTableInterpolator implements Interpolator {
    public final float mStepSize;
    public final float[] mValues;

    public LookupTableInterpolator(float[] fArr) {
        this.mValues = fArr;
        this.mStepSize = 1.0f / (fArr.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.mValues;
        int min = Math.min((int) ((fArr.length - 1) * f), fArr.length - 2);
        float f2 = this.mStepSize;
        float m = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(min, f2, f, f2);
        float[] fArr2 = this.mValues;
        float f3 = fArr2[min];
        return DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fArr2[min + 1], f3, m, f3);
    }
}
