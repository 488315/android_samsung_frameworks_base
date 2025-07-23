package androidx.compose.runtime;

import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.runtime.internal.PersistentCompositionLocalMapKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CompositionLocalMapKt {
    public static final Object read(PersistentCompositionLocalMap persistentCompositionLocalMap, CompositionLocal compositionLocal) {
        Object obj = persistentCompositionLocalMap.get(compositionLocal);
        if (obj == null) {
            obj = compositionLocal.getDefaultValueHolder$runtime_release();
        }
        return ((ValueHolder) obj).readValue(persistentCompositionLocalMap);
    }

    public static final PersistentCompositionLocalHashMap updateCompositionMap(ProvidedValue[] providedValueArr, PersistentCompositionLocalMap persistentCompositionLocalMap, PersistentCompositionLocalMap persistentCompositionLocalMap2) {
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMapOf = PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf();
        persistentCompositionLocalHashMapOf.getClass();
        PersistentCompositionLocalHashMap.Builder builder = new PersistentCompositionLocalHashMap.Builder(persistentCompositionLocalHashMapOf);
        for (ProvidedValue providedValue : providedValueArr) {
            ProvidableCompositionLocal providableCompositionLocal = (ProvidableCompositionLocal) providedValue.compositionLocal;
            if (providedValue.canOverride || !persistentCompositionLocalMap.containsKey(providableCompositionLocal)) {
                builder.put(providableCompositionLocal, providableCompositionLocal.updatedStateOf$runtime_release(providedValue, (ValueHolder) persistentCompositionLocalMap2.get(providableCompositionLocal)));
            }
        }
        return builder.build();
    }
}
