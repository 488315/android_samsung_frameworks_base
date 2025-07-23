package android.filterfw.format;

import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.NativeBuffer;

/* loaded from: classes.dex */
public class ObjectFormat {
    public static MutableFrameFormat fromClass(Class cls, int i, int i2) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(8, i2);
        mutableFrameFormat.setObjectClass(getBoxedClass(cls));
        if (i != 0) {
            mutableFrameFormat.setDimensions(i);
        }
        mutableFrameFormat.setBytesPerSample(bytesPerSampleForClass(cls, i2));
        return mutableFrameFormat;
    }

    public static MutableFrameFormat fromClass(Class cls, int i) {
        return fromClass(cls, 0, i);
    }

    public static MutableFrameFormat fromObject(Object obj, int i) {
        if (obj == null) {
            return new MutableFrameFormat(8, i);
        }
        return fromClass(obj.getClass(), 0, i);
    }

    public static MutableFrameFormat fromObject(Object obj, int i, int i2) {
        if (obj == null) {
            return new MutableFrameFormat(8, i2);
        }
        return fromClass(obj.getClass(), i, i2);
    }

    private static int bytesPerSampleForClass(Class cls, int i) {
        if (i != 2) {
            return 1;
        }
        if (!NativeBuffer.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Native object-based formats must be of a NativeBuffer subclass! (Received class: " + cls + ").");
        }
        try {
            return ((NativeBuffer) cls.newInstance()).getElementSize();
        } catch (Exception unused) {
            throw new RuntimeException("Could not determine the size of an element in a native object-based frame of type " + cls + "! Perhaps it is missing a default constructor?");
        }
    }

    private static Class getBoxedClass(Class cls) {
        if (!cls.isPrimitive()) {
            return cls;
        }
        if (cls == Boolean.TYPE) {
            return Boolean.class;
        }
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        if (cls == Character.TYPE) {
            return Character.class;
        }
        if (cls == Short.TYPE) {
            return Short.class;
        }
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        throw new IllegalArgumentException("Unknown primitive type: " + cls.getSimpleName() + "!");
    }
}
