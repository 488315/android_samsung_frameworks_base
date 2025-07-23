package android.hardware.camera2.marshal;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Rational;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class MarshalHelpers {
    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_DOUBLE = 8;
    public static final int SIZEOF_FLOAT = 4;
    public static final int SIZEOF_INT32 = 4;
    public static final int SIZEOF_INT64 = 8;
    public static final int SIZEOF_RATIONAL = 8;

    public static int getPrimitiveTypeSize(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1 || i == 2) {
            return 4;
        }
        if (i == 3 || i == 4 || i == 5) {
            return 8;
        }
        throw new UnsupportedOperationException("Unknown type, can't get size for " + i);
    }

    public static <T> Class<T> checkPrimitiveClass(Class<T> cls) {
        Preconditions.checkNotNull(cls, "klass must not be null");
        if (isPrimitiveClass(cls)) {
            return cls;
        }
        throw new UnsupportedOperationException("Unsupported class '" + cls + "'; expected a metadata primitive class");
    }

    public static boolean isUnwrappedPrimitiveClass(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return cls == Byte.TYPE || cls == Integer.TYPE || cls == Float.TYPE || cls == Long.TYPE || cls == Double.TYPE;
    }

    public static <T> boolean isPrimitiveClass(Class<T> cls) {
        if (cls == null) {
            return false;
        }
        return cls == Byte.TYPE || cls == Byte.class || cls == Integer.TYPE || cls == Integer.class || cls == Float.TYPE || cls == Float.class || cls == Long.TYPE || cls == Long.class || cls == Double.TYPE || cls == Double.class || cls == Rational.class;
    }

    public static <T> Class<T> wrapClassIfPrimitive(Class<T> cls) {
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        return cls == Double.TYPE ? Double.class : cls;
    }

    public static String toStringNativeType(int i) {
        if (i == 0) {
            return "TYPE_BYTE";
        }
        if (i == 1) {
            return "TYPE_INT32";
        }
        if (i == 2) {
            return "TYPE_FLOAT";
        }
        if (i == 3) {
            return "TYPE_INT64";
        }
        if (i == 4) {
            return "TYPE_DOUBLE";
        }
        if (i == 5) {
            return "TYPE_RATIONAL";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static int checkNativeType(int i) {
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            return i;
        }
        throw new UnsupportedOperationException("Unknown nativeType " + i);
    }

    public static Class<?> getPrimitiveTypeClass(int i) {
        if (i == 0) {
            return Byte.TYPE;
        }
        if (i == 1) {
            return Integer.TYPE;
        }
        if (i == 2) {
            return Float.TYPE;
        }
        if (i == 3) {
            return Long.TYPE;
        }
        if (i == 4) {
            return Double.TYPE;
        }
        throw new UnsupportedOperationException("Unknown nativeType " + i);
    }

    public static int checkNativeTypeEquals(int i, int i2) {
        if (i == i2) {
            return i2;
        }
        throw new UnsupportedOperationException(String.format("Expected native type %d, but got %d", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    private MarshalHelpers() {
        throw new AssertionError();
    }
}
