package android.os;

import android.app.AppOpsManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.wifi.WifiMigration;
import android.os.Parcelable;
import android.sec.enterprise.proxy.EnterpriseProxyConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Size;
import android.util.SizeF;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import libcore.util.SneakyThrow;

/* loaded from: classes3.dex */
public final class Parcel {
    private static final int ARRAY_ALLOCATION_LIMIT = 1000000;
    private static final boolean DEBUG_ARRAY_MAP = false;
    private static final boolean DEBUG_RECYCLE = false;
    private static final int EX_BAD_PARCELABLE = -2;
    public static final int EX_HAS_NOTED_APPOPS_REPLY_HEADER = -127;
    private static final int EX_HAS_STRICTMODE_REPLY_HEADER = -128;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_SERVICE_SPECIFIC = -8;
    private static final int EX_TRANSACTION_FAILED = -129;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    public static final int FLAG_IS_REPLY_FROM_BLOCKING_ALLOWED_OBJECT = 1;
    public static final int FLAG_PROPAGATE_ALLOW_BLOCKING = 2;
    private static final int OK = 0;
    private static final int POOL_SIZE = 32;
    private static final int SIZE_BOOLEAN = 4;
    private static final int SIZE_BYTE = 1;
    private static final int SIZE_CHAR = 2;
    private static final int SIZE_COMPLEX_TYPE = 1;
    private static final int SIZE_DOUBLE = 8;
    private static final int SIZE_FLOAT = 4;
    private static final int SIZE_INT = 4;
    private static final int SIZE_LONG = 8;
    private static final int SIZE_SHORT = 2;
    private static final String TAG = "Parcel";
    private static final int VAL_BOOLEAN = 9;
    private static final int VAL_BOOLEANARRAY = 23;
    private static final int VAL_BUNDLE = 3;
    private static final int VAL_BYTE = 20;
    private static final int VAL_BYTEARRAY = 13;
    private static final int VAL_CHAR = 29;
    private static final int VAL_CHARARRAY = 31;
    private static final int VAL_CHARSEQUENCE = 10;
    private static final int VAL_CHARSEQUENCEARRAY = 24;
    private static final int VAL_DOUBLE = 8;
    private static final int VAL_DOUBLEARRAY = 28;
    private static final int VAL_FLOAT = 7;
    private static final int VAL_FLOATARRAY = 32;
    private static final int VAL_IBINDER = 15;
    private static final int VAL_INTARRAY = 18;
    private static final int VAL_INTEGER = 1;
    private static final int VAL_LIST = 11;
    private static final int VAL_LONG = 6;
    private static final int VAL_LONGARRAY = 19;
    private static final int VAL_MAP = 2;
    private static final int VAL_NULL = -1;
    private static final int VAL_OBJECTARRAY = 17;
    private static final int VAL_PARCELABLE = 4;
    private static final int VAL_PARCELABLEARRAY = 16;
    private static final int VAL_PERSISTABLEBUNDLE = 25;
    private static final int VAL_SERIALIZABLE = 21;
    private static final int VAL_SHORT = 5;
    private static final int VAL_SHORTARRAY = 30;
    private static final int VAL_SIZE = 26;
    private static final int VAL_SIZEF = 27;
    private static final int VAL_SPARSEARRAY = 12;
    private static final int VAL_SPARSEBOOLEANARRAY = 22;
    private static final int VAL_STRING = 0;
    private static final int VAL_STRINGARRAY = 14;
    private static final int WRITE_EXCEPTION_STACK_TRACE_THRESHOLD_MS = 1000;
    private static Parcel sHolderPool;
    private static int sHolderPoolSize;
    private static volatile long sLastWriteExceptionStackTrace;
    private static Parcel sOwnedPool;
    private static int sOwnedPoolSize;
    private static boolean sParcelExceptionStackTrace;
    private ArrayMap<Class, Object> mClassCookies;
    private int mFlags;
    private long mNativePtr;
    private long mNativeSize;
    private boolean mOwnsNativeParcelObject;
    private Parcel mPoolNext;
    private SparseArray<Parcelable> mReadSquashableParcelables;
    private RuntimeException mStack;
    private ArrayMap<Parcelable, Integer> mWrittenSquashableParcelables;
    private static final Object sPoolSync = new Object();
    public static final Parcelable.Creator<String> STRING_CREATOR = new Parcelable.Creator<String>() { // from class: android.os.Parcel.1
        @Override // android.os.Parcelable.Creator
        public String createFromParcel(Parcel parcel) {
            return parcel.readString();
        }

        @Override // android.os.Parcelable.Creator
        public String[] newArray(int i) {
            return new String[i];
        }
    };
    private static final HashMap<ClassLoader, HashMap<String, Parcelable.Creator<?>>> mCreators = new HashMap<>();
    private static final HashMap<ClassLoader, HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>>> sPairedCreators = new HashMap<>();
    private boolean mRecycled = false;
    private String interfaceName = null;
    private ReadWriteHelper mReadWriteHelper = ReadWriteHelper.DEFAULT;
    private boolean mAllowSquashing = false;

    public interface ClassLoaderProvider {
        ClassLoader getClassLoader();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParcelFlags {
    }

    public interface SquashReadHelper<T> {
        T readRawParceled(Parcel parcel);
    }

    public static native long getGlobalAllocCount();

    public static native long getGlobalAllocSize();

    private boolean isLengthPrefixed(int i) {
        return i == 2 || i == 4 || i == 21 || i == 11 || i == 12 || i == 16 || i == 17;
    }

    private static native void nativeAppendFrom(long j, long j2, int i, int i2);

    private static native int nativeCompareData(long j, long j2);

    private static native boolean nativeCompareDataInRange(long j, int i, long j2, int i2, int i3);

    private static native long nativeCreate();

    private static native byte[] nativeCreateByteArray(long j);

    @CriticalNative
    private static native int nativeDataAvail(long j);

    @CriticalNative
    private static native int nativeDataCapacity(long j);

    @CriticalNative
    private static native int nativeDataPosition(long j);

    @CriticalNative
    private static native int nativeDataSize(long j);

    private static native void nativeDestroy(long j);

    private static native void nativeEnforceInterface(long j, String str);

    private static native void nativeFreeBuffer(long j);

    @CriticalNative
    private static native long nativeGetOpenAshmemSize(long j);

    private static native boolean nativeHasBinders(long j);

    private static native boolean nativeHasBindersInRange(long j, int i, int i2);

    @CriticalNative
    private static native boolean nativeHasFileDescriptors(long j);

    private static native boolean nativeHasFileDescriptorsInRange(long j, int i, int i2);

    @CriticalNative
    private static native boolean nativeIsForRpc(long j);

    @FastNative
    private static native void nativeMarkForBinder(long j, IBinder iBinder);

    @CriticalNative
    private static native void nativeMarkSensitive(long j);

    private static native byte[] nativeMarshall(long j);

    private static native int nativeMarshallArray(long j, byte[] bArr, int i, int i2);

    private static native int nativeMarshallBuffer(long j, ByteBuffer byteBuffer, int i, int i2);

    @CriticalNative
    private static native boolean nativePushAllowFds(long j, boolean z);

    private static native byte[] nativeReadBlob(long j);

    private static native boolean nativeReadByteArray(long j, byte[] bArr, int i);

    @CriticalNative
    private static native int nativeReadCallingWorkSourceUid(long j);

    @CriticalNative
    private static native double nativeReadDouble(long j);

    @FastNative
    private static native FileDescriptor nativeReadFileDescriptor(long j);

    @CriticalNative
    private static native float nativeReadFloat(long j);

    @CriticalNative
    private static native int nativeReadInt(long j);

    @CriticalNative
    private static native long nativeReadLong(long j);

    @FastNative
    private static native String nativeReadString16(long j);

    @FastNative
    private static native String nativeReadString8(long j);

    @FastNative
    private static native IBinder nativeReadStrongBinder(long j);

    @CriticalNative
    private static native boolean nativeReplaceCallingWorkSourceUid(long j, int i);

    @CriticalNative
    private static native void nativeRestoreAllowFds(long j, boolean z);

    @FastNative
    private static native void nativeSetDataCapacity(long j, int i);

    @CriticalNative
    private static native void nativeSetDataPosition(long j, int i);

    @FastNative
    private static native void nativeSetDataSize(long j, int i);

    private static native void nativeSignalExceptionForError(int i);

    private static native void nativeUnmarshall(long j, byte[] bArr, int i, int i2);

    private static native void nativeUnmarshallBuffer(long j, ByteBuffer byteBuffer, int i, int i2);

    private static native void nativeWriteBlob(long j, byte[] bArr, int i, int i2);

    private static native void nativeWriteByteArray(long j, byte[] bArr, int i, int i2);

    @CriticalNative
    private static native int nativeWriteDouble(long j, double d);

    @FastNative
    private static native void nativeWriteFileDescriptor(long j, FileDescriptor fileDescriptor);

    @CriticalNative
    private static native int nativeWriteFloat(long j, float f);

    @CriticalNative
    private static native int nativeWriteInt(long j, int i);

    private static native void nativeWriteInterfaceToken(long j, String str);

    @CriticalNative
    private static native int nativeWriteLong(long j, long j2);

    @FastNative
    private static native void nativeWriteString16(long j, String str);

    @FastNative
    private static native void nativeWriteString8(long j, String str);

    @FastNative
    private static native void nativeWriteStrongBinder(long j, IBinder iBinder);

    public static class ReadWriteHelper {
        public static final ReadWriteHelper DEFAULT = new ReadWriteHelper();

        public void writeString8(Parcel parcel, String str) {
            parcel.writeString8NoHelper(str);
        }

        public void writeString16(Parcel parcel, String str) {
            parcel.writeString16NoHelper(str);
        }

        public String readString8(Parcel parcel) {
            return parcel.readString8NoHelper();
        }

        public String readString16(Parcel parcel) {
            return parcel.readString16NoHelper();
        }
    }

    public static Parcel obtain() {
        Parcel parcel;
        synchronized (sPoolSync) {
            parcel = sOwnedPool;
            if (parcel != null) {
                sOwnedPool = parcel.mPoolNext;
                parcel.mPoolNext = null;
                sOwnedPoolSize--;
            } else {
                parcel = null;
            }
        }
        if (parcel == null) {
            parcel = new Parcel(0L);
        } else {
            parcel.mRecycled = false;
            parcel.mReadWriteHelper = ReadWriteHelper.DEFAULT;
        }
        if (parcel.mNativePtr == 0) {
            Log.e(TAG, "Obtained Parcel object has null native pointer. Invalid state.");
        }
        return parcel;
    }

    public static Parcel obtain(IBinder iBinder) {
        Parcel obtain = obtain();
        obtain.markForBinder(iBinder);
        return obtain;
    }

    public final void recycle() {
        if (this.mRecycled) {
            String str = "Recycle called on unowned Parcel. (recycle twice?) Here: " + Log.getStackTraceString(new Throwable()) + " Original recycle call (if DEBUG_RECYCLE): ";
            Log.wtf(TAG, str, this.mStack);
            throw new IllegalStateException(str, this.mStack);
        }
        this.mRecycled = true;
        this.mClassCookies = null;
        freeBuffer();
        if (this.mOwnsNativeParcelObject) {
            synchronized (sPoolSync) {
                int i = sOwnedPoolSize;
                if (i < 32) {
                    this.mPoolNext = sOwnedPool;
                    sOwnedPool = this;
                    sOwnedPoolSize = i + 1;
                }
            }
            return;
        }
        this.mNativePtr = 0L;
        synchronized (sPoolSync) {
            int i2 = sHolderPoolSize;
            if (i2 < 32) {
                this.mPoolNext = sHolderPool;
                sHolderPool = this;
                sHolderPoolSize = i2 + 1;
            }
        }
    }

