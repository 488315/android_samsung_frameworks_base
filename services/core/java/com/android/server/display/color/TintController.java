package com.android.server.display.color;

import android.animation.ValueAnimator;
import android.util.Slog;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class TintController {
    public ValueAnimator mAnimator;
    public Boolean mIsActivated;
    public boolean mIsActivationLocked;

    public static String matrixToString(int i, float[] fArr) {
        int i2 = 0;
        if (fArr == null || i <= 0) {
            StringBuilder sb = new StringBuilder("Invalid arguments when formatting matrix to string, matrix is null: ");
            sb.append(fArr == null);
            sb.append(" columns: ");
            sb.append(i);
            Slog.e("ColorDisplayService", sb.toString());
            return "";
        }
        StringBuilder sb2 = new StringBuilder("");
        while (i2 < fArr.length) {
            if (i2 % i == 0) {
                sb2.append("\n      ");
            }
            i2 = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%9.6f", new Object[]{Float.valueOf(fArr[i2])}, sb2, i2, 1);
        }
        return sb2.toString();
    }

    public final void cancelAnimator() {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public abstract int getLevel();

    public abstract float[] getMatrix();

    public long getTransitionDurationMilliseconds() {
        return 3000L;
    }

    public final boolean isActivated() {
        Boolean bool = this.mIsActivated;
        return bool != null && bool.booleanValue();
    }

    public final boolean isActivatedStateNotSet() {
        return this.mIsActivated == null;
    }
}
