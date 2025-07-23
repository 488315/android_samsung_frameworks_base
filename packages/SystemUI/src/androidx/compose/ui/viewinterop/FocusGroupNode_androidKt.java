package androidx.compose.ui.viewinterop;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.FocusTraversalKt;
import androidx.compose.ui.node.DelegatableNodeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FocusGroupNode_androidKt {
    public static final boolean access$containsDescendant(View view, View view2) {
        for (ViewParent parent = view2.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == view.getParent()) {
                return true;
            }
        }
        return false;
    }

    public static final Rect access$getCurrentlyFocusedRect(FocusOwnerImpl focusOwnerImpl, View view, View view2) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        view2.getLocationOnScreen(iArr2);
        FocusTargetNode findActiveFocusNode = FocusTraversalKt.findActiveFocusNode(focusOwnerImpl.rootFocusNode);
        androidx.compose.ui.geometry.Rect focusRect = findActiveFocusNode != null ? FocusTraversalKt.focusRect(findActiveFocusNode) : null;
        if (focusRect == null) {
            return null;
        }
        int i = (int) focusRect.left;
        int i2 = iArr[0];
        int i3 = iArr2[0];
        int i4 = (int) focusRect.top;
        int i5 = iArr[1];
        int i6 = iArr2[1];
        return new Rect((i + i2) - i3, (i4 + i5) - i6, (((int) focusRect.right) + i2) - i3, (((int) focusRect.bottom) + i5) - i6);
    }

    public static final View access$getEmbeddedView(Modifier.Node node) {
        AndroidViewHolder androidViewHolder = DelegatableNodeKt.requireLayoutNode(node.node).interopViewFactoryHolder;
        View view = androidViewHolder != null ? androidViewHolder.view : null;
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("Could not fetch interop view");
    }
}
