package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNode;

/* loaded from: classes.dex */
public interface FocusTargetModifierNode extends DelegatableNode {
    /* renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    static boolean m379requestFocus3ESFkO8$default(FocusTargetModifierNode focusTargetModifierNode) {
        FocusDirection.Companion.getClass();
        return ((FocusTargetNode) focusTargetModifierNode).m380requestFocus3ESFkO8(FocusDirection.Enter);
    }
}
