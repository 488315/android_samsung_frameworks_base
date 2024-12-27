package com.android.systemui.volume;

import android.animation.TimeInterpolator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SystemUIInterpolators$LogAccelerateInterpolator implements TimeInterpolator {
    public final int mBase;
    public final int mDrift;
    public final float mLogScale;

    public SystemUIInterpolators$LogAccelerateInterpolator() {
        this(100, 0);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return 1.0f - (((this.mDrift * (1.0f - f)) + (((float) (-Math.pow(this.mBase, -r8))) + 1.0f)) * this.mLogScale);
    }

    private SystemUIInterpolators$LogAccelerateInterpolator(int i, int i2) {
        this.mBase = i;
        this.mDrift = i2;
        this.mLogScale = 1.0f / ((i2 * 1.0f) + (((float) (-Math.pow(i, -1.0f))) + 1.0f));
    }
}
