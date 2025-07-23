package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LtrBoundsComparator implements Comparator<SemanticsNode> {
    public static final LtrBoundsComparator INSTANCE = new LtrBoundsComparator();

    private LtrBoundsComparator() {
    }

    @Override // java.util.Comparator
    public final int compare(SemanticsNode semanticsNode, SemanticsNode semanticsNode2) {
        Rect boundsInWindow = semanticsNode.getBoundsInWindow();
        Rect boundsInWindow2 = semanticsNode2.getBoundsInWindow();
        int compare = Float.compare(boundsInWindow.left, boundsInWindow2.left);
        if (compare != 0) {
            return compare;
        }
        int compare2 = Float.compare(boundsInWindow.top, boundsInWindow2.top);
        if (compare2 != 0) {
            return compare2;
        }
        int compare3 = Float.compare(boundsInWindow.bottom, boundsInWindow2.bottom);
        return compare3 != 0 ? compare3 : Float.compare(boundsInWindow.right, boundsInWindow2.right);
    }
}
