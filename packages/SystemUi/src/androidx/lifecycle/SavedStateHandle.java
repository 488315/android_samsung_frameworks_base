package androidx.lifecycle;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.core.os.BundleApi18ImplKt;
import androidx.core.os.BundleApi21ImplKt;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SavedStateHandle {
    public final Map flows;
    public final Map liveDatas;
    public final Map regular;
    public final SavedStateHandle$$ExternalSyntheticLambda0 savedStateProvider;
    public final Map savedStateProviders;
    public static final Companion Companion = new Companion(null);
    public static final Class[] ACCEPTABLE_CLASSES = {Boolean.TYPE, boolean[].class, Double.TYPE, double[].class, Integer.TYPE, int[].class, Long.TYPE, long[].class, String.class, String[].class, Binder.class, Bundle.class, Byte.TYPE, byte[].class, Character.TYPE, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, Float.TYPE, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, Short.TYPE, short[].class, SparseArray.class, Size.class, SizeF.class};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static SavedStateHandle createHandle(Bundle bundle, Bundle bundle2) {
            if (bundle == null) {
                if (bundle2 == null) {
                    return new SavedStateHandle();
                }
                HashMap hashMap = new HashMap();
                for (String str : bundle2.keySet()) {
                    hashMap.put(str, bundle2.get(str));
                }
                return new SavedStateHandle(hashMap);
            }
            ArrayList parcelableArrayList = bundle.getParcelableArrayList("keys");
            ArrayList parcelableArrayList2 = bundle.getParcelableArrayList("values");
            if (!((parcelableArrayList == null || parcelableArrayList2 == null || parcelableArrayList.size() != parcelableArrayList2.size()) ? false : true)) {
                throw new IllegalStateException("Invalid bundle passed as restored state".toString());
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int size = parcelableArrayList.size();
            for (int i = 0; i < size; i++) {
                linkedHashMap.put((String) parcelableArrayList.get(i), parcelableArrayList2.get(i));
            }
            return new SavedStateHandle(linkedHashMap);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0079 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bundle $r8$lambda$aMir0GWwzPQviKVGE0DPm0kayew(SavedStateHandle savedStateHandle) {
        Iterator it = MapsKt__MapsKt.toMap(savedStateHandle.savedStateProviders).entrySet().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            Map map = savedStateHandle.regular;
            int i = 0;
            if (!hasNext) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                Set<String> keySet = linkedHashMap.keySet();
                ArrayList arrayList = new ArrayList(keySet.size());
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                for (String str : keySet) {
                    arrayList.add(str);
                    arrayList2.add(linkedHashMap.get(str));
                }
                Pair[] pairArr = {new Pair("keys", arrayList), new Pair("values", arrayList2)};
                Bundle bundle = new Bundle(2);
                while (i < 2) {
                    Pair pair = pairArr[i];
                    String str2 = (String) pair.component1();
                    Object component2 = pair.component2();
                    if (component2 == null) {
                        bundle.putString(str2, null);
                    } else if (component2 instanceof Boolean) {
                        bundle.putBoolean(str2, ((Boolean) component2).booleanValue());
                    } else if (component2 instanceof Byte) {
                        bundle.putByte(str2, ((Number) component2).byteValue());
                    } else if (component2 instanceof Character) {
                        bundle.putChar(str2, ((Character) component2).charValue());
                    } else if (component2 instanceof Double) {
                        bundle.putDouble(str2, ((Number) component2).doubleValue());
                    } else if (component2 instanceof Float) {
                        bundle.putFloat(str2, ((Number) component2).floatValue());
                    } else if (component2 instanceof Integer) {
                        bundle.putInt(str2, ((Number) component2).intValue());
                    } else if (component2 instanceof Long) {
                        bundle.putLong(str2, ((Number) component2).longValue());
                    } else if (component2 instanceof Short) {
                        bundle.putShort(str2, ((Number) component2).shortValue());
                    } else if (component2 instanceof Bundle) {
                        bundle.putBundle(str2, (Bundle) component2);
                    } else if (component2 instanceof CharSequence) {
                        bundle.putCharSequence(str2, (CharSequence) component2);
                    } else if (component2 instanceof Parcelable) {
                        bundle.putParcelable(str2, (Parcelable) component2);
                    } else if (component2 instanceof boolean[]) {
                        bundle.putBooleanArray(str2, (boolean[]) component2);
                    } else if (component2 instanceof byte[]) {
                        bundle.putByteArray(str2, (byte[]) component2);
                    } else if (component2 instanceof char[]) {
                        bundle.putCharArray(str2, (char[]) component2);
                    } else if (component2 instanceof double[]) {
                        bundle.putDoubleArray(str2, (double[]) component2);
                    } else if (component2 instanceof float[]) {
                        bundle.putFloatArray(str2, (float[]) component2);
                    } else if (component2 instanceof int[]) {
                        bundle.putIntArray(str2, (int[]) component2);
                    } else if (component2 instanceof long[]) {
                        bundle.putLongArray(str2, (long[]) component2);
                    } else if (component2 instanceof short[]) {
                        bundle.putShortArray(str2, (short[]) component2);
                    } else if (component2 instanceof Object[]) {
                        Class<?> componentType = component2.getClass().getComponentType();
                        Intrinsics.checkNotNull(componentType);
                        if (Parcelable.class.isAssignableFrom(componentType)) {
                            bundle.putParcelableArray(str2, (Parcelable[]) component2);
                        } else if (String.class.isAssignableFrom(componentType)) {
                            bundle.putStringArray(str2, (String[]) component2);
                        } else if (CharSequence.class.isAssignableFrom(componentType)) {
                            bundle.putCharSequenceArray(str2, (CharSequence[]) component2);
                        } else {
                            if (!Serializable.class.isAssignableFrom(componentType)) {
                                throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + str2 + '\"');
                            }
                            bundle.putSerializable(str2, (Serializable) component2);
                        }
                    } else if (component2 instanceof Serializable) {
                        bundle.putSerializable(str2, (Serializable) component2);
                    } else if (component2 instanceof IBinder) {
                        int i2 = BundleApi18ImplKt.$r8$clinit;
                        bundle.putBinder(str2, (IBinder) component2);
                    } else if (component2 instanceof Size) {
                        int i3 = BundleApi21ImplKt.$r8$clinit;
                        bundle.putSize(str2, (Size) component2);
                    } else {
                        if (!(component2 instanceof SizeF)) {
                            throw new IllegalArgumentException("Illegal value type " + component2.getClass().getCanonicalName() + " for key \"" + str2 + '\"');
                        }
                        int i4 = BundleApi21ImplKt.$r8$clinit;
                        bundle.putSizeF(str2, (SizeF) component2);
                    }
                    i++;
                }
                return bundle;
            }
            Map.Entry entry = (Map.Entry) it.next();
            String str3 = (String) entry.getKey();
            Bundle saveState = ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState();
            Companion.getClass();
            if (saveState != null) {
                for (Class cls : ACCEPTABLE_CLASSES) {
                    Intrinsics.checkNotNull(cls);
                    if (!cls.isInstance(saveState)) {
                    }
                }
                if (i != 0) {
                    StringBuilder sb = new StringBuilder("Can't put value with type ");
                    Intrinsics.checkNotNull(saveState);
                    sb.append(saveState.getClass());
                    sb.append(" into saved state");
                    throw new IllegalArgumentException(sb.toString());
                }
                Object obj = ((LinkedHashMap) savedStateHandle.liveDatas).get(str3);
                MutableLiveData mutableLiveData = obj instanceof MutableLiveData ? (MutableLiveData) obj : null;
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(saveState);
                } else {
                    map.put(str3, saveState);
                }
                MutableStateFlow mutableStateFlow = (MutableStateFlow) ((LinkedHashMap) savedStateHandle.flows).get(str3);
                if (mutableStateFlow != null) {
                    ((StateFlowImpl) mutableStateFlow).setValue(saveState);
                }
            }
            i = 1;
            if (i != 0) {
            }
        }
    }

    public SavedStateHandle(Map<String, ? extends Object> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.regular = linkedHashMap;
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new SavedStateHandle$$ExternalSyntheticLambda0(this, 0);
        linkedHashMap.putAll(map);
    }

    public SavedStateHandle() {
        this.regular = new LinkedHashMap();
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new SavedStateHandle$$ExternalSyntheticLambda0(this, 1);
    }
}
