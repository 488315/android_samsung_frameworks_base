package android.os;

import android.app.AppOpsManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import java.util.ArrayList;
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

  /* renamed from: OK */
  private static final int f335OK = 0;
  private static final int POOL_SIZE = 32;
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
  private static volatile long sLastWriteExceptionStackTrace;
  private static Parcel sOwnedPool;
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
  private static int sOwnedPoolSize = 0;
  private static int sHolderPoolSize = 0;
  public static final Parcelable.Creator<String> STRING_CREATOR =
      new Parcelable.Creator<String>() { // from class: android.os.Parcel.1
        @Override // android.os.Parcelable.Creator
        public String createFromParcel(Parcel source) {
          return source.readString();
        }

        @Override // android.os.Parcelable.Creator
        public String[] newArray(int size) {
          return new String[size];
        }
      };
  private static final HashMap<ClassLoader, HashMap<String, Parcelable.Creator<?>>> mCreators =
      new HashMap<>();
  private static final HashMap<ClassLoader, HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>>>
      sPairedCreators = new HashMap<>();
  private boolean mRecycled = false;
  private String interfaceName = null;
  private ReadWriteHelper mReadWriteHelper = ReadWriteHelper.DEFAULT;
  private boolean mAllowSquashing = false;

  @Retention(RetentionPolicy.SOURCE)
  public @interface ParcelFlags {}

  public interface SquashReadHelper<T> {
    T readRawParceled(Parcel parcel);
  }

  public static native long getGlobalAllocCount();

  public static native long getGlobalAllocSize();

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

    public void writeString8(Parcel p, String s) {
      p.writeString8NoHelper(s);
    }

    public void writeString16(Parcel p, String s) {
      p.writeString16NoHelper(s);
    }

    public String readString8(Parcel p) {
      return p.readString8NoHelper();
    }

    public String readString16(Parcel p) {
      return p.readString16NoHelper();
    }
  }

  public static Parcel obtain() {
    Parcel res = null;
    synchronized (sPoolSync) {
      Parcel parcel = sOwnedPool;
      if (parcel != null) {
        res = parcel;
        sOwnedPool = res.mPoolNext;
        res.mPoolNext = null;
        sOwnedPoolSize--;
      }
    }
    if (res == null) {
      Parcel res2 = new Parcel(0L);
      return res2;
    }
    res.mRecycled = false;
    res.mReadWriteHelper = ReadWriteHelper.DEFAULT;
    return res;
  }

  public static Parcel obtain(IBinder binder) {
    Parcel parcel = obtain();
    parcel.markForBinder(binder);
    return parcel;
  }

  public final void recycle() {
    if (this.mRecycled) {
      Log.wtf(
          TAG,
          "Recycle called on unowned Parcel. (recycle twice?) Here: "
              + Log.getStackTraceString(new Throwable())
              + " Original recycle call (if DEBUG_RECYCLE): ",
          this.mStack);
      return;
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

  public void setReadWriteHelper(ReadWriteHelper helper) {
    this.mReadWriteHelper = helper != null ? helper : ReadWriteHelper.DEFAULT;
  }

  public boolean hasReadWriteHelper() {
    ReadWriteHelper readWriteHelper = this.mReadWriteHelper;
    return (readWriteHelper == null || readWriteHelper == ReadWriteHelper.DEFAULT) ? false : true;
  }

  public final void markSensitive() {
    nativeMarkSensitive(this.mNativePtr);
  }

  private void markForBinder(IBinder binder) {
    nativeMarkForBinder(this.mNativePtr, binder);
  }

  public final boolean isForRpc() {
    return nativeIsForRpc(this.mNativePtr);
  }

  public int getFlags() {
    return this.mFlags;
  }

  public void setFlags(int flags) {
    this.mFlags = flags;
  }

  public void addFlags(int flags) {
    this.mFlags |= flags;
  }

  private boolean hasFlags(int flags) {
    return (this.mFlags & flags) == flags;
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

  public final void setDataSize(int size) {
    nativeSetDataSize(this.mNativePtr, size);
  }

  public final void setDataPosition(int pos) {
    nativeSetDataPosition(this.mNativePtr, pos);
  }

  public final void setDataCapacity(int size) {
    nativeSetDataCapacity(this.mNativePtr, size);
  }

  public final boolean pushAllowFds(boolean allowFds) {
    return nativePushAllowFds(this.mNativePtr, allowFds);
  }

  public final void restoreAllowFds(boolean lastValue) {
    nativeRestoreAllowFds(this.mNativePtr, lastValue);
  }

  public final byte[] marshall() {
    return nativeMarshall(this.mNativePtr);
  }

  public final void unmarshall(byte[] data, int offset, int length) {
    nativeUnmarshall(this.mNativePtr, data, offset, length);
  }

  public final void appendFrom(Parcel parcel, int offset, int length) {
    nativeAppendFrom(this.mNativePtr, parcel.mNativePtr, offset, length);
  }

  public int compareData(Parcel other) {
    return nativeCompareData(this.mNativePtr, other.mNativePtr);
  }

  public static boolean compareData(Parcel a, int offsetA, Parcel b, int offsetB, int length) {
    return nativeCompareDataInRange(a.mNativePtr, offsetA, b.mNativePtr, offsetB, length);
  }

  public final void setClassCookie(Class clz, Object cookie) {
    if (this.mClassCookies == null) {
      this.mClassCookies = new ArrayMap<>();
    }
    this.mClassCookies.put(clz, cookie);
  }

  public final Object getClassCookie(Class clz) {
    ArrayMap<Class, Object> arrayMap = this.mClassCookies;
    if (arrayMap != null) {
      return arrayMap.get(clz);
    }
    return null;
  }

  public final void adoptClassCookies(Parcel from) {
    this.mClassCookies = from.mClassCookies;
  }

  public Map<Class, Object> copyClassCookies() {
    return new ArrayMap(this.mClassCookies);
  }

  public void putClassCookies(Map<Class, Object> cookies) {
    if (cookies == null) {
      return;
    }
    if (this.mClassCookies == null) {
      this.mClassCookies = new ArrayMap<>();
    }
    this.mClassCookies.putAll(cookies);
  }

  public boolean hasFileDescriptors() {
    return nativeHasFileDescriptors(this.mNativePtr);
  }

  public boolean hasFileDescriptors(int offset, int length) {
    return nativeHasFileDescriptorsInRange(this.mNativePtr, offset, length);
  }

  public static boolean hasFileDescriptors(Object value) {
    if (value instanceof Parcel) {
      Parcel parcel = (Parcel) value;
      return parcel.hasFileDescriptors();
    }
    if (value instanceof LazyValue) {
      LazyValue lazy = (LazyValue) value;
      return lazy.hasFileDescriptors();
    }
    if (value instanceof Parcelable) {
      Parcelable parcelable = (Parcelable) value;
      return (parcelable.describeContents() & 1) != 0;
    }
    if (value instanceof ArrayMap) {
      ArrayMap<?, ?> map = (ArrayMap) value;
      int n = map.size();
      for (int i = 0; i < n; i++) {
        if (hasFileDescriptors(map.keyAt(i)) || hasFileDescriptors(map.valueAt(i))) {
          return true;
        }
      }
      return false;
    }
    if (value instanceof Map) {
      for (Map.Entry<?, ?> entry : ((Map) value).entrySet()) {
        if (hasFileDescriptors(entry.getKey()) || hasFileDescriptors(entry.getValue())) {
          return true;
        }
      }
      return false;
    }
    if (value instanceof List) {
      List<?> list = (List) value;
      int n2 = list.size();
      for (int i2 = 0; i2 < n2; i2++) {
        if (hasFileDescriptors(list.get(i2))) {
          return true;
        }
      }
      return false;
    }
    if (value instanceof SparseArray) {
      SparseArray<?> array = (SparseArray) value;
      int n3 = array.size();
      for (int i3 = 0; i3 < n3; i3++) {
        if (hasFileDescriptors(array.valueAt(i3))) {
          return true;
        }
      }
      return false;
    }
    if (value instanceof Object[]) {
      for (Object obj : (Object[]) value) {
        if (hasFileDescriptors(obj)) {
          return true;
        }
      }
      return false;
    }
    getValueType(value);
    return false;
  }

  public final void writeInterfaceToken(String interfaceName) {
    this.interfaceName = interfaceName;
    nativeWriteInterfaceToken(this.mNativePtr, interfaceName);
  }

  public String getInterfaceName() {
    return this.interfaceName;
  }

  public final void enforceInterface(String interfaceName) {
    nativeEnforceInterface(this.mNativePtr, interfaceName);
  }

  public void enforceNoDataAvail() {
    int n = dataAvail();
    if (n > 0) {
      throw new BadParcelableException("Parcel data not fully consumed, unread size: " + n);
    }
  }

  public boolean replaceCallingWorkSourceUid(int workSourceUid) {
    return nativeReplaceCallingWorkSourceUid(this.mNativePtr, workSourceUid);
  }

  public int readCallingWorkSourceUid() {
    return nativeReadCallingWorkSourceUid(this.mNativePtr);
  }

  public final void writeByteArray(byte[] b) {
    writeByteArray(b, 0, b != null ? b.length : 0);
  }

  public final void writeByteArray(byte[] b, int offset, int len) {
    if (b == null) {
      writeInt(-1);
    } else {
      ArrayUtils.throwsIfOutOfBounds(b.length, offset, len);
      nativeWriteByteArray(this.mNativePtr, b, offset, len);
    }
  }

  public final void writeBlob(byte[] b) {
    writeBlob(b, 0, b != null ? b.length : 0);
  }

  public final void writeBlob(byte[] b, int offset, int len) {
    if (b == null) {
      writeInt(-1);
    } else {
      ArrayUtils.throwsIfOutOfBounds(b.length, offset, len);
      nativeWriteBlob(this.mNativePtr, b, offset, len);
    }
  }

  public final void writeInt(int val) {
    int err = nativeWriteInt(this.mNativePtr, val);
    if (err != 0) {
      nativeSignalExceptionForError(err);
    }
  }

  public final void writeLong(long val) {
    int err = nativeWriteLong(this.mNativePtr, val);
    if (err != 0) {
      nativeSignalExceptionForError(err);
    }
  }

  public final void writeFloat(float val) {
    int err = nativeWriteFloat(this.mNativePtr, val);
    if (err != 0) {
      nativeSignalExceptionForError(err);
    }
  }

  public final void writeDouble(double val) {
    int err = nativeWriteDouble(this.mNativePtr, val);
    if (err != 0) {
      nativeSignalExceptionForError(err);
    }
  }

  public final void writeString(String val) {
    writeString16(val);
  }

  public final void writeString8(String val) {
    this.mReadWriteHelper.writeString8(this, val);
  }

  public final void writeString16(String val) {
    this.mReadWriteHelper.writeString16(this, val);
  }

  public void writeStringNoHelper(String val) {
    writeString16NoHelper(val);
  }

  public void writeString8NoHelper(String val) {
    nativeWriteString8(this.mNativePtr, val);
  }

  public void writeString16NoHelper(String val) {
    nativeWriteString16(this.mNativePtr, val);
  }

  public final void writeBoolean(boolean z) {
    writeInt(z ? 1 : 0);
  }

  public final void writeCharSequence(CharSequence val) {
    TextUtils.writeToParcel(val, this, 0);
  }

  public final void writeStrongBinder(IBinder val) {
    nativeWriteStrongBinder(this.mNativePtr, val);
  }

  public final void writeStrongInterface(IInterface val) {
    writeStrongBinder(val == null ? null : val.asBinder());
  }

  public final void writeFileDescriptor(FileDescriptor val) {
    nativeWriteFileDescriptor(this.mNativePtr, val);
  }

  public final void writeRawFileDescriptor(FileDescriptor val) {
    nativeWriteFileDescriptor(this.mNativePtr, val);
  }

  public final void writeRawFileDescriptorArray(FileDescriptor[] value) {
    if (value != null) {
      int N = value.length;
      writeInt(N);
      for (FileDescriptor fileDescriptor : value) {
        writeRawFileDescriptor(fileDescriptor);
      }
      return;
    }
    writeInt(-1);
  }

  public final void writeByte(byte val) {
    writeInt(val);
  }

  public final void writeMap(Map val) {
    writeMapInternal(val);
  }

  void writeMapInternal(Map<String, Object> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    Set<Map.Entry<String, Object>> entries = val.entrySet();
    int size = entries.size();
    writeInt(size);
    for (Map.Entry<String, Object> e : entries) {
      writeValue(e.getKey());
      writeValue(e.getValue());
      size--;
    }
    if (size != 0) {
      throw new BadParcelableException("Map size does not match number of entries!");
    }
  }

  void writeArrayMapInternal(ArrayMap<String, Object> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeString(val.keyAt(i));
      writeValue(val.valueAt(i));
    }
  }

  public void writeArrayMap(ArrayMap<String, Object> val) {
    writeArrayMapInternal(val);
  }

  public <T extends Parcelable> void writeTypedArrayMap(
      ArrayMap<String, T> val, int parcelableFlags) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int count = val.size();
    writeInt(count);
    for (int i = 0; i < count; i++) {
      writeString(val.keyAt(i));
      writeTypedObject(val.valueAt(i), parcelableFlags);
    }
  }

  public void writeArraySet(ArraySet<? extends Object> val) {
    int size = val != null ? val.size() : -1;
    writeInt(size);
    for (int i = 0; i < size; i++) {
      writeValue(val.valueAt(i));
    }
  }

  public final void writeBundle(Bundle val) {
    if (val == null) {
      writeInt(-1);
    } else {
      val.writeToParcel(this, 0);
    }
  }

  public final void writePersistableBundle(PersistableBundle val) {
    if (val == null) {
      writeInt(-1);
    } else {
      val.writeToParcel(this, 0);
    }
  }

  public final void writeSize(Size val) {
    writeInt(val.getWidth());
    writeInt(val.getHeight());
  }

  public final void writeSizeF(SizeF val) {
    writeFloat(val.getWidth());
    writeFloat(val.getHeight());
  }

  public final void writeList(List val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeValue(val.get(i));
    }
  }

  public final void writeArray(Object[] val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.length;
    writeInt(N);
    for (Object obj : val) {
      writeValue(obj);
    }
  }

  public final <T> void writeSparseArray(SparseArray<T> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeInt(val.keyAt(i));
      writeValue(val.valueAt(i));
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

  public final void writeSparseIntArray(SparseIntArray val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeInt(val.keyAt(i));
      writeInt(val.valueAt(i));
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

  public final boolean[] createBooleanArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 2)) {
      boolean[] val = new boolean[N];
      for (int i = 0; i < N; i++) {
        val[i] = readInt() != 0;
      }
      return val;
    }
    return null;
  }

  public final void readBooleanArray(boolean[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readInt() != 0;
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public void writeShortArray(short[] val) {
    if (val != null) {
      int n = val.length;
      writeInt(n);
      for (short s : val) {
        writeInt(s);
      }
      return;
    }
    writeInt(-1);
  }

  public short[] createShortArray() {
    int n = readInt();
    if (n >= 0 && n <= (dataAvail() >> 2)) {
      short[] val = new short[n];
      for (int i = 0; i < n; i++) {
        val[i] = (short) readInt();
      }
      return val;
    }
    return null;
  }

  public void readShortArray(short[] val) {
    int n = readInt();
    if (n == val.length) {
      for (int i = 0; i < n; i++) {
        val[i] = (short) readInt();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeCharArray(char[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (char c : val) {
        writeInt(c);
      }
      return;
    }
    writeInt(-1);
  }

  public final char[] createCharArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 2)) {
      char[] val = new char[N];
      for (int i = 0; i < N; i++) {
        val[i] = (char) readInt();
      }
      return val;
    }
    return null;
  }

  public final void readCharArray(char[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = (char) readInt();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeIntArray(int[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (int i : val) {
        writeInt(i);
      }
      return;
    }
    writeInt(-1);
  }

  public final int[] createIntArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 2)) {
      int[] val = new int[N];
      for (int i = 0; i < N; i++) {
        val[i] = readInt();
      }
      return val;
    }
    return null;
  }

  public final void readIntArray(int[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readInt();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeLongArray(long[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (long j : val) {
        writeLong(j);
      }
      return;
    }
    writeInt(-1);
  }

  public final long[] createLongArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 3)) {
      long[] val = new long[N];
      for (int i = 0; i < N; i++) {
        val[i] = readLong();
      }
      return val;
    }
    return null;
  }

  public final void readLongArray(long[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readLong();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeFloatArray(float[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (float f : val) {
        writeFloat(f);
      }
      return;
    }
    writeInt(-1);
  }

  public final float[] createFloatArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 2)) {
      float[] val = new float[N];
      for (int i = 0; i < N; i++) {
        val[i] = readFloat();
      }
      return val;
    }
    return null;
  }

  public final void readFloatArray(float[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readFloat();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeDoubleArray(double[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (double d : val) {
        writeDouble(d);
      }
      return;
    }
    writeInt(-1);
  }

  public final double[] createDoubleArray() {
    int N = readInt();
    if (N >= 0 && N <= (dataAvail() >> 3)) {
      double[] val = new double[N];
      for (int i = 0; i < N; i++) {
        val[i] = readDouble();
      }
      return val;
    }
    return null;
  }

  public final void readDoubleArray(double[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readDouble();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeStringArray(String[] val) {
    writeString16Array(val);
  }

  public final String[] createStringArray() {
    return createString16Array();
  }

  public final void readStringArray(String[] val) {
    readString16Array(val);
  }

  public final void writeString8Array(String[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (String str : val) {
        writeString8(str);
      }
      return;
    }
    writeInt(-1);
  }

  public final String[] createString8Array() {
    int N = readInt();
    if (N >= 0) {
      String[] val = new String[N];
      for (int i = 0; i < N; i++) {
        val[i] = readString8();
      }
      return val;
    }
    return null;
  }

  public final void readString8Array(String[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readString8();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeString16Array(String[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (String str : val) {
        writeString16(str);
      }
      return;
    }
    writeInt(-1);
  }

  public final String[] createString16Array() {
    int N = readInt();
    if (N >= 0) {
      String[] val = new String[N];
      for (int i = 0; i < N; i++) {
        val[i] = readString16();
      }
      return val;
    }
    return null;
  }

  public final void readString16Array(String[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readString16();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final void writeBinderArray(IBinder[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (IBinder iBinder : val) {
        writeStrongBinder(iBinder);
      }
      return;
    }
    writeInt(-1);
  }

  public final <T extends IInterface> void writeInterfaceArray(T[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (T t : val) {
        writeStrongInterface(t);
      }
      return;
    }
    writeInt(-1);
  }

  public final void writeCharSequenceArray(CharSequence[] val) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (CharSequence charSequence : val) {
        writeCharSequence(charSequence);
      }
      return;
    }
    writeInt(-1);
  }

  public final void writeCharSequenceList(ArrayList<CharSequence> val) {
    if (val != null) {
      int N = val.size();
      writeInt(N);
      for (int i = 0; i < N; i++) {
        writeCharSequence(val.get(i));
      }
      return;
    }
    writeInt(-1);
  }

  public final IBinder[] createBinderArray() {
    int N = readInt();
    if (N >= 0) {
      IBinder[] val = new IBinder[N];
      for (int i = 0; i < N; i++) {
        val[i] = readStrongBinder();
      }
      return val;
    }
    return null;
  }

  public final void readBinderArray(IBinder[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readStrongBinder();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final <T extends IInterface> T[] createInterfaceArray(
      IntFunction<T[]> newArray, Function<IBinder, T> asInterface) {
    int N = readInt();
    if (N >= 0) {
      T[] val = newArray.apply(N);
      for (int i = 0; i < N; i++) {
        val[i] = asInterface.apply(readStrongBinder());
      }
      return val;
    }
    return null;
  }

  public final <T extends IInterface> void readInterfaceArray(
      T[] val, Function<IBinder, T> asInterface) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = asInterface.apply(readStrongBinder());
      }
      return;
    }
    throw new BadParcelableException("bad array lengths");
  }

  public final <T extends Parcelable> void writeTypedList(List<T> val) {
    writeTypedList(val, 0);
  }

  public final <T extends Parcelable> void writeTypedSparseArray(
      SparseArray<T> val, int parcelableFlags) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int count = val.size();
    writeInt(count);
    for (int i = 0; i < count; i++) {
      writeInt(val.keyAt(i));
      writeTypedObject(val.valueAt(i), parcelableFlags);
    }
  }

  public <T extends Parcelable> void writeTypedList(List<T> val, int parcelableFlags) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeTypedObject(val.get(i), parcelableFlags);
    }
  }

  public final void writeStringList(List<String> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeString(val.get(i));
    }
  }

  public final void writeBinderList(List<IBinder> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeStrongBinder(val.get(i));
    }
  }

  public final <T extends IInterface> void writeInterfaceList(List<T> val) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeStrongInterface(val.get(i));
    }
  }

  public final <T extends Parcelable> void writeParcelableList(List<T> val, int flags) {
    if (val == null) {
      writeInt(-1);
      return;
    }
    int N = val.size();
    writeInt(N);
    for (int i = 0; i < N; i++) {
      writeParcelable(val.get(i), flags);
    }
  }

  public final <T extends Parcelable> void writeTypedArray(T[] val, int parcelableFlags) {
    if (val != null) {
      int N = val.length;
      writeInt(N);
      for (T t : val) {
        writeTypedObject(t, parcelableFlags);
      }
      return;
    }
    writeInt(-1);
  }

  public final <T extends Parcelable> void writeTypedObject(T val, int parcelableFlags) {
    if (val != null) {
      writeInt(1);
      val.writeToParcel(this, parcelableFlags);
    } else {
      writeInt(0);
    }
  }

  public <T> void writeFixedArray(T val, int parcelableFlags, int... dimensions) {
    if (val == null) {
      writeInt(-1);
    } else {
      writeFixedArrayInternal(val, parcelableFlags, 0, dimensions);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  private <T> void writeFixedArrayInternal(T t, int parcelableFlags, int index, int[] dimensions) {
    if (index >= dimensions.length) {
      throw new BadParcelableException(
          "Array has more dimensions than expected: " + dimensions.length);
    }
    int length = dimensions[index];
    if (t == 0) {
      throw new BadParcelableException("Non-null array shouldn't have a null array.");
    }
    if (!t.getClass().isArray()) {
      throw new BadParcelableException("Not an array: " + t);
    }
    if (Array.getLength(t) != length) {
      throw new BadParcelableException(
          "bad length: expected " + length + ", but got " + Array.getLength(t));
    }
    Class<?> componentType = t.getClass().getComponentType();
    if (!componentType.isArray() && index + 1 != dimensions.length) {
      throw new BadParcelableException(
          "Array has fewer dimensions than expected: " + dimensions.length);
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
      writeTypedArray((Parcelable[]) t, parcelableFlags);
      return;
    }
    if (componentType.isArray()) {
      writeInt(length);
      for (int i = 0; i < length; i++) {
        writeFixedArrayInternal(Array.get(t, i), parcelableFlags, index + 1, dimensions);
      }
      return;
    }
    throw new BadParcelableException("unknown type for fixed-size array: " + componentType);
  }

  public final void writeValue(Object v) {
    if (v instanceof LazyValue) {
      LazyValue value = (LazyValue) v;
      value.writeToParcel(this);
      return;
    }
    int type = getValueType(v);
    writeInt(type);
    if (isLengthPrefixed(type)) {
      int length = dataPosition();
      writeInt(-1);
      int start = dataPosition();
      writeValue(type, v);
      int end = dataPosition();
      setDataPosition(length);
      writeInt(end - start);
      setDataPosition(end);
      return;
    }
    writeValue(type, v);
  }

  public static int getValueType(Object v) {
    if (v == null) {
      return -1;
    }
    if (v instanceof String) {
      return 0;
    }
    if (v instanceof Integer) {
      return 1;
    }
    if (v instanceof Map) {
      return 2;
    }
    if (v instanceof Bundle) {
      return 3;
    }
    if (v instanceof PersistableBundle) {
      return 25;
    }
    if (v instanceof SizeF) {
      return 27;
    }
    if (v instanceof Parcelable) {
      return 4;
    }
    if (v instanceof Short) {
      return 5;
    }
    if (v instanceof Long) {
      return 6;
    }
    if (v instanceof Float) {
      return 7;
    }
    if (v instanceof Double) {
      return 8;
    }
    if (v instanceof Boolean) {
      return 9;
    }
    if (v instanceof CharSequence) {
      return 10;
    }
    if (v instanceof List) {
      return 11;
    }
    if (v instanceof SparseArray) {
      return 12;
    }
    if (v instanceof boolean[]) {
      return 23;
    }
    if (v instanceof byte[]) {
      return 13;
    }
    if (v instanceof String[]) {
      return 14;
    }
    if (v instanceof CharSequence[]) {
      return 24;
    }
    if (v instanceof IBinder) {
      return 15;
    }
    if (v instanceof Parcelable[]) {
      return 16;
    }
    if (v instanceof int[]) {
      return 18;
    }
    if (v instanceof long[]) {
      return 19;
    }
    if (v instanceof Byte) {
      return 20;
    }
    if (v instanceof Size) {
      return 26;
    }
    if (v instanceof double[]) {
      return 28;
    }
    if (v instanceof Character) {
      return 29;
    }
    if (v instanceof short[]) {
      return 30;
    }
    if (v instanceof char[]) {
      return 31;
    }
    if (v instanceof float[]) {
      return 32;
    }
    Class<?> clazz = v.getClass();
    if (clazz.isArray() && clazz.getComponentType() == Object.class) {
      return 17;
    }
    if (v instanceof Serializable) {
      return 21;
    }
    throw new IllegalArgumentException("Parcel: unknown type for value " + v);
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

  public final void writeParcelable(Parcelable p, int parcelableFlags) {
    if (p == null) {
      writeString(null);
    } else {
      writeParcelableCreator(p);
      p.writeToParcel(this, parcelableFlags);
    }
  }

  public final void writeParcelableCreator(Parcelable p) {
    String name = p.getClass().getName();
    writeString(name);
  }

  private void ensureWrittenSquashableParcelables() {
    if (this.mWrittenSquashableParcelables != null) {
      return;
    }
    this.mWrittenSquashableParcelables = new ArrayMap<>();
  }

  public boolean allowSquashing() {
    boolean previous = this.mAllowSquashing;
    this.mAllowSquashing = true;
    return previous;
  }

  public void restoreAllowSquashing(boolean previous) {
    this.mAllowSquashing = previous;
    if (!previous) {
      this.mWrittenSquashableParcelables = null;
    }
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

  public boolean maybeWriteSquashed(Parcelable p) {
    if (!this.mAllowSquashing) {
      writeInt(0);
      return false;
    }
    ensureWrittenSquashableParcelables();
    Integer firstPos = this.mWrittenSquashableParcelables.get(p);
    if (firstPos != null) {
      int pos = dataPosition();
      writeInt((pos - firstPos.intValue()) + 4);
      return true;
    }
    writeInt(0);
    int pos2 = dataPosition();
    this.mWrittenSquashableParcelables.put(p, Integer.valueOf(pos2));
    return false;
  }

  public <T extends Parcelable> T readSquashed(SquashReadHelper<T> reader) {
    int offset = readInt();
    int pos = dataPosition();
    if (offset == 0) {
      T p = reader.readRawParceled(this);
      ensureReadSquashableParcelables();
      this.mReadSquashableParcelables.put(pos, p);
      return p;
    }
    int firstAbsolutePos = pos - offset;
    T t = (T) this.mReadSquashableParcelables.get(firstAbsolutePos);
    if (t == null) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < this.mReadSquashableParcelables.size(); i++) {
        sb.append(this.mReadSquashableParcelables.keyAt(i)).append(' ');
      }
      Slog.wtfStack(
          TAG, "Map doesn't contain offset " + firstAbsolutePos + " : contains=" + sb.toString());
    }
    return t;
  }

  public final void writeSerializable(Serializable s) {
    if (s == null) {
      writeString(null);
      return;
    }
    String name = s.getClass().getName();
    writeString(name);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(s);
      oos.close();
      writeByteArray(baos.toByteArray());
    } catch (IOException ioe) {
      throw new BadParcelableException(
          "Parcelable encountered IOException writing serializable object (name = "
              + name
              + NavigationBarInflaterView.KEY_CODE_END,
          ioe);
    }
  }

  public static void setStackTraceParceling(boolean enabled) {
    sParcelExceptionStackTrace = enabled;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final void writeException(Exception exc) {
    AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
    int code = getExceptionCode(exc);
    writeInt(code);
    StrictMode.clearGatheredViolations();
    if (code == 0) {
      if (exc instanceof RuntimeException) {
        throw ((RuntimeException) exc);
      }
      throw new RuntimeException(exc);
    }
    writeString(exc.getMessage());
    long timeNow = sParcelExceptionStackTrace ? SystemClock.elapsedRealtime() : 0L;
    if (sParcelExceptionStackTrace && timeNow - sLastWriteExceptionStackTrace > 1000) {
      sLastWriteExceptionStackTrace = timeNow;
      writeStackTrace(exc);
    } else {
      writeInt(0);
    }
    switch (code) {
      case -9:
        int sizePosition = dataPosition();
        writeInt(0);
        writeParcelable((Parcelable) exc, 1);
        int payloadPosition = dataPosition();
        setDataPosition(sizePosition);
        writeInt(payloadPosition - sizePosition);
        setDataPosition(payloadPosition);
        return;
      case -8:
        writeInt(((ServiceSpecificException) exc).errorCode);
        return;
      default:
        return;
    }
  }

  public static int getExceptionCode(Throwable e) {
    if ((e instanceof Parcelable)
        && e.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
      return -9;
    }
    if (e instanceof SecurityException) {
      return -1;
    }
    if (e instanceof BadParcelableException) {
      return -2;
    }
    if (e instanceof IllegalArgumentException) {
      return -3;
    }
    if (e instanceof NullPointerException) {
      return -4;
    }
    if (e instanceof IllegalStateException) {
      return -5;
    }
    if (e instanceof NetworkOnMainThreadException) {
      return -6;
    }
    if (e instanceof UnsupportedOperationException) {
      return -7;
    }
    if (!(e instanceof ServiceSpecificException)) {
      return 0;
    }
    return -8;
  }

  public void writeStackTrace(Throwable e) {
    int sizePosition = dataPosition();
    writeInt(0);
    StackTraceElement[] stackTrace = e.getStackTrace();
    int truncatedSize = Math.min(stackTrace.length, 5);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < truncatedSize; i++) {
      sb.append("\tat ").append(stackTrace[i]).append('\n');
    }
    writeString(sb.toString());
    int payloadPosition = dataPosition();
    setDataPosition(sizePosition);
    writeInt(payloadPosition - sizePosition);
    setDataPosition(payloadPosition);
  }

  public final void writeNoException() {
    AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
    if (StrictMode.hasGatheredViolations()) {
      writeInt(-128);
      int sizePosition = dataPosition();
      writeInt(0);
      StrictMode.writeGatheredViolationsToParcel(this);
      int payloadPosition = dataPosition();
      setDataPosition(sizePosition);
      writeInt(payloadPosition - sizePosition);
      setDataPosition(payloadPosition);
      return;
    }
    writeInt(0);
  }

  public final void readException() {
    int code = readExceptionCode();
    if (code != 0) {
      String msg = readString();
      readException(code, msg);
    }
  }

  public final int readExceptionCode() {
    int code = readInt();
    if (code == -127) {
      AppOpsManager.readAndLogNotedAppops(this);
      code = readInt();
    }
    if (code == -128) {
      int headerSize = readInt();
      if (headerSize == 0) {
        Log.m96e(TAG, "Unexpected zero-sized Parcel reply header.");
        return 0;
      }
      StrictMode.readAndHandleBinderCallViolations(this);
      return 0;
    }
    return code;
  }

  public final void readException(int code, String msg) {
    String remoteStackTrace = null;
    int remoteStackPayloadSize = readInt();
    if (remoteStackPayloadSize > 0) {
      remoteStackTrace = readString();
    }
    Exception e = createException(code, msg);
    if (remoteStackTrace != null) {
      RemoteException cause =
          new RemoteException("Remote stack trace:\n" + remoteStackTrace, null, false, false);
      ExceptionUtils.appendCause(e, cause);
    }
    SneakyThrow.sneakyThrow(e);
  }

  private Exception createException(int code, String msg) {
    Exception exception = createExceptionOrNull(code, msg);
    if (exception != null) {
      return exception;
    }
    return new RuntimeException("Unknown exception code: " + code + " msg " + msg);
  }

  public Exception createExceptionOrNull(int code, String msg) {
    switch (code) {
      case -9:
        if (readInt() > 0) {
          return (Exception) readParcelable(Parcelable.class.getClassLoader(), Exception.class);
        }
        return new RuntimeException(msg + " [missing Parcelable]");
      case -8:
        return new ServiceSpecificException(readInt(), msg);
      case -7:
        return new UnsupportedOperationException(msg);
      case -6:
        return new NetworkOnMainThreadException();
      case -5:
        return new IllegalStateException(msg);
      case -4:
        return new NullPointerException(msg);
      case -3:
        return new IllegalArgumentException(msg);
      case -2:
        return new BadParcelableException(msg);
      case -1:
        return new SecurityException(msg);
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
    IBinder result = nativeReadStrongBinder(this.mNativePtr);
    if (result != null && hasFlags(3)) {
      Binder.allowBlocking(result);
    }
    return result;
  }

  public final ParcelFileDescriptor readFileDescriptor() {
    FileDescriptor fd = nativeReadFileDescriptor(this.mNativePtr);
    if (fd != null) {
      return new ParcelFileDescriptor(fd);
    }
    return null;
  }

  public final FileDescriptor readRawFileDescriptor() {
    return nativeReadFileDescriptor(this.mNativePtr);
  }

  public final FileDescriptor[] createRawFileDescriptorArray() {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    FileDescriptor[] f = new FileDescriptor[N];
    for (int i = 0; i < N; i++) {
      f[i] = readRawFileDescriptor();
    }
    return f;
  }

  public final void readRawFileDescriptorArray(FileDescriptor[] val) {
    int N = readInt();
    if (N == val.length) {
      for (int i = 0; i < N; i++) {
        val[i] = readRawFileDescriptor();
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  public final byte readByte() {
    return (byte) (readInt() & 255);
  }

  @Deprecated
  public final void readMap(Map outVal, ClassLoader loader) {
    readMapInternal(outVal, loader, null, null);
  }

  public <K, V> void readMap(
      Map<? super K, ? super V> outVal,
      ClassLoader loader,
      Class<K> clazzKey,
      Class<V> clazzValue) {
    Objects.requireNonNull(clazzKey);
    Objects.requireNonNull(clazzValue);
    readMapInternal(outVal, loader, clazzKey, clazzValue);
  }

  @Deprecated
  public final void readList(List outVal, ClassLoader loader) {
    int N = readInt();
    readListInternal(outVal, N, loader, null);
  }

  public <T> void readList(List<? super T> outVal, ClassLoader loader, Class<T> clazz) {
    Objects.requireNonNull(clazz);
    int n = readInt();
    readListInternal(outVal, n, loader, clazz);
  }

  @Deprecated
  public HashMap readHashMap(ClassLoader loader) {
    return readHashMapInternal(loader, null, null);
  }

  public <K, V> HashMap<K, V> readHashMap(
      ClassLoader loader, Class<? extends K> clazzKey, Class<? extends V> clazzValue) {
    Objects.requireNonNull(clazzKey);
    Objects.requireNonNull(clazzValue);
    return readHashMapInternal(loader, clazzKey, clazzValue);
  }

  public final Bundle readBundle() {
    return readBundle(null);
  }

  public final Bundle readBundle(ClassLoader loader) {
    int length = readInt();
    if (length < 0) {
      return null;
    }
    Bundle bundle = new Bundle(this, length);
    if (loader != null) {
      bundle.setClassLoader(loader);
    }
    return bundle;
  }

  public final PersistableBundle readPersistableBundle() {
    return readPersistableBundle(null);
  }

  public final PersistableBundle readPersistableBundle(ClassLoader loader) {
    int length = readInt();
    if (length < 0) {
      return null;
    }
    PersistableBundle bundle = new PersistableBundle(this, length);
    if (loader != null) {
      bundle.setClassLoader(loader);
    }
    return bundle;
  }

  public final Size readSize() {
    int width = readInt();
    int height = readInt();
    return new Size(width, height);
  }

  public final SizeF readSizeF() {
    float width = readFloat();
    float height = readFloat();
    return new SizeF(width, height);
  }

  public final byte[] createByteArray() {
    return nativeCreateByteArray(this.mNativePtr);
  }

  public final void readByteArray(byte[] val) {
    boolean valid = nativeReadByteArray(this.mNativePtr, val, val != null ? val.length : 0);
    if (!valid) {
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
    CharSequence[] array = null;
    int length = readInt();
    if (length >= 0) {
      array = new CharSequence[length];
      for (int i = 0; i < length; i++) {
        array[i] = readCharSequence();
      }
    }
    return array;
  }

  public final ArrayList<CharSequence> readCharSequenceList() {
    ArrayList<CharSequence> array = null;
    int length = readInt();
    if (length >= 0) {
      array = new ArrayList<>(length);
      for (int i = 0; i < length; i++) {
        array.add(readCharSequence());
      }
    }
    return array;
  }

  @Deprecated
  public ArrayList readArrayList(ClassLoader loader) {
    return readArrayListInternal(loader, null);
  }

  public <T> ArrayList<T> readArrayList(ClassLoader loader, Class<? extends T> clazz) {
    Objects.requireNonNull(clazz);
    return readArrayListInternal(loader, clazz);
  }

  @Deprecated
  public Object[] readArray(ClassLoader loader) {
    return readArrayInternal(loader, null);
  }

  public <T> T[] readArray(ClassLoader classLoader, Class<T> cls) {
    Objects.requireNonNull(cls);
    return (T[]) readArrayInternal(classLoader, cls);
  }

  @Deprecated
  public <T> SparseArray<T> readSparseArray(ClassLoader loader) {
    return readSparseArrayInternal(loader, null);
  }

  public <T> SparseArray<T> readSparseArray(ClassLoader loader, Class<? extends T> clazz) {
    Objects.requireNonNull(clazz);
    return readSparseArrayInternal(loader, clazz);
  }

  public final SparseBooleanArray readSparseBooleanArray() {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    SparseBooleanArray sa = new SparseBooleanArray(N);
    readSparseBooleanArrayInternal(sa, N);
    return sa;
  }

  public final SparseIntArray readSparseIntArray() {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    SparseIntArray sa = new SparseIntArray(N);
    readSparseIntArrayInternal(sa, N);
    return sa;
  }

  public final <T> ArrayList<T> createTypedArrayList(Parcelable.Creator<T> creator) {
    int readInt = readInt();
    if (readInt < 0) {
      return null;
    }
    MediaController.C52592 c52592 = (ArrayList<T>) new ArrayList(readInt);
    while (readInt > 0) {
      c52592.add(readTypedObject(creator));
      readInt--;
    }
    return c52592;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final <T> void readTypedList(List<T> list, Parcelable.Creator<T> c) {
    int M = list.size();
    int N = readInt();
    int i = 0;
    while (i < M && i < N) {
      list.set(i, readTypedObject(c));
      i++;
    }
    while (i < N) {
      list.add(readTypedObject(c));
      i++;
    }
    while (i < M) {
      list.remove(N);
      i++;
    }
  }

  public final <T extends Parcelable> SparseArray<T> createTypedSparseArray(
      Parcelable.Creator<T> creator) {
    int readInt = readInt();
    if (readInt < 0) {
      return null;
    }
    PerUser perUser = (SparseArray<T>) new SparseArray(readInt);
    for (int i = 0; i < readInt; i++) {
      perUser.append(readInt(), (Parcelable) readTypedObject(creator));
    }
    return perUser;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final <T extends Parcelable> ArrayMap<String, T> createTypedArrayMap(
      Parcelable.Creator<T> creator) {
    int readInt = readInt();
    if (readInt < 0) {
      return null;
    }
    ArrayMap<String, T> arrayMap = (ArrayMap<String, T>) new ArrayMap(readInt);
    for (int i = 0; i < readInt; i++) {
      arrayMap.append(readString(), (Parcelable) readTypedObject(creator));
    }
    return arrayMap;
  }

  public final ArrayList<String> createStringArrayList() {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    ArrayList<String> l = new ArrayList<>(N);
    while (N > 0) {
      l.add(readString());
      N--;
    }
    return l;
  }

  public final ArrayList<IBinder> createBinderArrayList() {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    ArrayList<IBinder> l = new ArrayList<>(N);
    while (N > 0) {
      l.add(readStrongBinder());
      N--;
    }
    return l;
  }

  public final <T extends IInterface> ArrayList<T> createInterfaceArrayList(
      Function<IBinder, T> asInterface) {
    int N = readInt();
    if (N < 0) {
      return null;
    }
    ArrayList<T> l = new ArrayList<>(N);
    while (N > 0) {
      l.add(asInterface.apply(readStrongBinder()));
      N--;
    }
    return l;
  }

  public final void readStringList(List<String> list) {
    int M = list.size();
    int N = readInt();
    int i = 0;
    while (i < M && i < N) {
      list.set(i, readString());
      i++;
    }
    while (i < N) {
      list.add(readString());
      i++;
    }
    while (i < M) {
      list.remove(N);
      i++;
    }
  }

  public final void readBinderList(List<IBinder> list) {
    int M = list.size();
    int N = readInt();
    int i = 0;
    while (i < M && i < N) {
      list.set(i, readStrongBinder());
      i++;
    }
    while (i < N) {
      list.add(readStrongBinder());
      i++;
    }
    while (i < M) {
      list.remove(N);
      i++;
    }
  }

  public final <T extends IInterface> void readInterfaceList(
      List<T> list, Function<IBinder, T> asInterface) {
    int M = list.size();
    int N = readInt();
    int i = 0;
    while (i < M && i < N) {
      list.set(i, asInterface.apply(readStrongBinder()));
      i++;
    }
    while (i < N) {
      list.add(asInterface.apply(readStrongBinder()));
      i++;
    }
    while (i < M) {
      list.remove(N);
      i++;
    }
  }

  @Deprecated
  public final <T extends Parcelable> List<T> readParcelableList(List<T> list, ClassLoader cl) {
    return readParcelableListInternal(list, cl, null);
  }

  public <T> List<T> readParcelableList(List<T> list, ClassLoader cl, Class<? extends T> clazz) {
    Objects.requireNonNull(list);
    Objects.requireNonNull(clazz);
    return readParcelableListInternal(list, cl, clazz);
  }

  /* JADX WARN: Multi-variable type inference failed */
  private <T> List<T> readParcelableListInternal(
      List<T> list, ClassLoader cl, Class<? extends T> clazz) {
    int n = readInt();
    if (n == -1) {
      list.clear();
      return list;
    }
    int m = list.size();
    int i = 0;
    while (i < m && i < n) {
      list.set(i, readParcelableInternal(cl, clazz));
      i++;
    }
    while (i < n) {
      list.add(readParcelableInternal(cl, clazz));
      i++;
    }
    while (i < m) {
      list.remove(n);
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
    T[] newArray = creator.newArray(readInt);
    for (int i = 0; i < readInt; i++) {
      newArray[i] = readTypedObject(creator);
    }
    return newArray;
  }

  public final <T> void readTypedArray(T[] tArr, Parcelable.Creator<T> c) {
    int N = readInt();
    if (N == tArr.length) {
      for (int i = 0; i < N; i++) {
        tArr[i] = readTypedObject(c);
      }
      return;
    }
    throw new RuntimeException("bad array lengths");
  }

  @Deprecated
  public final <T> T[] readTypedArray(Parcelable.Creator<T> creator) {
    return (T[]) createTypedArray(creator);
  }

  public final <T> T readTypedObject(Parcelable.Creator<T> c) {
    if (readInt() != 0) {
      return c.createFromParcel(this);
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
      int length = readInt();
      if (length != Array.getLength(t)) {
        throw new BadParcelableException(
            "Bad length: expected " + Array.getLength(t) + ", but got " + length);
      }
      for (int i = 0; i < length; i++) {
        readFixedArray(Array.get(t, i));
      }
      return;
    }
    throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public <T, S extends IInterface> void readFixedArray(T t, Function<IBinder, S> asInterface) {
    Class<?> componentType = t.getClass().getComponentType();
    if (IInterface.class.isAssignableFrom(componentType)) {
      readInterfaceArray((IInterface[]) t, asInterface);
      return;
    }
    if (componentType.isArray()) {
      int length = readInt();
      if (length != Array.getLength(t)) {
        throw new BadParcelableException(
            "Bad length: expected " + Array.getLength(t) + ", but got " + length);
      }
      for (int i = 0; i < length; i++) {
        readFixedArray((Parcel) Array.get(t, i), (Function) asInterface);
      }
      return;
    }
    throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public <T, S extends Parcelable> void readFixedArray(T t, Parcelable.Creator<S> c) {
    Class<?> componentType = t.getClass().getComponentType();
    if (Parcelable.class.isAssignableFrom(componentType)) {
      readTypedArray((Parcelable[]) t, c);
      return;
    }
    if (componentType.isArray()) {
      int length = readInt();
      if (length != Array.getLength(t)) {
        throw new BadParcelableException(
            "Bad length: expected " + Array.getLength(t) + ", but got " + length);
      }
      for (int i = 0; i < length; i++) {
        readFixedArray((Parcel) Array.get(t, i), (Parcelable.Creator) c);
      }
      return;
    }
    throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
  }

  private void ensureClassHasExpectedDimensions(Class<?> cls, int numDimension) {
    if (numDimension <= 0) {
      throw new BadParcelableException("Fixed-size array should have dimensions.");
    }
    for (int i = 0; i < numDimension; i++) {
      if (!cls.isArray()) {
        throw new BadParcelableException(
            "Array has fewer dimensions than expected: " + numDimension);
      }
      cls = cls.getComponentType();
    }
    if (cls.isArray()) {
      throw new BadParcelableException("Array has more dimensions than expected: " + numDimension);
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
          throw new BadParcelableException(
              "Bad length: expected " + iArr[0] + ", but got " + readInt);
        }
        Class<?> componentType2 = componentType.getComponentType();
        while (componentType2.isArray()) {
          componentType2 = componentType2.getComponentType();
        }
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
    throw new BadParcelableException(
        "Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
  }

  /* JADX WARN: Multi-variable type inference failed */
  public <T, S extends IInterface> T createFixedArray(
      Class<T> cls, Function<IBinder, S> function, int... iArr) {
    ensureClassHasExpectedDimensions(cls, iArr.length);
    final Class<?> componentType = cls.getComponentType();
    if (IInterface.class.isAssignableFrom(componentType)) {
      T t =
          (T)
              createInterfaceArray(
                  new IntFunction() { // from class: android.os.Parcel$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                      return Parcel.lambda$createFixedArray$0(componentType, i);
                    }
                  },
                  function);
      if (t != null && Array.getLength(t) != iArr[0]) {
        throw new BadParcelableException(
            "Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
      }
      return t;
    }
    if (componentType.isArray()) {
      int readInt = readInt();
      if (readInt < 0) {
        return null;
      }
      if (readInt != iArr[0]) {
        throw new BadParcelableException(
            "Bad length: expected " + iArr[0] + ", but got " + readInt);
      }
      Class<?> componentType2 = componentType.getComponentType();
      while (componentType2.isArray()) {
        componentType2 = componentType2.getComponentType();
      }
      T t2 = (T) Array.newInstance(componentType2, iArr);
      for (int i = 0; i < readInt; i++) {
        readFixedArray((Parcel) Array.get(t2, i), (Function) function);
      }
      return t2;
    }
    throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
  }

  static /* synthetic */ IInterface[] lambda$createFixedArray$0(Class componentType, int n) {
    return (IInterface[]) Array.newInstance((Class<?>) componentType, n);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public <T, S extends Parcelable> T createFixedArray(
      Class<T> cls, Parcelable.Creator<S> creator, int... iArr) {
    ensureClassHasExpectedDimensions(cls, iArr.length);
    Class<?> componentType = cls.getComponentType();
    if (Parcelable.class.isAssignableFrom(componentType)) {
      T t = (T) createTypedArray(creator);
      if (t != null && Array.getLength(t) != iArr[0]) {
        throw new BadParcelableException(
            "Bad length: expected " + iArr[0] + ", but got " + Array.getLength(t));
      }
      return t;
    }
    if (componentType.isArray()) {
      int readInt = readInt();
      if (readInt < 0) {
        return null;
      }
      if (readInt != iArr[0]) {
        throw new BadParcelableException(
            "Bad length: expected " + iArr[0] + ", but got " + readInt);
      }
      Class<?> componentType2 = componentType.getComponentType();
      while (componentType2.isArray()) {
        componentType2 = componentType2.getComponentType();
      }
      T t2 = (T) Array.newInstance(componentType2, iArr);
      for (int i = 0; i < readInt; i++) {
        readFixedArray((Parcel) Array.get(t2, i), (Parcelable.Creator) creator);
      }
      return t2;
    }
    throw new BadParcelableException("Unknown type for fixed-size array: " + componentType);
  }

  public final <T extends Parcelable> void writeParcelableArray(T[] value, int parcelableFlags) {
    if (value != null) {
      int N = value.length;
      writeInt(N);
      for (T t : value) {
        writeParcelable(t, parcelableFlags);
      }
      return;
    }
    writeInt(-1);
  }

  public final Object readValue(ClassLoader loader) {
    return readValue(loader, (Class) null, new Class[0]);
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
        Slog.wtfStack(
            TAG,
            "Unparcelling of "
                + t
                + " of type "
                + valueTypeToString(readInt)
                + "  consumed "
                + dataPosition2
                + " bytes, but "
                + readInt2
                + " expected.");
        return t;
      }
      return t;
    }
    return (T) readValue(readInt, classLoader, cls, clsArr);
  }

  public Object readLazyValue(ClassLoader loader) {
    int start = dataPosition();
    int type = readInt();
    if (isLengthPrefixed(type)) {
      int objectLength = readInt();
      if (objectLength < 0) {
        return null;
      }
      int end = MathUtils.addOrThrow(dataPosition(), objectLength);
      int valueLength = end - start;
      setDataPosition(end);
      return new LazyValue(this, start, valueLength, type, loader);
    }
    return readValue(type, loader, (Class) null);
  }

  private static final class LazyValue implements BiFunction<Class<?>, Class<?>[], Object> {
    private final int mLength;
    private final ClassLoader mLoader;
    private Object mObject;
    private final int mPosition;
    private volatile Parcel mSource;
    private final int mType;

    LazyValue(Parcel source, int position, int length, int type, ClassLoader loader) {
      this.mSource = (Parcel) Objects.requireNonNull(source);
      this.mPosition = position;
      this.mLength = length;
      this.mType = type;
      this.mLoader = loader;
    }

    @Override // java.util.function.BiFunction
    public Object apply(Class<?> clazz, Class<?>[] itemTypes) {
      Parcel source = this.mSource;
      if (source != null) {
        synchronized (source) {
          if (this.mSource != null) {
            int restore = source.dataPosition();
            try {
              source.setDataPosition(this.mPosition);
              this.mObject = source.readValue(this.mLoader, clazz, itemTypes);
              source.setDataPosition(restore);
              this.mSource = null;
            } catch (Throwable th) {
              source.setDataPosition(restore);
              throw th;
            }
          }
        }
      }
      return this.mObject;
    }

    public void writeToParcel(Parcel out) {
      Parcel source = this.mSource;
      if (source != null) {
        synchronized (source) {
          if (this.mSource != null) {
            out.appendFrom(source, this.mPosition, this.mLength);
            return;
          }
        }
      }
      out.writeValue(this.mObject);
    }

    public boolean hasFileDescriptors() {
      Parcel source = this.mSource;
      if (source != null) {
        synchronized (source) {
          if (this.mSource != null) {
            return source.hasFileDescriptors(this.mPosition, this.mLength);
          }
        }
      }
      return Parcel.hasFileDescriptors(this.mObject);
    }

    public String toString() {
      if (this.mSource != null) {
        return "Supplier{"
            + Parcel.valueTypeToString(this.mType)
            + "@"
            + this.mPosition
            + "+"
            + this.mLength
            + '}';
      }
      return "Supplier{" + this.mObject + "}";
    }

    public boolean equals(Object other) {
      int i;
      if (this == other) {
        return true;
      }
      if (!(other instanceof LazyValue)) {
        return false;
      }
      LazyValue value = (LazyValue) other;
      Parcel source = this.mSource;
      Parcel otherSource = value.mSource;
      if ((source == null) != (otherSource == null)) {
        return false;
      }
      if (source == null) {
        return Objects.equals(this.mObject, value.mObject);
      }
      if (Objects.equals(this.mLoader, value.mLoader)
          && this.mType == value.mType
          && (i = this.mLength) == value.mLength) {
        return Parcel.compareData(source, this.mPosition, otherSource, value.mPosition, i);
      }
      return false;
    }

    public int hashCode() {
      Object[] objArr = new Object[5];
      objArr[0] = Boolean.valueOf(this.mSource == null);
      objArr[1] = this.mObject;
      objArr[2] = this.mLoader;
      objArr[3] = Integer.valueOf(this.mType);
      objArr[4] = Integer.valueOf(this.mLength);
      return Objects.hash(objArr);
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
        throw new BadParcelableException(
            "Parcel "
                + this
                + ": Unmarshalling unknown type code "
                + i
                + " at offset "
                + (dataPosition() - 4));
    }
    if (t == null || cls == null || cls.isInstance(t)) {
      return t;
    }
    throw new BadTypeParcelableException(
        "Unparcelled object "
            + t
            + " is not an instance of required class "
            + cls.getName()
            + " provided in the parameter");
  }

  private boolean isLengthPrefixed(int type) {
    switch (type) {
      case 2:
      case 4:
      case 11:
      case 12:
      case 16:
      case 17:
      case 21:
        return true;
      default:
        return false;
    }
  }

  private void checkArrayTypeToUnparcel(
      Class<?> requiredArrayType, Class<?> componentTypeToUnparcel) {
    if (requiredArrayType != null) {
      Class<?> requiredComponentType = requiredArrayType.getComponentType();
      if (requiredComponentType == null) {
        throw new BadTypeParcelableException(
            "About to unparcel an array but type "
                + requiredArrayType.getCanonicalName()
                + " required by caller is not an array.");
      }
      checkTypeToUnparcel(requiredComponentType, componentTypeToUnparcel);
    }
  }

  private void checkTypeToUnparcel(Class<?> requiredType, Class<?> typeToUnparcel) {
    if (requiredType != null && !requiredType.isAssignableFrom(typeToUnparcel)) {
      throw new BadTypeParcelableException(
          "About to unparcel a "
              + typeToUnparcel.getCanonicalName()
              + ", which is not a subtype of type "
              + requiredType.getCanonicalName()
              + " required by caller.");
    }
  }

  @Deprecated
  public final <T extends Parcelable> T readParcelable(ClassLoader loader) {
    return (T) readParcelableInternal(loader, null);
  }

  public <T> T readParcelable(ClassLoader classLoader, Class<T> cls) {
    Objects.requireNonNull(cls);
    return (T) readParcelableInternal(classLoader, cls);
  }

  private <T> T readParcelableInternal(ClassLoader classLoader, Class<T> cls) {
    Parcelable.Creator<T> readParcelableCreatorInternal =
        readParcelableCreatorInternal(classLoader, cls);
    if (readParcelableCreatorInternal == null) {
      return null;
    }
    if (readParcelableCreatorInternal instanceof Parcelable.ClassLoaderCreator) {
      return (T)
          ((Parcelable.ClassLoaderCreator) readParcelableCreatorInternal)
              .createFromParcel(this, classLoader);
    }
    return readParcelableCreatorInternal.createFromParcel(this);
  }

  public final <T extends Parcelable> T readCreator(
      Parcelable.Creator<?> creator, ClassLoader loader) {
    if (creator instanceof Parcelable.ClassLoaderCreator) {
      Parcelable.ClassLoaderCreator<?> classLoaderCreator = (Parcelable.ClassLoaderCreator) creator;
      return (T) classLoaderCreator.createFromParcel(this, loader);
    }
    return (T) creator.createFromParcel(this);
  }

  @Deprecated
  public final Parcelable.Creator<?> readParcelableCreator(ClassLoader loader) {
    return readParcelableCreatorInternal(loader, null);
  }

  public <T> Parcelable.Creator<T> readParcelableCreator(ClassLoader loader, Class<T> clazz) {
    Objects.requireNonNull(clazz);
    return readParcelableCreatorInternal(loader, clazz);
  }

  private <T> Parcelable.Creator<T> readParcelableCreatorInternal(
      ClassLoader loader, Class<T> clazz) {
    Pair<Parcelable.Creator<?>, Class<?>> creatorAndParcelableClass;
    ClassLoader parcelableClassLoader;
    String name = readString();
    if (name == null) {
      return null;
    }
    HashMap<ClassLoader, HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>>> hashMap =
        sPairedCreators;
    synchronized (hashMap) {
      HashMap<String, Pair<Parcelable.Creator<?>, Class<?>>> map = hashMap.get(loader);
      if (map == null) {
        hashMap.put(loader, new HashMap<>());
        mCreators.put(loader, new HashMap<>());
        creatorAndParcelableClass = null;
      } else {
        Pair<Parcelable.Creator<?>, Class<?>> creatorAndParcelableClass2 = map.get(name);
        creatorAndParcelableClass = creatorAndParcelableClass2;
      }
    }
    if (creatorAndParcelableClass != null) {
      Parcelable.Creator<T> creator = (Parcelable.Creator) creatorAndParcelableClass.first;
      Class<?> parcelableClass = creatorAndParcelableClass.second;
      if (clazz != null && !clazz.isAssignableFrom(parcelableClass)) {
        throw new BadTypeParcelableException(
            "Parcelable creator "
                + name
                + " is not a subclass of required class "
                + clazz.getName()
                + " provided in the parameter");
      }
      return creator;
    }
    if (loader != null) {
      parcelableClassLoader = loader;
    } else {
      try {
        parcelableClassLoader = getClass().getClassLoader();
      } catch (ClassNotFoundException e) {
        Log.m97e(TAG, "Class not found when unmarshalling: " + name, e);
        throw new BadParcelableException("ClassNotFoundException when unmarshalling: " + name, e);
      } catch (IllegalAccessException e2) {
        Log.m97e(TAG, "Illegal access when unmarshalling: " + name, e2);
        throw new BadParcelableException("IllegalAccessException when unmarshalling: " + name, e2);
      } catch (NoSuchFieldException e3) {
        throw new BadParcelableException(
            "Parcelable protocol requires a Parcelable.Creator object called CREATOR on class "
                + name,
            e3);
      }
    }
    Class<?> parcelableClass2 = Class.forName(name, false, parcelableClassLoader);
    if (!Parcelable.class.isAssignableFrom(parcelableClass2)) {
      throw new BadParcelableException(
          "Parcelable protocol requires subclassing from Parcelable on class " + name);
    }
    if (clazz != null && !clazz.isAssignableFrom(parcelableClass2)) {
      throw new BadTypeParcelableException(
          "Parcelable creator "
              + name
              + " is not a subclass of required class "
              + clazz.getName()
              + " provided in the parameter");
    }
    Field f = parcelableClass2.getField("CREATOR");
    if ((f.getModifiers() & 8) == 0) {
      throw new BadParcelableException(
          "Parcelable protocol requires the CREATOR object to be static on class " + name);
    }
    Class<?> creatorType = f.getType();
    if (!Parcelable.Creator.class.isAssignableFrom(creatorType)) {
      throw new BadParcelableException(
          "Parcelable protocol requires a Parcelable.Creator object called CREATOR on class "
              + name);
    }
    Parcelable.Creator<T> creator2 = (Parcelable.Creator) f.get(null);
    if (creator2 == null) {
      throw new BadParcelableException(
          "Parcelable protocol requires a non-null Parcelable.Creator object called CREATOR on"
              + " class "
              + name);
    }
    synchronized (hashMap) {
      hashMap.get(loader).put(name, Pair.create(creator2, parcelableClass2));
      mCreators.get(loader).put(name, creator2);
    }
    return creator2;
  }

  @Deprecated
  public Parcelable[] readParcelableArray(ClassLoader loader) {
    return (Parcelable[]) readParcelableArrayInternal(loader, null);
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
    T[] tArr =
        (T[])
            ((Object[])
                (cls == null
                    ? new Parcelable[readInt]
                    : Array.newInstance((Class<?>) cls, readInt)));
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
    return (T)
        readSerializableInternal(
            classLoader == null ? getClass().getClassLoader() : classLoader, cls);
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
          throw new BadTypeParcelableException(
              "Serializable object "
                  + cls2.getName()
                  + " is not a subclass of required class "
                  + cls.getName()
                  + " provided in the parameter");
        }
      } catch (IOException e) {
        throw new BadParcelableException(
            "Parcelable encountered IOException reading a Serializable object (name = "
                + readString
                + NavigationBarInflaterView.KEY_CODE_END,
            e);
      } catch (ClassNotFoundException e2) {
        throw new BadParcelableException(
            "Parcelable encountered ClassNotFoundException reading a Serializable object (name = "
                + readString
                + NavigationBarInflaterView.KEY_CODE_END,
            e2);
      }
    }
    T t =
        (T)
            new ObjectInputStream(
                new ByteArrayInputStream(createByteArray())) { // from class: android.os.Parcel.2
              @Override // java.io.ObjectInputStream
              protected Class<?> resolveClass(ObjectStreamClass osClass)
                  throws IOException, ClassNotFoundException {
                if (classLoader != null) {
                  Class<?> c = Class.forName(osClass.getName(), false, classLoader);
                  return (Class) Objects.requireNonNull(c);
                }
                Class<?> c2 = super.resolveClass(osClass);
                return c2;
              }
            }.readObject();
    if (cls != null && classLoader == null && !cls.isAssignableFrom(t.getClass())) {
      throw new BadTypeParcelableException(
          "Serializable object "
              + t.getClass().getName()
              + " is not a subclass of required class "
              + cls.getName()
              + " provided in the parameter");
    }
    return t;
  }

  protected static final Parcel obtain(int obj) {
    throw new UnsupportedOperationException();
  }

  protected static final Parcel obtain(long obj) {
    Parcel res = null;
    synchronized (sPoolSync) {
      Parcel parcel = sHolderPool;
      if (parcel != null) {
        res = parcel;
        sHolderPool = res.mPoolNext;
        res.mPoolNext = null;
        sHolderPoolSize--;
      }
    }
    if (res == null) {
      Parcel res2 = new Parcel(obj);
      return res2;
    }
    res.mRecycled = false;
    res.init(obj);
    return res;
  }

  private Parcel(long nativePtr) {
    init(nativePtr);
  }

  private void init(long nativePtr) {
    if (nativePtr != 0) {
      this.mNativePtr = nativePtr;
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

  void readMapInternal(Map outVal, int n, ClassLoader loader) {
    readMapInternal(outVal, n, loader, null, null);
  }

  private <K, V> HashMap<K, V> readHashMapInternal(
      ClassLoader loader, Class<? extends K> clazzKey, Class<? extends V> clazzValue) {
    int n = readInt();
    if (n < 0) {
      return null;
    }
    HashMap<K, V> map = new HashMap<>(n);
    readMapInternal(map, n, loader, clazzKey, clazzValue);
    return map;
  }

  private <K, V> void readMapInternal(
      Map<? super K, ? super V> outVal,
      ClassLoader loader,
      Class<K> clazzKey,
      Class<V> clazzValue) {
    int n = readInt();
    readMapInternal(outVal, n, loader, clazzKey, clazzValue);
  }

  private <K, V> void readMapInternal(
      Map<? super K, ? super V> map, int i, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
    while (i > 0) {
      map.put(
          (Object) readValue(classLoader, cls, new Class[0]),
          (Object) readValue(classLoader, cls2, new Class[0]));
      i--;
    }
  }

  private void readArrayMapInternal(
      ArrayMap<? super String, Object> outVal, int size, ClassLoader loader) {
    readArrayMap(outVal, size, true, false, loader);
  }

  int readArrayMap(
      ArrayMap<? super String, Object> map,
      int size,
      boolean sorted,
      boolean lazy,
      ClassLoader loader) {
    int lazyValues = 0;
    while (size > 0) {
      String key = readString();
      Object value = lazy ? readLazyValue(loader) : readValue(loader);
      if (value instanceof LazyValue) {
        lazyValues++;
      }
      if (sorted) {
        map.append(key, value);
      } else {
        map.put(key, value);
      }
      size--;
    }
    if (sorted) {
      map.validate();
    }
    return lazyValues;
  }

  public void readArrayMap(ArrayMap<? super String, Object> outVal, ClassLoader loader) {
    int N = readInt();
    if (N < 0) {
      return;
    }
    readArrayMapInternal(outVal, N, loader);
  }

  public ArraySet<? extends Object> readArraySet(ClassLoader loader) {
    int size = readInt();
    if (size < 0) {
      return null;
    }
    ArraySet<Object> result = new ArraySet<>(size);
    for (int i = 0; i < size; i++) {
      Object value = readValue(loader);
      result.append(value);
    }
    return result;
  }

  private void readListInternal(List outVal, int n, ClassLoader loader) {
    readListInternal(outVal, n, loader, null);
  }

  private <T> void readListInternal(
      List<? super T> list, int i, ClassLoader classLoader, Class<T> cls) {
    while (i > 0) {
      list.add((Object) readValue(classLoader, cls, new Class[0]));
      i--;
    }
  }

  private <T> ArrayList<T> readArrayListInternal(ClassLoader loader, Class<? extends T> clazz) {
    int n = readInt();
    if (n < 0) {
      return null;
    }
    ArrayList<T> l = new ArrayList<>(n);
    readListInternal(l, n, loader, clazz);
    return l;
  }

  private void readArrayInternal(Object[] outVal, int N, ClassLoader loader) {
    for (int i = 0; i < N; i++) {
      Object value = readValue(loader, (Class<Object>) null, new Class[0]);
      outVal[i] = value;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  private <T> T[] readArrayInternal(ClassLoader classLoader, Class<T> cls) {
    int readInt = readInt();
    if (readInt < 0) {
      return null;
    }
    T[] tArr =
        (T[])
            ((Object[])
                (cls == null ? new Object[readInt] : Array.newInstance((Class<?>) cls, readInt)));
    for (int i = 0; i < readInt; i++) {
      tArr[i] = readValue(classLoader, cls, new Class[0]);
    }
    return tArr;
  }

  private void readSparseArrayInternal(SparseArray outVal, int N, ClassLoader loader) {
    while (N > 0) {
      int key = readInt();
      Object value = readValue(loader);
      outVal.append(key, value);
      N--;
    }
  }

  private <T> SparseArray<T> readSparseArrayInternal(
      ClassLoader classLoader, Class<? extends T> cls) {
    int readInt = readInt();
    if (readInt < 0) {
      return null;
    }
    PerUser perUser = (SparseArray<T>) new SparseArray(readInt);
    while (readInt > 0) {
      perUser.append(readInt(), readValue(classLoader, cls, new Class[0]));
      readInt--;
    }
    return perUser;
  }

  private void readSparseBooleanArrayInternal(SparseBooleanArray outVal, int N) {
    while (N > 0) {
      int key = readInt();
      boolean z = true;
      if (readByte() != 1) {
        z = false;
      }
      boolean value = z;
      outVal.append(key, value);
      N--;
    }
  }

  private void readSparseIntArrayInternal(SparseIntArray outVal, int N) {
    while (N > 0) {
      int key = readInt();
      int value = readInt();
      outVal.append(key, value);
      N--;
    }
  }

  public long getOpenAshmemSize() {
    return nativeGetOpenAshmemSize(this.mNativePtr);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static String valueTypeToString(int type) {
    switch (type) {
      case -1:
        return "VAL_NULL";
      case 0:
      case 22:
      default:
        return "UNKNOWN(" + type + NavigationBarInflaterView.KEY_CODE_END;
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
