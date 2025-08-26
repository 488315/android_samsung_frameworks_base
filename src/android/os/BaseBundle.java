package android.os;

import android.content.Intent;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.util.ArrayMap;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/* loaded from: classes3.dex */
public class BaseBundle implements Parcel.ClassLoaderProvider {
    static final int BUNDLE_MAGIC = 1279544898;
    private static final int BUNDLE_MAGIC_NATIVE = 1279544900;
    static final boolean DEBUG = false;
    static final int FLAG_DEFUSABLE = 1;
    private static final boolean LOG_DEFUSABLE = false;
    protected static final String TAG = "Bundle";
    private static volatile boolean sShouldDefuse = false;
    private ClassLoader mClassLoader;
    public int mFlags;
    private boolean mHasIntent;
    private int mLazyValues;
    ArrayMap<String, Object> mMap;
    boolean mOwnsLazyValues;
    private boolean mParcelledByNative;
    volatile Parcel mParcelledData;
    private WeakReference<Parcel> mWeakParcelledData;

    public static void setShouldDefuse(boolean z) {
        sShouldDefuse = z;
    }

    static final class NoImagePreloadHolder {
        public static final Parcel EMPTY_PARCEL = Parcel.obtain();

        NoImagePreloadHolder() {
        }
    }

    BaseBundle(ClassLoader classLoader, int i) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mHasIntent = false;
        this.mMap = i > 0 ? new ArrayMap<>(i) : new ArrayMap<>();
        this.mClassLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle() {
        this((ClassLoader) null, 0);
    }

