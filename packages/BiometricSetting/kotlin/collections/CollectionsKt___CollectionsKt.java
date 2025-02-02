package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: _Collections.kt */
/* loaded from: classes.dex */
class CollectionsKt___CollectionsKt extends CollectionsKt__ReversedViewsKt {
    public static final void toCollection(Iterable iterable, Collection collection) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
    }
}
