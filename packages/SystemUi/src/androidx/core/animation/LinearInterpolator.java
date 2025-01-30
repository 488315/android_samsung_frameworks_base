package androidx.core.animation;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LinearInterpolator implements Interpolator {
    public LinearInterpolator() {
    }

    public LinearInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return f;
    }
}
