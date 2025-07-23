package androidx.compose.foundation;

import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScrollingContainerKt {
    public static final Modifier scrollingContainer(Modifier modifier, ScrollableState scrollableState, Orientation orientation, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, boolean z3, OverscrollEffect overscrollEffect, BringIntoViewSpec bringIntoViewSpec) {
        return ClipScrollableContainerKt.clipScrollableContainer(modifier, orientation).then(new ScrollingContainerElement(scrollableState, orientation, z, z2, flingBehavior, mutableInteractionSource, bringIntoViewSpec, z3, overscrollEffect));
    }
}
