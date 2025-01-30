package com.google.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnsafeUtil {
    public static final long BYTE_ARRAY_BASE_OFFSET;
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    public static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    public static final boolean IS_BIG_ENDIAN;
    public static final MemoryAccessor MEMORY_ACCESSOR;
    public static final Class MEMORY_CLASS;
    public static final Unsafe UNSAFE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.UnsafeUtil$1 */
    public final class C45421 implements PrivilegedExceptionAction {
        @Override // java.security.PrivilegedExceptionAction
        public final Object run() {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (Unsafe.class.isInstance(obj)) {
                    return (Unsafe) Unsafe.class.cast(obj);
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Android32MemoryAccessor extends MemoryAccessor {
        public Android32MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean getBoolean(long j, Object obj) {
            return UnsafeUtil.IS_BIG_ENDIAN ? UnsafeUtil.getByteBigEndian(j, obj) != 0 : UnsafeUtil.getByteLittleEndian(j, obj) != 0;
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final byte getByte(long j, Object obj) {
            return UnsafeUtil.IS_BIG_ENDIAN ? UnsafeUtil.getByteBigEndian(j, obj) : UnsafeUtil.getByteLittleEndian(j, obj);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final double getDouble(long j, Object obj) {
            return Double.longBitsToDouble(this.unsafe.getLong(obj, j));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final float getFloat(long j, Object obj) {
            return Float.intBitsToFloat(this.unsafe.getInt(obj, j));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putBoolean(Object obj, long j, boolean z) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(obj, j, z ? (byte) 1 : (byte) 0);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j, z ? (byte) 1 : (byte) 0);
            }
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putByte(Object obj, long j, byte b) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(obj, j, b);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j, b);
            }
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putDouble(Object obj, long j, double d) {
            this.unsafe.putLong(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putFloat(Object obj, long j, float f) {
            this.unsafe.putInt(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean supportsUnsafeByteBufferOperations() {
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Android64MemoryAccessor extends MemoryAccessor {
        public Android64MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean getBoolean(long j, Object obj) {
            return UnsafeUtil.IS_BIG_ENDIAN ? UnsafeUtil.getByteBigEndian(j, obj) != 0 : UnsafeUtil.getByteLittleEndian(j, obj) != 0;
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final byte getByte(long j, Object obj) {
            return UnsafeUtil.IS_BIG_ENDIAN ? UnsafeUtil.getByteBigEndian(j, obj) : UnsafeUtil.getByteLittleEndian(j, obj);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final double getDouble(long j, Object obj) {
            return Double.longBitsToDouble(this.unsafe.getLong(obj, j));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final float getFloat(long j, Object obj) {
            return Float.intBitsToFloat(this.unsafe.getInt(obj, j));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putBoolean(Object obj, long j, boolean z) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(obj, j, z ? (byte) 1 : (byte) 0);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j, z ? (byte) 1 : (byte) 0);
            }
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putByte(Object obj, long j, byte b) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(obj, j, b);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j, b);
            }
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putDouble(Object obj, long j, double d) {
            this.unsafe.putLong(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putFloat(Object obj, long j, float f) {
            this.unsafe.putInt(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean supportsUnsafeByteBufferOperations() {
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class JvmMemoryAccessor extends MemoryAccessor {
        public JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean getBoolean(long j, Object obj) {
            return this.unsafe.getBoolean(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final byte getByte(long j, Object obj) {
            return this.unsafe.getByte(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final double getDouble(long j, Object obj) {
            return this.unsafe.getDouble(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final float getFloat(long j, Object obj) {
            return this.unsafe.getFloat(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putBoolean(Object obj, long j, boolean z) {
            this.unsafe.putBoolean(obj, j, z);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putByte(Object obj, long j, byte b) {
            this.unsafe.putByte(obj, j, b);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putDouble(Object obj, long j, double d) {
            this.unsafe.putDouble(obj, j, d);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final void putFloat(Object obj, long j, float f) {
            this.unsafe.putFloat(obj, j, f);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean supportsUnsafeArrayOperations() {
            if (!super.supportsUnsafeArrayOperations()) {
                return false;
            }
            try {
                Class<?> cls = this.unsafe.getClass();
                Class<?> cls2 = Long.TYPE;
                cls.getMethod("getByte", Object.class, cls2);
                cls.getMethod("putByte", Object.class, cls2, Byte.TYPE);
                cls.getMethod("getBoolean", Object.class, cls2);
                cls.getMethod("putBoolean", Object.class, cls2, Boolean.TYPE);
                cls.getMethod("getFloat", Object.class, cls2);
                cls.getMethod("putFloat", Object.class, cls2, Float.TYPE);
                cls.getMethod("getDouble", Object.class, cls2);
                cls.getMethod("putDouble", Object.class, cls2, Double.TYPE);
                return true;
            } catch (Throwable th) {
                UnsafeUtil.access$000(th);
                return false;
            }
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public final boolean supportsUnsafeByteBufferOperations() {
            if (!super.supportsUnsafeByteBufferOperations()) {
                return false;
            }
            try {
                Class<?> cls = this.unsafe.getClass();
                Class<?> cls2 = Long.TYPE;
                cls.getMethod("getByte", cls2);
                cls.getMethod("putByte", cls2, Byte.TYPE);
                cls.getMethod("getInt", cls2);
                cls.getMethod("putInt", cls2, Integer.TYPE);
                cls.getMethod("getLong", cls2);
                cls.getMethod("putLong", cls2, cls2);
                cls.getMethod("copyMemory", cls2, cls2, cls2);
                cls.getMethod("copyMemory", Object.class, cls2, Object.class, cls2, cls2);
                return true;
            } catch (Throwable th) {
                UnsafeUtil.access$000(th);
                return false;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MemoryAccessor {
        public final Unsafe unsafe;

        public MemoryAccessor(Unsafe unsafe) {
            this.unsafe = unsafe;
        }

        public abstract boolean getBoolean(long j, Object obj);

        public abstract byte getByte(long j, Object obj);

        public abstract double getDouble(long j, Object obj);

        public abstract float getFloat(long j, Object obj);

        public abstract void putBoolean(Object obj, long j, boolean z);

        public abstract void putByte(Object obj, long j, byte b);

        public abstract void putDouble(Object obj, long j, double d);

        public abstract void putFloat(Object obj, long j, float f);

        public boolean supportsUnsafeArrayOperations() {
            Unsafe unsafe = this.unsafe;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("arrayBaseOffset", Class.class);
                cls.getMethod("arrayIndexScale", Class.class);
                Class<?> cls2 = Long.TYPE;
                cls.getMethod("getInt", Object.class, cls2);
                cls.getMethod("putInt", Object.class, cls2, Integer.TYPE);
                cls.getMethod("getLong", Object.class, cls2);
                cls.getMethod("putLong", Object.class, cls2, cls2);
                cls.getMethod("getObject", Object.class, cls2);
                cls.getMethod("putObject", Object.class, cls2, Object.class);
                return true;
            } catch (Throwable th) {
                UnsafeUtil.access$000(th);
                return false;
            }
        }

        public boolean supportsUnsafeByteBufferOperations() {
            Unsafe unsafe = this.unsafe;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("getLong", Object.class, Long.TYPE);
                return UnsafeUtil.bufferAddressField() != null;
            } catch (Throwable th) {
                UnsafeUtil.access$000(th);
                return false;
            }
        }
    }

    static {
        Unsafe unsafe;
        MemoryAccessor memoryAccessor = null;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new C45421());
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
        MEMORY_CLASS = Android.MEMORY_CLASS;
        boolean determineAndroidSupportByAddressSize = determineAndroidSupportByAddressSize(Long.TYPE);
        boolean determineAndroidSupportByAddressSize2 = determineAndroidSupportByAddressSize(Integer.TYPE);
        if (unsafe != null) {
            if (!Android.isOnAndroidDevice()) {
                memoryAccessor = new JvmMemoryAccessor(unsafe);
            } else if (determineAndroidSupportByAddressSize) {
                memoryAccessor = new Android64MemoryAccessor(unsafe);
            } else if (determineAndroidSupportByAddressSize2) {
                memoryAccessor = new Android32MemoryAccessor(unsafe);
            }
        }
        MEMORY_ACCESSOR = memoryAccessor;
        HAS_UNSAFE_BYTEBUFFER_OPERATIONS = memoryAccessor == null ? false : memoryAccessor.supportsUnsafeByteBufferOperations();
        HAS_UNSAFE_ARRAY_OPERATIONS = memoryAccessor == null ? false : memoryAccessor.supportsUnsafeArrayOperations();
        BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
        arrayBaseOffset(boolean[].class);
        arrayIndexScale(boolean[].class);
        arrayBaseOffset(int[].class);
        arrayIndexScale(int[].class);
        arrayBaseOffset(long[].class);
        arrayIndexScale(long[].class);
        arrayBaseOffset(float[].class);
        arrayIndexScale(float[].class);
        arrayBaseOffset(double[].class);
        arrayIndexScale(double[].class);
        arrayBaseOffset(Object[].class);
        arrayIndexScale(Object[].class);
        Field bufferAddressField = bufferAddressField();
        if (bufferAddressField != null && memoryAccessor != null) {
            memoryAccessor.unsafe.objectFieldOffset(bufferAddressField);
        }
        IS_BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private UnsafeUtil() {
    }

    public static void access$000(Throwable th) {
        Logger.getLogger(UnsafeUtil.class.getName()).log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
    }

    public static Object allocateInstance(Class cls) {
        try {
            return UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int arrayBaseOffset(Class cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.unsafe.arrayBaseOffset(cls);
        }
        return -1;
    }

    public static void arrayIndexScale(Class cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            MEMORY_ACCESSOR.unsafe.arrayIndexScale(cls);
        }
    }

    public static Field bufferAddressField() {
        Field field;
        Field field2;
        if (Android.isOnAndroidDevice()) {
            try {
                field2 = Buffer.class.getDeclaredField("effectiveDirectAddress");
            } catch (Throwable unused) {
                field2 = null;
            }
            if (field2 != null) {
                return field2;
            }
        }
        try {
            field = Buffer.class.getDeclaredField("address");
        } catch (Throwable unused2) {
            field = null;
        }
        if (field == null || field.getType() != Long.TYPE) {
            return null;
        }
        return field;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean determineAndroidSupportByAddressSize(Class cls) {
        if (!Android.isOnAndroidDevice()) {
            return false;
        }
        try {
            Class cls2 = MEMORY_CLASS;
            Class cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class cls4 = Integer.TYPE;
            cls2.getMethod("pokeInt", cls, cls4, cls3);
            cls2.getMethod("peekInt", cls, cls3);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls4, cls4);
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean getBoolean(long j, Object obj) {
        return MEMORY_ACCESSOR.getBoolean(j, obj);
    }

    public static byte getByte(byte[] bArr, long j) {
        return MEMORY_ACCESSOR.getByte(BYTE_ARRAY_BASE_OFFSET + j, bArr);
    }

    public static byte getByteBigEndian(long j, Object obj) {
        return (byte) ((getInt((-4) & j, obj) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    public static byte getByteLittleEndian(long j, Object obj) {
        return (byte) ((getInt((-4) & j, obj) >>> ((int) ((j & 3) << 3))) & 255);
    }

    public static double getDouble(long j, Object obj) {
        return MEMORY_ACCESSOR.getDouble(j, obj);
    }

    public static float getFloat(long j, Object obj) {
        return MEMORY_ACCESSOR.getFloat(j, obj);
    }

    public static int getInt(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getInt(obj, j);
    }

    public static long getLong(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getLong(obj, j);
    }

    public static Object getObject(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getObject(obj, j);
    }

    public static long objectFieldOffset(Field field) {
        return MEMORY_ACCESSOR.unsafe.objectFieldOffset(field);
    }

    public static void putByte(byte[] bArr, long j, byte b) {
        MEMORY_ACCESSOR.putByte(bArr, BYTE_ARRAY_BASE_OFFSET + j, b);
    }

    public static void putByteBigEndian(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = getInt(j2, obj);
        int i2 = ((~((int) j)) & 3) << 3;
        putInt(((255 & b) << i2) | (i & (~(255 << i2))), j2, obj);
    }

    public static void putByteLittleEndian(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        putInt(((255 & b) << i) | (getInt(j2, obj) & (~(255 << i))), j2, obj);
    }

    public static void putInt(int i, long j, Object obj) {
        MEMORY_ACCESSOR.unsafe.putInt(obj, j, i);
    }

    public static void putLong(Object obj, long j, long j2) {
        MEMORY_ACCESSOR.unsafe.putLong(obj, j, j2);
    }

    public static void putObject(long j, Object obj, Object obj2) {
        MEMORY_ACCESSOR.unsafe.putObject(obj, j, obj2);
    }
}
