package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OnPlacedNode extends Modifier.Node implements LayoutAwareModifierNode {
    public Function1 callback;

    public OnPlacedNode(Function1 function1) {
        this.callback = function1;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        this.callback.mo779invoke(layoutCoordinates);
    }
}
