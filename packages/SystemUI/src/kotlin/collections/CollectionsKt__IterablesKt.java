package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
    public static int collectionSizeOrDefault(Iterable iterable, int i) {
        return iterable instanceof Collection ? ((Collection) iterable).size() : i;
    }

    public static List flatten(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll((Iterable) it.next(), arrayList);
        }
        return arrayList;
    }
}
