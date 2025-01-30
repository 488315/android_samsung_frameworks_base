package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
    public static final int collectionSizeOrDefault(Iterable iterable, int i) {
        return iterable instanceof Collection ? ((Collection) iterable).size() : i;
    }

    public static final List flatten(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll((Iterable) it.next(), arrayList);
        }
        return arrayList;
    }
}
