package com.android.internal.widget.remotecompose.core.operations.paint;

public interface PaintChanges {
    public static final int CLEAR_ALPHA = 2048;
    public static final int CLEAR_CAP = 64;
    public static final int CLEAR_COLOR = 8;
    public static final int CLEAR_COLOR_FILTER = 4096;
    public static final int CLEAR_IMAGE_FILTER_QUALITY = 512;
    public static final int CLEAR_RADIENT = 1024;
    public static final int CLEAR_SHADER = 256;
    public static final int CLEAR_STROKE_MITER = 32;
    public static final int CLEAR_STROKE_WIDTH = 16;
    public static final int CLEAR_STYLE = 128;
    public static final int CLEAR_TEXT_SIZE = 1;
    public static final int CLEAR_TEXT_STYLE = 32768;
    public static final int VALID_BITS = 8191;

    void clear(long j);

    void setAlpha(float f);

    void setAntiAlias(boolean z);

    void setBlendMode(int i);

    void setColor(int i);

    void setColorFilter(int i, int i2);

    void setFilterBitmap(boolean z);

    void setImageFilterQuality(int i);

    void setLinearGradient(int[] iArr, float[] fArr, float f, float f2, float f3, float f4, int i);

    void setRadialGradient(int[] iArr, float[] fArr, float f, float f2, float f3, int i);

    void setShader(int i);

    void setStrokeCap(int i);

    void setStrokeJoin(int i);

    void setStrokeMiter(float f);

    void setStrokeWidth(float f);

    void setStyle(int i);

    void setSweepGradient(int[] iArr, float[] fArr, float f, float f2);

    void setTextSize(float f);

    void setTypeFace(int i, int i2, boolean z);
}
