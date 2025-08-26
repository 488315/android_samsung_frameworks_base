package android.os;

import android.app.AppOpsManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
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
import com.android.internal.infra.PerUser;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.sume.core.controller.MediaController;
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
        Parcel parcelObtain = obtain();
        parcelObtain.markForBinder(iBinder);
        return parcelObtain;
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
        int iNativeMarshallArray;
        byteBuffer.getClass();
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int iPosition = byteBuffer.position();
        int iRemaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            iNativeMarshallArray = nativeMarshallBuffer(this.mNativePtr, byteBuffer, iPosition, iRemaining);
        } else if (byteBuffer.hasArray()) {
            iNativeMarshallArray = nativeMarshallArray(this.mNativePtr, byteBuffer.array(), byteBuffer.arrayOffset() + iPosition, iRemaining);
        } else {
            throw new IllegalArgumentException();
        }
        byteBuffer.position(iPosition + iNativeMarshallArray);
    }

    public final void unmarshall(byte[] bArr, int i, int i2) {
        nativeUnmarshall(this.mNativePtr, bArr, i, i2);
    }

    public final void unmarshall(ByteBuffer byteBuffer) {
        byteBuffer.getClass();
        int iPosition = byteBuffer.position();
        int iRemaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            nativeUnmarshallBuffer(this.mNativePtr, byteBuffer, iPosition, iRemaining);
        } else if (byteBuffer.hasArray()) {
            nativeUnmarshall(this.mNativePtr, byteBuffer.array(), byteBuffer.arrayOffset() + iPosition, iRemaining);
        } else {
            throw new IllegalArgumentException();
        }
        byteBuffer.position(iPosition + iRemaining);
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
            Object objRemove = arrayMap.remove(cls);
            if (objRemove != obj) {
                Log.wtf(TAG, "Expected to remove " + obj + " (with key=" + cls + ") but instead removed " + objRemove);
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
        int iDataAvail = dataAvail();
        if (iDataAvail <= 0) {
            return;
        }
        throw new BadParcelableException("Parcel data not fully consumed, unread size: " + iDataAvail);
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
        int iNativeWriteInt = nativeWriteInt(this.mNativePtr, i);
        if (iNativeWriteInt != 0) {
            nativeSignalExceptionForError(iNativeWriteInt);
        }
    }

    public final void writeLong(long j) {
        int iNativeWriteLong = nativeWriteLong(this.mNativePtr, j);
        if (iNativeWriteLong != 0) {
            nativeSignalExceptionForError(iNativeWriteLong);
        }
    }

    public final void writeFloat(float f) {
        int iNativeWriteFloat = nativeWriteFloat(this.mNativePtr, f);
        if (iNativeWriteFloat != 0) {
            nativeSignalExceptionForError(iNativeWriteFloat);
        }
    }

    public final void writeDouble(double d) {
        int iNativeWriteDouble = nativeWriteDouble(this.mNativePtr, d);
        if (iNativeWriteDouble != 0) {
            nativeSignalExceptionForError(iNativeWriteDouble);
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
        Set<Map.Entry<String, Object>> setEntrySet = map.entrySet();
        int size = setEntrySet.size();
        writeInt(size);
        for (Map.Entry<String, Object> entry : setEntrySet) {
            writeValue(entry.getKey());
            writeValue(entry.getValue());
            size--;
        }
        if (size != 0) {
            throw new BadParcelableException("Map size does not match number of entries!");
        }
    }

    void writeArrayMapInternal(ArrayMap<String, Object> arrayMap) throws IOException {
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

    public void writeArrayMap(ArrayMap<String, Object> arrayMap) throws IOException {
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

    public void writeArraySet(ArraySet<? extends Object> arraySet) throws IOException {
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
        int iMultiplyExact = 1;
        try {
            for (int i2 : iArr) {
                iMultiplyExact = Math.multiplyExact(iMultiplyExact, i2);
            }
        } catch (ArithmeticException e) {
            Log.e(TAG, "ArithmeticException occurred while multiplying dimensions " + e);
            SneakyThrow.sneakyThrow(new BadParcelableException("Estimated array length is too large. Array Dimensions:" + Arrays.toString(iArr)));
        }
        ensureWithinMemoryLimit(i, iMultiplyExact);
    }

    private void ensureWithinMemoryLimit(int i, int i2) {
        int iMultiplyExact;
        try {
            iMultiplyExact = Math.multiplyExact(i, i2);
        } catch (ArithmeticException e) {
            Log.e(TAG, "ArithmeticException occurred while multiplying values " + i + " and " + i2 + " Exception: " + e);
            StringBuilder sb = new StringBuilder("Estimated allocation size is too large. typeSize: ");
            sb.append(i);
            sb.append(" length: ");
            sb.append(i2);
            SneakyThrow.sneakyThrow(new BadParcelableException(sb.toString()));
            iMultiplyExact = 0;
        }
        boolean zIsDirectlyHandlingTransaction = Binder.isDirectlyHandlingTransaction();
        if (!zIsDirectlyHandlingTransaction || iMultiplyExact <= 1000000) {
            return;
        }
        Log.e(TAG, "Trying to Allocate " + iMultiplyExact + " memory, In Binder Transaction : " + zIsDirectlyHandlingTransaction);
        StringBuilder sb2 = new StringBuilder("Allocation of size ");
        sb2.append(iMultiplyExact);
        sb2.append(" is above allowed limit of 1MB");
        SneakyThrow.sneakyThrow(new BadParcelableException(sb2.toString()));
    }

    public final boolean[] createBooleanArray() {
        int i = readInt();
        ensureWithinMemoryLimit(4, i);
        if (i < 0 || i > (dataAvail() >> 2)) {
            return null;
        }
        boolean[] zArr = new boolean[i];
        for (int i2 = 0; i2 < i; i2++) {
            zArr[i2] = readInt() != 0;
        }
        return zArr;
    }

    public final void readBooleanArray(boolean[] zArr) {
        int i = readInt();
        if (i != zArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            zArr[i2] = readInt() != 0;
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
        int i = readInt();
        ensureWithinMemoryLimit(2, i);
        if (i < 0 || i > (dataAvail() >> 2)) {
            return null;
        }
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = (short) readInt();
        }
        return sArr;
    }

    public void readShortArray(short[] sArr) {
        int i = readInt();
        if (i != sArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = (short) readInt();
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
        int i = readInt();
        ensureWithinMemoryLimit(2, i);
        if (i < 0 || i > (dataAvail() >> 2)) {
            return null;
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) readInt();
        }
        return cArr;
    }

    public final void readCharArray(char[] cArr) {
        int i = readInt();
        if (i != cArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) readInt();
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
        int i = readInt();
        ensureWithinMemoryLimit(4, i);
        if (i < 0 || i > (dataAvail() >> 2)) {
            return null;
        }
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt();
        }
        return iArr;
    }

    public final void readIntArray(int[] iArr) {
        int i = readInt();
        if (i != iArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt();
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
        int i = readInt();
        ensureWithinMemoryLimit(8, i);
        if (i < 0 || i > (dataAvail() >> 3)) {
            return null;
        }
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readLong();
        }
        return jArr;
    }

    public final void readLongArray(long[] jArr) {
        int i = readInt();
        if (i != jArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readLong();
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
        int i = readInt();
        ensureWithinMemoryLimit(4, i);
        if (i < 0 || i > (dataAvail() >> 2)) {
            return null;
        }
        float[] fArr = new float[i];
        for (int i2 = 0; i2 < i; i2++) {
            fArr[i2] = readFloat();
        }
        return fArr;
    }

    public final void readFloatArray(float[] fArr) {
        int i = readInt();
        if (i != fArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            fArr[i2] = readFloat();
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
        int i = readInt();
        ensureWithinMemoryLimit(8, i);
        if (i < 0 || i > (dataAvail() >> 3)) {
            return null;
        }
        double[] dArr = new double[i];
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = readDouble();
        }
        return dArr;
    }

    public final void readDoubleArray(double[] dArr) {
        int i = readInt();
        if (i != dArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = readDouble();
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
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        if (i < 0) {
            return null;
        }
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = readString8();
        }
        return strArr;
    }

    public final void readString8Array(String[] strArr) {
        int i = readInt();
        if (i != strArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = readString8();
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
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        if (i < 0) {
            return null;
        }
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = readString16();
        }
        return strArr;
    }

    public final void readString16Array(String[] strArr) {
        int i = readInt();
        if (i != strArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = readString16();
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
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        if (i < 0) {
            return null;
        }
        IBinder[] iBinderArr = new IBinder[i];
        for (int i2 = 0; i2 < i; i2++) {
            iBinderArr[i2] = readStrongBinder();
        }
        return iBinderArr;
    }

    public final void readBinderArray(IBinder[] iBinderArr) {
        int i = readInt();
        if (i != iBinderArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            iBinderArr[i2] = readStrongBinder();
        }
    }

    public final <T extends IInterface> T[] createInterfaceArray(IntFunction<T[]> intFunction, Function<IBinder, T> function) {
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        if (i < 0) {
            return null;
        }
        T[] tArrApply = intFunction.apply(i);
        for (int i2 = 0; i2 < i; i2++) {
            tArrApply[i2] = function.apply(readStrongBinder());
        }
        return tArrApply;
    }

    public final <T extends IInterface> void readInterfaceArray(T[] tArr, Function<IBinder, T> function) {
        int i = readInt();
        if (i != tArr.length) {
            throw new BadParcelableException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            tArr[i2] = function.apply(readStrongBinder());
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

    public final void writeValue(Object obj) throws IOException {
        if (obj instanceof LazyValue) {
            ((LazyValue) obj).writeToParcel(this);
            return;
        }
        int valueType = getValueType(obj);
        writeInt(valueType);
        if (isLengthPrefixed(valueType)) {
            int iDataPosition = dataPosition();
            writeInt(-1);
            int iDataPosition2 = dataPosition();
            writeValue(valueType, obj);
            int iDataPosition3 = dataPosition();
            setDataPosition(iDataPosition);
            writeInt(iDataPosition3 - iDataPosition2);
            setDataPosition(iDataPosition3);
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

    public void writeValue(int i, Object obj) throws IOException {
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
        int i = readInt();
        int iDataPosition = dataPosition();
        if (i == 0) {
            T rawParceled = squashReadHelper.readRawParceled(this);
            ensureReadSquashableParcelables();
            this.mReadSquashableParcelables.put(iDataPosition, rawParceled);
            return rawParceled;
        }
        int i2 = iDataPosition - i;
        T t = (T) this.mReadSquashableParcelables.get(i2);
        if (t == null) {
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < this.mReadSquashableParcelables.size(); i3++) {
                sb.append(this.mReadSquashableParcelables.keyAt(i3));
                sb.append(' ');
            }
            Slog.wtfStack(TAG, "Map doesn't contain offset " + i2 + " : contains=" + sb.toString());
        }
        return t;
    }

    public final void writeSerializable(Serializable serializable) throws IOException {
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
        long jElapsedRealtime = sParcelExceptionStackTrace ? SystemClock.elapsedRealtime() : 0L;
        if (sParcelExceptionStackTrace && jElapsedRealtime - sLastWriteExceptionStackTrace > 1000) {
            sLastWriteExceptionStackTrace = jElapsedRealtime;
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
        int iDataPosition = dataPosition();
        writeInt(0);
        writeParcelable((Parcelable) exc, 1);
        int iDataPosition2 = dataPosition();
        setDataPosition(iDataPosition);
        writeInt(iDataPosition2 - iDataPosition);
        setDataPosition(iDataPosition2);
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
        int iDataPosition = dataPosition();
        writeInt(0);
        StackTraceElement[] stackTrace = th.getStackTrace();
        int iMin = Math.min(stackTrace.length, 5);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iMin; i++) {
            sb.append("\tat ");
            sb.append(stackTrace[i]);
            sb.append('\n');
        }
        writeString(sb.toString());
        int iDataPosition2 = dataPosition();
        setDataPosition(iDataPosition);
        writeInt(iDataPosition2 - iDataPosition);
        setDataPosition(iDataPosition2);
    }

    public final void writeNoException() {
        AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
        if (StrictMode.hasGatheredViolations()) {
            writeInt(-128);
            int iDataPosition = dataPosition();
            writeInt(0);
            StrictMode.writeGatheredViolationsToParcel(this);
            int iDataPosition2 = dataPosition();
            setDataPosition(iDataPosition);
            writeInt(iDataPosition2 - iDataPosition);
            setDataPosition(iDataPosition2);
            return;
        }
        writeInt(0);
    }

    private void writeNoException$ravenwood() {
        writeInt(0);
    }

    public final void readException() {
        int exceptionCode = readExceptionCode();
        if (exceptionCode != 0) {
            readException(exceptionCode, readString());
        }
    }

    public final int readExceptionCode() {
        int i = readInt();
        if (i == -127) {
            AppOpsManager.readAndLogNotedAppops(this);
            i = readInt();
        }
        if (i != -128) {
            return i;
        }
        if (readInt() == 0) {
            Log.e(TAG, "Unexpected zero-sized Parcel reply header.");
            return 0;
        }
        StrictMode.readAndHandleBinderCallViolations(this);
        return 0;
    }

    public final void readException(int i, String str) {
        String string = readInt() > 0 ? readString() : null;
        Exception excCreateException = createException(i, str);
        if (string != null) {
            ExceptionUtils.appendCause(excCreateException, new RemoteException("Remote stack trace:\n" + string, null, false, false));
        }
        SneakyThrow.sneakyThrow(excCreateException);
    }

    private Exception createException(int i, String str) {
        Exception excCreateExceptionOrNull = createExceptionOrNull(i, str);
        if (excCreateExceptionOrNull != null) {
            return excCreateExceptionOrNull;
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
        IBinder iBinderNativeReadStrongBinder = nativeReadStrongBinder(this.mNativePtr);
        if (iBinderNativeReadStrongBinder != null && hasFlags(3)) {
            Binder.allowBlocking(iBinderNativeReadStrongBinder);
        }
        return iBinderNativeReadStrongBinder;
    }

    public final ParcelFileDescriptor readFileDescriptor() {
        FileDescriptor fileDescriptorNativeReadFileDescriptor = nativeReadFileDescriptor(this.mNativePtr);
        if (fileDescriptorNativeReadFileDescriptor != null) {
            return new ParcelFileDescriptor(fileDescriptorNativeReadFileDescriptor);
        }
        return null;
    }

    public final FileDescriptor readRawFileDescriptor() {
        return nativeReadFileDescriptor(this.mNativePtr);
    }

    public final FileDescriptor[] createRawFileDescriptorArray() {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        FileDescriptor[] fileDescriptorArr = new FileDescriptor[i];
        for (int i2 = 0; i2 < i; i2++) {
            fileDescriptorArr[i2] = readRawFileDescriptor();
        }
        return fileDescriptorArr;
    }

    public final void readRawFileDescriptorArray(FileDescriptor[] fileDescriptorArr) {
        int i = readInt();
        if (i != fileDescriptorArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            fileDescriptorArr[i2] = readRawFileDescriptor();
        }
    }

    public final byte readByte() {
        return (byte) (readInt() & 255);
    }

    @Deprecated
    public final void readMap(Map map, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        readMapInternal(map, classLoader, null, null);
    }

    public <K, V> void readMap(Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) throws ClassNotFoundException, IOException {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(cls2);
        readMapInternal(map, classLoader, cls, cls2);
    }

    @Deprecated
    public final void readList(List list, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        readListInternal(list, readInt(), classLoader, null);
    }

    public <T> void readList(List<? super T> list, ClassLoader classLoader, Class<T> cls) throws ClassNotFoundException, IOException {
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
        int i = readInt();
        if (i < 0) {
            return null;
        }
        Bundle bundle = new Bundle(this, i);
        if (classLoader != null) {
            bundle.setClassLoader(classLoader);
        }
        return bundle;
    }

    public final PersistableBundle readPersistableBundle() {
        return readPersistableBundle(null);
    }

    public final PersistableBundle readPersistableBundle(ClassLoader classLoader) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        PersistableBundle persistableBundle = new PersistableBundle(this, i);
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
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        CharSequence[] charSequenceArr = new CharSequence[i];
        for (int i2 = 0; i2 < i; i2++) {
            charSequenceArr[i2] = readCharSequence();
        }
        return charSequenceArr;
    }

    public final ArrayList<CharSequence> readCharSequenceList() {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayList<CharSequence> arrayList = new ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
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
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(i);
        readSparseBooleanArrayInternal(sparseBooleanArray, i);
        return sparseBooleanArray;
    }

    public final SparseIntArray readSparseIntArray() {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        SparseIntArray sparseIntArray = new SparseIntArray(i);
        readSparseIntArrayInternal(sparseIntArray, i);
        return sparseIntArray;
    }

    public final <T> ArrayList<T> createTypedArrayList(Parcelable.Creator<T> creator) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        MediaController.AnonymousClass2 anonymousClass2 = (ArrayList<T>) new ArrayList(i);
        while (i > 0) {
            anonymousClass2.add(readTypedObject(creator));
            i--;
        }
        return anonymousClass2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void readTypedList(List<T> list, Parcelable.Creator<T> creator) {
        int size = list.size();
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        int i2 = 0;
        while (i2 < size && i2 < i) {
            list.set(i2, readTypedObject(creator));
            i2++;
        }
        while (i2 < i) {
            list.add(readTypedObject(creator));
            i2++;
        }
        while (i2 < size) {
            list.remove(i);
            i2++;
        }
    }

    public final <T extends Parcelable> SparseArray<T> createTypedSparseArray(Parcelable.Creator<T> creator) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        PerUser perUser = (SparseArray<T>) new SparseArray(i);
        for (int i2 = 0; i2 < i; i2++) {
            perUser.append(readInt(), (Parcelable) readTypedObject(creator));
        }
        return perUser;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends Parcelable> ArrayMap<String, T> createTypedArrayMap(Parcelable.Creator<T> creator) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayMap<String, T> arrayMap = (ArrayMap<String, T>) new ArrayMap(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayMap.append(readString(), (Parcelable) readTypedObject(creator));
        }
        return arrayMap;
    }

    public final ArrayList<String> createStringArrayList() {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayList<String> arrayList = new ArrayList<>(i);
        while (i > 0) {
            arrayList.add(readString());
            i--;
        }
        return arrayList;
    }

    public final ArrayList<IBinder> createBinderArrayList() {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayList<IBinder> arrayList = new ArrayList<>(i);
        while (i > 0) {
            arrayList.add(readStrongBinder());
            i--;
        }
        return arrayList;
    }

    public final <T extends IInterface> ArrayList<T> createInterfaceArrayList(Function<IBinder, T> function) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayList<T> arrayList = new ArrayList<>(i);
        while (i > 0) {
            arrayList.add(function.apply(readStrongBinder()));
            i--;
        }
        return arrayList;
    }

    public final void readStringList(List<String> list) {
        int size = list.size();
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        int i2 = 0;
        while (i2 < size && i2 < i) {
            list.set(i2, readString());
            i2++;
        }
        while (i2 < i) {
            list.add(readString());
            i2++;
        }
        while (i2 < size) {
            list.remove(i);
            i2++;
        }
    }

    public final void readBinderList(List<IBinder> list) {
        int size = list.size();
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        int i2 = 0;
        while (i2 < size && i2 < i) {
            list.set(i2, readStrongBinder());
            i2++;
        }
        while (i2 < i) {
            list.add(readStrongBinder());
            i2++;
        }
        while (i2 < size) {
            list.remove(i);
            i2++;
        }
    }

    public final <T extends IInterface> void readInterfaceList(List<T> list, Function<IBinder, T> function) {
        int size = list.size();
        int i = readInt();
        ensureWithinMemoryLimit(1, i);
        int i2 = 0;
        while (i2 < size && i2 < i) {
            list.set(i2, function.apply(readStrongBinder()));
            i2++;
        }
        while (i2 < i) {
            list.add(function.apply(readStrongBinder()));
            i2++;
        }
        while (i2 < size) {
            list.remove(i);
            i2++;
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
        int i = readInt();
        if (i == -1) {
            list.clear();
            return list;
        }
        ensureWithinMemoryLimit(1, i);
        int size = list.size();
        int i2 = 0;
        while (i2 < size && i2 < i) {
            list.set(i2, readParcelableInternal(classLoader, cls));
            i2++;
        }
        while (i2 < i) {
            list.add(readParcelableInternal(classLoader, cls));
            i2++;
        }
        while (i2 < size) {
            list.remove(i);
            i2++;
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T[] createTypedArray(Parcelable.Creator<T> creator) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        T[] tArrNewArray = creator.newArray(i);
        for (int i2 = 0; i2 < i; i2++) {
            tArrNewArray[i2] = readTypedObject(creator);
        }
        return tArrNewArray;
    }

    public final <T> void readTypedArray(T[] tArr, Parcelable.Creator<T> creator) {
        int i = readInt();
        if (i != tArr.length) {
            throw new RuntimeException("bad array lengths");
        }
        for (int i2 = 0; i2 < i; i2++) {
            tArr[i2] = readTypedObject(creator);
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
            int i = readInt();
            if (i == Array.getLength(t)) {
                for (int i2 = 0; i2 < i; i2++) {
                    readFixedArray(Array.get(t, i2));
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + i);
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
            int i = readInt();
            if (i == Array.getLength(t)) {
                for (int i2 = 0; i2 < i; i2++) {
                    readFixedArray((Parcel) Array.get(t, i2), (Function) function);
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + i);
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
            int i = readInt();
            if (i == Array.getLength(t)) {
                for (int i2 = 0; i2 < i; i2++) {
                    readFixedArray((Parcel) Array.get(t, i2), (Parcelable.Creator) creator);
                }
                return;
            }
            throw new BadParcelableException("Bad length: expected " + Array.getLength(t) + ", but got " + i);
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
                int i = readInt();
                if (i < 0) {
                    return null;
                }
                if (i != iArr[0]) {
                    throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + i);
                }
                Class<?> componentType2 = componentType.getComponentType();
                while (componentType2.isArray()) {
                    componentType2 = componentType2.getComponentType();
                }
                ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
                T t2 = (T) Array.newInstance(componentType2, iArr);
                for (int i2 = 0; i2 < i; i2++) {
                    readFixedArray(Array.get(t2, i2));
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
            int i = readInt();
            if (i < 0) {
                return null;
            }
            if (i != iArr[0]) {
                throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + i);
            }
            Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) Array.newInstance(componentType2, iArr);
            for (int i2 = 0; i2 < i; i2++) {
                readFixedArray((Parcel) Array.get(t2, i2), (Function) function);
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
            int i = readInt();
            if (i < 0) {
                return null;
            }
            if (i != iArr[0]) {
                throw new BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + i);
            }
            Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) Array.newInstance(componentType2, iArr);
            for (int i2 = 0; i2 < i; i2++) {
                readFixedArray((Parcel) Array.get(t2, i2), (Parcelable.Creator) creator);
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

    public final Object readValue(ClassLoader classLoader) throws ClassNotFoundException, IOException {
        return readValue(classLoader, (Class) null, new Class[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T readValue(ClassLoader classLoader, Class<T> cls, Class<?>... clsArr) throws ClassNotFoundException, IOException {
        int i = readInt();
        if (isLengthPrefixed(i)) {
            int i2 = readInt();
            int iDataPosition = dataPosition();
            T t = (T) readValue(i, classLoader, cls, clsArr);
            int iDataPosition2 = dataPosition() - iDataPosition;
            if (iDataPosition2 != i2) {
                Slog.wtfStack(TAG, "Unparcelling of " + t + " of type " + valueTypeToString(i) + "  consumed " + iDataPosition2 + " bytes, but " + i2 + " expected.");
            }
            return t;
        }
        return (T) readValue(i, classLoader, cls, clsArr);
    }

    private Object readLazyValue(ClassLoaderProvider classLoaderProvider) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        int iDataPosition = dataPosition();
        int i = readInt();
        if (isLengthPrefixed(i)) {
            int i2 = readInt();
            if (i2 < 0) {
                return null;
            }
            int iAddOrThrow = MathUtils.addOrThrow(dataPosition(), i2);
            setDataPosition(iAddOrThrow);
            return new LazyValue(this, iDataPosition, iAddOrThrow - iDataPosition, i, classLoaderProvider);
        }
        return readValue(i, getClassLoader(classLoaderProvider), (Class) null);
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
                        int iDataPosition = parcel.dataPosition();
                        try {
                            parcel.setDataPosition(this.mPosition);
                            this.mObject = parcel.readValue(this.mLoaderProvider.getClassLoader(), cls, clsArr);
                            parcel.setDataPosition(iDataPosition);
                            this.mSource = null;
                        } catch (Throwable th) {
                            parcel.setDataPosition(iDataPosition);
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

    private <T> T readValue(int i, ClassLoader classLoader, Class<T> cls) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        return (T) readValue(i, classLoader, cls, null);
    }

    private <T> T readValue(int i, ClassLoader classLoader, Class<T> cls, Class<?>... clsArr) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
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

    private <T> T readParcelableInternal(ClassLoader classLoader, Class<T> cls) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException {
        Parcelable.Creator<T> parcelableCreatorInternal = readParcelableCreatorInternal(classLoader, cls);
        if (parcelableCreatorInternal == null) {
            return null;
        }
        if (parcelableCreatorInternal instanceof Parcelable.ClassLoaderCreator) {
            return (T) ((Parcelable.ClassLoaderCreator) parcelableCreatorInternal).createFromParcel(this, classLoader);
        }
        return parcelableCreatorInternal.createFromParcel(this);
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

    private <T> Parcelable.Creator<T> readParcelableCreatorInternal(ClassLoader classLoader, Class<T> cls) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException {
        Pair<Parcelable.Creator<?>, Class<?>> pair;
        ClassLoader classLoader2;
        String string = readString();
        if (string == null) {
            return null;
        }
        HashMap<ClassLoader, HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>>> map = sPairedCreators;
        synchronized (map) {
            HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>> map2 = map.get(classLoader);
            if (map2 == null) {
                map.put(classLoader, new HashMap<>());
                mCreators.put(classLoader, new HashMap<>());
                pair = null;
            } else {
                pair = map2.get(string);
            }
        }
        if (pair != null) {
            Parcelable.Creator<T> creator = (Parcelable.Creator) pair.first;
            Class<?> cls2 = pair.second;
            if (cls == null || cls.isAssignableFrom(cls2)) {
                return creator;
            }
            throw new BadTypeParcelableException("Parcelable creator " + string + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        if (classLoader == null) {
            try {
                classLoader2 = getClass().getClassLoader();
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Class not found when unmarshalling: " + string, e);
                throw new BadParcelableException("ClassNotFoundException when unmarshalling: " + string, e);
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Illegal access when unmarshalling: " + string, e2);
                throw new BadParcelableException("IllegalAccessException when unmarshalling: " + string, e2);
            } catch (NoSuchFieldException e3) {
                throw new BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + string, e3);
            }
        } else {
            classLoader2 = classLoader;
        }
        Class<?> cls3 = Class.forName(string, false, classLoader2);
        if (!Parcelable.class.isAssignableFrom(cls3)) {
            throw new BadParcelableException("Parcelable protocol requires subclassing from Parcelable on class " + string);
        }
        if (cls != null && !cls.isAssignableFrom(cls3)) {
            throw new BadTypeParcelableException("Parcelable creator " + string + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        Field field = cls3.getField("CREATOR");
        if ((field.getModifiers() & 8) == 0) {
            throw new BadParcelableException("Parcelable protocol requires the CREATOR object to be static on class " + string);
        }
        if (!Parcelable.Creator.class.isAssignableFrom(field.getType())) {
            throw new BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + string);
        }
        Parcelable.Creator<T> creator2 = (Parcelable.Creator) field.get(null);
        if (creator2 == null) {
            throw new BadParcelableException("Parcelable protocol requires a non-null Parcelable.Creator object called CREATOR on class " + string);
        }
        synchronized (map) {
            map.get(classLoader).put(string, Pair.create(creator2, cls3));
            mCreators.get(classLoader).put(string, creator2);
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
    private <T> T[] readParcelableArrayInternal(ClassLoader classLoader, Class<T> cls) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, NegativeArraySizeException {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        T[] tArr = (T[]) ((Object[]) (cls == null ? new Parcelable[i] : Array.newInstance((Class<?>) cls, i)));
        for (int i2 = 0; i2 < i; i2++) {
            tArr[i2] = readParcelableInternal(classLoader, cls);
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

    private <T> T readSerializableInternal(final ClassLoader classLoader, Class<T> cls) throws ClassNotFoundException, IOException {
        String string = readString();
        if (string == null) {
            return null;
        }
        if (cls != null && classLoader != null) {
            try {
                Class<?> cls2 = Class.forName(string, false, classLoader);
                if (!cls.isAssignableFrom(cls2)) {
                    throw new BadTypeParcelableException("Serializable object " + cls2.getName() + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
                }
            } catch (IOException e) {
                throw new BadParcelableException("Parcelable encountered IOException reading a Serializable object (name = " + string + NavigationBarInflaterView.KEY_CODE_END, e);
            } catch (ClassNotFoundException e2) {
                throw new BadParcelableException("Parcelable encountered ClassNotFoundException reading a Serializable object (name = " + string + NavigationBarInflaterView.KEY_CODE_END, e2);
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

    void readMapInternal(Map map, int i, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        readMapInternal(map, i, classLoader, null, null);
    }

    private <K, V> HashMap<K, V> readHashMapInternal(ClassLoader classLoader, Class<? extends K> cls, Class<? extends V> cls2) throws ClassNotFoundException, IOException {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        HashMap<K, V> map = new HashMap<>(i);
        readMapInternal(map, i, classLoader, cls, cls2);
        return map;
    }

    private <K, V> void readMapInternal(Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) throws ClassNotFoundException, IOException {
        readMapInternal(map, readInt(), classLoader, cls, cls2);
    }

    private <K, V> void readMapInternal(Map<? super K, ? super V> map, int i, ClassLoader classLoader, Class<K> cls, Class<V> cls2) throws ClassNotFoundException, IOException {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            map.put((Object) readValue(classLoader, cls, new Class[0]), (Object) readValue(classLoader, cls2, new Class[0]));
            i--;
        }
    }

    private void readArrayMapInternal(ArrayMap<? super String, Object> arrayMap, int i, ClassLoaderProvider classLoaderProvider) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        readArrayMap(arrayMap, i, true, false, classLoaderProvider, null);
    }

    void readArrayMap(ArrayMap<? super String, Object> arrayMap, int i, boolean z, boolean z2, ClassLoaderProvider classLoaderProvider, int[] iArr) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            String string = readString();
            Object lazyValue = z2 ? readLazyValue(classLoaderProvider) : readValue(getClassLoader(classLoaderProvider));
            if (lazyValue instanceof LazyValue) {
                iArr[0] = iArr[0] + 1;
            }
            if (z) {
                arrayMap.append(string, lazyValue);
            } else {
                arrayMap.put(string, lazyValue);
            }
            i--;
        }
        if (z) {
            arrayMap.validate();
        }
    }

    public void readArrayMap(ArrayMap<? super String, Object> arrayMap, ClassLoaderProvider classLoaderProvider) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        int i = readInt();
        if (i < 0) {
            return;
        }
        readArrayMapInternal(arrayMap, i, classLoaderProvider);
    }

    public ArraySet<? extends Object> readArraySet(ClassLoader classLoader) {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArraySet<? extends Object> arraySet = new ArraySet<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            arraySet.append(readValue(classLoader));
        }
        return arraySet;
    }

    private void readListInternal(List list, int i, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        readListInternal(list, i, classLoader, null);
    }

    private <T> void readListInternal(List<? super T> list, int i, ClassLoader classLoader, Class<T> cls) throws ClassNotFoundException, IOException {
        ensureWithinMemoryLimit(1, i);
        while (i > 0) {
            list.add((Object) readValue(classLoader, cls, new Class[0]));
            i--;
        }
    }

    private <T> ArrayList<T> readArrayListInternal(ClassLoader classLoader, Class<? extends T> cls) throws ClassNotFoundException, IOException {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        ArrayList<T> arrayList = new ArrayList<>(i);
        readListInternal(arrayList, i, classLoader, cls);
        return arrayList;
    }

    private void readArrayInternal(Object[] objArr, int i, ClassLoader classLoader) {
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = readValue(classLoader, (Class) null, new Class[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readArrayInternal(ClassLoader classLoader, Class<T> cls) throws ClassNotFoundException, IOException, NegativeArraySizeException {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        T[] tArr = (T[]) ((Object[]) (cls == null ? new Object[i] : Array.newInstance((Class<?>) cls, i)));
        for (int i2 = 0; i2 < i; i2++) {
            tArr[i2] = readValue(classLoader, cls, new Class[0]);
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

    private <T> SparseArray<T> readSparseArrayInternal(ClassLoader classLoader, Class<? extends T> cls) throws ClassNotFoundException, IOException {
        int i = readInt();
        if (i < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, i);
        PerUser perUser = (SparseArray<T>) new SparseArray(i);
        while (i > 0) {
            perUser.append(readInt(), readValue(classLoader, cls, new Class[0]));
            i--;
        }
        return perUser;
    }

    private void readSparseBooleanArrayInternal(SparseBooleanArray sparseBooleanArray, int i) {
        while (i > 0) {
            int i2 = readInt();
            boolean z = true;
            if (readByte() != 1) {
                z = false;
            }
            sparseBooleanArray.append(i2, z);
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
