package com.android.internal.policy;

import android.graphics.Rect;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Transformation;

/* loaded from: classes5.dex */
public class ClipRectLRAnimation extends ClipRectAnimation {
    public ClipRectLRAnimation(int fromL, int fromR, int toL, int toR) {
        super(fromL, 0, fromR, 0, toL, 0, toR, 0);
    }

    @Override // android.view.animation.ClipRectAnimation, android.view.animation.Animation
    protected void applyTransformation(float it, Transformation tr) {
        Rect oldClipRect = tr.getClipRect();
        tr.setClipRect(
                this.mFromRect.left + ((int) ((this.mToRect.left - this.mFromRect.left) * it)),
                oldClipRect.top,
                this.mFromRect.right + ((int) ((this.mToRect.right - this.mFromRect.right) * it)),
                oldClipRect.bottom);
    }
}
