package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class OnPlacedNode extends Modifier.Node implements LayoutAwareModifierNode {
    public Function1 callback;

    public OnPlacedNode(Function1 function1) {
        this.callback = function1;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        this.callback.mo781invoke(layoutCoordinates);
    }
}
