package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DrawingDelegate {
    public DrawableWithAnimatedVisibilityChange drawable;
    public final BaseProgressIndicatorSpec spec;

    public DrawingDelegate(BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.spec = baseProgressIndicatorSpec;
    }

    public abstract void adjustCanvas(Canvas canvas, Rect rect, float f);

    public abstract void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i);

    public abstract void fillTrack(Canvas canvas, Paint paint);

    public abstract int getPreferredHeight();

    public abstract int getPreferredWidth();
}
