package com.google.android.material.progressindicator;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class IndeterminateAnimatorDelegate {
    public final List activeIndicators = new ArrayList();
    public IndeterminateDrawable drawable;

    public IndeterminateAnimatorDelegate(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            this.activeIndicators.add(new DrawingDelegate.ActiveIndicator());
        }
    }

    public static float getFractionInRange(int i, int i2, int i3) {
        return (i - i2) / i3;
    }

    public abstract void cancelAnimatorImmediately();

    public abstract void registerAnimatorsCompleteCallback(BaseProgressIndicator.AnonymousClass3 anonymousClass3);

    public abstract void requestCancelAnimatorAfterCurrentCycle();

    public abstract void resetPropertiesForNewStart();

    public abstract void setAnimationFraction(float f);

    public abstract void startAnimator();

    public abstract void unregisterAnimatorsCompleteCallback();
}
