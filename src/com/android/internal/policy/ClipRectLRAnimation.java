package com.android.internal.policy;

import android.graphics.Rect;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Transformation;

/* loaded from: classes5.dex */
public class ClipRectLRAnimation extends ClipRectAnimation {
    public ClipRectLRAnimation(int i, int i2, int i3, int i4) {
        super(i, 0, i2, 0, i3, 0, i4, 0);
    }

    @Override // android.view.animation.ClipRectAnimation, android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        Rect clipRect = transformation.getClipRect();
        transformation.setClipRect(this.mFromRect.left + ((int) ((this.mToRect.left - this.mFromRect.left) * f)), clipRect.top, this.mFromRect.right + ((int) ((this.mToRect.right - this.mFromRect.right) * f)), clipRect.bottom);
    }
}
