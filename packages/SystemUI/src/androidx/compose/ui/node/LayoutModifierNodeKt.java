package androidx.compose.ui.node;

/* loaded from: classes.dex */
public abstract class LayoutModifierNodeKt {
    public static final void invalidateMeasurement(LayoutModifierNode layoutModifierNode) {
        DelegatableNodeKt.requireLayoutNode(layoutModifierNode).invalidateMeasurements$ui_release();
    }
}
