package androidx.compose.ui.layout;

import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LookaheadCapablePlacementScope extends Placeable.PlacementScope {
    public final LookaheadCapablePlaceable within;

    public LookaheadCapablePlacementScope(LookaheadCapablePlaceable lookaheadCapablePlaceable) {
        this.within = lookaheadCapablePlaceable;
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final LayoutCoordinates getCoordinates() {
        LookaheadCapablePlaceable lookaheadCapablePlaceable = this.within;
        LayoutCoordinates coordinates = lookaheadCapablePlaceable.isPlacingForAlignment ? null : lookaheadCapablePlaceable.getCoordinates();
        if (coordinates == null) {
            lookaheadCapablePlaceable.getLayoutNode().layoutDelegate.onCoordinatesUsed();
        }
        return coordinates;
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final LayoutDirection getParentLayoutDirection() {
        return this.within.getLayoutDirection();
    }

    @Override // androidx.compose.ui.layout.Placeable.PlacementScope
    public final int getParentWidth() {
        return this.within.getMeasuredWidth();
    }
}