    BaseBundle(Parcel parcel) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mHasIntent = false;
        readFromParcelInner(parcel);
    }

    BaseBundle(Parcel parcel, int i) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mHasIntent = false;
        readFromParcelInner(parcel, i);
    }

    BaseBundle(ClassLoader classLoader) {
        this(classLoader, 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle(int i) {
        this((ClassLoader) null, i);
    }

    BaseBundle(BaseBundle baseBundle) {
        this(baseBundle, false);
    }

    BaseBundle(BaseBundle baseBundle, boolean z) {
        Parcel parcelObtain = null;
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mHasIntent = false;
        synchronized (baseBundle) {
            this.mClassLoader = baseBundle.mClassLoader;
            ArrayMap<String, Object> arrayMap = baseBundle.mMap;
            if (arrayMap != null) {
                this.mOwnsLazyValues = false;
                baseBundle.mOwnsLazyValues = false;
                if (!z) {
                    this.mMap = new ArrayMap<>(baseBundle.mMap);
                } else {
                    int size = arrayMap.size();
                    this.mMap = new ArrayMap<>(size);
                    for (int i = 0; i < size; i++) {
                        this.mMap.append(arrayMap.keyAt(i), deepCopyValue(arrayMap.valueAt(i)));
                    }
                }
            } else {
                this.mMap = null;
            }
            if (baseBundle.mParcelledData != null) {
                if (baseBundle.isEmptyParcel()) {
                    parcelObtain = NoImagePreloadHolder.EMPTY_PARCEL;
                    this.mParcelledByNative = false;
                } else {
                    parcelObtain = Parcel.obtain();
                    parcelObtain.appendFrom(baseBundle.mParcelledData, 0, baseBundle.mParcelledData.dataSize());
                    parcelObtain.setDataPosition(0);
                    this.mParcelledByNative = baseBundle.mParcelledByNative;
                }
            } else {
                this.mParcelledByNative = false;
            }
            this.mParcelledData = parcelObtain;
            this.mHasIntent = baseBundle.mHasIntent;
        }
    }

    public boolean hasIntent() {
        return this.mHasIntent;
    }

    public void setHasIntent(boolean z) {
        this.mHasIntent = z;
    }

    public String getPairValue() {
        unparcel();
        int size = this.mMap.size();
        if (size > 1) {
            Log.w(TAG, "getPairValue() used on Bundle with multiple pairs.");
        }
        if (size == 0) {
            return null;
        }
        try {
            return (String) getValueAt(0, String.class, new Class[0]);
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning("getPairValue()", "String", e);
            return null;
        }
    }

    void setClassLoader(ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    @Override // android.os.Parcel.ClassLoaderProvider
    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    final void unparcel() {
        unparcel(false);
    }

    final void unparcel(boolean z) {
        synchronized (this) {
            Parcel parcel = this.mParcelledData;
            if (parcel != null) {
                Preconditions.checkState(this.mOwnsLazyValues);
                initializeFromParcelLocked(parcel, true, this.mParcelledByNative);
            }
            if (z) {
                int size = this.mMap.size();
                for (int i = 0; i < size; i++) {
                    getValueAt(i, null, new Class[0]);
                }
            }
        }
    }

    @Deprecated
    final Object getValue(String str) {
        return getValue(str, null);
    }

    final <T> T getValue(String str, Class<T> cls) {
        return (T) getValue(str, cls, null);
    }

    final <T> T getValue(String str, Class<T> cls, Class<?>... clsArr) {
        int iIndexOfKey = this.mMap.indexOfKey(str);
        if (iIndexOfKey >= 0) {
            return (T) getValueAt(iIndexOfKey, cls, clsArr);
        }
        return null;
    }

    public boolean isValueParceled(String str) {
        ArrayMap<String, Object> arrayMap = this.mMap;
        if (arrayMap == null) {
            return true;
        }
        return this.mMap.valueAt(arrayMap.indexOfKey(str)) instanceof BiFunction;
    }

    final <T> T getValueAt(int i, Class<T> cls, Class<?>... clsArr) {
        Object obj = (T) this.mMap.valueAt(i);
        if (obj instanceof BiFunction) {
            synchronized (this) {
                obj = (T) unwrapLazyValueFromMapLocked(i, cls, clsArr);
            }
            if ((this.mFlags & 8192) != 0) {
                Intent.maybeMarkAsMissingCreatorToken(obj);
            }
        } else if (obj instanceof Bundle) {
            ((Bundle) obj).setClassLoaderSameAsContainerBundleWhenRetrievedFirstTime(this);
        }
        return cls != null ? cls.cast(obj) : (T) obj;
    }

    private Object unwrapLazyValueFromMapLocked(int i, Class<?> cls, Class<?>... clsArr) {
        Object objValueAt = this.mMap.valueAt(i);
        if (!(objValueAt instanceof BiFunction)) {
            return objValueAt;
        }
        try {
            Object objApply = ((BiFunction) objValueAt).apply(cls, clsArr);
            this.mMap.setValueAt(i, objApply);
            int i2 = this.mLazyValues - 1;
            this.mLazyValues = i2;
            if (this.mOwnsLazyValues) {
                Preconditions.checkState(i2 >= 0, "Lazy values ref count below 0");
                Parcel parcel = this.mWeakParcelledData.get();
                if (this.mLazyValues == 0 && parcel != null) {
                    recycleParcel(parcel);
                    this.mWeakParcelledData = null;
                }
            }
            return objApply;
        } catch (BadParcelableException e) {
            if (sShouldDefuse) {
                Log.w(TAG, "Failed to parse item " + this.mMap.keyAt(i) + ", returning null.", e);
                return null;
            }
            throw e;
        }
    }

    private void initializeFromParcelLocked(Parcel parcel, boolean z, boolean z2) {
        if (isEmptyParcel(parcel)) {
            ArrayMap<String, Object> arrayMap = this.mMap;
            if (arrayMap == null) {
                this.mMap = new ArrayMap<>(1);
            } else {
                arrayMap.erase();
            }
            this.mParcelledByNative = false;
            this.mParcelledData = null;
            return;
        }
        int i = parcel.readInt();
        if (i < 0) {
            return;
        }
        ArrayMap<String, Object> arrayMap2 = this.mMap;
        if (arrayMap2 == null) {
            arrayMap2 = new ArrayMap<>(i);
        } else {
            arrayMap2.erase();
            arrayMap2.ensureCapacity(i);
        }
        ArrayMap<String, Object> arrayMap3 = arrayMap2;
        int[] iArr = {0};
        try {
            try {
                parcel.readArrayMap(arrayMap3, i, !z2, z, this, iArr);
                this.mWeakParcelledData = null;
                if (z) {
                    if (iArr[0] == 0) {
                        recycleParcel(parcel);
                    } else {
                        this.mWeakParcelledData = new WeakReference<>(parcel);
                    }
                }
                this.mLazyValues = iArr[0];
                this.mParcelledByNative = false;
                this.mMap = arrayMap3;
                this.mParcelledData = null;
            } catch (BadParcelableException e) {
                if (sShouldDefuse) {
                    Log.w(TAG, "Failed to parse Bundle, but defusing quietly", e);
                    arrayMap3.erase();
                    this.mWeakParcelledData = null;
                    if (z) {
                        if (iArr[0] == 0) {
                            recycleParcel(parcel);
                        } else {
                            this.mWeakParcelledData = new WeakReference<>(parcel);
                        }
                    }
                    this.mLazyValues = iArr[0];
                    this.mParcelledByNative = false;
                    this.mMap = arrayMap3;
                    this.mParcelledData = null;
                    return;
                }
                throw e;
            }
        } catch (Throwable th) {
            this.mWeakParcelledData = null;
            if (z) {
                if (iArr[0] == 0) {
                    recycleParcel(parcel);
                } else {
                    this.mWeakParcelledData = new WeakReference<>(parcel);
                }
            }
            this.mLazyValues = iArr[0];
            this.mParcelledByNative = false;
            this.mMap = arrayMap3;
            this.mParcelledData = null;
            throw th;
        }
    }

    public boolean isParcelled() {
        return this.mParcelledData != null;
    }

    public boolean isEmptyParcel() {
        return isEmptyParcel(this.mParcelledData);
    }

    private static boolean isEmptyParcel(Parcel parcel) {
        return parcel == NoImagePreloadHolder.EMPTY_PARCEL;
    }

    private static void recycleParcel(Parcel parcel) {
        if (parcel == null || isEmptyParcel(parcel)) {
            return;
        }
        parcel.recycle();
    }

    ArrayMap<String, Object> getItemwiseMap() {
        unparcel(true);
        return this.mMap;
    }

    public int size() {
        unparcel();
        return this.mMap.size();
    }

    public boolean isEmpty() {
        unparcel();
        return this.mMap.isEmpty();
    }

    public boolean isDefinitelyEmpty() {
        if (isParcelled()) {
            return isEmptyParcel();
        }
        return isEmpty();
    }

    public static boolean kindofEquals(BaseBundle baseBundle, BaseBundle baseBundle2) {
        if (baseBundle != baseBundle2) {
            return baseBundle != null && baseBundle.kindofEquals(baseBundle2);
        }
        return true;
    }

    public boolean kindofEquals(BaseBundle baseBundle) {
        if (baseBundle == null) {
            return false;
        }
        if (isDefinitelyEmpty() && baseBundle.isDefinitelyEmpty()) {
            return true;
        }
        if (isParcelled() != baseBundle.isParcelled()) {
            return false;
        }
        if (isParcelled()) {
            return this.mParcelledData.compareData(baseBundle.mParcelledData) == 0;
        }
        return this.mMap.equals(baseBundle.mMap);
    }

    public void clear() {
        WeakReference<Parcel> weakReference;
        unparcel();
        if (this.mOwnsLazyValues && (weakReference = this.mWeakParcelledData) != null) {
            recycleParcel(weakReference.get());
        }
        this.mWeakParcelledData = null;
        this.mLazyValues = 0;
        this.mOwnsLazyValues = true;
        this.mMap.clear();
    }

    private Object deepCopyValue(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Bundle) {
            return ((Bundle) obj).deepCopy();
        }
        if (obj instanceof PersistableBundle) {
            return ((PersistableBundle) obj).deepCopy();
        }
        if (obj instanceof ArrayList) {
            return deepcopyArrayList((ArrayList) obj);
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof int[]) {
                return ((int[]) obj).clone();
            }
            if (obj instanceof long[]) {
                return ((long[]) obj).clone();
            }
            if (obj instanceof float[]) {
                return ((float[]) obj).clone();
            }
            if (obj instanceof double[]) {
                return ((double[]) obj).clone();
            }
            if (obj instanceof Object[]) {
                return ((Object[]) obj).clone();
            }
            if (obj instanceof byte[]) {
                return ((byte[]) obj).clone();
            }
            if (obj instanceof short[]) {
                return ((short[]) obj).clone();
            }
            if (obj instanceof char[]) {
                return ((char[]) obj).clone();
            }
        }
        return obj;
    }

    private ArrayList deepcopyArrayList(ArrayList arrayList) {
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(deepCopyValue(arrayList.get(i)));
        }
        return arrayList2;
    }

    public boolean containsKey(String str) {
        unparcel();
        return this.mMap.containsKey(str);
    }

    @Deprecated
    public Object get(String str) {
        unparcel();
        return getValue(str);
    }

    <T> T get(String str, Class<T> cls) {
        unparcel();
        try {
            return (T) getValue(str, (Class) Objects.requireNonNull(cls));
        } catch (BadTypeParcelableException | ClassCastException e) {
            this.typeWarning(str, cls.getCanonicalName(), e);
            return null;
        }
    }

    public void remove(String str) {
        unparcel();
        this.mMap.remove(str);
    }

    public void putAll(PersistableBundle persistableBundle) {
        unparcel();
        persistableBundle.unparcel();
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) persistableBundle.mMap);
    }

    void putAll(ArrayMap arrayMap) {
        unparcel();
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) arrayMap);
    }

    public Set<String> keySet() {
        unparcel();
        return this.mMap.keySet();
    }

    public void putObject(String str, Object obj) {
        if (obj == null) {
            putString(str, null);
            return;
        }
        if (obj instanceof Boolean) {
            putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof Integer) {
            putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof Double) {
            putDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof String) {
            putString(str, (String) obj);
            return;
        }
        if (obj instanceof boolean[]) {
            putBooleanArray(str, (boolean[]) obj);
            return;
        }
        if (obj instanceof int[]) {
            putIntArray(str, (int[]) obj);
            return;
        }
        if (obj instanceof long[]) {
            putLongArray(str, (long[]) obj);
            return;
        }
        if (obj instanceof double[]) {
            putDoubleArray(str, (double[]) obj);
        } else if (obj instanceof String[]) {
            putStringArray(str, (String[]) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    public void putBoolean(String str, boolean z) {
        unparcel();
        this.mMap.put(str, Boolean.valueOf(z));
    }

    void putByte(String str, byte b) {
        unparcel();
        this.mMap.put(str, Byte.valueOf(b));
    }

    void putChar(String str, char c) {
        unparcel();
        this.mMap.put(str, Character.valueOf(c));
    }

    void putShort(String str, short s) {
        unparcel();
        this.mMap.put(str, Short.valueOf(s));
    }

    public void putInt(String str, int i) {
        unparcel();
        this.mMap.put(str, Integer.valueOf(i));
    }

    public void putLong(String str, long j) {
        unparcel();
        this.mMap.put(str, Long.valueOf(j));
    }

    void putFloat(String str, float f) {
        unparcel();
        this.mMap.put(str, Float.valueOf(f));
    }

    public void putDouble(String str, double d) {
        unparcel();
        this.mMap.put(str, Double.valueOf(d));
    }

    public void putString(String str, String str2) {
        unparcel();
        this.mMap.put(str, str2);
    }

    void putCharSequence(String str, CharSequence charSequence) {
        unparcel();
        this.mMap.put(str, charSequence);
    }

    void putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putStringArrayList(String str, ArrayList<String> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putCharSequenceArrayList(String str, ArrayList<CharSequence> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putSerializable(String str, Serializable serializable) {
        unparcel();
        this.mMap.put(str, serializable);
    }

    public void putBooleanArray(String str, boolean[] zArr) {
        unparcel();
        this.mMap.put(str, zArr);
    }

    void putByteArray(String str, byte[] bArr) {
        unparcel();
        this.mMap.put(str, bArr);
    }

    void putShortArray(String str, short[] sArr) {
        unparcel();
        this.mMap.put(str, sArr);
    }

    void putCharArray(String str, char[] cArr) {
        unparcel();
        this.mMap.put(str, cArr);
    }

    public void putIntArray(String str, int[] iArr) {
        unparcel();
        this.mMap.put(str, iArr);
    }

    public void putLongArray(String str, long[] jArr) {
        unparcel();
        this.mMap.put(str, jArr);
    }

    void putFloatArray(String str, float[] fArr) {
        unparcel();
        this.mMap.put(str, fArr);
    }

    public void putDoubleArray(String str, double[] dArr) {
        unparcel();
        this.mMap.put(str, dArr);
    }

    public void putStringArray(String str, String[] strArr) {
        unparcel();
        this.mMap.put(str, strArr);
    }

    void putCharSequenceArray(String str, CharSequence[] charSequenceArr) {
        unparcel();
        this.mMap.put(str, charSequenceArr);
    }

    public boolean getBoolean(String str) {
        unparcel();
        return getBoolean(str, false);
    }

    void typeWarning(String str, Object obj, String str2, Object obj2, RuntimeException runtimeException) {
        StringBuilder sb = new StringBuilder("Key ");
        sb.append(str);
        sb.append(" expected ");
        sb.append(str2);
        if (obj != null) {
            sb.append(" but value was a ");
            sb.append(obj.getClass().getName());
        } else {
            sb.append(" but value was of a different type");
        }
        sb.append(".  The default value ");
        sb.append(obj2);
        sb.append(" was returned.");
        Log.w(TAG, sb.toString());
        Log.w(TAG, "Attempt to cast generated internal exception:", runtimeException);
    }

    void typeWarning(String str, Object obj, String str2, RuntimeException runtimeException) {
        typeWarning(str, obj, str2, "<null>", runtimeException);
    }

    void typeWarning(String str, String str2, RuntimeException runtimeException) {
        typeWarning(str, null, str2, "<null>", runtimeException);
    }

    public boolean getBoolean(String str, boolean z) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Boolean", Boolean.valueOf(z), e);
            return z;
        }
    }

    byte getByte(String str) {
        unparcel();
        return getByte(str, (byte) 0).byteValue();
    }

    Byte getByte(String str, byte b) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return Byte.valueOf(b);
        }
        try {
            return (Byte) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "Byte", Byte.valueOf(b), e);
            return Byte.valueOf(b);
        }
    }

    char getChar(String str) {
        unparcel();
        return getChar(str, (char) 0);
    }

    char getChar(String str, char c) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return c;
        }
        try {
            return ((Character) obj).charValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Character", Character.valueOf(c), e);
            return c;
        }
    }

    short getShort(String str) {
        unparcel();
        return getShort(str, (short) 0);
    }

    short getShort(String str, short s) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return s;
        }
        try {
            return ((Short) obj).shortValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Short", Short.valueOf(s), e);
            return s;
        }
    }

    public int getInt(String str) {
        unparcel();
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Integer", Integer.valueOf(i), e);
            return i;
        }
    }

    public long getLong(String str) {
        unparcel();
        return getLong(str, 0L);
    }

    public long getLong(String str, long j) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Long", Long.valueOf(j), e);
            return j;
        }
    }

    float getFloat(String str) {
        unparcel();
        return getFloat(str, 0.0f);
    }

    float getFloat(String str, float f) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return f;
        }
        try {
            return ((Float) obj).floatValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Float", Float.valueOf(f), e);
            return f;
        }
    }

    public double getDouble(String str) {
        unparcel();
        return getDouble(str, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getDouble(String str, double d) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return d;
        }
        try {
            return ((Double) obj).doubleValue();
        } catch (ClassCastException e) {
            this.typeWarning(str, obj, "Double", Double.valueOf(d), e);
            return d;
        }
    }

    public String getString(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "String", e);
            return null;
        }
    }

    public String getString(String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    CharSequence getCharSequence(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        try {
            return (CharSequence) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "CharSequence", e);
            return null;
        }
    }

    CharSequence getCharSequence(String str, CharSequence charSequence) {
        CharSequence charSequence2 = getCharSequence(str);
        return charSequence2 == null ? charSequence : charSequence2;
    }

    @Deprecated
    Serializable getSerializable(String str) {
        unparcel();
        Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (Serializable) value;
        } catch (ClassCastException e) {
            typeWarning(str, value, "Serializable", e);
            return null;
        }
    }

    <T extends Serializable> T getSerializable(String str, Class<T> cls) {
        return (T) get(str, cls);
    }

    <T> ArrayList<T> getArrayList(String str, Class<? extends T> cls) {
        unparcel();
        try {
            return (ArrayList) getValue(str, ArrayList.class, (Class) Objects.requireNonNull(cls));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(str, "ArrayList<" + cls.getCanonicalName() + ">", e);
            return null;
        }
    }

    ArrayList<Integer> getIntegerArrayList(String str) {
        return getArrayList(str, Integer.class);
    }

    ArrayList<String> getStringArrayList(String str) {
        return getArrayList(str, String.class);
    }

    ArrayList<CharSequence> getCharSequenceArrayList(String str) {
        return getArrayList(str, CharSequence.class);
    }

    public boolean[] getBooleanArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (boolean[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "byte[]", e);
            return null;
        }
    }

    byte[] getByteArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "byte[]", e);
            return null;
        }
    }

    short[] getShortArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (short[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "short[]", e);
            return null;
        }
    }

    char[] getCharArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (char[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "char[]", e);
            return null;
        }
    }

    public int[] getIntArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (int[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "int[]", e);
            return null;
        }
    }

    public long[] getLongArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "long[]", e);
            return null;
        }
    }

    float[] getFloatArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "float[]", e);
            return null;
        }
    }

    public double[] getDoubleArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (double[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "double[]", e);
            return null;
        }
    }

    public String[] getStringArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "String[]", e);
            return null;
        }
    }

    CharSequence[] getCharSequenceArray(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (CharSequence[]) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "CharSequence[]", e);
            return null;
        }
    }

    void writeToParcelInner(Parcel parcel, int i) throws IOException {
        if (parcel.hasReadWriteHelper()) {
            unparcel(true);
        }
        synchronized (this) {
            Parcel parcel2 = this.mParcelledData;
            int i2 = BUNDLE_MAGIC;
            if (parcel2 != null) {
                if (this.mParcelledData == NoImagePreloadHolder.EMPTY_PARCEL) {
                    parcel.writeInt(0);
                } else {
                    int iDataSize = this.mParcelledData.dataSize();
                    parcel.writeInt(iDataSize);
                    if (this.mParcelledByNative) {
                        i2 = BUNDLE_MAGIC_NATIVE;
                    }
                    parcel.writeInt(i2);
                    parcel.appendFrom(this.mParcelledData, 0, iDataSize);
                    parcel.writeBoolean(this.mHasIntent);
                }
                return;
            }
            ArrayMap<String, Object> arrayMap = this.mMap;
            if (arrayMap == null || arrayMap.size() <= 0) {
                parcel.writeInt(0);
                return;
            }
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(-1);
            parcel.writeInt(BUNDLE_MAGIC);
            int iDataPosition2 = parcel.dataPosition();
            parcel.writeArrayMapInternal(arrayMap);
            int iDataPosition3 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition3 - iDataPosition2);
            parcel.setDataPosition(iDataPosition3);
            parcel.writeBoolean(this.mHasIntent);
        }
    }

    void readFromParcelInner(Parcel parcel) {
        readFromParcelInner(parcel, parcel.readInt());
    }

    private void readFromParcelInner(Parcel parcel, int i) {
        if (i < 0) {
            throw new RuntimeException("Bad length in parcel: " + i);
        }
        if (i == 0) {
            this.mParcelledByNative = false;
            this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            return;
        }
        if (i % 4 != 0) {
            throw new IllegalStateException("Bundle length is not aligned by 4: " + i);
        }
        int i2 = parcel.readInt();
        boolean z = i2 == BUNDLE_MAGIC;
        boolean z2 = i2 == BUNDLE_MAGIC_NATIVE;
        if (!z && !z2) {
            throw new IllegalStateException("Bad magic number for Bundle: 0x" + Integer.toHexString(i2));
        }
        if (parcel.hasReadWriteHelper()) {
            synchronized (this) {
                this.mOwnsLazyValues = false;
                initializeFromParcelLocked(parcel, false, z2);
            }
            this.mHasIntent = parcel.readBoolean();
            return;
        }
        int iDataPosition = parcel.dataPosition();
        parcel.setDataPosition(MathUtils.addOrThrow(iDataPosition, i));
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.setDataPosition(0);
        parcelObtain.appendFrom(parcel, iDataPosition, i);
        parcelObtain.adoptClassCookies(parcel);
        parcelObtain.setDataPosition(0);
        this.mOwnsLazyValues = true;
        this.mParcelledByNative = z2;
        this.mParcelledData = parcelObtain;
        this.mHasIntent = parcel.readBoolean();
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, String str, Object obj) throws IOException {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeValue(obj);
        int iDataPosition = parcelObtain.dataPosition();
        parcelObtain.recycle();
        if (iDataPosition > 1024) {
            indentingPrintWriter.println(str + " [size=" + iDataPosition + NavigationBarInflaterView.SIZE_MOD_END);
            if (obj instanceof BaseBundle) {
                dumpStats(indentingPrintWriter, (BaseBundle) obj);
            } else if (obj instanceof SparseArray) {
                dumpStats(indentingPrintWriter, (SparseArray) obj);
            }
        }
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, SparseArray sparseArray) throws IOException {
        indentingPrintWriter.increaseIndent();
        if (sparseArray == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        for (int i = 0; i < sparseArray.size(); i++) {
            dumpStats(indentingPrintWriter, "0x" + Integer.toHexString(sparseArray.keyAt(i)), sparseArray.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, BaseBundle baseBundle) throws IOException {
        indentingPrintWriter.increaseIndent();
        if (baseBundle == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        ArrayMap<String, Object> itemwiseMap = baseBundle.getItemwiseMap();
        for (int i = 0; i < itemwiseMap.size(); i++) {
            dumpStats(indentingPrintWriter, itemwiseMap.keyAt(i), itemwiseMap.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
