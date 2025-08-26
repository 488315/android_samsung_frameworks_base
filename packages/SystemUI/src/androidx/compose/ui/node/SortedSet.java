package androidx.compose.ui.node;

import java.util.Comparator;
import java.util.TreeSet;

/* loaded from: classes.dex */
public final class SortedSet<E> extends TreeSet<E> {
    public SortedSet(Comparator<? super E> comparator) {
        super(comparator);
    }
}
