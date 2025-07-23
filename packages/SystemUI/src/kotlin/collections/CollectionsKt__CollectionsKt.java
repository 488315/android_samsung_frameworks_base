package kotlin.collections;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
    public static ArrayList arrayListOf(Object... objArr) {
        return objArr.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(objArr, true));
    }

    public static int binarySearch$default(List list, Comparable comparable) {
        int size = ((ArrayList) list).size();
        ArrayList arrayList = (ArrayList) list;
        rangeCheck$CollectionsKt__CollectionsKt(arrayList.size(), size);
        int i = size - 1;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = (i2 + i) >>> 1;
            int compareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) arrayList.get(i3), comparable);
            if (compareValues < 0) {
                i2 = i3 + 1;
            } else {
                if (compareValues <= 0) {
                    return i3;
                }
                i = i3 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static int getLastIndex(List list) {
        return list.size() - 1;
    }

    public static List listOf(Object... objArr) {
        return objArr.length > 0 ? Arrays.asList(objArr) : EmptyList.INSTANCE;
    }

    public static List mutableListOf(Object... objArr) {
        return objArr.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(objArr, true));
    }

    public static final List optimizeReadOnlyList(List list) {
        int size = list.size();
        return size != 0 ? size != 1 ? list : Collections.singletonList(list.get(0)) : EmptyList.INSTANCE;
    }

    public static final void rangeCheck$CollectionsKt__CollectionsKt(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i2, "fromIndex (0) is greater than toIndex (", ")."));
        }
        if (i2 > i) {
            throw new IndexOutOfBoundsException(MutableVectorKt$$ExternalSyntheticOutline0.m(i2, i, "toIndex (", ") is greater than size (", ")."));
        }
    }

    public static void throwCountOverflow() {
        throw new ArithmeticException("Count overflow has happened.");
    }

    public static void throwIndexOverflow() {
        throw new ArithmeticException("Index overflow has happened.");
    }
}
