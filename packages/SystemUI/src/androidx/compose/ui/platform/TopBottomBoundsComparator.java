package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.Comparator;
import java.util.List;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TopBottomBoundsComparator implements Comparator<Pair<? extends Rect, ? extends List<SemanticsNode>>> {
    public static final TopBottomBoundsComparator INSTANCE = new TopBottomBoundsComparator();

    private TopBottomBoundsComparator() {
    }

    @Override // java.util.Comparator
    public final int compare(Pair<? extends Rect, ? extends List<SemanticsNode>> pair, Pair<? extends Rect, ? extends List<SemanticsNode>> pair2) {
        Pair<? extends Rect, ? extends List<SemanticsNode>> pair3 = pair;
        Pair<? extends Rect, ? extends List<SemanticsNode>> pair4 = pair2;
        int compare = Float.compare(((Rect) pair3.getFirst()).top, ((Rect) pair4.getFirst()).top);
        return compare != 0 ? compare : Float.compare(((Rect) pair3.getFirst()).bottom, ((Rect) pair4.getFirst()).bottom);
    }
}
