package androidx.compose.ui.layout;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.LayoutDirection;

/* loaded from: classes.dex */
final class OuterPlacementScope extends Placeable.PlacementScope {
    public final Owner owner;

    public OuterPlacementScope(Owner owner) {
        this.owner = owner;
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final LayoutCoordinates getCoordinates() {
        return ((AndroidComposeView) this.owner).root.nodes.outerCoordinator;
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final LayoutDirection getParentLayoutDirection() {
        return (LayoutDirection) ((SnapshotMutableStateImpl) ((AndroidComposeView) this.owner).layoutDirection$delegate).getValue();
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final int getParentWidth() {
        return ((AndroidComposeView) this.owner).root.layoutDelegate.measurePassDelegate.width;
    }
}
