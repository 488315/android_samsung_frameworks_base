package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutBeyondBoundsModifierLocalKt {
    public static final Modifier lazyLayoutBeyondBoundsModifier(Modifier.Companion companion, LazyLayoutBeyondBoundsState lazyLayoutBeyondBoundsState, LazyLayoutBeyondBoundsInfo lazyLayoutBeyondBoundsInfo, boolean z, Orientation orientation) {
        LazyLayoutBeyondBoundsModifierElement lazyLayoutBeyondBoundsModifierElement = new LazyLayoutBeyondBoundsModifierElement(lazyLayoutBeyondBoundsState, lazyLayoutBeyondBoundsInfo, z, orientation);
        companion.getClass();
        return lazyLayoutBeyondBoundsModifierElement;
    }
}
