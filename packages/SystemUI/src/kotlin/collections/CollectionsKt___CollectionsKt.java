package kotlin.collections;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.text.StringsKt__AppendableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CollectionsKt___CollectionsKt extends CollectionsKt___CollectionsJvmKt {
    public static double averageOfDouble(Iterable iterable) {
        ArrayList arrayList = (ArrayList) iterable;
        int size = arrayList.size();
        int i = 0;
        double d = 0.0d;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            d += ((Number) obj).doubleValue();
            i++;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        if (i == 0) {
            return Double.NaN;
        }
        return d / i;
    }

    public static double averageOfFloat(Iterable iterable) {
        Iterator it = iterable.iterator();
        double d = 0.0d;
        int i = 0;
        while (it.hasNext()) {
            d += ((Number) it.next()).floatValue();
            i++;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        if (i == 0) {
            return Double.NaN;
        }
        return d / i;
    }

    public static List chunked(Iterable iterable, int i) {
        if (i <= 0 || i <= 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "size ", " must be greater than zero.").toString());
        }
        if (!(iterable instanceof RandomAccess) || !(iterable instanceof List)) {
            ArrayList arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            Iterator it2 = !it.hasNext() ? EmptyIterator.INSTANCE : SequencesKt__SequenceBuilderKt.iterator(new SlidingWindowKt$windowedIterator$1(i, i, it, false, true, null));
            while (it2.hasNext()) {
                arrayList.add((List) it2.next());
            }
            return arrayList;
        }
        List list = (List) iterable;
        int size = list.size();
        ArrayList arrayList2 = new ArrayList((size / i) + (size % i == 0 ? 0 : 1));
        int i2 = 0;
        while (i2 >= 0 && i2 < size) {
            int i3 = size - i2;
            if (i <= i3) {
                i3 = i;
            }
            ArrayList arrayList3 = new ArrayList(i3);
            for (int i4 = 0; i4 < i3; i4++) {
                arrayList3.add(list.get(i4 + i2));
            }
            arrayList2.add(arrayList3);
            i2 += i;
        }
        return arrayList2;
    }

    public static boolean contains(Iterable iterable, Object obj) {
        return iterable instanceof Collection ? ((Collection) iterable).contains(obj) : indexOf(iterable, obj) >= 0;
    }

    public static List distinct(Iterable iterable) {
        return toList(toMutableSet(iterable));
    }

    public static List drop(Iterable iterable, int i) {
        ArrayList arrayList;
        Object obj;
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " is less than zero.").toString());
        }
        if (i == 0) {
            return toList(iterable);
        }
        if (iterable instanceof Collection) {
            int size = ((Collection) iterable).size() - i;
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
                    List list = (List) iterable;
                    int size2 = list.size();
                    while (i < size2) {
                        arrayList.add(list.get(i));
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
        int i2 = 0;
        for (Object obj2 : iterable) {
            if (i2 >= i) {
                arrayList.add(obj2);
            } else {
                i2++;
            }
        }
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(arrayList);
    }

    public static Object elementAtOrNull(Iterable iterable, int i) {
        if (iterable instanceof List) {
            return getOrNull(i, (List) iterable);
        }
        if (i < 0) {
            return null;
        }
        int i2 = 0;
        for (Object obj : iterable) {
            int i3 = i2 + 1;
            if (i == i2) {
                return obj;
            }
            i2 = i3;
        }
        return null;
    }

    public static List filterNotNull(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static Object first(Iterable iterable) {
        if (iterable instanceof List) {
            return first((List) iterable);
        }
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        throw new NoSuchElementException("Collection is empty.");
    }

    public static Object firstOrNull(Iterable iterable) {
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

    public static Object getOrNull(int i, List list) {
        if (i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }

    public static int indexOf(Iterable iterable, Object obj) {
        if (iterable instanceof List) {
            return ((List) iterable).indexOf(obj);
        }
        int i = 0;
        for (Object obj2 : iterable) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            if (Intrinsics.areEqual(obj, obj2)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static Set intersect(Iterable iterable, Iterable iterable2) {
        Set mutableSet = toMutableSet(iterable);
        mutableSet.retainAll(CollectionsKt__MutableCollectionsKt.convertToListIfNotCollection(iterable2));
        return mutableSet;
    }

    public static final void joinTo(Iterable iterable, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Function1 function1) {
        appendable.append(charSequence2);
        int i = 0;
        for (Object obj : iterable) {
            i++;
            if (i > 1) {
                appendable.append(charSequence);
            }
            StringsKt__AppendableKt.appendElement(appendable, obj, function1);
        }
        appendable.append(charSequence3);
    }

    public static /* synthetic */ void joinTo$default(Iterable iterable, Appendable appendable, CharSequence charSequence, Function1 function1, int i) {
        if ((i & 64) != 0) {
            function1 = null;
        }
        joinTo(iterable, appendable, charSequence, "", "", function1);
    }

    public static String joinToString$default(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Function1 function1, int i) {
        if ((i & 1) != 0) {
            charSequence = ", ";
        }
        CharSequence charSequence4 = charSequence;
        CharSequence charSequence5 = (i & 2) != 0 ? "" : charSequence2;
        CharSequence charSequence6 = (i & 4) != 0 ? "" : charSequence3;
        if ((i & 32) != 0) {
            function1 = null;
        }
        StringBuilder sb = new StringBuilder();
        joinTo(iterable, sb, charSequence4, charSequence5, charSequence6, function1);
        return sb.toString();
    }

    public static Object last(List list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
    }

    public static Object lastOrNull(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
    }

    /* renamed from: maxOrNull, reason: collision with other method in class */
    public static Float m3430maxOrNull(Iterable iterable) {
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

    public static Comparable minOrNull(Iterable iterable) {
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

    public static List minus(Iterable iterable, Object obj) {
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

    public static List plus(Collection collection, Object obj) {
        ArrayList arrayList = new ArrayList(collection.size() + 1);
        arrayList.addAll(collection);
        arrayList.add(obj);
        return arrayList;
    }

    public static List reversed(Iterable iterable) {
        if ((iterable instanceof Collection) && ((Collection) iterable).size() <= 1) {
            return toList(iterable);
        }
        List mutableList = toMutableList(iterable);
        Collections.reverse(mutableList);
        return mutableList;
    }

    public static Object single(Iterable iterable) {
        if (iterable instanceof List) {
            return single((List) iterable);
        }
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("Collection is empty.");
        }
        Object next = it.next();
        if (it.hasNext()) {
            throw new IllegalArgumentException("Collection has more than one element.");
        }
        return next;
    }

    public static List sorted(Iterable iterable) {
        if (!(iterable instanceof Collection)) {
            List mutableList = toMutableList(iterable);
            CollectionsKt__MutableCollectionsJVMKt.sort(mutableList);
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

    public static List sortedWith(Iterable iterable, Comparator comparator) {
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

    public static Set subtract(Iterable iterable, Iterable iterable2) {
        Set mutableSet = toMutableSet(iterable);
        mutableSet.removeAll(CollectionsKt__MutableCollectionsKt.convertToListIfNotCollection(iterable2));
        return mutableSet;
    }

    public static int sumOfInt(Iterable iterable) {
        Iterator it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((Number) it.next()).intValue();
        }
        return i;
    }

    public static List take(Iterable iterable, int i) {
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " is less than zero.").toString());
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
        int i2 = 0;
        while (it.hasNext()) {
            arrayList.add(it.next());
            i2++;
            if (i2 == i) {
                break;
            }
        }
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(arrayList);
    }

    public static List takeLast(int i, List list) {
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " is less than zero.").toString());
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

    public static boolean[] toBooleanArray(Collection collection) {
        boolean[] zArr = new boolean[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            zArr[i] = ((Boolean) it.next()).booleanValue();
            i++;
        }
        return zArr;
    }

    public static void toCollection(Iterable iterable, Collection collection) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
    }

    public static float[] toFloatArray(Collection collection) {
        float[] fArr = new float[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            fArr[i] = ((Number) it.next()).floatValue();
            i++;
        }
        return fArr;
    }

    public static HashSet toHashSet(Iterable iterable) {
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 12)));
        toCollection(iterable, hashSet);
        return hashSet;
    }

    public static int[] toIntArray(Collection collection) {
        int[] iArr = new int[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Number) it.next()).intValue();
            i++;
        }
        return iArr;
    }

    public static List toList(Iterable iterable) {
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
        return Collections.singletonList(iterable instanceof List ? ((List) iterable).get(0) : collection.iterator().next());
    }

    public static List toMutableList(Iterable iterable) {
        if (iterable instanceof Collection) {
            return new ArrayList((Collection) iterable);
        }
        ArrayList arrayList = new ArrayList();
        toCollection(iterable, arrayList);
        return arrayList;
    }

    public static Set toMutableSet(Iterable iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet((Collection) iterable);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        toCollection(iterable, linkedHashSet);
        return linkedHashSet;
    }

    public static Set toSet(Iterable iterable) {
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
            return Collections.singleton(iterable instanceof List ? ((List) iterable).get(0) : collection.iterator().next());
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(collection.size()));
        toCollection(iterable, linkedHashSet2);
        return linkedHashSet2;
    }

    public static List zip(Iterable iterable, Iterable iterable2) {
        Iterator it = iterable.iterator();
        Iterator it2 = iterable2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(new Pair(it.next(), it2.next()));
        }
        return arrayList;
    }

    public static List plus(Iterable iterable, Collection collection) {
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

    public static List minus(Iterable iterable, Iterable iterable2) {
        Collection convertToListIfNotCollection = CollectionsKt__MutableCollectionsKt.convertToListIfNotCollection(iterable2);
        if (convertToListIfNotCollection.isEmpty()) {
            return toList(iterable);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (!convertToListIfNotCollection.contains(obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static Object first(List list) {
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static Object firstOrNull(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static Comparable maxOrNull(Iterable iterable) {
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return null;
        }
        Comparable comparable = (Comparable) it.next();
        while (it.hasNext()) {
            Comparable comparable2 = (Comparable) it.next();
            if (comparable.compareTo(comparable2) < 0) {
                comparable = comparable2;
            }
        }
        return comparable;
    }

    public static Object single(List list) {
        int size = list.size();
        if (size == 0) {
            throw new NoSuchElementException("List is empty.");
        }
        if (size == 1) {
            return list.get(0);
        }
        throw new IllegalArgumentException("List has more than one element.");
    }
}
