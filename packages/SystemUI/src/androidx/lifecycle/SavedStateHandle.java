package androidx.lifecycle;

import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.core.os.BundleKt;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SavedStateHandle {
    public final Map flows;
    public final Map liveDatas;
    public final Map regular;
    public final SavedStateRegistry.SavedStateProvider savedStateProvider;
    public final Map savedStateProviders;
    public static final Companion Companion = new Companion(null);
    public static final Class[] ACCEPTABLE_CLASSES = {Boolean.TYPE, boolean[].class, Double.TYPE, double[].class, Integer.TYPE, int[].class, Long.TYPE, long[].class, String.class, String[].class, Binder.class, Bundle.class, Byte.TYPE, byte[].class, Character.TYPE, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, Float.TYPE, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, Short.TYPE, short[].class, SparseArray.class, Size.class, SizeF.class};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
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
            ClassLoader classLoader = SavedStateHandle.class.getClassLoader();
            classLoader.getClass();
            bundle.setClassLoader(classLoader);
            ArrayList parcelableArrayList = bundle.getParcelableArrayList("keys");
            ArrayList parcelableArrayList2 = bundle.getParcelableArrayList("values");
            if (parcelableArrayList == null || parcelableArrayList2 == null || parcelableArrayList.size() != parcelableArrayList2.size()) {
                throw new IllegalStateException("Invalid bundle passed as restored state");
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int size = parcelableArrayList.size();
            for (int i = 0; i < size; i++) {
                linkedHashMap.put((String) parcelableArrayList.get(i), parcelableArrayList2.get(i));
            }
            return new SavedStateHandle(linkedHashMap);
        }

        private Companion() {
        }
    }

    public static Bundle $r8$lambda$eeLDsk5Qp_lgSAYrhUViF2PFB0k(SavedStateHandle savedStateHandle) {
        for (Map.Entry entry : MapsKt__MapsKt.toMap(savedStateHandle.savedStateProviders).entrySet()) {
            savedStateHandle.set(((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState(), (String) entry.getKey());
        }
        Set<String> keySet = ((LinkedHashMap) savedStateHandle.regular).keySet();
        ArrayList arrayList = new ArrayList(keySet.size());
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (String str : keySet) {
            arrayList.add(str);
            arrayList2.add(((LinkedHashMap) savedStateHandle.regular).get(str));
        }
        return BundleKt.bundleOf(new Pair("keys", arrayList), new Pair("values", arrayList2));
    }

    public SavedStateHandle(Map<String, ? extends Object> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.regular = linkedHashMap;
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new SavedStateRegistry.SavedStateProvider() { // from class: androidx.lifecycle.SavedStateHandle$$ExternalSyntheticLambda0
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                return SavedStateHandle.$r8$lambda$eeLDsk5Qp_lgSAYrhUViF2PFB0k(SavedStateHandle.this);
            }
        };
        linkedHashMap.putAll(map);
    }

    public final Object get(String str) {
        try {
            return ((LinkedHashMap) this.regular).get(str);
        } catch (ClassCastException unused) {
            this.regular.remove(str);
            SavingStateLiveData savingStateLiveData = (SavingStateLiveData) this.liveDatas.remove(str);
            if (savingStateLiveData != null) {
                savingStateLiveData.handle = null;
            }
            this.flows.remove(str);
            return null;
        }
    }

    public final void set(Object obj, String str) {
        Companion.getClass();
        if (obj != null) {
            for (Class cls : ACCEPTABLE_CLASSES) {
                cls.getClass();
                if (!cls.isInstance(obj)) {
                }
            }
            StringBuilder sb = new StringBuilder("Can't put value with type ");
            obj.getClass();
            sb.append(obj.getClass());
            sb.append(" into saved state");
            throw new IllegalArgumentException(sb.toString());
        }
        Object obj2 = ((LinkedHashMap) this.liveDatas).get(str);
        MutableLiveData mutableLiveData = obj2 instanceof MutableLiveData ? (MutableLiveData) obj2 : null;
        if (mutableLiveData != null) {
            mutableLiveData.setValue(obj);
        } else {
            this.regular.put(str, obj);
        }
        MutableStateFlow mutableStateFlow = (MutableStateFlow) ((LinkedHashMap) this.flows).get(str);
        if (mutableStateFlow == null) {
            return;
        }
        mutableStateFlow.setValue(obj);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SavingStateLiveData extends MutableLiveData {
        public SavedStateHandle handle;
        public final String key;

        public SavingStateLiveData(SavedStateHandle savedStateHandle, String str, Object obj) {
            super(obj);
            this.key = str;
            this.handle = savedStateHandle;
        }

        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public final void setValue(Object obj) {
            SavedStateHandle savedStateHandle = this.handle;
            if (savedStateHandle != null) {
                Map map = savedStateHandle.regular;
                String str = this.key;
                map.put(str, obj);
                MutableStateFlow mutableStateFlow = (MutableStateFlow) ((LinkedHashMap) savedStateHandle.flows).get(str);
                if (mutableStateFlow != null) {
                    mutableStateFlow.setValue(obj);
                }
            }
            super.setValue(obj);
        }

        public SavingStateLiveData(SavedStateHandle savedStateHandle, String str) {
            this.key = str;
            this.handle = savedStateHandle;
        }
    }

    public SavedStateHandle() {
        this.regular = new LinkedHashMap();
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        this.savedStateProvider = new SavedStateRegistry.SavedStateProvider() { // from class: androidx.lifecycle.SavedStateHandle$$ExternalSyntheticLambda0
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                return SavedStateHandle.$r8$lambda$eeLDsk5Qp_lgSAYrhUViF2PFB0k(SavedStateHandle.this);
            }
        };
    }
}
