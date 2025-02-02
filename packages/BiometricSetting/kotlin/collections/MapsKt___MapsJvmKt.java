package kotlin.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Pair;

/* compiled from: _MapsJvm.kt */
/* loaded from: classes.dex */
class MapsKt___MapsJvmKt extends MapsKt__MapsJVMKt {
    public static final void toMap(Iterable iterable, Map map) {
        Iterator it = ((ArrayList) iterable).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            map.put(pair.component1(), pair.component2());
        }
    }
}
