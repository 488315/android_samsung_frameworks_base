package androidx.compose.ui.node;

import android.view.View;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DelegatableNode_androidKt {
    public static final View requireView(DelegatableNode delegatableNode) {
        if (!((Modifier.Node) delegatableNode).node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("Cannot get View because the Modifier node is not currently attached.");
        }
        return (View) LayoutNodeKt.requireOwner(DelegatableNodeKt.requireLayoutNode(delegatableNode));
    }
}
