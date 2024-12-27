package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;

/* loaded from: classes5.dex */
public abstract class PaintContext {
    protected RemoteContext mContext;

    public abstract void applyPaint(PaintBundle paintBundle);

    public abstract void clipPath(int i, int i2);

    public abstract void clipRect(float f, float f2, float f3, float f4);

    public abstract void drawArc(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void drawBitmap(int i, float f, float f2, float f3, float f4);

    public abstract void drawBitmap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    public abstract void drawCircle(float f, float f2, float f3);

    public abstract void drawLine(float f, float f2, float f3, float f4);

    public abstract void drawOval(float f, float f2, float f3, float f4);

    public abstract void drawPath(int i, float f, float f2);

    public abstract void drawRect(float f, float f2, float f3, float f4);

    public abstract void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void drawTextOnPath(int i, int i2, float f, float f2);

    public abstract void drawTextRun(int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z);

    public abstract void drawTweenPath(int i, int i2, float f, float f2, float f3);

    public abstract void getTextBounds(int i, int i2, int i3, boolean z, float[] fArr);

    public abstract void matrixRestore();

    public abstract void matrixRotate(float f, float f2, float f3);

    public abstract void matrixSave();

    public abstract void matrixScale(float f, float f2, float f3, float f4);

    public abstract void matrixSkew(float f, float f2);

    public abstract void matrixTranslate(float f, float f2);

    public abstract void reset();

    public abstract void scale(float f, float f2);

    public abstract void translate(float f, float f2);

    public PaintContext(RemoteContext context) {
        this.mContext = context;
    }

    public void setContext(RemoteContext context) {
        this.mContext = context;
    }
}
