package android.os;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.opengl.GLES30;
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
    public static final Bundle EMPTY;
    static final int FLAG_ALLOW_FDS = 1024;
    static final int FLAG_HAS_BINDERS = 4096;
    static final int FLAG_HAS_BINDERS_KNOWN = 2048;
    static final int FLAG_HAS_FDS = 256;
    static final int FLAG_HAS_FDS_KNOWN = 512;
    static final int FLAG_HAS_INTENT = 16384;
    static final int FLAG_VERIFY_TOKENS_PRESENT = 8192;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int STATUS_BINDERS_NOT_PRESENT = 0;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int STATUS_BINDERS_PRESENT = 1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int STATUS_BINDERS_UNKNOWN = 2;
    public static final Bundle STRIPPED;
    public static Class<?> intentClass;
    private boolean isFirstRetrievedFromABundle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HasBinderStatus {
    }

    static {
        Bundle bundle = new Bundle();
        EMPTY = bundle;
        bundle.mMap = ArrayMap.EMPTY;
        Bundle bundle2 = new Bundle();
        STRIPPED = bundle2;
        bundle2.putInt("STRIPPED", 1);
        CREATOR = new Parcelable.Creator<Bundle>() { // from class: android.os.Bundle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Bundle createFromParcel(Parcel parcel) {
                return parcel.readBundle();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Bundle[] newArray(int i) {
                return new Bundle[i];
            }
        };
    }

    public Bundle() {
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 3584;
    }

    public Bundle(Parcel parcel) {
        super(parcel);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    public Bundle(Parcel parcel, int i) {
        super(parcel, i);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    Bundle(Bundle bundle, boolean z) {
        super(bundle, z);
        this.isFirstRetrievedFromABundle = false;
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

    public Bundle(ClassLoader classLoader) {
        super(classLoader);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 3584;
    }

    public Bundle(int i) {
        super(i);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 3584;
    }

    public Bundle(Bundle bundle) {
        super(bundle);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = bundle.mFlags;
    }

    public Bundle(PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.isFirstRetrievedFromABundle = false;
        this.mFlags = 3584;
    }

    public static Bundle forPair(String str, String str2) {
        Bundle bundle = new Bundle(1);
        bundle.putString(str, str2);
        return bundle;
    }

    @Override // android.os.BaseBundle
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }

    @Override // android.os.BaseBundle, android.os.Parcel.ClassLoaderProvider
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    public boolean setAllowFds(boolean z) {
        boolean z2 = (this.mFlags & 1024) != 0;
        if (z) {
            this.mFlags |= 1024;
            return z2;
        }
        this.mFlags &= -1025;
        return z2;
    }

    public void enableTokenVerification() {
        this.mFlags |= 8192;
    }

    public void setDefusable(boolean z) {
        if (z) {
            this.mFlags |= 1;
        } else {
            this.mFlags &= -2;
        }
    }

    public static Bundle setDefusable(Bundle bundle, boolean z) {
        if (bundle != null) {
            bundle.setDefusable(z);
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
    public void remove(String str) {
        super.remove(str);
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
        int size = bundle.mMap.size();
        for (int i = 0; i < size; i++) {
            String keyAt = bundle.mMap.keyAt(i);
            Object valueAt = bundle.mMap.valueAt(i);
            if (valueAt instanceof Bundle) {
                ((Bundle) valueAt).isFirstRetrievedFromABundle = true;
            }
            this.mMap.put(keyAt, valueAt);
        }
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
        setHasIntent(hasIntent() || bundle.hasIntent());
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
            Object obj = this.mParcelledData;
            if (obj == null) {
                obj = this.mMap;
            }
            if (Parcel.hasFileDescriptors(obj)) {
                i = this.mFlags | 256;
            } else {
                i = this.mFlags & (-257);
            }
            this.mFlags = i;
            this.mFlags |= 512;
        }
        return (this.mFlags & 256) != 0;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int hasBinders() {
        if ((this.mFlags & 2048) != 0) {
            return (this.mFlags & 4096) != 0 ? 1 : 0;
        }
        Parcel parcel = this.mParcelledData;
        if (parcel == null) {
            return 2;
        }
        if (parcel.hasBinders()) {
            this.mFlags |= GLES30.GL_COLOR;
            return 1;
        }
        this.mFlags &= -4097;
        this.mFlags |= 2048;
        return 0;
    }

    @Override // android.os.BaseBundle
    public boolean hasIntent() {
        return super.hasIntent();
    }

    @Override // android.os.BaseBundle
    public void putObject(String str, Object obj) {
        if (obj instanceof Byte) {
            putByte(str, ((Byte) obj).byteValue());
            return;
        }
        if (obj instanceof Character) {
            putChar(str, ((Character) obj).charValue());
            return;
        }
        if (obj instanceof Short) {
            putShort(str, ((Short) obj).shortValue());
            return;
        }
        if (obj instanceof Float) {
            putFloat(str, ((Float) obj).floatValue());
            return;
        }
        if (obj instanceof CharSequence) {
            putCharSequence(str, (CharSequence) obj);
            return;
        }
        if (obj instanceof Parcelable) {
            putParcelable(str, (Parcelable) obj);
            return;
        }
        if (obj instanceof Size) {
            putSize(str, (Size) obj);
            return;
        }
        if (obj instanceof SizeF) {
            putSizeF(str, (SizeF) obj);
            return;
        }
        if (obj instanceof Parcelable[]) {
            putParcelableArray(str, (Parcelable[]) obj);
            return;
        }
        if (obj instanceof ArrayList) {
            putParcelableArrayList(str, (ArrayList) obj);
            return;
        }
        if (obj instanceof List) {
            putParcelableList(str, (List) obj);
            return;
        }
        if (obj instanceof SparseArray) {
            putSparseParcelableArray(str, (SparseArray) obj);
            return;
        }
        if (obj instanceof Serializable) {
            putSerializable(str, (Serializable) obj);
            return;
        }
        if (obj instanceof byte[]) {
            putByteArray(str, (byte[]) obj);
            return;
        }
        if (obj instanceof short[]) {
            putShortArray(str, (short[]) obj);
            return;
        }
        if (obj instanceof char[]) {
            putCharArray(str, (char[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            putFloatArray(str, (float[]) obj);
            return;
        }
        if (obj instanceof CharSequence[]) {
            putCharSequenceArray(str, (CharSequence[]) obj);
            return;
        }
        if (obj instanceof Bundle) {
            putBundle(str, (Bundle) obj);
            return;
        }
        if (obj instanceof Binder) {
            putBinder(str, (Binder) obj);
        } else if (obj instanceof IBinder) {
            putIBinder(str, (IBinder) obj);
        } else {
            super.putObject(str, obj);
        }
    }

    @Override // android.os.BaseBundle
    public void putByte(String str, byte b) {
        super.putByte(str, b);
    }

    @Override // android.os.BaseBundle
    public void putChar(String str, char c) {
        super.putChar(str, c);
    }

    @Override // android.os.BaseBundle
    public void putShort(String str, short s) {
        super.putShort(str, s);
    }

    @Override // android.os.BaseBundle
    public void putFloat(String str, float f) {
        super.putFloat(str, f);
    }

    @Override // android.os.BaseBundle
    public void putCharSequence(String str, CharSequence charSequence) {
        super.putCharSequence(str, charSequence);
    }

    public void putParcelable(String str, Parcelable parcelable) {
        unparcel();
        this.mMap.put(str, parcelable);
        this.mFlags &= -513;
        this.mFlags &= -2049;
        Class<?> cls = intentClass;
        if (cls != null && cls.isInstance(parcelable)) {
            setHasIntent(true);
        } else if (parcelable instanceof Bundle) {
            ((Bundle) parcelable).isFirstRetrievedFromABundle = true;
        }
    }

    public void putSize(String str, Size size) {
        unparcel();
        this.mMap.put(str, size);
    }

    public void putSizeF(String str, SizeF sizeF) {
        unparcel();
        this.mMap.put(str, sizeF);
    }

    public void putParcelableArray(String str, Parcelable[] parcelableArr) {
        unparcel();
        this.mMap.put(str, parcelableArr);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putParcelableArrayList(String str, ArrayList<? extends Parcelable> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putParcelableList(String str, List<? extends Parcelable> list) {
        unparcel();
        this.mMap.put(str, list);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    public void putSparseParcelableArray(String str, SparseArray<? extends Parcelable> sparseArray) {
        unparcel();
        this.mMap.put(str, sparseArray);
        this.mFlags &= -513;
        this.mFlags &= -2049;
    }

    @Override // android.os.BaseBundle
    public void putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        super.putIntegerArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putStringArrayList(String str, ArrayList<String> arrayList) {
        super.putStringArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArrayList(String str, ArrayList<CharSequence> arrayList) {
        super.putCharSequenceArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putSerializable(String str, Serializable serializable) {
        super.putSerializable(str, serializable);
    }

    @Override // android.os.BaseBundle
    public void putByteArray(String str, byte[] bArr) {
        super.putByteArray(str, bArr);
    }

    @Override // android.os.BaseBundle
    public void putShortArray(String str, short[] sArr) {
        super.putShortArray(str, sArr);
    }

    @Override // android.os.BaseBundle
    public void putCharArray(String str, char[] cArr) {
        super.putCharArray(str, cArr);
    }

    @Override // android.os.BaseBundle
    public void putFloatArray(String str, float[] fArr) {
        super.putFloatArray(str, fArr);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArray(String str, CharSequence[] charSequenceArr) {
        super.putCharSequenceArray(str, charSequenceArr);
    }

    public void putBundle(String str, Bundle bundle) {
        unparcel();
        if (bundle != null) {
            bundle.isFirstRetrievedFromABundle = true;
        }
        this.mMap.put(str, bundle);
    }

    public void putBinder(String str, IBinder iBinder) {
        unparcel();
        this.mMap.put(str, iBinder);
        this.mFlags &= -2049;
    }

    @Deprecated
    public void putIBinder(String str, IBinder iBinder) {
        unparcel();
        this.mMap.put(str, iBinder);
        this.mFlags &= -2049;
    }

    @Override // android.os.BaseBundle
    public byte getByte(String str) {
        return super.getByte(str);
    }

    @Override // android.os.BaseBundle
    public Byte getByte(String str, byte b) {
        return super.getByte(str, b);
    }

    @Override // android.os.BaseBundle
    public char getChar(String str) {
        return super.getChar(str);
    }

    @Override // android.os.BaseBundle
    public char getChar(String str, char c) {
        return super.getChar(str, c);
    }

    @Override // android.os.BaseBundle
    public short getShort(String str) {
        return super.getShort(str);
    }

    @Override // android.os.BaseBundle
    public short getShort(String str, short s) {
        return super.getShort(str, s);
    }

    @Override // android.os.BaseBundle
    public float getFloat(String str) {
        return super.getFloat(str);
    }

    @Override // android.os.BaseBundle
    public float getFloat(String str, float f) {
        return super.getFloat(str, f);
    }

    @Override // android.os.BaseBundle
    public CharSequence getCharSequence(String str) {
        return super.getCharSequence(str);
    }

    @Override // android.os.BaseBundle
    public CharSequence getCharSequence(String str, CharSequence charSequence) {
        return super.getCharSequence(str, charSequence);
    }

    public Size getSize(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        try {
            return (Size) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "Size", e);
            return null;
        }
    }

    public SizeF getSizeF(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        try {
            return (SizeF) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "SizeF", e);
            return null;
        }
    }

    public Bundle getBundle(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            Bundle bundle = (Bundle) obj;
            bundle.setClassLoaderSameAsContainerBundleWhenRetrievedFirstTime(this);
            return bundle;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "Bundle", e);
            return null;
        }
    }

    void setClassLoaderSameAsContainerBundleWhenRetrievedFirstTime(BaseBundle baseBundle) {
        if (this.isFirstRetrievedFromABundle) {
            return;
        }
        setClassLoader(baseBundle.getClassLoader());
        this.isFirstRetrievedFromABundle = true;
    }

    @Deprecated
    public <T extends Parcelable> T getParcelable(String str) {
        unparcel();
        Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (T) value;
        } catch (ClassCastException e) {
            typeWarning(str, value, "Parcelable", e);
            return null;
        }
    }

    public <T> T getParcelable(String str, Class<T> cls) {
        return (T) get(str, cls);
    }

    @Deprecated
    public Parcelable[] getParcelableArray(String str) {
        unparcel();
        Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (Parcelable[]) value;
        } catch (ClassCastException e) {
            typeWarning(str, value, "Parcelable[]", e);
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
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String str) {
        unparcel();
        Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (ArrayList) value;
        } catch (ClassCastException e) {
            typeWarning(str, value, "ArrayList", e);
            return null;
        }
    }

    public <T> ArrayList<T> getParcelableArrayList(String str, Class<? extends T> cls) {
        return getArrayList(str, cls);
    }

    @Deprecated
    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(String str) {
        unparcel();
        Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (SparseArray) value;
        } catch (ClassCastException e) {
            typeWarning(str, value, "SparseArray", e);
            return null;
        }
    }

    public <T> SparseArray<T> getSparseParcelableArray(String str, Class<? extends T> cls) {
        unparcel();
        try {
            return (SparseArray) getValue(str, SparseArray.class, (Class) Objects.requireNonNull(cls));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(str, "SparseArray<" + cls.getCanonicalName() + ">", e);
            return null;
        }
    }

    @Override // android.os.BaseBundle
    @Deprecated
    public Serializable getSerializable(String str) {
        return super.getSerializable(str);
    }

    @Override // android.os.BaseBundle
    public <T extends Serializable> T getSerializable(String str, Class<T> cls) {
        return (T) super.getSerializable(str, (Class) Objects.requireNonNull(cls));
    }

    @Override // android.os.BaseBundle
    public ArrayList<Integer> getIntegerArrayList(String str) {
        return super.getIntegerArrayList(str);
    }

    @Override // android.os.BaseBundle
    public ArrayList<String> getStringArrayList(String str) {
        return super.getStringArrayList(str);
    }

    @Override // android.os.BaseBundle
    public ArrayList<CharSequence> getCharSequenceArrayList(String str) {
        return super.getCharSequenceArrayList(str);
    }

    @Override // android.os.BaseBundle
    public byte[] getByteArray(String str) {
        return super.getByteArray(str);
    }

    @Override // android.os.BaseBundle
    public short[] getShortArray(String str) {
        return super.getShortArray(str);
    }

    @Override // android.os.BaseBundle
    public char[] getCharArray(String str) {
        return super.getCharArray(str);
    }

    @Override // android.os.BaseBundle
    public float[] getFloatArray(String str) {
        return super.getFloatArray(str);
    }

    @Override // android.os.BaseBundle
    public CharSequence[] getCharSequenceArray(String str) {
        return super.getCharSequenceArray(str);
    }

    public IBinder getBinder(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (IBinder) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "IBinder", e);
            return null;
        }
    }

    @Deprecated
    public IBinder getIBinder(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (IBinder) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "IBinder", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return hasFileDescriptors() ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean pushAllowFds = parcel.pushAllowFds((this.mFlags & 1024) != 0);
        try {
            writeToParcelInner(parcel, i);
        } finally {
            parcel.restoreAllowFds(pushAllowFds);
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

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(start);
    }
}
