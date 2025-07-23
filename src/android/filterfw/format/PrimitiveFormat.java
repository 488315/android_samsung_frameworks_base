package android.filterfw.format;

import android.filterfw.core.MutableFrameFormat;

/* loaded from: classes.dex */
public class PrimitiveFormat {
    public static MutableFrameFormat createByteFormat(int i, int i2) {
        return createFormat(2, i, i2);
    }

    public static MutableFrameFormat createInt16Format(int i, int i2) {
        return createFormat(3, i, i2);
    }

    public static MutableFrameFormat createInt32Format(int i, int i2) {
        return createFormat(4, i, i2);
    }

    public static MutableFrameFormat createFloatFormat(int i, int i2) {
        return createFormat(5, i, i2);
    }

    public static MutableFrameFormat createDoubleFormat(int i, int i2) {
        return createFormat(6, i, i2);
    }

    public static MutableFrameFormat createByteFormat(int i) {
        return createFormat(2, i);
    }

    public static MutableFrameFormat createInt16Format(int i) {
        return createFormat(3, i);
    }

    public static MutableFrameFormat createInt32Format(int i) {
        return createFormat(4, i);
    }

    public static MutableFrameFormat createFloatFormat(int i) {
        return createFormat(5, i);
    }

    public static MutableFrameFormat createDoubleFormat(int i) {
        return createFormat(6, i);
    }

    private static MutableFrameFormat createFormat(int i, int i2, int i3) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(i, i3);
        mutableFrameFormat.setDimensions(i2);
        return mutableFrameFormat;
    }

    private static MutableFrameFormat createFormat(int i, int i2) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(i, i2);
        mutableFrameFormat.setDimensionCount(1);
        return mutableFrameFormat;
    }
}
