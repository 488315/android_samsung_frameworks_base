package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
    public static void sort(List list) {
        if (list.size() > 1) {
            Collections.sort(list);
        }
    }

    public static void sortWith(List list, Comparator comparator) {
        if (list.size() > 1) {
            Collections.sort(list, comparator);
        }
    }
}
