package com.android.internal.widget.remotecompose.core.operations.paint;

/* loaded from: classes6.dex */
class Painter {
    PaintBundle mPaint;

    Painter() {
    }

    public PaintBundle commit() {
        return this.mPaint;
    }

    public Painter setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        return this;
    }

    public Painter setColor(int i) {
        this.mPaint.setColor(i);
        return this;
    }

    public Painter setColorId(int i) {
        this.mPaint.setColorId(i);
        return this;
    }

    public Painter setStrokeJoin(int i) {
        this.mPaint.setStrokeJoin(i);
        return this;
    }

    public Painter setStrokeWidth(float f) {
        this.mPaint.setStrokeWidth(f);
        return this;
    }

    public Painter setStyle(int i) {
        this.mPaint.setStyle(i);
        return this;
    }

    public Painter setStrokeCap(int i) {
        this.mPaint.setStrokeCap(i);
        return this;
    }

    public Painter setStrokeMiter(float f) {
        this.mPaint.setStrokeMiter(f);
        return this;
    }

    public Painter setAlpha(float f) {
        PaintBundle paintBundle = this.mPaint;
        if (f > 2.0f) {
            f /= 255.0f;
        }
        paintBundle.setAlpha(f);
        return this;
    }

    public Painter setPorterDuffColorFilter(int i, int i2) {
        this.mPaint.setColorFilter(i, i2);
        return this;
    }

    public Painter setLinearGradient(float f, float f2, float f3, float f4, int[] iArr, float[] fArr, int i) {
        this.mPaint.setLinearGradient(iArr, 0, fArr, f, f2, f3, f4, i);
        return this;
    }

    public Painter setRadialGradient(float f, float f2, float f3, int[] iArr, float[] fArr, int i) {
        this.mPaint.setRadialGradient(iArr, 0, fArr, f, f2, f3, i);
        return this;
    }

    public Painter setSweepGradient(float f, float f2, int[] iArr, float[] fArr) {
        this.mPaint.setSweepGradient(iArr, 0, fArr, f, f2);
        return this;
    }

    public Painter setTextSize(float f) {
        this.mPaint.setTextSize(f);
        return this;
    }

    public Painter setTypeface(int i, int i2, boolean z) {
        this.mPaint.setTextStyle(i, i2, z);
        return this;
    }

    public Painter setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        return this;
    }

    public Painter setShader(int i) {
        this.mPaint.setShader(i);
        return this;
    }
}
