package kotlin.collections;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import kotlin.Pair;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.text.StringsKt__AppendableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CollectionsKt___CollectionsKt extends CollectionsKt___CollectionsJvmKt {
    public static final boolean contains(Iterable iterable, Object obj) {
        int i;
        if (iterable instanceof Collection) {
            return ((Collection) iterable).contains(obj);
        }
        if (!(iterable instanceof List)) {
            Iterator it = iterable.iterator();
            int i2 = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                Object next = it.next();
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                if (Intrinsics.areEqual(obj, next)) {
                    i = i2;
                    break;
                }
                i2++;
            }
        } else {
            i = ((List) iterable).indexOf(obj);
        }
        return i >= 0;
    }

    public static final List distinct(Iterable iterable) {
        return toList(toMutableSet(iterable));
    }

    public static final List drop(Iterable iterable, int i) {
        ArrayList arrayList;
        Object obj;
        int i2 = 0;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested element count ", i, " is less than zero.").toString());
        }
        if (i == 0) {
            return toList(iterable);
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            int size = collection.size() - i;
            if (size <= 0) {
                return EmptyList.INSTANCE;
            }
            if (size == 1) {
                if (iterable instanceof List) {
                    obj = last((List) iterable);
                } else {
                    Iterator it = iterable.iterator();
                    if (!it.hasNext()) {
                        throw new NoSuchElementException("Collection is empty.");
                    }
                    Object next = it.next();
                    while (it.hasNext()) {
                        next = it.next();
                    }
                    obj = next;
                }
                return Collections.singletonList(obj);
            }
            arrayList = new ArrayList(size);
            if (iterable instanceof List) {
                if (iterable instanceof RandomAccess) {
                    int size2 = collection.size();
                    while (i < size2) {
                        arrayList.add(((List) iterable).get(i));
                        i++;
                    }
                } else {
                    ListIterator listIterator = ((List) iterable).listIterator(i);
                    while (listIterator.hasNext()) {
                        arrayList.add(listIterator.next());
                    }
                }
                return arrayList;
            }
        } else {
            arrayList = new ArrayList();
        }
        for (Object obj2 : iterable) {
            if (i2 >= i) {
                arrayList.add(obj2);
            } else {
                i2++;
            }
        }
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(arrayList);
    }

    public static final Object first(Iterable iterable) {
        if (iterable instanceof List) {
            return first((List) iterable);
        }
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        throw new NoSuchElementException("Collection is empty.");
    }

    public static final Object firstOrNull(Iterable iterable) {
        if (iterable instanceof List) {
            List list = (List) iterable;
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static final Object getOrNull(int i, List list) {
        if (i < 0 || i > CollectionsKt__CollectionsKt.getLastIndex(list)) {
            return null;
        }
        return list.get(i);
    }

    public static final void joinTo(Iterable iterable, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, Function1 function1) {
        StringBuilder sb = (StringBuilder) appendable;
        sb.append(charSequence2);
        int i2 = 0;
        for (Object obj : iterable) {
            i2++;
            if (i2 > 1) {
                sb.append(charSequence);
            }
            if (i >= 0 && i2 > i) {
                break;
            } else {
                StringsKt__AppendableKt.appendElement(appendable, obj, function1);
            }
        }
        if (i >= 0 && i2 > i) {
            sb.append(charSequence4);
        }
        sb.append(charSequence3);
    }

    public static String joinToString$default(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Function1 function1, int i) {
        if ((i & 1) != 0) {
            charSequence = ", ";
        }
        CharSequence charSequence4 = charSequence;
        CharSequence charSequence5 = (i & 2) != 0 ? "" : charSequence2;
        CharSequence charSequence6 = (i & 4) != 0 ? "" : charSequence3;
        int i2 = (i & 8) != 0 ? -1 : 0;
        CharSequence charSequence7 = (i & 16) != 0 ? "..." : null;
        Function1 function12 = (i & 32) != 0 ? null : function1;
        StringBuilder sb = new StringBuilder();
        joinTo(iterable, sb, charSequence4, charSequence5, charSequence6, i2, charSequence7, function12);
        return sb.toString();
    }

    public static final Object last(List list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return list.get(CollectionsKt__CollectionsKt.getLastIndex(list));
    }

    public static final Float maxOrNull(Iterable iterable) {
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return null;
        }
        float floatValue = ((Number) it.next()).floatValue();
        while (it.hasNext()) {
            floatValue = Math.max(floatValue, ((Number) it.next()).floatValue());
        }
        return Float.valueOf(floatValue);
    }

    public static final Comparable minOrNull(Iterable iterable) {
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return null;
        }
        Comparable comparable = (Comparable) it.next();
        while (it.hasNext()) {
            Comparable comparable2 = (Comparable) it.next();
            if (comparable.compareTo(comparable2) > 0) {
                comparable = comparable2;
            }
        }
        return comparable;
    }

    public static final List minus(Iterable iterable, Iterable iterable2) {
        Collection list = iterable2 instanceof Collection ? (Collection) iterable2 : toList(iterable2);
        if (list.isEmpty()) {
            return toList(iterable);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (!list.contains(obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final List plus(Collection collection, Object obj) {
        ArrayList arrayList = new ArrayList(collection.size() + 1);
        arrayList.addAll(collection);
        arrayList.add(obj);
        return arrayList;
    }

    public static final List sorted(Iterable iterable) {
        if (!(iterable instanceof Collection)) {
            List mutableList = toMutableList(iterable);
            if (((ArrayList) mutableList).size() > 1) {
                Collections.sort(mutableList);
            }
            return mutableList;
        }
        Collection collection = (Collection) iterable;
        if (collection.size() <= 1) {
            return toList(iterable);
        }
        Object[] array = collection.toArray(new Comparable[0]);
        Comparable[] comparableArr = (Comparable[]) array;
        if (comparableArr.length > 1) {
            Arrays.sort(comparableArr);
        }
        return Arrays.asList(array);
    }

    public static final List sortedWith(Iterable iterable, Comparator comparator) {
        if (!(iterable instanceof Collection)) {
            List mutableList = toMutableList(iterable);
            CollectionsKt__MutableCollectionsJVMKt.sortWith(mutableList, comparator);
            return mutableList;
        }
        Collection collection = (Collection) iterable;
        if (collection.size() <= 1) {
            return toList(iterable);
        }
        Object[] array = collection.toArray(new Object[0]);
        if (array.length > 1) {
            Arrays.sort(array, comparator);
        }
        return Arrays.asList(array);
    }

    public static final Set subtract(Iterable iterable, Iterable iterable2) {
        Set mutableSet = toMutableSet(iterable);
        mutableSet.removeAll(iterable2 instanceof Collection ? (Collection) iterable2 : toList(iterable2));
        return mutableSet;
    }

    public static final List take(Iterable iterable, int i) {
        int i2 = 0;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested element count ", i, " is less than zero.").toString());
        }
        if (i == 0) {
            return EmptyList.INSTANCE;
        }
        if (iterable instanceof Collection) {
            if (i >= ((Collection) iterable).size()) {
                return toList(iterable);
            }
            if (i == 1) {
                return Collections.singletonList(first(iterable));
            }
        }
        ArrayList arrayList = new ArrayList(i);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
            i2++;
            if (i2 == i) {
                break;
            }
        }
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(arrayList);
    }

    public static final List takeLast(int i, List list) {
        if (!(i >= 0)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested element count ", i, " is less than zero.").toString());
        }
        if (i == 0) {
            return EmptyList.INSTANCE;
        }
        int size = list.size();
        if (i >= size) {
            return toList(list);
        }
        if (i == 1) {
            return Collections.singletonList(last(list));
        }
        ArrayList arrayList = new ArrayList(i);
        if (list instanceof RandomAccess) {
            for (int i2 = size - i; i2 < size; i2++) {
                arrayList.add(list.get(i2));
            }
        } else {
            ListIterator listIterator = list.listIterator(size - i);
            while (listIterator.hasNext()) {
                arrayList.add(listIterator.next());
            }
        }
        return arrayList;
    }

    public static final void toCollection(Iterable iterable, Collection collection) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
    }

    public static final List toList(Iterable iterable) {
        if (!(iterable instanceof Collection)) {
            return CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(iterable));
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return EmptyList.INSTANCE;
        }
        if (size != 1) {
            return new ArrayList(collection);
        }
        return Collections.singletonList(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
    }

    public static final List toMutableList(Iterable iterable) {
        if (iterable instanceof Collection) {
            return new ArrayList((Collection) iterable);
        }
        ArrayList arrayList = new ArrayList();
        toCollection(iterable, arrayList);
        return arrayList;
    }

    public static final Set toMutableSet(Iterable iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet((Collection) iterable);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        toCollection(iterable, linkedHashSet);
        return linkedHashSet;
    }

    public static final Set toSet(Iterable iterable) {
        if (!(iterable instanceof Collection)) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            toCollection(iterable, linkedHashSet);
            int size = linkedHashSet.size();
            return size != 0 ? size != 1 ? linkedHashSet : Collections.singleton(linkedHashSet.iterator().next()) : EmptySet.INSTANCE;
        }
        Collection collection = (Collection) iterable;
        int size2 = collection.size();
        if (size2 == 0) {
            return EmptySet.INSTANCE;
        }
        if (size2 == 1) {
            return Collections.singleton(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(collection.size()));
        toCollection(iterable, linkedHashSet2);
        return linkedHashSet2;
    }

    public static List windowed$default(Iterable iterable) {
        Iterator it;
        if (!(iterable instanceof RandomAccess) || !(iterable instanceof List)) {
            ArrayList arrayList = new ArrayList();
            Iterator it2 = iterable.iterator();
            if (it2.hasNext()) {
                SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(4, 1, it2, false, false, null);
                SequenceBuilderIterator sequenceBuilderIterator = new SequenceBuilderIterator();
                sequenceBuilderIterator.nextStep = IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(sequenceBuilderIterator, slidingWindowKt$windowedIterator$1, sequenceBuilderIterator);
                it = sequenceBuilderIterator;
            } else {
                it = EmptyIterator.INSTANCE;
            }
            while (it.hasNext()) {
                arrayList.add((List) it.next());
            }
            return arrayList;
        }
        List list = (List) iterable;
        int size = list.size();
        ArrayList arrayList2 = new ArrayList((size / 1) + (size % 1 == 0 ? 0 : 1));
        int i = 0;
        while (true) {
            if (!(i >= 0 && i < size)) {
                return arrayList2;
            }
            int i2 = size - i;
            if (4 <= i2) {
                i2 = 4;
            }
            if (i2 < 4) {
                return arrayList2;
            }
            ArrayList arrayList3 = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList3.add(list.get(i3 + i));
            }
            arrayList2.add(arrayList3);
            i++;
        }
    }

    public static final List zip(Iterable iterable, Iterable iterable2) {
        Iterator it = iterable.iterator();
        Iterator it2 = ((ArrayList) iterable2).iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(new Pair(it.next(), it2.next()));
        }
        return arrayList;
    }

    public static final List plus(Iterable iterable, Collection collection) {
        if (iterable instanceof Collection) {
            Collection collection2 = (Collection) iterable;
            ArrayList arrayList = new ArrayList(collection2.size() + collection.size());
            arrayList.addAll(collection);
            arrayList.addAll(collection2);
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(collection);
        CollectionsKt__MutableCollectionsKt.addAll(iterable, arrayList2);
        return arrayList2;
    }

    public static final Object first(List list) {
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static final Object firstOrNull(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static final List minus(Iterable iterable, Object obj) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        boolean z = false;
        for (Object obj2 : iterable) {
            boolean z2 = true;
            if (!z && Intrinsics.areEqual(obj2, obj)) {
                z = true;
                z2 = false;
            }
            if (z2) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
