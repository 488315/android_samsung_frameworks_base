package com.android.internal.widget.remotecompose.core.operations.paint;

/* loaded from: classes5.dex */
public class PaintChangeAdapter implements PaintChanges {
    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setTextSize(float size) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setTypeFace(int fontType, int weight, boolean italic) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setStrokeWidth(float width) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setColor(int color) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setStrokeCap(int cap) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setStyle(int style) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setShader(int shader) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setImageFilterQuality(int quality) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setAlpha(float a) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setStrokeMiter(float miter) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setStrokeJoin(int join) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setFilterBitmap(boolean filter) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setBlendMode(int blendmode) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setAntiAlias(boolean aa) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void clear(long mask) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setLinearGradient(
            int[] colorsArray,
            float[] stopsArray,
            float startX,
            float startY,
            float endX,
            float endY,
            int tileMode) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setRadialGradient(
            int[] colorsArray,
            float[] stopsArray,
            float centerX,
            float centerY,
            float radius,
            int tileMode) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setSweepGradient(
            int[] colorsArray, float[] stopsArray, float centerX, float centerY) {}

    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
    public void setColorFilter(int color, int mode) {}
}
