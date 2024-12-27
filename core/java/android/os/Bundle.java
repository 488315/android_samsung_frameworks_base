package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Bundle extends BaseBundle implements Cloneable, Parcelable {
    public static final Parcelable.Creator<Bundle> CREATOR;
    public static final Bundle EMPTY = new Bundle();
    static final int FLAG_ALLOW_FDS = 1024;
    static final int FLAG_HAS_BINDERS = 4096;
    static final int FLAG_HAS_BINDERS_KNOWN = 2048;
    static final int FLAG_HAS_FDS = 256;
    static final int FLAG_HAS_FDS_KNOWN = 512;
    public static final int STATUS_BINDERS_NOT_PRESENT = 0;
    public static final int STATUS_BINDERS_PRESENT = 1;
    public static final int STATUS_BINDERS_UNKNOWN = 2;
    public static final Bundle STRIPPED;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HasBinderStatus {
    }

    static {
        EMPTY.mMap = ArrayMap.EMPTY;
        STRIPPED = new Bundle();
        STRIPPED.putInt("STRIPPED", 1);
        CREATOR = new Parcelable.Creator<Bundle>() { // from class: android.os.Bundle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Bundle createFromParcel(Parcel in) {
                return in.readBundle();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Bundle[] newArray(int size) {
                return new Bundle[size];
            }
        };
    }

    public Bundle() {
        this.mFlags = 3584;
    }

    public Bundle(Parcel parcelledData) {
        super(parcelledData);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    public Bundle(Parcel parcelledData, int length) {
        super(parcelledData, length);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    Bundle(Bundle from, boolean deep) {
        super(from, deep);
    }

    private void maybePrefillHasFds() {
        if (this.mParcelledData != null) {
            if (this.mParcelledData.hasFileDescriptors()) {
                this.mFlags |= 768;
            } else {
                this.mFlags |= 512;
            }
        }
    }

    public Bundle(ClassLoader loader) {
        super(loader);
        this.mFlags = 3584;
    }

    public Bundle(int capacity) {
        super(capacity);
        this.mFlags = 3584;
    }

    public Bundle(Bundle b) {
        super(b);
        this.mFlags = b.mFlags;
    }

    public Bundle(PersistableBundle b) {
        super(b);
        this.mFlags = 3584;
    }

    public static Bundle forPair(String key, String value) {
        Bundle b = new Bundle(1);
        b.putString(key, value);
        return b;
    }

    @Override // android.os.BaseBundle
    public void setClassLoader(ClassLoader loader) {
        super.setClassLoader(loader);
    }

    @Override // android.os.BaseBundle
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    public boolean setAllowFds(boolean allowFds) {
        boolean orig = (this.mFlags & 1024) != 0;
        if (allowFds) {
            this.mFlags |= 1024;
        } else {
            this.mFlags &= -1025;
        }
        return orig;
    }

    public void setDefusable(boolean defusable) {
        if (defusable) {
            this.mFlags |= 1;
        } else {
            this.mFlags &= -2;
        }
    }

    public static Bundle setDefusable(Bundle bundle, boolean defusable) {
        if (bundle != null) {
            bundle.setDefusable(defusable);
        }
        return bundle;
    }

    public Object clone() {
        return new Bundle(this);
    }

    public Bundle deepCopy() {
        return new Bundle(this, true);
    }

    @Override // android.os.BaseBundle
    public void clear() {
        super.clear();
        this.mFlags = 1536;
    }

    @Override // android.os.BaseBundle
    public void remove(String key) {
        super.remove(key);
        if ((this.mFlags & 256) != 0) {
            this.mFlags &= -513;
        }
        if ((this.mFlags & 4096) != 0) {
            this.mFlags &= -2049;
        }
    }

    public void putAll(Bundle bundle) {
        unparcel();
        bundle.unparcel();
        this.mOwnsLazyValues = false;
        bundle.mOwnsLazyValues = false;
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) bundle.mMap);
        if ((bundle.mFlags & 256) != 0) {
            this.mFlags |= 256;
        }
        if ((bundle.mFlags & 512) == 0) {
            this.mFlags &= -513;
        }
        if ((bundle.mFlags & 4096) != 0) {
            this.mFlags |= 4096;
        }
        if ((bundle.mFlags & 2048) == 0) {
            this.mFlags &= -2049;
        }
    }

    public int getSize() {
        if (this.mParcelledData != null) {
            return this.mParcelledData.dataSize();
        }
        return 0;
    }

    public boolean hasFileDescriptors() {
        int i;
        if ((this.mFlags & 512) == 0) {
            Parcel p = this.mParcelledData;
            if (Parcel.hasFileDescriptors(p != null ? p : this.mMap)) {
                i = this.mFlags | 256;
            } else {
                i = this.mFlags & (-257);
            }
            this.mFlags = i;
            this.mFlags |= 512;
        }
        return (this.mFlags & 256) != 0;
    }

    public int hasBinders() {
        if ((this.mFlags & 2048) != 0) {
            return (this.mFlags & 4096) != 0 ? 1 : 0;
        }
        Parcel p = this.mParcelledData;
        if (p == null) {
            return 2;
        }
        if (p.hasBinders()) {
            this.mFlags = this.mFlags | 4096 | 2048;
            return 1;
        }
        this.mFlags &= -4097;
        this.mFlags |= 2048;
        return 0;
    }

    @Override // android.os.BaseBundle
    public void putObject(String key, Object value) {
        if (value instanceof Byte) {
            putByte(key, ((Byte) value).byteValue());
            return;
        }
        if (value instanceof Character) {
            putChar(key, ((Character) value).charValue());
            return;
        }
        if (value instanceof Short) {
            putShort(key, ((Short) value).shortValue());
            return;
        }
        if (value instanceof Float) {
            putFloat(key, ((Float) value).floatValue());
            return;
        }
        if (value instanceof CharSequence) {
            putCharSequence(key, (CharSequence) value);
            return;
        }
        if (value instanceof Parcelable) {
            putParcelable(key, (Parcelable) value);
            return;
        }
        if (value instanceof Size) {
            putSize(key, (Size) value);
            return;
        }
        if (value instanceof SizeF) {
            putSizeF(key, (SizeF) value);
            return;
        }
        if (value instanceof Parcelable[]) {
            putParcelableArray(key, (Parcelable[]) value);
            return;
        }
        if (value instanceof ArrayList) {
            putParcelableArrayList(key, (ArrayList) value);
            return;
        }
        if (value instanceof List) {
            putParcelableList(key, (List) value);
            return;
        }
        if (value instanceof SparseArray) {
            putSparseParcelableArray(key, (SparseArray) value);
            return;
        }
        if (value instanceof Serializable) {
            putSerializable(key, (Serializable) value);
            return;
        }
        if (value instanceof byte[]) {
            putByteArray(key, (byte[]) value);
            return;
        }
        if (value instanceof short[]) {
            putShortArray(key, (short[]) value);
            return;
        }
        if (value instanceof char[]) {
            putCharArray(key, (char[]) value);
            return;
        }
        if (value instanceof float[]) {
            putFloatArray(key, (float[]) value);
            return;
        }
        if (value instanceof CharSequence[]) {
            putCharSequenceArray(key, (CharSequence[]) value);
            return;
        }
        if (value instanceof Bundle) {
            putBundle(key, (Bundle) value);
            return;
        }
        if (value instanceof Binder) {
            putBinder(key, (Binder) value);
        } else if (value instanceof IBinder) {
            putIBinder(key, (IBinder) value);
        } else {
            super.putObject(key, value);
        }
    }

    @Override // android.os.BaseBundle
    public void putByte(String key, byte value) {
        super.putByte(key, value);
    }

    @Override // android.os.BaseBundle
    public void putChar(String key, char value) {
        super.putChar(key, value);
    }

    @Override // android.os.BaseBundle
    public void putShort(String key, short value) {
        super.putShort(key, value);
    }

    @Override // android.os.BaseBundle
    public void putFloat(String key, float value) {
        super.putFloat(key, value);
    }

    @Override // android.os.BaseBundle
    public void putCharSequence(String key, CharSequence value) {
        super.putCharSequence(key, value);
    }

    public void putParcelable(String key, Parcelable value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putSize(String key, Size value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putSizeF(String key, SizeF value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putParcelableArray(String key, Parcelable[] value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putParcelableList(String key, List<? extends Parcelable> value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    @Override // android.os.BaseBundle
    public void putIntegerArrayList(String key, ArrayList<Integer> value) {
        super.putIntegerArrayList(key, value);
    }

    @Override // android.os.BaseBundle
    public void putStringArrayList(String key, ArrayList<String> value) {
        super.putStringArrayList(key, value);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        super.putCharSequenceArrayList(key, value);
    }

    @Override // android.os.BaseBundle
    public void putSerializable(String key, Serializable value) {
        super.putSerializable(key, value);
    }

    @Override // android.os.BaseBundle
    public void putByteArray(String key, byte[] value) {
        super.putByteArray(key, value);
    }

    @Override // android.os.BaseBundle
    public void putShortArray(String key, short[] value) {
        super.putShortArray(key, value);
    }

    @Override // android.os.BaseBundle
    public void putCharArray(String key, char[] value) {
        super.putCharArray(key, value);
    }

    @Override // android.os.BaseBundle
    public void putFloatArray(String key, float[] value) {
        super.putFloatArray(key, value);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArray(String key, CharSequence[] value) {
        super.putCharSequenceArray(key, value);
    }

    public void putBundle(String key, Bundle value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putBinder(String key, IBinder value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -2049;
    }

    @Deprecated
    public void putIBinder(String key, IBinder value) {
        unparcel();
        this.mMap.put(key, value);
        this.mFlags &= -2049;
    }

    @Override // android.os.BaseBundle
    public byte getByte(String key) {
        return super.getByte(key);
    }

    @Override // android.os.BaseBundle
    public Byte getByte(String key, byte defaultValue) {
        return super.getByte(key, defaultValue);
    }

    @Override // android.os.BaseBundle
    public char getChar(String key) {
        return super.getChar(key);
    }

    @Override // android.os.BaseBundle
    public char getChar(String key, char defaultValue) {
        return super.getChar(key, defaultValue);
    }

    @Override // android.os.BaseBundle
    public short getShort(String key) {
        return super.getShort(key);
    }

    @Override // android.os.BaseBundle
    public short getShort(String key, short defaultValue) {
        return super.getShort(key, defaultValue);
    }

    @Override // android.os.BaseBundle
    public float getFloat(String key) {
        return super.getFloat(key);
    }

    @Override // android.os.BaseBundle
    public float getFloat(String key, float defaultValue) {
        return super.getFloat(key, defaultValue);
    }

    @Override // android.os.BaseBundle
    public CharSequence getCharSequence(String key) {
        return super.getCharSequence(key);
    }

    @Override // android.os.BaseBundle
    public CharSequence getCharSequence(String key, CharSequence defaultValue) {
        return super.getCharSequence(key, defaultValue);
    }

    public Size getSize(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (Size) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Size", e);
            return null;
        }
    }

    public SizeF getSizeF(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (SizeF) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "SizeF", e);
            return null;
        }
    }

    public Bundle getBundle(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (Bundle) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Bundle", e);
            return null;
        }
    }

    @Deprecated
    public <T extends Parcelable> T getParcelable(String key) {
        unparcel();
        Object o = getValue(key);
        if (o == null) {
            return null;
        }
        try {
            return (T) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Parcelable", e);
            return null;
        }
    }

    public <T> T getParcelable(String str, Class<T> cls) {
        return (T) get(str, cls);
    }

    @Deprecated
    public Parcelable[] getParcelableArray(String key) {
        unparcel();
        Object o = getValue(key);
        if (o == null) {
            return null;
        }
        try {
            return (Parcelable[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Parcelable[]", e);
            return null;
        }
    }

    public <T> T[] getParcelableArray(String str, Class<T> cls) {
        unparcel();
        try {
            return (T[]) ((Object[]) getValue(str, Parcelable[].class, (Class) Objects.requireNonNull(cls)));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(str, cls.getCanonicalName() + "[]", e);
            return null;
        }
    }

    @Deprecated
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key) {
        unparcel();
        Object o = getValue(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "ArrayList", e);
            return null;
        }
    }

    public <T> ArrayList<T> getParcelableArrayList(String key, Class<? extends T> clazz) {
        return getArrayList(key, clazz);
    }

    @Deprecated
    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(String key) {
        unparcel();
        Object o = getValue(key);
        if (o == null) {
            return null;
        }
        try {
            return (SparseArray) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "SparseArray", e);
            return null;
        }
    }

    public <T> SparseArray<T> getSparseParcelableArray(String key, Class<? extends T> clazz) {
        unparcel();
        try {
            return (SparseArray) getValue(key, SparseArray.class, (Class) Objects.requireNonNull(clazz));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(key, "SparseArray<" + clazz.getCanonicalName() + ">", e);
            return null;
        }
    }

    @Override // android.os.BaseBundle
    @Deprecated
    public Serializable getSerializable(String key) {
        return super.getSerializable(key);
    }

    @Override // android.os.BaseBundle
    public <T extends Serializable> T getSerializable(String str, Class<T> cls) {
        return (T) super.getSerializable(str, (Class) Objects.requireNonNull(cls));
    }

    @Override // android.os.BaseBundle
    public ArrayList<Integer> getIntegerArrayList(String key) {
        return super.getIntegerArrayList(key);
    }

    @Override // android.os.BaseBundle
    public ArrayList<String> getStringArrayList(String key) {
        return super.getStringArrayList(key);
    }

    @Override // android.os.BaseBundle
    public ArrayList<CharSequence> getCharSequenceArrayList(String key) {
        return super.getCharSequenceArrayList(key);
    }

    @Override // android.os.BaseBundle
    public byte[] getByteArray(String key) {
        return super.getByteArray(key);
    }

    @Override // android.os.BaseBundle
    public short[] getShortArray(String key) {
        return super.getShortArray(key);
    }

    @Override // android.os.BaseBundle
    public char[] getCharArray(String key) {
        return super.getCharArray(key);
    }

    @Override // android.os.BaseBundle
    public float[] getFloatArray(String key) {
        return super.getFloatArray(key);
    }

    @Override // android.os.BaseBundle
    public CharSequence[] getCharSequenceArray(String key) {
        return super.getCharSequenceArray(key);
    }

    public IBinder getBinder(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (IBinder) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "IBinder", e);
            return null;
        }
    }

    @Deprecated
    public IBinder getIBinder(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (IBinder) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "IBinder", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (!hasFileDescriptors()) {
            return 0;
        }
        int mask = 0 | 1;
        return mask;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        boolean oldAllowFds = parcel.pushAllowFds((this.mFlags & 1024) != 0);
        try {
            writeToParcelInner(parcel, flags);
        } finally {
            parcel.restoreAllowFds(oldAllowFds);
        }
    }

    public void readFromParcel(Parcel parcel) {
        readFromParcelInner(parcel);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    public synchronized String toString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "Bundle[EMPTY_PARCEL]";
            }
            return "Bundle[mParcelledData.dataSize=" + this.mParcelledData.dataSize() + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return "Bundle[" + this.mMap.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public synchronized String toShortString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "EMPTY_PARCEL";
            }
            return "mParcelledData.dataSize=" + this.mParcelledData.dataSize();
        }
        return this.mMap.toString();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                proto.write(1120986464257L, 0);
            } else {
                proto.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            proto.write(1138166333442L, this.mMap.toString());
        }
        proto.end(token);
    }
}
