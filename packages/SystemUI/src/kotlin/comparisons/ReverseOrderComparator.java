package kotlin.comparisons;

import java.util.Comparator;

/* loaded from: classes4.dex */
public final class ReverseOrderComparator implements Comparator {
    public static final ReverseOrderComparator INSTANCE = new ReverseOrderComparator();

    private ReverseOrderComparator() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((Comparable) obj2).compareTo((Comparable) obj);
    }

    @Override // java.util.Comparator
    public final Comparator reversed() {
        return NaturalOrderComparator.INSTANCE;
    }
}
