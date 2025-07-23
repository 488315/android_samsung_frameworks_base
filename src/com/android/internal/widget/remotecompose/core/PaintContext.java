package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.Platform;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import java.util.HashMap;

/* loaded from: classes6.dex */
public abstract class PaintContext {
    public static final int TEXT_COMPLEX = 8;
    public static final int TEXT_MEASURE_FONT_HEIGHT = 2;
    public static final int TEXT_MEASURE_MONOSPACE_WIDTH = 1;
    public static final int TEXT_MEASURE_SPACES = 4;
    protected RemoteContext mContext;
    private boolean mNeedsRepaint = false;

    public abstract void applyPaint(PaintBundle paintBundle);

    public abstract void clipPath(int i, int i2);

    public abstract void clipRect(float f, float f2, float f3, float f4);

    public abstract void combinePath(int i, int i2, int i3, byte b);

    public abstract void drawArc(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void drawBitmap(int i, float f, float f2, float f3, float f4);

    public abstract void drawBitmap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    public abstract void drawCircle(float f, float f2, float f3);

    public abstract void drawComplexText(Platform.ComputedTextLayout computedTextLayout);

    public abstract void drawLine(float f, float f2, float f3, float f4);

    public abstract void drawOval(float f, float f2, float f3, float f4);

    public abstract void drawPath(int i, float f, float f2);

    public abstract void drawRect(float f, float f2, float f3, float f4);

    public abstract void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void drawSector(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void drawTextOnPath(int i, int i2, float f, float f2);

    public abstract void drawTextRun(int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z);

    public abstract void drawTweenPath(int i, int i2, float f, float f2, float f3);

    public abstract void endGraphicsLayer();

    public abstract String getText(int i);

    public abstract void getTextBounds(int i, int i2, int i3, int i4, float[] fArr);

    public abstract Platform.ComputedTextLayout layoutComplexText(int i, int i2, int i3, int i4, int i5, int i6, float f, int i7);

    public abstract void matrixRestore();

    public abstract void matrixRotate(float f, float f2, float f3);

    public abstract void matrixSave();

    public abstract void matrixScale(float f, float f2, float f3, float f4);

    public abstract void matrixSkew(float f, float f2);

    public abstract void matrixTranslate(float f, float f2);

    public abstract void replacePaint(PaintBundle paintBundle);

    public abstract void reset();

    public abstract void restorePaint();

    public abstract void roundedClipRect(float f, float f2, float f3, float f4, float f5, float f6);

    public abstract void savePaint();

    public abstract void scale(float f, float f2);

    public abstract void setGraphicsLayer(HashMap<Integer, Object> hashMap);

    public abstract void startGraphicsLayer(int i, int i2);

    public abstract void translate(float f, float f2);

    public abstract void tweenPath(int i, int i2, int i3, float f);

    public RemoteContext getContext() {
        return this.mContext;
    }

    public boolean doesNeedsRepaint() {
        return this.mNeedsRepaint;
    }

    public void clearNeedsRepaint() {
        this.mNeedsRepaint = false;
    }

    public PaintContext(RemoteContext remoteContext) {
        this.mContext = remoteContext;
    }

    public void setContext(RemoteContext remoteContext) {
        this.mContext = remoteContext;
    }

    public void save() {
        matrixSave();
    }

    public void restore() {
        matrixRestore();
    }

    public void saveLayer(float f, float f2, float f3, float f4) {
        matrixSave();
    }

    public boolean isDebug() {
        return this.mContext.isDebug();
    }

    public boolean isAnimationEnabled() {
        return this.mContext.isAnimationEnabled();
    }

    public void log(String str) {
        System.out.println("[LOG] " + str);
    }

    public void needsRepaint() {
        this.mNeedsRepaint = true;
    }

    public boolean isVisualDebug() {
        return this.mContext.isVisualDebug();
    }

    public boolean supportsVersion(int i, int i2, int i3) {
        return this.mContext.supportsVersion(i, i2, i3);
    }
}
