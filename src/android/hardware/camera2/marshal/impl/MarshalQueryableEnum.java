package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalHelpers;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Log;
import java.lang.Enum;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class MarshalQueryableEnum<T extends Enum<T>> implements MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final String TAG = "MarshalQueryableEnum";
    private static final int UINT8_MASK = 255;
    private static final int UINT8_MAX = 255;
    private static final int UINT8_MIN = 0;
    private static final HashMap<Class<? extends Enum>, int[]> sEnumValues = new HashMap<>();

    private class MarshalerEnum extends Marshaler<T> {
        private final Class<T> mClass;

        protected MarshalerEnum(MarshalQueryableEnum marshalQueryableEnum, TypeReference<T> typeReference, int i) {
            super(marshalQueryableEnum, typeReference, i);
            this.mClass = typeReference.getRawType();
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, ByteBuffer byteBuffer) {
            int enumValue = MarshalQueryableEnum.getEnumValue(t);
            if (this.mNativeType == 1) {
                byteBuffer.putInt(enumValue);
            } else {
                if (this.mNativeType != 0) {
                    throw new AssertionError();
                }
                if (enumValue < 0 || enumValue > 255) {
                    throw new UnsupportedOperationException(String.format("Enum value %x too large to fit into unsigned byte", Integer.valueOf(enumValue)));
                }
                byteBuffer.put((byte) enumValue);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(ByteBuffer byteBuffer) {
            int i;
            int i2 = this.mNativeType;
            if (i2 == 0) {
                i = byteBuffer.get() & 255;
            } else if (i2 == 1) {
                i = byteBuffer.getInt();
            } else {
                throw new AssertionError("Unexpected native type; impossible since its not supported");
            }
            return (T) MarshalQueryableEnum.getEnumFromValue(this.mClass, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int i) {
        return new MarshalerEnum(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<T> typeReference, int i) {
        if ((i == 1 || i == 0) && (typeReference.getType() instanceof Class)) {
            Class cls = (Class) typeReference.getType();
            if (cls.isEnum()) {
                try {
                    cls.getDeclaredConstructor(String.class, Integer.TYPE);
                    return true;
                } catch (NoSuchMethodException unused) {
                    Log.e(TAG, "Can't marshal class " + cls + "; no default constructor");
                } catch (SecurityException unused2) {
                    Log.e(TAG, "Can't marshal class " + cls + "; not accessible");
                }
            }
        }
        return false;
    }

    public static <T extends Enum<T>> void registerEnumValues(Class<T> cls, int[] iArr) {
        if (cls.getEnumConstants().length != iArr.length) {
            throw new IllegalArgumentException("Expected values array to be the same size as the enumTypes values " + iArr.length + " for type " + cls);
        }
        sEnumValues.put(cls, iArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends Enum<T>> int getEnumValue(T t) {
        int[] iArr = sEnumValues.get(t.getClass());
        int ordinal = t.ordinal();
        return iArr != null ? iArr[ordinal] : ordinal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends Enum<T>> T getEnumFromValue(Class<T> cls, int i) {
        int i2;
        int[] iArr = sEnumValues.get(cls);
        if (iArr != null) {
            i2 = 0;
            while (true) {
                if (i2 >= iArr.length) {
                    i2 = -1;
                    break;
                }
                if (iArr[i2] == i) {
                    break;
                }
                i2++;
            }
        } else {
            i2 = i;
        }
        T[] enumConstants = cls.getEnumConstants();
        if (i2 < 0 || i2 >= enumConstants.length) {
            throw new IllegalArgumentException(String.format("Argument 'value' (%d) was not a valid enum value for type %s (registered? %b)", Integer.valueOf(i), cls, Boolean.valueOf(iArr != null)));
        }
        return enumConstants[i2];
    }
}