    public void setReadWriteHelper(ReadWriteHelper readWriteHelper) {
        if (readWriteHelper == null) {
            readWriteHelper = ReadWriteHelper.DEFAULT;
        }
        this.mReadWriteHelper = readWriteHelper;
    }

    public boolean hasReadWriteHelper() {
        ReadWriteHelper readWriteHelper = this.mReadWriteHelper;
        return (readWriteHelper == null || readWriteHelper == ReadWriteHelper.DEFAULT) ? false : true;
    }

    public final void markSensitive() {
        nativeMarkSensitive(this.mNativePtr);
    }

    private void markForBinder(IBinder iBinder) {
        nativeMarkForBinder(this.mNativePtr, iBinder);
    }

    public final boolean isForRpc() {
        return nativeIsForRpc(this.mNativePtr);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public void addFlags(int i) {
        this.mFlags = i | this.mFlags;
    }

    private boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public void setPropagateAllowBlocking() {
        addFlags(2);
    }

    public int dataSize() {
        return nativeDataSize(this.mNativePtr);
    }

    public final int dataAvail() {
        return nativeDataAvail(this.mNativePtr);
    }

    public final int dataPosition() {
        return nativeDataPosition(this.mNativePtr);
    }

    public final int dataCapacity() {
        return nativeDataCapacity(this.mNativePtr);
    }

    public final void setDataSize(int i) {
        nativeSetDataSize(this.mNativePtr, i);
    }

    public final void setDataPosition(int i) {
        nativeSetDataPosition(this.mNativePtr, i);
    }

    public final void setDataCapacity(int i) {
        nativeSetDataCapacity(this.mNativePtr, i);
    }

    public final boolean pushAllowFds(boolean z) {
        return nativePushAllowFds(this.mNativePtr, z);
    }

    public final void restoreAllowFds(boolean z) {
        nativeRestoreAllowFds(this.mNativePtr, z);
    }

    public final byte[] marshall() {
        return nativeMarshall(this.mNativePtr);
    }

    public final void marshall(ByteBuffer byteBuffer) {
        int nativeMarshallArray;
        byteBuffer.getClass();
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int position = byteBuffer.position();
        int remaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            nativeMarshallArray = nativeMarshallBuffer(this.mNativePtr, byteBuffer, position, remaining);
        } else if (byteBuffer.hasArray()) {
            nativeMarshallArray = nativeMarshallArray(this.mNativePtr, byteBuffer.array(), byteBuffer.arrayOffset() + position, remaining);
        } else {
            throw new IllegalArgumentException();
        }
        byteBuffer.position(position + nativeMarshallArray);
    }

    public final void unmarshall(byte[] bArr, int i, int i2) {
        nativeUnmarshall(this.mNativePtr, bArr, i, i2);
    }

    public final void unmarshall(ByteBuffer byteBuffer) {
        byteBuffer.getClass();
        int position = byteBuffer.position();
        int remaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            nativeUnmarshallBuffer(this.mNativePtr, byteBuffer, position, remaining);
        } else if (byteBuffer.hasArray()) {
            nativeUnmarshall(this.mNativePtr, byteBuffer.array(), byteBuffer.arrayOffset() + position, remaining);
        } else {
            throw new IllegalArgumentException();
        }
        byteBuffer.position(position + remaining);
    }

    public final void appendFrom(Parcel parcel, int i, int i2) {
        nativeAppendFrom(this.mNativePtr, parcel.mNativePtr, i, i2);
    }

    public int compareData(Parcel parcel) {
        return nativeCompareData(this.mNativePtr, parcel.mNativePtr);
    }

    public static boolean compareData(Parcel parcel, int i, Parcel parcel2, int i2, int i3) {
        return nativeCompareDataInRange(parcel.mNativePtr, i, parcel2.mNativePtr, i2, i3);
    }

    public final void setClassCookie(Class cls, Object obj) {
        if (this.mClassCookies == null) {
            this.mClassCookies = new ArrayMap<>();
        }
        this.mClassCookies.put(cls, obj);
    }

    public final Object getClassCookie(Class cls) {
        ArrayMap<Class, Object> arrayMap = this.mClassCookies;
        if (arrayMap != null) {
            return arrayMap.get(cls);
        }
        return null;
    }

    public void removeClassCookie(Class cls, Object obj) {
        ArrayMap<Class, Object> arrayMap = this.mClassCookies;
        if (arrayMap != null) {
            Object remove = arrayMap.remove(cls);
            if (remove != obj) {
                Log.wtf(TAG, "Expected to remove " + obj + " (with key=" + cls + ") but instead removed " + remove);
                return;
            }
            return;
        }
        Log.wtf(TAG, "Expected to remove " + obj + " (with key=" + cls + ") but no cookies were present");
    }

    public boolean hasClassCookie(Class cls) {
        ArrayMap<Class, Object> arrayMap = this.mClassCookies;
        return arrayMap != null && arrayMap.containsKey(cls);
    }

    public final void adoptClassCookies(Parcel parcel) {
        this.mClassCookies = parcel.mClassCookies;
    }

    public Map<Class, Object> copyClassCookies() {
        return new ArrayMap(this.mClassCookies);
    }

    public void putClassCookies(Map<Class, Object> map) {
        if (map == null) {
            return;
        }
        if (this.mClassCookies == null) {
            this.mClassCookies = new ArrayMap<>();
        }
        this.mClassCookies.putAll(map);
    }

    public boolean hasFileDescriptors() {
        return nativeHasFileDescriptors(this.mNativePtr);
    }

    public boolean hasFileDescriptors(int i, int i2) {
        return nativeHasFileDescriptorsInRange(this.mNativePtr, i, i2);
    }

