package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
    public static final void addAll(Iterable iterable, Collection collection) {
        if (iterable instanceof Collection) {
            collection.addAll((Collection) iterable);
            return;
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
    }

    public static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable iterable, Function1 function1, boolean z) {
        Iterator it = iterable.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (((Boolean) function1.invoke(it.next())).booleanValue() == z) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    public static final boolean addAll(Collection collection, Sequence sequence) {
        Iterator it = sequence.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (collection.add(it.next())) {
                z = true;
            }
        }
        return z;
    }

    public static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List list, Function1 function1, boolean z) {
        if (!(list instanceof RandomAccess)) {
            if ((list instanceof KMappedMarker) && !(list instanceof KMutableCollection)) {
                TypeIntrinsics.throwCce(list, "kotlin.collections.MutableIterable");
                throw null;
            }
            return filterInPlace$CollectionsKt__MutableCollectionsKt((Iterable) list, function1, z);
        }
        IntProgressionIterator it = new IntRange(0, CollectionsKt__CollectionsKt.getLastIndex(list)).iterator();
        int i = 0;
        while (it.hasNext) {
            int nextInt = it.nextInt();
            ArrayList arrayList = (ArrayList) list;
            Object obj = arrayList.get(nextInt);
            if (((Boolean) function1.invoke(obj)).booleanValue() != z) {
                if (i != nextInt) {
                    arrayList.set(i, obj);
                }
                i++;
            }
        }
        ArrayList arrayList2 = (ArrayList) list;
        if (i >= arrayList2.size()) {
            return false;
        }
        int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(list);
        if (i > lastIndex) {
            return true;
        }
        while (true) {
            arrayList2.remove(lastIndex);
            if (lastIndex == i) {
                return true;
            }
            lastIndex--;
        }
    }
}
