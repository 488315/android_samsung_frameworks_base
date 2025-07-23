package com.samsung.sesl.compose.foundation.scroll;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.ScrollableState;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SeslScrollableState implements ScrollableState {
    public final ScrollableState base;

    public SeslScrollableState(ScrollableState scrollableState) {
        this.base = scrollableState;
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final float dispatchRawDelta(float f) {
        return this.base.dispatchRawDelta(f);
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollBackward() {
        return this.base.getCanScrollBackward();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollForward() {
        return this.base.getCanScrollForward();
    }

    public abstract float getHandleSizeFraction();

    public abstract float getPositionFraction();

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return this.base.isScrollInProgress();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        return this.base.scroll(mutatePriority, function2, continuation);
    }

    public abstract Object scrollTo(float f, Continuation continuation);
}
