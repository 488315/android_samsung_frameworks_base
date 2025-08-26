package com.android.internal.policy;

import android.graphics.Rect;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

/* loaded from: classes5.dex */
public class ClipRectTBAnimation extends ClipRectAnimation {
    private final int mFromTranslateY;
    private float mNormalizedTime;
    private final int mToTranslateY;
    private final Interpolator mTranslateInterpolator;

    public ClipRectTBAnimation(int i, int i2, int i3, int i4, int i5, int i6, Interpolator interpolator) {
        super(0, i, 0, i2, 0, i3, 0, i4);
        this.mFromTranslateY = i5;
        this.mToTranslateY = i6;
        this.mTranslateInterpolator = interpolator;
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long j, Transformation transformation) {
        float startTime;
        long startOffset = getStartOffset();
        long duration = getDuration();
        if (duration != 0) {
            startTime = (j - (getStartTime() + startOffset)) / duration;
        } else {
            startTime = j < getStartTime() ? 0.0f : 1.0f;
        }
        this.mNormalizedTime = startTime;
        return super.getTransformation(j, transformation);
    }

    @Override // android.view.animation.ClipRectAnimation, android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        float interpolation = this.mTranslateInterpolator.getInterpolation(this.mNormalizedTime);
        int i = (int) (this.mFromTranslateY + ((this.mToTranslateY - r1) * interpolation));
        Rect clipRect = transformation.getClipRect();
        transformation.setClipRect(clipRect.left, (this.mFromRect.top - i) + ((int) ((this.mToRect.top - this.mFromRect.top) * f)), clipRect.right, (this.mFromRect.bottom - i) + ((int) ((this.mToRect.bottom - this.mFromRect.bottom) * f)));
    }
}
