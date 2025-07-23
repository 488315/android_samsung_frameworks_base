package androidx.compose.runtime.saveable;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.CharsKt__CharJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SaveableStateRegistryImpl implements SaveableStateRegistry {
    public final Function1 canBeSaved;
    public final MutableScatterMap restored;
    public MutableScatterMap valueProviders;

    public SaveableStateRegistryImpl(Map<String, ? extends List<? extends Object>> map, Function1 function1) {
        MutableScatterMap mutableScatterMap;
        this.canBeSaved = function1;
        if (map == null || map.isEmpty()) {
            mutableScatterMap = null;
        } else {
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = SaveableStateRegistryKt.LocalSaveableStateRegistry;
            mutableScatterMap = new MutableScatterMap(map.size());
            for (Map.Entry<String, ? extends List<? extends Object>> entry : map.entrySet()) {
                mutableScatterMap.set(entry.getKey(), entry.getValue());
            }
        }
        this.restored = mutableScatterMap;
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final boolean canBeSaved(Object obj) {
        return ((Boolean) this.canBeSaved.mo779invoke(obj)).booleanValue();
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Object consumeRestored(String str) {
        MutableScatterMap mutableScatterMap = this.restored;
        List list = mutableScatterMap != null ? (List) mutableScatterMap.remove(str) : null;
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return null;
        }
        if (list.size() > 1 && mutableScatterMap != null) {
            List subList = list.subList(1, list.size());
            int findInsertIndex = mutableScatterMap.findInsertIndex(str);
            if (findInsertIndex < 0) {
                findInsertIndex = ~findInsertIndex;
            }
            Object[] objArr = mutableScatterMap.values;
            Object obj = objArr[findInsertIndex];
            mutableScatterMap.keys[findInsertIndex] = str;
            objArr[findInsertIndex] = subList;
        }
        return list.get(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x009c  */
    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map performSave() {
        /*
            Method dump skipped, instructions count: 357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.saveable.SaveableStateRegistryImpl.performSave():java.util.Map");
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final SaveableStateRegistry.Entry registerProvider(String str, Function0 function0) {
        boolean z = true;
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = SaveableStateRegistryKt.LocalSaveableStateRegistry;
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (!CharsKt__CharJVMKt.isWhitespace(str.charAt(i))) {
                z = false;
                break;
            }
            i++;
        }
        if (z) {
            throw new IllegalArgumentException("Registered key is empty or blank");
        }
        MutableScatterMap mutableScatterMap = this.valueProviders;
        if (mutableScatterMap == null) {
            mutableScatterMap = ScatterMapKt.mutableScatterMapOf();
            this.valueProviders = mutableScatterMap;
        }
        Object obj = mutableScatterMap.get(str);
        if (obj == null) {
            obj = new ArrayList();
            mutableScatterMap.set(str, obj);
        }
        ((List) obj).add(function0);
        return new SaveableStateRegistryImpl$registerProvider$3(mutableScatterMap, str, function0);
    }
}
