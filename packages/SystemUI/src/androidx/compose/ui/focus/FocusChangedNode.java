package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusChangedNode extends Modifier.Node implements FocusEventModifierNode {
    public FocusStateImpl focusState;
    public Function1 onFocusChanged;

    public FocusChangedNode(Function1 function1) {
        this.onFocusChanged = function1;
    }

    @Override // androidx.compose.ui.focus.FocusEventModifierNode
    public final void onFocusEvent(FocusStateImpl focusStateImpl) {
        if (Intrinsics.areEqual(this.focusState, focusStateImpl)) {
            return;
        }
        this.focusState = focusStateImpl;
        this.onFocusChanged.mo779invoke(focusStateImpl);
    }
}
