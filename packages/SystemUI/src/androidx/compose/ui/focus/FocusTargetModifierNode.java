package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface FocusTargetModifierNode extends DelegatableNode {
    /* renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    static boolean m377requestFocus3ESFkO8$default(FocusTargetModifierNode focusTargetModifierNode) {
        FocusDirection.Companion.getClass();
        return ((FocusTargetNode) focusTargetModifierNode).m378requestFocus3ESFkO8(FocusDirection.Enter);
    }
}
