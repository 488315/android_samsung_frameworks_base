package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SetsKt___SetsKt extends SetsKt__SetsKt {
    public static final Set minus(Set set, Iterable iterable) {
        Collection<?> list = iterable instanceof Collection ? (Collection) iterable : CollectionsKt___CollectionsKt.toList(iterable);
        if (list.isEmpty()) {
            return CollectionsKt___CollectionsKt.toSet(set);
        }
        if (!(list instanceof Set)) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(set);
            linkedHashSet.removeAll(list);
            return linkedHashSet;
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (Object obj : set) {
            if (!list.contains(obj)) {
                linkedHashSet2.add(obj);
            }
        }
        return linkedHashSet2;
    }

    public static final Set plus(Set set, Iterable iterable) {
        Integer valueOf = iterable instanceof Collection ? Integer.valueOf(((Collection) iterable).size()) : null;
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(valueOf != null ? set.size() + valueOf.intValue() : set.size() * 2));
        linkedHashSet.addAll(set);
        CollectionsKt__MutableCollectionsKt.addAll(iterable, linkedHashSet);
        return linkedHashSet;
    }
}
