package androidx.compose.ui.node;

/* loaded from: classes.dex */
public interface DrawModifierNode extends DelegatableNode {
    void draw(LayoutNodeDrawScope layoutNodeDrawScope);

    default void onMeasureResultChanged() {
    }
}
