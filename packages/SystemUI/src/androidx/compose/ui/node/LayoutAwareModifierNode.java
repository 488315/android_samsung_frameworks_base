package androidx.compose.ui.node;

import androidx.compose.ui.layout.LayoutCoordinates;

/* loaded from: classes.dex */
public interface LayoutAwareModifierNode extends DelegatableNode {
    default void onPlaced(LayoutCoordinates layoutCoordinates) {
    }

    /* renamed from: onRemeasured-ozmzZPI */
    default void mo50onRemeasuredozmzZPI(long j) {
    }
}
