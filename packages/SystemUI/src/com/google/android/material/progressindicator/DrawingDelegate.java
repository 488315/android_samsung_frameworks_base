package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/* loaded from: classes4.dex */
public abstract class DrawingDelegate {
    public final BaseProgressIndicatorSpec spec;

    public class ActiveIndicator {
        public int color;
        public float endFraction;
        public int gapSize;
        public float startFraction;
    }

    public DrawingDelegate(BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.spec = baseProgressIndicatorSpec;
    }

    public abstract void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2);

    public abstract void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2);

    public abstract void fillIndicator(Canvas canvas, Paint paint, ActiveIndicator activeIndicator, int i);

    public abstract void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3);

    public abstract int getPreferredHeight();

    public abstract int getPreferredWidth();
}
