package com.google.android.material.progressindicator;

import com.google.android.material.progressindicator.BaseProgressIndicator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class IndeterminateAnimatorDelegate {
    public IndeterminateDrawable drawable;
    public final int[] segmentColors;
    public final float[] segmentPositions;

    public IndeterminateAnimatorDelegate(int i) {
        this.segmentPositions = new float[i * 2];
        this.segmentColors = new int[i];
    }

    public abstract void cancelAnimatorImmediately();

    public abstract void registerAnimatorsCompleteCallback(BaseProgressIndicator.C43293 c43293);

    public abstract void requestCancelAnimatorAfterCurrentCycle();

    public abstract void startAnimator();

    public abstract void unregisterAnimatorsCompleteCallback();
}
