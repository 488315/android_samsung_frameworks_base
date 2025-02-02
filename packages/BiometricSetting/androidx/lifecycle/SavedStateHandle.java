package androidx.lifecycle;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: SavedStateHandle.kt */
/* loaded from: classes.dex */
public final class SavedStateHandle {
    private static final Class<? extends Object>[] ACCEPTABLE_CLASSES = {Boolean.TYPE, boolean[].class, Double.TYPE, double[].class, Integer.TYPE, int[].class, Long.TYPE, long[].class, String.class, String[].class, Binder.class, Bundle.class, Byte.TYPE, byte[].class, Character.TYPE, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, Float.TYPE, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, Short.TYPE, short[].class, SparseArray.class, Size.class, SizeF.class};
    private final Map<String, Object> regular = new LinkedHashMap();
    private final Map<String, SavedStateRegistry.SavedStateProvider> savedStateProviders = new LinkedHashMap();
    private final Map<String, Object> liveDatas = new LinkedHashMap();
    private final Map<String, MutableStateFlow<Object>> flows = new LinkedHashMap();
    private final SavedStateHandle$$ExternalSyntheticLambda0 savedStateProvider = new SavedStateHandle$$ExternalSyntheticLambda0(this);

    /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bundle $r8$lambda$aMir0GWwzPQviKVGE0DPm0kayew(SavedStateHandle this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Iterator it = MapsKt.toMap(this$0.savedStateProviders).entrySet().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            Map<String, Object> map = this$0.regular;
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
                    String key = (String) pair.component1();
                    Object component2 = pair.component2();
                    if (component2 == null) {
                        bundle.putString(key, null);
                    } else if (component2 instanceof Boolean) {
                        bundle.putBoolean(key, ((Boolean) component2).booleanValue());
                    } else if (component2 instanceof Byte) {
                        bundle.putByte(key, ((Number) component2).byteValue());
                    } else if (component2 instanceof Character) {
                        bundle.putChar(key, ((Character) component2).charValue());
                    } else if (component2 instanceof Double) {
                        bundle.putDouble(key, ((Number) component2).doubleValue());
                    } else if (component2 instanceof Float) {
                        bundle.putFloat(key, ((Number) component2).floatValue());
                    } else if (component2 instanceof Integer) {
                        bundle.putInt(key, ((Number) component2).intValue());
                    } else if (component2 instanceof Long) {
                        bundle.putLong(key, ((Number) component2).longValue());
                    } else if (component2 instanceof Short) {
                        bundle.putShort(key, ((Number) component2).shortValue());
                    } else if (component2 instanceof Bundle) {
                        bundle.putBundle(key, (Bundle) component2);
                    } else if (component2 instanceof CharSequence) {
                        bundle.putCharSequence(key, (CharSequence) component2);
                    } else if (component2 instanceof Parcelable) {
                        bundle.putParcelable(key, (Parcelable) component2);
                    } else if (component2 instanceof boolean[]) {
                        bundle.putBooleanArray(key, (boolean[]) component2);
                    } else if (component2 instanceof byte[]) {
                        bundle.putByteArray(key, (byte[]) component2);
                    } else if (component2 instanceof char[]) {
                        bundle.putCharArray(key, (char[]) component2);
                    } else if (component2 instanceof double[]) {
                        bundle.putDoubleArray(key, (double[]) component2);
                    } else if (component2 instanceof float[]) {
                        bundle.putFloatArray(key, (float[]) component2);
                    } else if (component2 instanceof int[]) {
                        bundle.putIntArray(key, (int[]) component2);
                    } else if (component2 instanceof long[]) {
                        bundle.putLongArray(key, (long[]) component2);
                    } else if (component2 instanceof short[]) {
                        bundle.putShortArray(key, (short[]) component2);
                    } else if (component2 instanceof Object[]) {
                        Class<?> componentType = component2.getClass().getComponentType();
                        Intrinsics.checkNotNull(componentType);
                        if (Parcelable.class.isAssignableFrom(componentType)) {
                            bundle.putParcelableArray(key, (Parcelable[]) component2);
                        } else if (String.class.isAssignableFrom(componentType)) {
                            bundle.putStringArray(key, (String[]) component2);
                        } else if (CharSequence.class.isAssignableFrom(componentType)) {
                            bundle.putCharSequenceArray(key, (CharSequence[]) component2);
                        } else {
                            if (!Serializable.class.isAssignableFrom(componentType)) {
                                throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + key + '\"');
                            }
                            bundle.putSerializable(key, (Serializable) component2);
                        }
                    } else if (component2 instanceof Serializable) {
                        bundle.putSerializable(key, (Serializable) component2);
                    } else if (component2 instanceof IBinder) {
                        Intrinsics.checkNotNullParameter(key, "key");
                        bundle.putBinder(key, (IBinder) component2);
                    } else if (component2 instanceof Size) {
                        Intrinsics.checkNotNullParameter(key, "key");
                        bundle.putSize(key, (Size) component2);
                    } else {
                        if (!(component2 instanceof SizeF)) {
                            throw new IllegalArgumentException("Illegal value type " + component2.getClass().getCanonicalName() + " for key \"" + key + '\"');
                        }
                        Intrinsics.checkNotNullParameter(key, "key");
                        bundle.putSizeF(key, (SizeF) component2);
                    }
                    i++;
                }
                return bundle;
            }
            Map.Entry entry = (Map.Entry) it.next();
            String key2 = (String) entry.getKey();
            Bundle saveState = ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState();
            Intrinsics.checkNotNullParameter(key2, "key");
            if (saveState != null) {
                Class<? extends Object>[] clsArr = ACCEPTABLE_CLASSES;
                for (int i2 = 0; i2 < 29; i2++) {
                    Class<? extends Object> cls = clsArr[i2];
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
                Object obj = ((LinkedHashMap) this$0.liveDatas).get(key2);
                MutableLiveData mutableLiveData = obj instanceof MutableLiveData ? (MutableLiveData) obj : null;
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(saveState);
                } else {
                    map.put(key2, saveState);
                }
                MutableStateFlow mutableStateFlow = (MutableStateFlow) ((LinkedHashMap) this$0.flows).get(key2);
                if (mutableStateFlow != null) {
                    mutableStateFlow.setValue();
                }
            }
            i = 1;
            if (i != 0) {
            }
        }
    }

    public final SavedStateHandle$$ExternalSyntheticLambda0 savedStateProvider() {
        return this.savedStateProvider;
    }
}
