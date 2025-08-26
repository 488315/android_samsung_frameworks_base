package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class DrawModifierNodeKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final void invalidateDraw(DrawModifierNode drawModifierNode) {
        if (((Modifier.Node) drawModifierNode).node.isAttached) {
            DelegatableNodeKt.m634requireCoordinator64DMado(drawModifierNode, 1).invalidateLayer();
        }
    }
}
