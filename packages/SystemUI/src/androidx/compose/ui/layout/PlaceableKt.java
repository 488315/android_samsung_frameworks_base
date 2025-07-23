package androidx.compose.ui.layout;

import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlaceableKt {
    public static final Function1 DefaultLayerBlock = new Function1() { // from class: androidx.compose.ui.layout.PlaceableKt$DefaultLayerBlock$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };
    public static final long DefaultConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);

    public static final Placeable.PlacementScope PlacementScope(LookaheadCapablePlaceable lookaheadCapablePlaceable) {
        return new LookaheadCapablePlacementScope(lookaheadCapablePlaceable);
    }

    public static final Placeable.PlacementScope PlacementScope(Owner owner) {
        return new OuterPlacementScope(owner);
    }
}
