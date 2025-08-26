package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNode;

/* loaded from: classes.dex */
public interface FocusEventModifierNode extends DelegatableNode {
    void onFocusEvent(FocusStateImpl focusStateImpl);
}
