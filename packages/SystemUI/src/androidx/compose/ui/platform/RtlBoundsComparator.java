package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.Comparator;

/* loaded from: classes.dex */
final class RtlBoundsComparator implements Comparator<SemanticsNode> {
    public static final RtlBoundsComparator INSTANCE = new RtlBoundsComparator();

    private RtlBoundsComparator() {
    }

    @Override // java.util.Comparator
    public final int compare(SemanticsNode semanticsNode, SemanticsNode semanticsNode2) {
        Rect boundsInWindow = semanticsNode.getBoundsInWindow();
        Rect boundsInWindow2 = semanticsNode2.getBoundsInWindow();
        int iCompare = Float.compare(boundsInWindow2.right, boundsInWindow.right);
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = Float.compare(boundsInWindow.top, boundsInWindow2.top);
        if (iCompare2 != 0) {
            return iCompare2;
        }
        int iCompare3 = Float.compare(boundsInWindow.bottom, boundsInWindow2.bottom);
        return iCompare3 != 0 ? iCompare3 : Float.compare(boundsInWindow2.left, boundsInWindow.left);
    }
}
