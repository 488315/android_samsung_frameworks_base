package androidx.compose.ui.node;

import java.util.Comparator;
import java.util.TreeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SortedSet<E> extends TreeSet<E> {
    public SortedSet(Comparator<? super E> comparator) {
        super(comparator);
    }
}