    public static boolean hasFileDescriptors(Object obj) {
        if (obj instanceof Parcel) {
            if (((Parcel) obj).hasFileDescriptors()) {
                return true;
            }
        } else if (obj instanceof LazyValue) {
            if (((LazyValue) obj).hasFileDescriptors()) {
                return true;
            }
        } else if (obj instanceof Parcelable) {
            if ((((Parcelable) obj).describeContents() & 1) != 0) {
                return true;
            }
        } else if (obj instanceof ArrayMap) {
            ArrayMap arrayMap = (ArrayMap) obj;
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                if (hasFileDescriptors(arrayMap.keyAt(i)) || hasFileDescriptors(arrayMap.valueAt(i))) {
                    return true;
                }
            }
        } else if (obj instanceof Map) {
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                if (hasFileDescriptors(entry.getKey()) || hasFileDescriptors(entry.getValue())) {
                    return true;
                }
            }
        } else if (obj instanceof List) {
            List list = (List) obj;
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (hasFileDescriptors(list.get(i2))) {
                    return true;
                }
            }
        } else if (obj instanceof SparseArray) {
            SparseArray sparseArray = (SparseArray) obj;
            int size3 = sparseArray.size();
            for (int i3 = 0; i3 < size3; i3++) {
                if (hasFileDescriptors(sparseArray.valueAt(i3))) {
                    return true;
                }
            }
        } else if (obj instanceof Object[]) {
            for (Object obj2 : (Object[]) obj) {
                if (hasFileDescriptors(obj2)) {
                    return true;
                }
            }
        } else {
            getValueType(obj);
        }
        return false;
    }

    public boolean hasBinders() {
        return nativeHasBinders(this.mNativePtr);
    }

    public boolean hasBinders(int i, int i2) {
        return nativeHasBindersInRange(this.mNativePtr, i, i2);
    }

    public final void writeInterfaceToken(String str) {
        this.interfaceName = str;
        nativeWriteInterfaceToken(this.mNativePtr, str);
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public final void enforceInterface(String str) {
        nativeEnforceInterface(this.mNativePtr, str);
    }

    public void enforceNoDataAvail() {
        int dataAvail = dataAvail();
        if (dataAvail <= 0) {
            return;
        }
        throw new BadParcelableException("Parcel data not fully consumed, unread size: " + dataAvail);
    }

    public boolean replaceCallingWorkSourceUid(int i) {
        return nativeReplaceCallingWorkSourceUid(this.mNativePtr, i);
    }

    public int readCallingWorkSourceUid() {
        return nativeReadCallingWorkSourceUid(this.mNativePtr);
    }

    public final void writeByteArray(byte[] bArr) {
        writeByteArray(bArr, 0, bArr != null ? bArr.length : 0);
    }

    public final void writeByteArray(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            writeInt(-1);
        } else {
            ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            nativeWriteByteArray(this.mNativePtr, bArr, i, i2);
        }
    }

    public final void writeBlob(byte[] bArr) {
        writeBlob(bArr, 0, bArr != null ? bArr.length : 0);
    }

    public final void writeBlob(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            writeInt(-1);
        } else {
            ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            nativeWriteBlob(this.mNativePtr, bArr, i, i2);
        }
    }

    public final void writeInt(int i) {
        int nativeWriteInt = nativeWriteInt(this.mNativePtr, i);
        if (nativeWriteInt != 0) {
            nativeSignalExceptionForError(nativeWriteInt);
        }
    }

    public final void writeLong(long j) {
        int nativeWriteLong = nativeWriteLong(this.mNativePtr, j);
        if (nativeWriteLong != 0) {
            nativeSignalExceptionForError(nativeWriteLong);
        }
    }

    public final void writeFloat(float f) {
        int nativeWriteFloat = nativeWriteFloat(this.mNativePtr, f);
        if (nativeWriteFloat != 0) {
            nativeSignalExceptionForError(nativeWriteFloat);
        }
    }

    public final void writeDouble(double d) {
        int nativeWriteDouble = nativeWriteDouble(this.mNativePtr, d);
        if (nativeWriteDouble != 0) {
            nativeSignalExceptionForError(nativeWriteDouble);
        }
    }

    public final void writeString(String str) {
        writeString16(str);
    }

    public final void writeString8(String str) {
        this.mReadWriteHelper.writeString8(this, str);
    }

    public final void writeString16(String str) {
        this.mReadWriteHelper.writeString16(this, str);
    }

    public void writeStringNoHelper(String str) {
        writeString16NoHelper(str);
    }

    public void writeString8NoHelper(String str) {
        nativeWriteString8(this.mNativePtr, str);
    }

    public void writeString16NoHelper(String str) {
        nativeWriteString16(this.mNativePtr, str);
    }

    public final void writeBoolean(boolean z) {
        writeInt(z ? 1 : 0);
    }

    public final void writeCharSequence(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this, 0);
    }

    public final void writeStrongBinder(IBinder iBinder) {
        nativeWriteStrongBinder(this.mNativePtr, iBinder);
    }

    public final void writeStrongInterface(IInterface iInterface) {
        writeStrongBinder(iInterface == null ? null : iInterface.asBinder());
    }

    public final void writeFileDescriptor(FileDescriptor fileDescriptor) {
        nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor);
    }

    public final void writeRawFileDescriptor(FileDescriptor fileDescriptor) {
        nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor);
    }

    public final void writeRawFileDescriptorArray(FileDescriptor[] fileDescriptorArr) {
        if (fileDescriptorArr != null) {
            writeInt(fileDescriptorArr.length);
            for (FileDescriptor fileDescriptor : fileDescriptorArr) {
                writeRawFileDescriptor(fileDescriptor);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeByte(byte b) {
        writeInt(b);
    }

    public final void writeMap(Map map) {
        writeMapInternal(map);
    }

    void writeMapInternal(Map<String, Object> map) {
        if (map == null) {
            writeInt(-1);
            return;
        }
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        int size = entrySet.size();
        writeInt(size);
        for (Map.Entry<String, Object> entry : entrySet) {
            writeValue(entry.getKey());
            writeValue(entry.getValue());
            size--;
        }
        if (size != 0) {
            throw new BadParcelableException("Map size does not match number of entries!");
        }
    }

    void writeArrayMapInternal(ArrayMap<String, Object> arrayMap) {
        if (arrayMap == null) {
            writeInt(-1);
            return;
        }
        int size = arrayMap.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeString(arrayMap.keyAt(i));
            writeValue(arrayMap.valueAt(i));
        }
    }

    public void writeArrayMap(ArrayMap<String, Object> arrayMap) {
        writeArrayMapInternal(arrayMap);
    }

    public <T extends Parcelable> void writeTypedArrayMap(ArrayMap<String, T> arrayMap, int i) {
        if (arrayMap == null) {
            writeInt(-1);
            return;
        }
        int size = arrayMap.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeString(arrayMap.keyAt(i2));
            writeTypedObject(arrayMap.valueAt(i2), i);
        }
    }

    public void writeArraySet(ArraySet<? extends Object> arraySet) {
        int size = arraySet != null ? arraySet.size() : -1;
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeValue(arraySet.valueAt(i));
        }
    }

    public final void writeBundle(Bundle bundle) {
        if (bundle == null) {
            writeInt(-1);
        } else {
            bundle.writeToParcel(this, 0);
        }
    }

    public final void writePersistableBundle(PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            writeInt(-1);
        } else {
            persistableBundle.writeToParcel(this, 0);
        }
    }

    public final void writeSize(Size size) {
        writeInt(size.getWidth());
        writeInt(size.getHeight());
    }

    public final void writeSizeF(SizeF sizeF) {
        writeFloat(sizeF.getWidth());
        writeFloat(sizeF.getHeight());
    }

    public final void writeList(List list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeValue(list.get(i));
        }
    }

    public final void writeArray(Object[] objArr) {
        if (objArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(objArr.length);
        for (Object obj : objArr) {
            writeValue(obj);
        }
    }

    public final <T> void writeSparseArray(SparseArray<T> sparseArray) {
        if (sparseArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseArray.keyAt(i));
            writeValue(sparseArray.valueAt(i));
        }
    }

    public final void writeSparseBooleanArray(SparseBooleanArray sparseBooleanArray) {
        if (sparseBooleanArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseBooleanArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseBooleanArray.keyAt(i));
            writeByte(sparseBooleanArray.valueAt(i) ? (byte) 1 : (byte) 0);
        }
    }

    public final void writeSparseIntArray(SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseIntArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseIntArray.keyAt(i));
            writeInt(sparseIntArray.valueAt(i));
        }
    }

    public final void writeBooleanArray(boolean[] zArr) {
        if (zArr != null) {
            writeInt(zArr.length);
            for (boolean z : zArr) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    private static <T> int getItemTypeSize(Class<T> cls) {
        Class<?> componentType = cls.getComponentType();
        if (componentType == Boolean.TYPE) {
            return 4;
        }
        if (componentType == Byte.TYPE) {
            return 1;
        }
        if (componentType == Character.TYPE) {
            return 2;
        }
        if (componentType == Integer.TYPE) {
            return 4;
        }
        if (componentType == Long.TYPE) {
            return 8;
        }
        if (componentType == Float.TYPE) {
            return 4;
        }
        return componentType == Double.TYPE ? 8 : 1;
    }

    private void ensureWithinMemoryLimit(int i, int... iArr) {
        int i2 = 1;
        try {
            for (int i3 : iArr) {
                i2 = Math.multiplyExact(i2, i3);
            }
        } catch (ArithmeticException e) {
            Log.e(TAG, "ArithmeticException occurred while multiplying dimensions " + e);
            SneakyThrow.sneakyThrow(new BadParcelableException("Estimated array length is too large. Array Dimensions:" + Arrays.toString(iArr)));
        }
        ensureWithinMemoryLimit(i, i2);
    }

    private void ensureWithinMemoryLimit(int i, int i2) {
        int i3;
        try {
            i3 = Math.multiplyExact(i, i2);
        } catch (ArithmeticException e) {
            Log.e(TAG, "ArithmeticException occurred while multiplying values " + i + " and " + i2 + " Exception: " + e);
            StringBuilder sb = new StringBuilder("Estimated allocation size is too large. typeSize: ");
            sb.append(i);
            sb.append(" length: ");
            sb.append(i2);
            SneakyThrow.sneakyThrow(new BadParcelableException(sb.toString()));
            i3 = 0;
        }
        boolean isDirectlyHandlingTransaction = Binder.isDirectlyHandlingTransaction();
        if (!isDirectlyHandlingTransaction || i3 <= 1000000) {
            return;
        }
        Log.e(TAG, "Trying to Allocate " + i3 + " memory, In Binder Transaction : " + isDirectlyHandlingTransaction);
        StringBuilder sb2 = new StringBuilder("Allocation of size ");
        sb2.append(i3);
        sb2.append(" is above allowed limit of 1MB");
        SneakyThrow.sneakyThrow(new BadParcelableException(sb2.toString()));
    }

    public final boolean[] createBooleanArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 2)) {
            return null;
        }
        boolean[] zArr = new boolean[readInt];
        for (int i = 0; i < readInt; i++) {
            zArr[i] = readInt() != 0;
        }
        return zArr;
    }

    public final void readBooleanArray(boolean[] zArr) {
        int readInt = readInt();
        if (readInt != zArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            zArr[i] = readInt() != 0;
        }
    }

    public void writeShortArray(short[] sArr) {
        if (sArr != null) {
            writeInt(sArr.length);
            for (short s : sArr) {
                writeInt(s);
            }
            return;
        }
        writeInt(-1);
    }

    public short[] createShortArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(2, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 2)) {
            return null;
        }
        short[] sArr = new short[readInt];
        for (int i = 0; i < readInt; i++) {
            sArr[i] = (short) readInt();
        }
        return sArr;
    }

    public void readShortArray(short[] sArr) {
        int readInt = readInt();
        if (readInt != sArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            sArr[i] = (short) readInt();
        }
    }

    public final void writeCharArray(char[] cArr) {
        if (cArr != null) {
            writeInt(cArr.length);
            for (char c : cArr) {
                writeInt(c);
            }
            return;
        }
        writeInt(-1);
    }

    public final char[] createCharArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(2, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 2)) {
            return null;
        }
        char[] cArr = new char[readInt];
        for (int i = 0; i < readInt; i++) {
            cArr[i] = (char) readInt();
        }
        return cArr;
    }

    public final void readCharArray(char[] cArr) {
        int readInt = readInt();
        if (readInt != cArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            cArr[i] = (char) readInt();
        }
    }

    public final void writeIntArray(int[] iArr) {
        if (iArr != null) {
            writeInt(iArr.length);
            for (int i : iArr) {
                writeInt(i);
            }
            return;
        }
        writeInt(-1);
    }

    public final int[] createIntArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 2)) {
            return null;
        }
        int[] iArr = new int[readInt];
        for (int i = 0; i < readInt; i++) {
            iArr[i] = readInt();
        }
        return iArr;
    }

    public final void readIntArray(int[] iArr) {
        int readInt = readInt();
        if (readInt != iArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            iArr[i] = readInt();
        }
    }

    public final void writeLongArray(long[] jArr) {
        if (jArr != null) {
            writeInt(jArr.length);
            for (long j : jArr) {
                writeLong(j);
            }
            return;
        }
        writeInt(-1);
    }

    public final long[] createLongArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(8, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 3)) {
            return null;
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readLong();
        }
        return jArr;
    }

    public final void readLongArray(long[] jArr) {
        int readInt = readInt();
        if (readInt != jArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readLong();
        }
    }

    public final void writeFloatArray(float[] fArr) {
        if (fArr != null) {
            writeInt(fArr.length);
            for (float f : fArr) {
                writeFloat(f);
            }
            return;
        }
        writeInt(-1);
    }

    public final float[] createFloatArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 2)) {
            return null;
        }
        float[] fArr = new float[readInt];
        for (int i = 0; i < readInt; i++) {
            fArr[i] = readFloat();
        }
        return fArr;
    }

    public final void readFloatArray(float[] fArr) {
        int readInt = readInt();
        if (readInt != fArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            fArr[i] = readFloat();
        }
    }

    public final void writeDoubleArray(double[] dArr) {
        if (dArr != null) {
            writeInt(dArr.length);
            for (double d : dArr) {
                writeDouble(d);
            }
            return;
        }
        writeInt(-1);
    }

    public final double[] createDoubleArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(8, readInt);
        if (readInt < 0 || readInt > (dataAvail() >> 3)) {
            return null;
        }
        double[] dArr = new double[readInt];
        for (int i = 0; i < readInt; i++) {
            dArr[i] = readDouble();
        }
        return dArr;
    }

    public final void readDoubleArray(double[] dArr) {
        int readInt = readInt();
        if (readInt != dArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            dArr[i] = readDouble();
        }
    }

    public final void writeStringArray(String[] strArr) {
        writeString16Array(strArr);
    }

    public final String[] createStringArray() {
        return createString16Array();
    }

    public final void readStringArray(String[] strArr) {
        readString16Array(strArr);
    }

    public final void writeString8Array(String[] strArr) {
        if (strArr != null) {
            writeInt(strArr.length);
            for (String str : strArr) {
                writeString8(str);
            }
            return;
        }
        writeInt(-1);
    }

    public final String[] createString8Array() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt < 0) {
            return null;
        }
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            strArr[i] = readString8();
        }
        return strArr;
    }

    public final void readString8Array(String[] strArr) {
        int readInt = readInt();
        if (readInt != strArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            strArr[i] = readString8();
        }
    }

    public final void writeString16Array(String[] strArr) {
        if (strArr != null) {
            writeInt(strArr.length);
            for (String str : strArr) {
                writeString16(str);
            }
            return;
        }
        writeInt(-1);
    }

    public final String[] createString16Array() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt < 0) {
            return null;
        }
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            strArr[i] = readString16();
        }
        return strArr;
    }

    public final void readString16Array(String[] strArr) {
        int readInt = readInt();
        if (readInt != strArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            strArr[i] = readString16();
        }
    }

    public final void writeBinderArray(IBinder[] iBinderArr) {
        if (iBinderArr != null) {
            writeInt(iBinderArr.length);
            for (IBinder iBinder : iBinderArr) {
                writeStrongBinder(iBinder);
            }
            return;
        }
        writeInt(-1);
    }

    public final <T extends IInterface> void writeInterfaceArray(T[] tArr) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeStrongInterface(t);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeCharSequenceArray(CharSequence[] charSequenceArr) {
        if (charSequenceArr != null) {
            writeInt(charSequenceArr.length);
            for (CharSequence charSequence : charSequenceArr) {
                writeCharSequence(charSequence);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeCharSequenceList(ArrayList<CharSequence> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            writeInt(size);
            for (int i = 0; i < size; i++) {
                writeCharSequence(arrayList.get(i));
            }
            return;
        }
        writeInt(-1);
    }

    public final IBinder[] createBinderArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt < 0) {
            return null;
        }
        IBinder[] iBinderArr = new IBinder[readInt];
        for (int i = 0; i < readInt; i++) {
            iBinderArr[i] = readStrongBinder();
        }
        return iBinderArr;
    }

    public final void readBinderArray(IBinder[] iBinderArr) {
        int readInt = readInt();
        if (readInt != iBinderArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            iBinderArr[i] = readStrongBinder();
        }
    }

    public final <T extends IInterface> T[] createInterfaceArray(IntFunction<T[]> intFunction, Function<IBinder, T> function) {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt < 0) {
            return null;
        }
        T[] apply = intFunction.apply(readInt);
        for (int i = 0; i < readInt; i++) {
            apply[i] = function.apply(readStrongBinder());
        }
        return apply;
    }

    public final <T extends IInterface> void readInterfaceArray(T[] tArr, Function<IBinder, T> function) {
        int readInt = readInt();
        if (readInt != tArr.length) {
            throw new BadParcelableException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            tArr[i] = function.apply(readStrongBinder());
        }
    }

    public final <T extends Parcelable> void writeTypedList(List<T> list) {
        writeTypedList(list, 0);
    }

    public final <T extends Parcelable> void writeTypedSparseArray(SparseArray<T> sparseArray, int i) {
        if (sparseArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseArray.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeInt(sparseArray.keyAt(i2));
            writeTypedObject(sparseArray.valueAt(i2), i);
        }
    }

    public <T extends Parcelable> void writeTypedList(List<T> list, int i) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeTypedObject(list.get(i2), i);
        }
    }

    public final void writeStringList(List<String> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeString(list.get(i));
        }
    }

    public final void writeBinderList(List<IBinder> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeStrongBinder(list.get(i));
        }
    }

    public final <T extends IInterface> void writeInterfaceList(List<T> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeStrongInterface(list.get(i));
        }
    }

    public final <T extends Parcelable> void writeParcelableList(List<T> list, int i) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeParcelable(list.get(i2), i);
        }
    }

    public final <T extends Parcelable> void writeTypedArray(T[] tArr, int i) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeTypedObject(t, i);
            }
            return;
        }
        writeInt(-1);
    }

    public final <T extends Parcelable> void writeTypedObject(T t, int i) {
        if (t != null) {
            writeInt(1);
            t.writeToParcel(this, i);
        } else {
            writeInt(0);
        }
    }

    public <T> void writeFixedArray(T t, int i, int... iArr) {
        if (t == null) {
            writeInt(-1);
        } else {
            writeFixedArrayInternal(t, i, 0, iArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void writeFixedArrayInternal(T t, int i, int i2, int[] iArr) {
        if (i2 >= iArr.length) {
            throw new BadParcelableException("Array has more dimensions than expected: " + iArr.length);
        }
        int i3 = iArr[i2];
        if (t == 0) {
            throw new BadParcelableException("Non-null array shouldn't have a null array.");
        }
        if (!t.getClass().isArray()) {
            throw new BadParcelableException("Not an array: " + t);
        }
        if (Array.getLength(t) != i3) {
            throw new BadParcelableException("bad length: expected " + i3 + ", but got " + Array.getLength(t));
        }
        Class<?> componentType = t.getClass().getComponentType();
        if (!componentType.isArray() && i2 + 1 != iArr.length) {
            throw new BadParcelableException("Array has fewer dimensions than expected: " + iArr.length);
        }
        if (componentType == Boolean.TYPE) {
            writeBooleanArray((boolean[]) t);
            return;
        }
        if (componentType == Byte.TYPE) {
            writeByteArray((byte[]) t);
            return;
        }
        if (componentType == Character.TYPE) {
            writeCharArray((char[]) t);
            return;
        }
        if (componentType == Integer.TYPE) {
            writeIntArray((int[]) t);
            return;
        }
        if (componentType == Long.TYPE) {
            writeLongArray((long[]) t);
            return;
        }
        if (componentType == Float.TYPE) {
            writeFloatArray((float[]) t);
            return;
        }
        if (componentType == Double.TYPE) {
            writeDoubleArray((double[]) t);
            return;
        }
        if (componentType == IBinder.class) {
            writeBinderArray((IBinder[]) t);
            return;
        }
        if (IInterface.class.isAssignableFrom(componentType)) {
            writeInterfaceArray((IInterface[]) t);
            return;
        }
        if (Parcelable.class.isAssignableFrom(componentType)) {
            writeTypedArray((Parcelable[]) t, i);
            return;
        }
        if (componentType.isArray()) {
            writeInt(i3);
            for (int i4 = 0; i4 < i3; i4++) {
                writeFixedArrayInternal(Array.get(t, i4), i, i2 + 1, iArr);
            }
            return;
        }
        throw new BadParcelableException("unknown type for fixed-size array: " + componentType);
    }

    public final void writeValue(Object obj) {
        if (obj instanceof LazyValue) {
            ((LazyValue) obj).writeToParcel(this);
            return;
        }
        int valueType = getValueType(obj);
        writeInt(valueType);
        if (isLengthPrefixed(valueType)) {
            int dataPosition = dataPosition();
            writeInt(-1);
            int dataPosition2 = dataPosition();
            writeValue(valueType, obj);
            int dataPosition3 = dataPosition();
            setDataPosition(dataPosition);
            writeInt(dataPosition3 - dataPosition2);
            setDataPosition(dataPosition3);
            return;
        }
        writeValue(valueType, obj);
    }

    public static int getValueType(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (obj instanceof String) {
            return 0;
        }
        if (obj instanceof Integer) {
            return 1;
        }
        if (obj instanceof Map) {
            return 2;
        }
        if (obj instanceof Bundle) {
            return 3;
        }
        if (obj instanceof PersistableBundle) {
            return 25;
        }
        if (obj instanceof SizeF) {
            return 27;
        }
        if (obj instanceof Parcelable) {
            return 4;
        }
        if (obj instanceof Short) {
            return 5;
        }
        if (obj instanceof Long) {
            return 6;
        }
        if (obj instanceof Float) {
            return 7;
        }
        if (obj instanceof Double) {
            return 8;
        }
        if (obj instanceof Boolean) {
            return 9;
        }
        if (obj instanceof CharSequence) {
            return 10;
        }
        if (obj instanceof List) {
            return 11;
        }
        if (obj instanceof SparseArray) {
            return 12;
        }
        if (obj instanceof boolean[]) {
            return 23;
        }
        if (obj instanceof byte[]) {
            return 13;
        }
        if (obj instanceof String[]) {
            return 14;
        }
        if (obj instanceof CharSequence[]) {
            return 24;
        }
        if (obj instanceof IBinder) {
            return 15;
        }
        if (obj instanceof Parcelable[]) {
            return 16;
        }
        if (obj instanceof int[]) {
            return 18;
        }
        if (obj instanceof long[]) {
            return 19;
        }
        if (obj instanceof Byte) {
            return 20;
        }
        if (obj instanceof Size) {
            return 26;
        }
        if (obj instanceof double[]) {
            return 28;
        }
        if (obj instanceof Character) {
            return 29;
        }
        if (obj instanceof short[]) {
            return 30;
        }
        if (obj instanceof char[]) {
            return 31;
        }
        if (obj instanceof float[]) {
            return 32;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray() && cls.getComponentType() == Object.class) {
            return 17;
        }
        if (obj instanceof Serializable) {
            return 21;
        }
        throw new IllegalArgumentException("Parcel: unknown type for value " + obj);
    }

    public void writeValue(int i, Object obj) {
        switch (i) {
            case -1:
                return;
            case 0:
                writeString((String) obj);
                return;
            case 1:
                writeInt(((Integer) obj).intValue());
                return;
            case 2:
                writeMap((Map) obj);
                return;
            case 3:
                writeBundle((Bundle) obj);
                return;
            case 4:
                writeParcelable((Parcelable) obj, 0);
                return;
            case 5:
                writeInt(((Short) obj).intValue());
                return;
            case 6:
                writeLong(((Long) obj).longValue());
                return;
            case 7:
                writeFloat(((Float) obj).floatValue());
                return;
            case 8:
                writeDouble(((Double) obj).doubleValue());
                return;
            case 9:
                writeInt(((Boolean) obj).booleanValue() ? 1 : 0);
                return;
            case 10:
                writeCharSequence((CharSequence) obj);
                return;
            case 11:
                writeList((List) obj);
                return;
            case 12:
                writeSparseArray((SparseArray) obj);
                return;
            case 13:
                writeByteArray((byte[]) obj);
                return;
            case 14:
                writeStringArray((String[]) obj);
                return;
            case 15:
                writeStrongBinder((IBinder) obj);
                return;
            case 16:
                writeParcelableArray((Parcelable[]) obj, 0);
                return;
            case 17:
                writeArray((Object[]) obj);
                return;
            case 18:
                writeIntArray((int[]) obj);
                return;
            case 19:
                writeLongArray((long[]) obj);
                return;
            case 20:
                writeInt(((Byte) obj).byteValue());
                return;
            case 21:
                writeSerializable((Serializable) obj);
                return;
            case 22:
            default:
                throw new RuntimeException("Parcel: unable to marshal value " + obj);
            case 23:
                writeBooleanArray((boolean[]) obj);
                return;
            case 24:
                writeCharSequenceArray((CharSequence[]) obj);
                return;
            case 25:
                writePersistableBundle((PersistableBundle) obj);
                return;
            case 26:
                writeSize((Size) obj);
                return;
            case 27:
                writeSizeF((SizeF) obj);
                return;
            case 28:
                writeDoubleArray((double[]) obj);
                return;
            case 29:
                writeInt(((Character) obj).charValue());
                return;
            case 30:
                writeShortArray((short[]) obj);
                return;
            case 31:
                writeCharArray((char[]) obj);
                return;
            case 32:
                writeFloatArray((float[]) obj);
                return;
        }
    }

    public final void writeParcelable(Parcelable parcelable, int i) {
        if (parcelable == null) {
            writeString(null);
        } else {
            writeParcelableCreator(parcelable);
            parcelable.writeToParcel(this, i);
        }
    }

    public final void writeParcelableCreator(Parcelable parcelable) {
        writeString(parcelable.getClass().getName());
    }

    private void ensureWrittenSquashableParcelables() {
        if (this.mWrittenSquashableParcelables != null) {
            return;
        }
        this.mWrittenSquashableParcelables = new ArrayMap<>();
    }

    public boolean allowSquashing() {
        boolean z = this.mAllowSquashing;
        this.mAllowSquashing = true;
        return z;
    }

    public void restoreAllowSquashing(boolean z) {
        this.mAllowSquashing = z;
        if (z) {
            return;
        }
        this.mWrittenSquashableParcelables = null;
    }

    private void resetSqaushingState() {
        if (this.mAllowSquashing) {
            Slog.wtf(TAG, "allowSquashing wasn't restored.");
        }
        this.mWrittenSquashableParcelables = null;
        this.mReadSquashableParcelables = null;
        this.mAllowSquashing = false;
    }

    private void ensureReadSquashableParcelables() {
        if (this.mReadSquashableParcelables != null) {
            return;
        }
        this.mReadSquashableParcelables = new SparseArray<>();
    }

    public boolean maybeWriteSquashed(Parcelable parcelable) {
        if (!this.mAllowSquashing) {
            writeInt(0);
            return false;
        }
        ensureWrittenSquashableParcelables();
        Integer num = this.mWrittenSquashableParcelables.get(parcelable);
        if (num != null) {
            writeInt((dataPosition() - num.intValue()) + 4);
            return true;
        }
        writeInt(0);
        this.mWrittenSquashableParcelables.put(parcelable, Integer.valueOf(dataPosition()));
        return false;
    }

    public <T extends Parcelable> T readSquashed(SquashReadHelper<T> squashReadHelper) {
        int readInt = readInt();
        int dataPosition = dataPosition();
        if (readInt == 0) {
            T readRawParceled = squashReadHelper.readRawParceled(this);
            ensureReadSquashableParcelables();
            this.mReadSquashableParcelables.put(dataPosition, readRawParceled);
            return readRawParceled;
        }
        int i = dataPosition - readInt;
        T t = (T) this.mReadSquashableParcelables.get(i);
        if (t == null) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < this.mReadSquashableParcelables.size(); i2++) {
                sb.append(this.mReadSquashableParcelables.keyAt(i2));
                sb.append(' ');
            }
            Slog.wtfStack(TAG, "Map doesn't contain offset " + i + " : contains=" + sb.toString());
        }
        return t;
    }

    public final void writeSerializable(Serializable serializable) {
        if (serializable == null) {
            writeString(null);
            return;
        }
        String name = serializable.getClass().getName();
        writeString(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new BadParcelableException("Parcelable encountered IOException writing serializable object (name = " + name + NavigationBarInflaterView.KEY_CODE_END, e);
        }
    }

    public static void setStackTraceParceling(boolean z) {
        sParcelExceptionStackTrace = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void writeException(Exception exc) {
        AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
        int exceptionCode = getExceptionCode(exc);
        writeInt(exceptionCode);
        StrictMode.clearGatheredViolations();
        if (exceptionCode == 0) {
            if (exc instanceof RuntimeException) {
                throw ((RuntimeException) exc);
            }
            throw new RuntimeException(exc);
        }
        writeString(exc.getMessage());
        long elapsedRealtime = sParcelExceptionStackTrace ? SystemClock.elapsedRealtime() : 0L;
        if (sParcelExceptionStackTrace && elapsedRealtime - sLastWriteExceptionStackTrace > 1000) {
            sLastWriteExceptionStackTrace = elapsedRealtime;
            writeStackTrace(exc);
        } else {
            writeInt(0);
        }
        if (exceptionCode != -9) {
            if (exceptionCode != -8) {
                return;
            }
            writeInt(((ServiceSpecificException) exc).errorCode);
            return;
        }
        int dataPosition = dataPosition();
        writeInt(0);
        writeParcelable((Parcelable) exc, 1);
        int dataPosition2 = dataPosition();
        setDataPosition(dataPosition);
        writeInt(dataPosition2 - dataPosition);
        setDataPosition(dataPosition2);
    }

    private void writeException$ravenwood(Exception exc) {
        int exceptionCode = getExceptionCode(exc);
        writeInt(exceptionCode);
        if (exceptionCode == 0) {
            if (exc instanceof RuntimeException) {
                throw ((RuntimeException) exc);
            }
            throw new RuntimeException(exc);
        }
        writeString(exc.getMessage());
        writeInt(0);
    }

    public static int getExceptionCode(Throwable th) {
        if ((th instanceof Parcelable) && th.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            return -9;
        }
        if (th instanceof SecurityException) {
            return -1;
        }
        if (th instanceof BadParcelableException) {
            return -2;
        }
        if (th instanceof IllegalArgumentException) {
            return -3;
        }
        if (th instanceof NullPointerException) {
            return -4;
        }
        if (th instanceof IllegalStateException) {
            return -5;
        }
        if (th instanceof NetworkOnMainThreadException) {
            return -6;
        }
        if (th instanceof UnsupportedOperationException) {
            return -7;
        }
        return th instanceof ServiceSpecificException ? -8 : 0;
    }

    public void writeStackTrace(Throwable th) {
        int dataPosition = dataPosition();
        writeInt(0);
        StackTraceElement[] stackTrace = th.getStackTrace();
        int min = Math.min(stackTrace.length, 5);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < min; i++) {
            sb.append("\tat ");
            sb.append(stackTrace[i]);
            sb.append('\n');
        }
        writeString(sb.toString());
        int dataPosition2 = dataPosition();
        setDataPosition(dataPosition);
        writeInt(dataPosition2 - dataPosition);
        setDataPosition(dataPosition2);
    }

    public final void writeNoException() {
        AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
        if (StrictMode.hasGatheredViolations()) {
            writeInt(-128);
            int dataPosition = dataPosition();
            writeInt(0);
            StrictMode.writeGatheredViolationsToParcel(this);
            int dataPosition2 = dataPosition();
            setDataPosition(dataPosition);
            writeInt(dataPosition2 - dataPosition);
            setDataPosition(dataPosition2);
            return;
        }
        writeInt(0);
    }

    private void writeNoException$ravenwood() {
        writeInt(0);
    }

    public final void readException() {
        int readExceptionCode = readExceptionCode();
        if (readExceptionCode != 0) {
            readException(readExceptionCode, readString());
        }
    }

    public final int readExceptionCode() {
        int readInt = readInt();
        if (readInt == -127) {
            AppOpsManager.readAndLogNotedAppops(this);
            readInt = readInt();
        }
        if (readInt != -128) {
            return readInt;
        }
        if (readInt() == 0) {
            Log.e(TAG, "Unexpected zero-sized Parcel reply header.");
            return 0;
        }
        StrictMode.readAndHandleBinderCallViolations(this);
        return 0;
    }

    public final void readException(int i, String str) {
        String readString = readInt() > 0 ? readString() : null;
        Exception createException = createException(i, str);
        if (readString != null) {
            ExceptionUtils.appendCause(createException, new RemoteException("Remote stack trace:\n" + readString, null, false, false));
        }
        SneakyThrow.sneakyThrow(createException);
    }

    private Exception createException(int i, String str) {
        Exception createExceptionOrNull = createExceptionOrNull(i, str);
        if (createExceptionOrNull != null) {
            return createExceptionOrNull;
        }
        return new RuntimeException("Unknown exception code: " + i + " msg " + str);
    }

    public Exception createExceptionOrNull(int i, String str) {
        switch (i) {
            case -9:
                if (readInt() > 0) {
                    return (Exception) readParcelable(Parcelable.class.getClassLoader(), Exception.class);
                }
                return new RuntimeException(str + " [missing Parcelable]");
            case -8:
                return new ServiceSpecificException(readInt(), str);
            case -7:
                return new UnsupportedOperationException(str);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(str);
            case -4:
                return new NullPointerException(str);
            case -3:
                return new IllegalArgumentException(str);
            case -2:
                return new BadParcelableException(str);
            case -1:
                return new SecurityException(str);
            default:
                return null;
        }
    }

    public final int readInt() {
        return nativeReadInt(this.mNativePtr);
    }

    public final long readLong() {
        return nativeReadLong(this.mNativePtr);
    }

    public final float readFloat() {
        return nativeReadFloat(this.mNativePtr);
    }

    public final double readDouble() {
        return nativeReadDouble(this.mNativePtr);
    }

    public final String readString() {
        return readString16();
    }

    public final String readString8() {
        return this.mReadWriteHelper.readString8(this);
    }

    public final String readString16() {
        return this.mReadWriteHelper.readString16(this);
    }

    public String readStringNoHelper() {
        return readString16NoHelper();
    }

    public String readString8NoHelper() {
        return nativeReadString8(this.mNativePtr);
    }

    public String readString16NoHelper() {
        return nativeReadString16(this.mNativePtr);
    }

    public final boolean readBoolean() {
        return readInt() != 0;
    }

    public final CharSequence readCharSequence() {
        return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this);
    }

    public final IBinder readStrongBinder() {
        IBinder nativeReadStrongBinder = nativeReadStrongBinder(this.mNativePtr);
        if (nativeReadStrongBinder != null && hasFlags(3)) {
            Binder.allowBlocking(nativeReadStrongBinder);
        }
        return nativeReadStrongBinder;
    }

    public final ParcelFileDescriptor readFileDescriptor() {
        FileDescriptor nativeReadFileDescriptor = nativeReadFileDescriptor(this.mNativePtr);
        if (nativeReadFileDescriptor != null) {
            return new ParcelFileDescriptor(nativeReadFileDescriptor);
        }
        return null;
    }

    public final FileDescriptor readRawFileDescriptor() {
        return nativeReadFileDescriptor(this.mNativePtr);
    }

    public final FileDescriptor[] createRawFileDescriptorArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        FileDescriptor[] fileDescriptorArr = new FileDescriptor[readInt];
        for (int i = 0; i < readInt; i++) {
            fileDescriptorArr[i] = readRawFileDescriptor();
        }
        return fileDescriptorArr;
    }

    public final void readRawFileDescriptorArray(FileDescriptor[] fileDescriptorArr) {
        int readInt = readInt();
        if (readInt != fileDescriptorArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            fileDescriptorArr[i] = readRawFileDescriptor();
        }
    }

    public final byte readByte() {
        return (byte) (readInt() & 255);
    }

    @Deprecated
    public final void readMap(Map map, ClassLoader classLoader) {
        readMapInternal(map, classLoader, null, null);
    }

    public <K, V> void readMap(Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(cls2);
        readMapInternal(map, classLoader, cls, cls2);
    }

    @Deprecated
    public final void readList(List list, ClassLoader classLoader) {
        readListInternal(list, readInt(), classLoader, null);
    }

    public <T> void readList(List<? super T> list, ClassLoader classLoader, Class<T> cls) {
        Objects.requireNonNull(cls);
        readListInternal(list, readInt(), classLoader, cls);
    }

    @Deprecated
    public HashMap readHashMap(ClassLoader classLoader) {
        return readHashMapInternal(classLoader, null, null);
    }

    public <K, V> HashMap<K, V> readHashMap(ClassLoader classLoader, Class<? extends K> cls, Class<? extends V> cls2) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(cls2);
        return readHashMapInternal(classLoader, cls, cls2);
    }

    public final Bundle readBundle() {
        return readBundle(null);
    }

    public final Bundle readBundle(ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        Bundle bundle = new Bundle(this, readInt);
        if (classLoader != null) {
            bundle.setClassLoader(classLoader);
        }
        return bundle;
    }

    public final PersistableBundle readPersistableBundle() {
        return readPersistableBundle(null);
    }

    public final PersistableBundle readPersistableBundle(ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        PersistableBundle persistableBundle = new PersistableBundle(this, readInt);
        if (classLoader != null) {
            persistableBundle.setClassLoader(classLoader);
        }
        return persistableBundle;
    }

    public final Size readSize() {
        return new Size(readInt(), readInt());
    }

    public final SizeF readSizeF() {
        return new SizeF(readFloat(), readFloat());
    }

    public final byte[] createByteArray() {
        return nativeCreateByteArray(this.mNativePtr);
    }

    public final void readByteArray(byte[] bArr) {
        if (!nativeReadByteArray(this.mNativePtr, bArr, bArr != null ? bArr.length : 0)) {
            throw new RuntimeException("bad array lengths");
        }
    }

    public final byte[] readBlob() {
        return nativeReadBlob(this.mNativePtr);
    }

    public final String[] readStringArray() {
        return createString16Array();
    }

    public final CharSequence[] readCharSequenceArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        CharSequence[] charSequenceArr = new CharSequence[readInt];
        for (int i = 0; i < readInt; i++) {
            charSequenceArr[i] = readCharSequence();
        }
        return charSequenceArr;
    }

    public final ArrayList<CharSequence> readCharSequenceList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayList<CharSequence> arrayList = new ArrayList<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(readCharSequence());
        }
        return arrayList;
    }

    @Deprecated
    public ArrayList readArrayList(ClassLoader classLoader) {
        return readArrayListInternal(classLoader, null);
    }

    public <T> ArrayList<T> readArrayList(ClassLoader classLoader, Class<? extends T> cls) {
        Objects.requireNonNull(cls);
        return readArrayListInternal(classLoader, cls);
    }

    @Deprecated
    public Object[] readArray(ClassLoader classLoader) {
        return readArrayInternal(classLoader, null);
    }

    public <T> T[] readArray(ClassLoader classLoader, Class<T> cls) {
        Objects.requireNonNull(cls);
        return (T[]) readArrayInternal(classLoader, cls);
    }

    @Deprecated
    public <T> SparseArray<T> readSparseArray(ClassLoader classLoader) {
        return readSparseArrayInternal(classLoader, null);
    }

    public <T> SparseArray<T> readSparseArray(ClassLoader classLoader, Class<? extends T> cls) {
        Objects.requireNonNull(cls);
        return readSparseArrayInternal(classLoader, cls);
    }

    public final SparseBooleanArray readSparseBooleanArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(readInt);
        readSparseBooleanArrayInternal(sparseBooleanArray, readInt);
        return sparseBooleanArray;
    }

    public final SparseIntArray readSparseIntArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        SparseIntArray sparseIntArray = new SparseIntArray(readInt);
        readSparseIntArrayInternal(sparseIntArray, readInt);
        return sparseIntArray;
    }

    public final <T> ArrayList<T> createTypedArrayList(Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        EnterpriseProxyConstants.AnonymousClass1 anonymousClass1 = (ArrayList<T>) new ArrayList(readInt);
        while (readInt > 0) {
            anonymousClass1.add(readTypedObject(creator));
            readInt--;
        }
        return anonymousClass1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void readTypedList(List<T> list, Parcelable.Creator<T> creator) {
        int size = list.size();
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readTypedObject(creator));
            i++;
        }
        while (i < readInt) {
            list.add(readTypedObject(creator));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final <T extends Parcelable> SparseArray<T> createTypedSparseArray(Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        WifiMigration.AnonymousClass1 anonymousClass1 = (SparseArray<T>) new SparseArray(readInt);
        for (int i = 0; i < readInt; i++) {
            anonymousClass1.append(readInt(), (Parcelable) readTypedObject(creator));
        }
        return anonymousClass1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends Parcelable> ArrayMap<String, T> createTypedArrayMap(Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayMap<String, T> arrayMap = (ArrayMap<String, T>) new ArrayMap(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayMap.append(readString(), (Parcelable) readTypedObject(creator));
        }
        return arrayMap;
    }

    public final ArrayList<String> createStringArrayList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayList<String> arrayList = new ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(readString());
            readInt--;
        }
        return arrayList;
    }

    public final ArrayList<IBinder> createBinderArrayList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayList<IBinder> arrayList = new ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(readStrongBinder());
            readInt--;
        }
        return arrayList;
    }

    public final <T extends IInterface> ArrayList<T> createInterfaceArrayList(Function<IBinder, T> function) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayList<T> arrayList = new ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(function.apply(readStrongBinder()));
            readInt--;
        }
        return arrayList;
    }

    public final void readStringList(List<String> list) {
        int size = list.size();
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readString());
            i++;
        }
        while (i < readInt) {
            list.add(readString());
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final void readBinderList(List<IBinder> list) {
        int size = list.size();
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readStrongBinder());
            i++;
        }
        while (i < readInt) {
            list.add(readStrongBinder());
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final <T extends IInterface> void readInterfaceList(List<T> list, Function<IBinder, T> function) {
        int size = list.size();
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, function.apply(readStrongBinder()));
            i++;
        }
        while (i < readInt) {
            list.add(function.apply(readStrongBinder()));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    @Deprecated
    public final <T extends Parcelable> List<T> readParcelableList(List<T> list, ClassLoader classLoader) {
        return readParcelableListInternal(list, classLoader, null);
    }

    public <T> List<T> readParcelableList(List<T> list, ClassLoader classLoader, Class<? extends T> cls) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(cls);
        return readParcelableListInternal(list, classLoader, cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> List<T> readParcelableListInternal(List<T> list, ClassLoader classLoader, Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt == -1) {
            list.clear();
            return list;
        }
        ensureWithinMemoryLimit(1, readInt);
        int size = list.size();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readParcelableInternal(classLoader, cls));
            i++;
        }
        while (i < readInt) {
            list.add(readParcelableInternal(classLoader, cls));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T[] createTypedArray(Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        T[] newArray = creator.newArray(readInt);
        for (int i = 0; i < readInt; i++) {
            newArray[i] = readTypedObject(creator);
        }
        return newArray;
    }

    public final <T> void readTypedArray(T[] tArr, Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt != tArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i = 0; i < readInt; i++) {
            tArr[i] = readTypedObject(creator);
        }
    }

    @Deprecated
    public final <T> T[] readTypedArray(Parcelable.Creator<T> creator) {
        return (T[]) createTypedArray(creator);
    }

    public final <T> T readTypedObject(Parcelable.Creator<T> creator) {
        if (readInt() != 0) {
            return creator.createFromParcel(this);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void readFixedArray(T t) {
        Class<?> componentType = t.getClass().getComponentType();
        if (componentType == Boolean.TYPE) {
            readBooleanArray((boolean[]) t);
            return;
        }
        if (componentType == Byte.TYPE) {
            readByteArray((byte[]) t);
            return;
        }
        if (componentType == Character.TYPE) {
            readCharArray((char[]) t);
            return;
        }
        if (componentType == Integer.TYPE) {
            readIntArray((int[]) t);
            return;
        }
        if (componentType == Long.TYPE) {
            readLongArray((long[]) t);
            return;
        }
        if (componentType == Float.TYPE) {
            readFloatArray((float[]) t);
            return;
        }
        if (componentType == Double.TYPE) {
            readDoubleArray((double[]) t);
            return;
        }
        if (componentType == IBinder.class) {
            readBinderArray((IBinder[]) t);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt == Array.getLength(t)) {
                for (int i = 0; i < readInt; i++) {
                    readFixedArray(Array.get(t, i));
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + readInt);
        }
        throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends IInterface> void readFixedArray(T t, Function<IBinder, S> function) {
        Class<?> componentType = t.getClass().getComponentType();
        if (IInterface.class.isAssignableFrom(componentType)) {
            readInterfaceArray((IInterface[]) t, function);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt == Array.getLength(t)) {
                for (int i = 0; i < readInt; i++) {
                    readFixedArray((Parcel) Array.get(t, i), (Function) function);
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + readInt);
        }
        throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends Parcelable> void readFixedArray(T t, Parcelable.Creator<S> creator) {
        Class<?> componentType = t.getClass().getComponentType();
        if (Parcelable.class.isAssignableFrom(componentType)) {
            readTypedArray((Parcelable[]) t, creator);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt == Array.getLength(t)) {
                for (int i = 0; i < readInt; i++) {
                    readFixedArray((Parcel) Array.get(t, i), (Parcelable.Creator) creator);
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + readInt);
        }
        throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    private void ensureClassHasExpectedDimensions(Class<?> cls, int i) {
        if (i <= 0) {
            throw new BadParcelableException("Fixed-size array should have dimensions.");
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (!cls.isArray()) {
                throw new BadParcelableException("Array has fewer dimensions than expected: " + i);
            }
            cls = cls.getComponentType();
        }
        if (cls.isArray()) {
            throw new BadParcelableException("Array has more dimensions than expected: " + i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T createFixedArray(Class<T> cls, int... iArr) {
        T t;
        ensureClassHasExpectedDimensions(cls, iArr.length);
        Class<?> componentType = cls.getComponentType();
        if (componentType == Boolean.TYPE) {
            t = (T) createBooleanArray();
        } else if (componentType == Byte.TYPE) {
            t = (T) createByteArray();
        } else if (componentType == Character.TYPE) {
            t = (T) createCharArray();
        } else if (componentType == Integer.TYPE) {
            t = (T) createIntArray();
        } else if (componentType == Long.TYPE) {
            t = (T) createLongArray();
        } else if (componentType == Float.TYPE) {
            t = (T) createFloatArray();
        } else if (componentType == Double.TYPE) {
            t = (T) createDoubleArray();
        } else if (componentType == IBinder.class) {
            t = (T) createBinderArray();
        } else {
            if (componentType.isArray()) {
                int readInt = readInt();
                if (readInt < 0) {
                    return null;
                }
                if (readInt != iArr[0]) {
                    throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
                }
                Class<?> componentType2 = componentType.getComponentType();
                while (componentType2.isArray()) {
                    componentType2 = componentType2.getComponentType();
                }
                ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
                T t2 = (T) Array.newInstance(componentType2, iArr);
                for (int i = 0; i < readInt; i++) {
                    readFixedArray(Array.get(t2, i));
                }
                return t2;
            }
            throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
        }
        if (t == null || Array.getLength(t) == iArr[0]) {
            return t;
        }
        throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends IInterface> T createFixedArray(Class<T> cls, Function<IBinder, S> function, int... iArr) {
        ensureClassHasExpectedDimensions(cls, iArr.length);
        final Class<?> componentType = cls.getComponentType();
        if (IInterface.class.isAssignableFrom(componentType)) {
            T t = (T) createInterfaceArray(new IntFunction() { // from class: android.os.Parcel$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return Parcel.lambda$createFixedArray$0(componentType, i);
                }
            }, function);
            if (t == null || Array.getLength(t) == iArr[0]) {
                return t;
            }
            throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt != iArr[0]) {
                throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
            }
            Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) Array.newInstance(componentType2, iArr);
            for (int i = 0; i < readInt; i++) {
                readFixedArray((Parcel) Array.get(t2, i), (Function) function);
            }
            return t2;
        }
        throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    static /* synthetic */ IInterface[] lambda$createFixedArray$0(Class cls, int i) {
        return (IInterface[]) Array.newInstance((Class<?>) cls, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends Parcelable> T createFixedArray(Class<T> cls, Parcelable.Creator<S> creator, int... iArr) {
        ensureClassHasExpectedDimensions(cls, iArr.length);
        Class<?> componentType = cls.getComponentType();
        if (Parcelable.class.isAssignableFrom(componentType)) {
            T t = (T) createTypedArray(creator);
            if (t == null || Array.getLength(t) == iArr[0]) {
                return t;
            }
            throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt != iArr[0]) {
                throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
            }
            Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) Array.newInstance(componentType2, iArr);
            for (int i = 0; i < readInt; i++) {
                readFixedArray((Parcel) Array.get(t2, i), (Parcelable.Creator) creator);
            }
            return t2;
        }
        throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    public final <T extends Parcelable> void writeParcelableArray(T[] tArr, int i) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeParcelable(t, i);
            }
            return;
        }
        writeInt(-1);
    }

    public final Object readValue(ClassLoader classLoader) {
        return readValue(classLoader, (Class) null, new Class[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T readValue(ClassLoader classLoader, Class<T> cls, Class<?>... clsArr) {
        int readInt = readInt();
        if (isLengthPrefixed(readInt)) {
            int readInt2 = readInt();
            int dataPosition = dataPosition();
            T t = (T) readValue(readInt, classLoader, cls, clsArr);
            int dataPosition2 = dataPosition() - dataPosition;
            if (dataPosition2 != readInt2) {
                Slog.wtfStack(TAG, "Unparcelling of " + t + " of type " + valueTypeToString(readInt) + "  consumed " + dataPosition2 + " bytes, but " + readInt2 + " expected.");
            }
            return t;
        }
        return (T) readValue(readInt, classLoader, cls, clsArr);
    }

    private Object readLazyValue(ClassLoaderProvider classLoaderProvider) {
        int dataPosition = dataPosition();
        int readInt = readInt();
        if (isLengthPrefixed(readInt)) {
            int readInt2 = readInt();
            if (readInt2 < 0) {
                return null;
            }
            int addOrThrow = MathUtils.addOrThrow(dataPosition(), readInt2);
            setDataPosition(addOrThrow);
            return new LazyValue(this, dataPosition, addOrThrow - dataPosition, readInt, classLoaderProvider);
        }
        return readValue(readInt, getClassLoader(classLoaderProvider), (Class) null);
    }

    private static ClassLoader getClassLoader(ClassLoaderProvider classLoaderProvider) {
        if (classLoaderProvider == null) {
            return null;
        }
        return classLoaderProvider.getClassLoader();
    }

    private static final class LazyValue implements BiFunction<Class<?>, Class<?>[], Object> {
        private final int mLength;
        private final ClassLoaderProvider mLoaderProvider;
        private Object mObject;
        private final int mPosition;
        private volatile Parcel mSource;
        private final int mType;

        LazyValue(Parcel parcel, int i, int i2, int i3, ClassLoaderProvider classLoaderProvider) {
            this.mSource = (Parcel) Objects.requireNonNull(parcel);
            this.mPosition = i;
            this.mLength = i2;
            this.mType = i3;
            this.mLoaderProvider = classLoaderProvider;
        }

        @Override // java.util.function.BiFunction
        public Object apply(Class<?> cls, Class<?>[] clsArr) {
            Parcel parcel = this.mSource;
            if (parcel != null) {
                synchronized (parcel) {
                    if (this.mSource != null) {
                        int dataPosition = parcel.dataPosition();
                        try {
                            parcel.setDataPosition(this.mPosition);
                            this.mObject = parcel.readValue(this.mLoaderProvider.getClassLoader(), cls, clsArr);
                            parcel.setDataPosition(dataPosition);
                            this.mSource = null;
                        } catch (Throwable th) {
                            parcel.setDataPosition(dataPosition);
                            throw th;
                        }
                    }
                }
            }
            return this.mObject;
        }

        public void writeToParcel(Parcel parcel) {
            Parcel parcel2 = this.mSource;
            if (parcel2 != null) {
                synchronized (parcel2) {
                    if (this.mSource != null) {
                        parcel.appendFrom(parcel2, this.mPosition, this.mLength);
                        return;
                    }
                }
            }
            parcel.writeValue(this.mObject);
        }

        public boolean hasFileDescriptors() {
            Parcel parcel = this.mSource;
            if (parcel != null) {
                synchronized (parcel) {
                    if (this.mSource != null) {
                        return parcel.hasFileDescriptors(this.mPosition, this.mLength);
                    }
                }
            }
            return Parcel.hasFileDescriptors(this.mObject);
        }

        public ClassLoader getClassLoader() {
            return this.mLoaderProvider.getClassLoader();
        }

        public String toString() {
            if (this.mSource != null) {
                return "Supplier{" + Parcel.valueTypeToString(this.mType) + "@" + this.mPosition + "+" + this.mLength + '}';
            }
            return "Supplier{" + this.mObject + "}";
        }

        public boolean equals(Object obj) {
            int i;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LazyValue)) {
                return false;
            }
            LazyValue lazyValue = (LazyValue) obj;
            Parcel parcel = this.mSource;
            Parcel parcel2 = lazyValue.mSource;
            if ((parcel == null) != (parcel2 == null)) {
                return false;
            }
            if (parcel == null) {
                return Objects.equals(this.mObject, lazyValue.mObject);
            }
            if (Objects.equals(this.mLoaderProvider.getClassLoader(), lazyValue.mLoaderProvider.getClassLoader()) && this.mType == lazyValue.mType && (i = this.mLength) == lazyValue.mLength) {
                return Parcel.compareData(parcel, this.mPosition, parcel2, lazyValue.mPosition, i);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.mSource == null), this.mObject, this.mLoaderProvider.getClassLoader(), Integer.valueOf(this.mType), Integer.valueOf(this.mLength));
        }
    }

    private <T> T readValue(int i, ClassLoader classLoader, Class<T> cls) {
        return (T) readValue(i, classLoader, cls, null);
    }

    private <T> T readValue(int i, ClassLoader classLoader, Class<T> cls, Class<?>... clsArr) {
        T t;
        switch (i) {
            case -1:
                t = null;
                break;
            case 0:
                t = (T) readString();
                break;
            case 1:
                t = (T) Integer.valueOf(readInt());
                break;
            case 2:
                checkTypeToUnparcel(cls, HashMap.class);
                Class cls2 = (Class) ArrayUtils.getOrNull(clsArr, 0);
                Class cls3 = (Class) ArrayUtils.getOrNull(clsArr, 1);
                Preconditions.checkArgument((cls2 == null) == (cls3 == null));
                t = (T) readHashMapInternal(classLoader, cls2, cls3);
                break;
            case 3:
                t = (T) readBundle(classLoader);
                break;
            case 4:
                t = (T) readParcelableInternal(classLoader, cls);
                break;
            case 5:
                t = (T) Short.valueOf((short) readInt());
                break;
            case 6:
                t = (T) Long.valueOf(readLong());
                break;
            case 7:
                t = (T) Float.valueOf(readFloat());
                break;
            case 8:
                t = (T) Double.valueOf(readDouble());
                break;
            case 9:
                t = (T) Boolean.valueOf(readInt() == 1);
                break;
            case 10:
                t = (T) readCharSequence();
                break;
            case 11:
                checkTypeToUnparcel(cls, ArrayList.class);
                t = (T) readArrayListInternal(classLoader, (Class) ArrayUtils.getOrNull(clsArr, 0));
                break;
            case 12:
                checkTypeToUnparcel(cls, SparseArray.class);
                t = (T) readSparseArrayInternal(classLoader, (Class) ArrayUtils.getOrNull(clsArr, 0));
                break;
            case 13:
                t = (T) createByteArray();
                break;
            case 14:
                t = (T) readStringArray();
                break;
            case 15:
                t = (T) readStrongBinder();
                break;
            case 16:
                Class<Parcelable> cls4 = (Class) ArrayUtils.getOrNull(clsArr, 0);
                checkArrayTypeToUnparcel(cls, cls4 != null ? cls4 : Parcelable.class);
                t = (T) readParcelableArrayInternal(classLoader, cls4);
                break;
            case 17:
                Class<?> cls5 = (Class) ArrayUtils.getOrNull(clsArr, 0);
                checkArrayTypeToUnparcel(cls, cls5 != null ? cls5 : Object.class);
                t = (T) readArrayInternal(classLoader, cls5);
                break;
            case 18:
                t = (T) createIntArray();
                break;
            case 19:
                t = (T) createLongArray();
                break;
            case 20:
                t = (T) Byte.valueOf(readByte());
                break;
            case 21:
                t = (T) readSerializableInternal(classLoader, cls);
                break;
            case 22:
                t = (T) readSparseBooleanArray();
                break;
            case 23:
                t = (T) createBooleanArray();
                break;
            case 24:
                t = (T) readCharSequenceArray();
                break;
            case 25:
                t = (T) readPersistableBundle(classLoader);
                break;
            case 26:
                t = (T) readSize();
                break;
            case 27:
                t = (T) readSizeF();
                break;
            case 28:
                t = (T) createDoubleArray();
                break;
            case 29:
                t = (T) Character.valueOf((char) readInt());
                break;
            case 30:
                t = (T) createShortArray();
                break;
            case 31:
                t = (T) createCharArray();
                break;
            case 32:
                t = (T) createFloatArray();
                break;
            default:
                throw new BadParcelableException("Parcel " + this + ": Unmarshalling unknown type code " + i + " at offset " + (dataPosition() - 4));
        }
        if (t == null || cls == null || cls.isInstance(t)) {
            return t;
        }
        throw new BadTypeParcelableException("Unparcelled object " + t + " is not an instance of required class " + cls.getName() + " provided in the parameter");
    }

    private void checkArrayTypeToUnparcel(Class<?> cls, Class<?> cls2) {
        if (cls != null) {
            Class<?> componentType = cls.getComponentType();
            if (componentType == null) {
                throw new BadTypeParcelableException("About to unparcel an array but type " + cls.getCanonicalName() + " required by caller is not an array.");
            }
            checkTypeToUnparcel(componentType, cls2);
        }
    }

    private void checkTypeToUnparcel(Class<?> cls, Class<?> cls2) {
        if (cls == null || cls.isAssignableFrom(cls2)) {
            return;
        }
        throw new BadTypeParcelableException("About to unparcel a " + cls2.getCanonicalName() + ", which is not a subtype of type " + cls.getCanonicalName() + " required by caller.");
    }

    @Deprecated
    public final <T extends Parcelable> T readParcelable(ClassLoader classLoader) {
        return (T) readParcelableInternal(classLoader, null);
    }

    public <T> T readParcelable(ClassLoader classLoader, Class<T> cls) {
        Objects.requireNonNull(cls);
        return (T) readParcelableInternal(classLoader, cls);
    }

    private <T> T readParcelableInternal(ClassLoader classLoader, Class<T> cls) {
        Parcelable.Creator<T> readParcelableCreatorInternal = readParcelableCreatorInternal(classLoader, cls);
        if (readParcelableCreatorInternal == null) {
            return null;
        }
        if (readParcelableCreatorInternal instanceof Parcelable.ClassLoaderCreator) {
            return (T) ((Parcelable.ClassLoaderCreator) readParcelableCreatorInternal).createFromParcel(this, classLoader);
        }
        return readParcelableCreatorInternal.createFromParcel(this);
    }

    public final <T extends Parcelable> T readCreator(Parcelable.Creator<?> creator, ClassLoader classLoader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return (T) ((Parcelable.ClassLoaderCreator) creator).createFromParcel(this, classLoader);
        }
        return (T) creator.createFromParcel(this);
    }

    @Deprecated
    public final Parcelable.Creator<?> readParcelableCreator(ClassLoader classLoader) {
        return readParcelableCreatorInternal(classLoader, null);
    }

    public <T> Parcelable.Creator<T> readParcelableCreator(ClassLoader classLoader, Class<T> cls) {
        Objects.requireNonNull(cls);
        return readParcelableCreatorInternal(classLoader, cls);
    }

    private <T> Parcelable.Creator<T> readParcelableCreatorInternal(ClassLoader classLoader, Class<T> cls) {
        Pair<Parcelable.Creator<?>, Class<?>> pair;
        ClassLoader classLoader2;
        String readString = readString();
        if (readString == null) {
            return null;
        }
        HashMap<ClassLoader, HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>>> hashMap = sPairedCreators;
        synchronized (hashMap) {
            HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>> hashMap2 = hashMap.get(classLoader);
            if (hashMap2 == null) {
                hashMap.put(classLoader, new HashMap<>());
                mCreators.put(classLoader, new HashMap<>());
                pair = null;
            } else {
                pair = hashMap2.get(readString);
            }
        }
        if (pair != null) {
            Parcelable.Creator<T> creator = (Parcelable.Creator) pair.first;
            Class<?> cls2 = pair.second;
            if (cls == null || cls.isAssignableFrom(cls2)) {
                return creator;
            }
            throw new BadTypeParcelableException("Parcelable creator " + readString + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        if (classLoader == null) {
            try {
                classLoader2 = getClass().getClassLoader();
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Class not found when unmarshalling: " + readString, e);
                throw new BadParcelableException("ClassNotFoundException when unmarshalling: " + readString, e);
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Illegal access when unmarshalling: " + readString, e2);
                throw new BadParcelableException("IllegalAccessException when unmarshalling: " + readString, e2);
            } catch (NoSuchFieldException e3) {
                throw new BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + readString, e3);
            }
        } else {
            classLoader2 = classLoader;
        }
        Class<?> cls3 = Class.forName(readString, false, classLoader2);
        if (!Parcelable.class.isAssignableFrom(cls3)) {
            throw new BadParcelableException("Parcelable protocol requires subclassing from Parcelable on class " + readString);
        }
        if (cls != null && !cls.isAssignableFrom(cls3)) {
            throw new BadTypeParcelableException("Parcelable creator " + readString + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        Field field = cls3.getField("CREATOR");
        if ((field.getModifiers() & 8) == 0) {
            throw new BadParcelableException("Parcelable protocol requires the CREATOR object to be static on class " + readString);
        }
        if (!Parcelable.Creator.class.isAssignableFrom(field.getType())) {
            throw new BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + readString);
        }
        Parcelable.Creator<T> creator2 = (Parcelable.Creator) field.get(null);
        if (creator2 == null) {
            throw new BadParcelableException("Parcelable protocol requires a non-null Parcelable.Creator object called CREATOR on class " + readString);
        }
        synchronized (hashMap) {
            hashMap.get(classLoader).put(readString, Pair.create(creator2, cls3));
            mCreators.get(classLoader).put(readString, creator2);
        }
        return creator2;
    }

    @Deprecated
    public Parcelable[] readParcelableArray(ClassLoader classLoader) {
        return (Parcelable[]) readParcelableArrayInternal(classLoader, null);
    }

    public <T> T[] readParcelableArray(ClassLoader classLoader, Class<T> cls) {
        return (T[]) readParcelableArrayInternal(classLoader, (Class) Objects.requireNonNull(cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readParcelableArrayInternal(ClassLoader classLoader, Class<T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        T[] tArr = (T[]) ((Object[]) (cls == null ? new Parcelable[readInt] : Array.newInstance((Class<?>) cls, readInt)));
        for (int i = 0; i < readInt; i++) {
            tArr[i] = readParcelableInternal(classLoader, cls);
        }
        return tArr;
    }

    @Deprecated
    public Serializable readSerializable() {
        return (Serializable) readSerializableInternal(null, null);
    }

    public <T> T readSerializable(ClassLoader classLoader, Class<T> cls) {
        Objects.requireNonNull(cls);
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        return (T) readSerializableInternal(classLoader, cls);
    }

    private <T> T readSerializableInternal(final ClassLoader classLoader, Class<T> cls) {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        if (cls != null && classLoader != null) {
            try {
                Class<?> cls2 = Class.forName(readString, false, classLoader);
                if (!cls.isAssignableFrom(cls2)) {
                    throw new BadTypeParcelableException("Serializable object " + cls2.getName() + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
                }
            } catch (IOException e) {
                throw new BadParcelableException("Parcelable encountered IOException reading a Serializable object (name = " + readString + NavigationBarInflaterView.KEY_CODE_END, e);
            } catch (ClassNotFoundException e2) {
                throw new BadParcelableException("Parcelable encountered ClassNotFoundException reading a Serializable object (name = " + readString + NavigationBarInflaterView.KEY_CODE_END, e2);
            }
        }
        T t = (T) new ObjectInputStream(this, new ByteArrayInputStream(createByteArray())) { // from class: android.os.Parcel.2
            @Override // java.io.ObjectInputStream
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                if (classLoader != null) {
                    return (Class) Objects.requireNonNull(Class.forName(objectStreamClass.getName(), false, classLoader));
                }
                return super.resolveClass(objectStreamClass);
            }
        }.readObject();
        if (cls != null && classLoader == null && !cls.isAssignableFrom(t.getClass())) {
            throw new BadTypeParcelableException("Serializable object " + t.getClass().getName() + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        return t;
    }

    protected static final Parcel obtain(int i) {
        throw new UnsupportedOperationException();
    }

    protected static final Parcel obtain(long j) {
        Parcel parcel;
        synchronized (sPoolSync) {
            parcel = sHolderPool;
            if (parcel != null) {
                sHolderPool = parcel.mPoolNext;
                parcel.mPoolNext = null;
                sHolderPoolSize--;
            } else {
                parcel = null;
            }
        }
        if (parcel == null) {
            return new Parcel(j);
        }
        parcel.mRecycled = false;
        parcel.init(j);
        return parcel;
    }

    private Parcel(long j) {
        init(j);
    }

    private void init(long j) {
        if (j != 0) {
            this.mNativePtr = j;
            this.mOwnsNativeParcelObject = false;
        } else {
            this.mNativePtr = nativeCreate();
            this.mOwnsNativeParcelObject = true;
        }
    }

    private void freeBuffer() {
        this.mFlags = 0;
        resetSqaushingState();
        if (this.mOwnsNativeParcelObject) {
            nativeFreeBuffer(this.mNativePtr);
        }
        this.mReadWriteHelper = ReadWriteHelper.DEFAULT;
    }

    private void destroy() {
        resetSqaushingState();
        long j = this.mNativePtr;
        if (j != 0) {
            if (this.mOwnsNativeParcelObject) {
                nativeDestroy(j);
            }
            this.mNativePtr = 0L;
        }
    }

    protected void finalize() throws Throwable {
        destroy();
    }

    void readMapInternal(Map map, int i, ClassLoader classLoader) {
        readMapInternal(map, i, classLoader, null, null);
    }

    private <K, V> HashMap<K, V> readHashMapInternal(ClassLoader classLoader, Class<? extends K> cls, Class<? extends V> cls2) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        HashMap<K, V> hashMap = new HashMap<>(readInt);
        readMapInternal(hashMap, readInt, classLoader, cls, cls2);
        return hashMap;
    }

    private <K, V> void readMapInternal(Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
        readMapInternal(map, readInt(), classLoader, cls, cls2);
    }

    private <K, V> void readMapInternal(Map<? super K, ? super V> map, int i, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            map.put((Object) readValue(classLoader, cls, new Class[0]), (Object) readValue(classLoader, cls2, new Class[0]));
            i--;
        }
    }

    private void readArrayMapInternal(ArrayMap<? super String, Object> arrayMap, int i, ClassLoaderProvider classLoaderProvider) {
        readArrayMap(arrayMap, i, true, false, classLoaderProvider, null);
    }

    void readArrayMap(ArrayMap<? super String, Object> arrayMap, int i, boolean z, boolean z2, ClassLoaderProvider classLoaderProvider, int[] iArr) {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            String readString = readString();
            Object readLazyValue = z2 ? readLazyValue(classLoaderProvider) : readValue(getClassLoader(classLoaderProvider));
            if (readLazyValue instanceof LazyValue) {
                iArr[0] = iArr[0] + 1;
            }
            if (z) {
                arrayMap.append(readString, readLazyValue);
            } else {
                arrayMap.put(readString, readLazyValue);
            }
            i--;
        }
        if (z) {
            arrayMap.validate();
        }
    }

    public void readArrayMap(ArrayMap<? super String, Object> arrayMap, ClassLoaderProvider classLoaderProvider) {
        int readInt = readInt();
        if (readInt < 0) {
            return;
        }
        readArrayMapInternal(arrayMap, readInt, classLoaderProvider);
    }

    public ArraySet<? extends Object> readArraySet(ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArraySet<? extends Object> arraySet = new ArraySet<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arraySet.append(readValue(classLoader));
        }
        return arraySet;
    }

    private void readListInternal(List list, int i, ClassLoader classLoader) {
        readListInternal(list, i, classLoader, null);
    }

    private <T> void readListInternal(List<? super T> list, int i, ClassLoader classLoader, Class<T> cls) {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            list.add((Object) readValue(classLoader, cls, new Class[0]));
            i--;
        }
    }

    private <T> ArrayList<T> readArrayListInternal(ClassLoader classLoader, Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        ArrayList<T> arrayList = new ArrayList<>(readInt);
        readListInternal(arrayList, readInt, classLoader, cls);
        return arrayList;
    }

    private void readArrayInternal(Object[] objArr, int i, ClassLoader classLoader) {
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = readValue(classLoader, (Class) null, new Class[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readArrayInternal(ClassLoader classLoader, Class<T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        T[] tArr = (T[]) ((Object[]) (cls == null ? new Object[readInt] : Array.newInstance((Class<?>) cls, readInt)));
        for (int i = 0; i < readInt; i++) {
            tArr[i] = readValue(classLoader, cls, new Class[0]);
        }
        return tArr;
    }

    private void readSparseArrayInternal(SparseArray sparseArray, int i, ClassLoader classLoader) {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            sparseArray.append(readInt(), readValue(classLoader));
            i--;
        }
    }

    private <T> SparseArray<T> readSparseArrayInternal(ClassLoader classLoader, Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        WifiMigration.AnonymousClass1 anonymousClass1 = (SparseArray<T>) new SparseArray(readInt);
        while (readInt > 0) {
            anonymousClass1.append(readInt(), readValue(classLoader, cls, new Class[0]));
            readInt--;
        }
        return anonymousClass1;
    }

    private void readSparseBooleanArrayInternal(SparseBooleanArray sparseBooleanArray, int i) {
        while (i > 0) {
            int readInt = readInt();
            boolean z = true;
            if (readByte() != 1) {
                z = false;
            }
            sparseBooleanArray.append(readInt, z);
            i--;
        }
    }

    private void readSparseIntArrayInternal(SparseIntArray sparseIntArray, int i) {
        while (i > 0) {
            sparseIntArray.append(readInt(), readInt());
            i--;
        }
    }

    public long getOpenAshmemSize() {
        return nativeGetOpenAshmemSize(this.mNativePtr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String valueTypeToString(int i) {
        switch (i) {
            case -1:
                return "VAL_NULL";
            case 0:
            case 22:
            default:
                return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "VAL_INTEGER";
            case 2:
                return "VAL_MAP";
            case 3:
                return "VAL_BUNDLE";
            case 4:
                return "VAL_PARCELABLE";
            case 5:
                return "VAL_SHORT";
            case 6:
                return "VAL_LONG";
            case 7:
                return "VAL_FLOAT";
            case 8:
                return "VAL_DOUBLE";
            case 9:
                return "VAL_BOOLEAN";
            case 10:
                return "VAL_CHARSEQUENCE";
            case 11:
                return "VAL_LIST";
            case 12:
                return "VAL_SPARSEARRAY";
            case 13:
                return "VAL_BYTEARRAY";
            case 14:
                return "VAL_STRINGARRAY";
            case 15:
                return "VAL_IBINDER";
            case 16:
                return "VAL_PARCELABLEARRAY";
            case 17:
                return "VAL_OBJECTARRAY";
            case 18:
                return "VAL_INTARRAY";
            case 19:
                return "VAL_LONGARRAY";
            case 20:
                return "VAL_BYTE";
            case 21:
                return "VAL_SERIALIZABLE";
            case 23:
                return "VAL_BOOLEANARRAY";
            case 24:
                return "VAL_CHARSEQUENCEARRAY";
            case 25:
                return "VAL_PERSISTABLEBUNDLE";
            case 26:
                return "VAL_SIZE";
            case 27:
                return "VAL_SIZEF";
            case 28:
                return "VAL_DOUBLEARRAY";
            case 29:
                return "VAL_CHAR";
            case 30:
                return "VAL_SHORTARRAY";
            case 31:
                return "VAL_CHARARRAY";
            case 32:
                return "VAL_FLOATARRAY";
        }
    }
}
