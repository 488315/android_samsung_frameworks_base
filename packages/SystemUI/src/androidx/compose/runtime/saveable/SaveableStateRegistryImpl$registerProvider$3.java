package androidx.compose.runtime.saveable;

import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SaveableStateRegistryImpl$registerProvider$3 implements SaveableStateRegistry.Entry {
    public final /* synthetic */ String $key;
    public final /* synthetic */ Function0 $valueProvider;
    public final /* synthetic */ MutableScatterMap $valueProviders;

    public SaveableStateRegistryImpl$registerProvider$3(MutableScatterMap mutableScatterMap, String str, Function0 function0) {
        this.$valueProviders = mutableScatterMap;
        this.$key = str;
        this.$valueProvider = function0;
    }

    public final void unregister() {
        MutableScatterMap mutableScatterMap = this.$valueProviders;
        String str = this.$key;
        List list = (List) mutableScatterMap.remove(str);
        if (list != null) {
            list.remove(this.$valueProvider);
        }
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        mutableScatterMap.set(str, list);
    }
}
