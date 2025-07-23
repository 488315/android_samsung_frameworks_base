package com.samsung.sesl.compose.component;

import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ScrollAdapter {
    public final CoroutineScope coroutineScope;
    public final int handleMinSize;
    public float rawPosition;
    public final int scrollBarSize;
    public final SeslScrollableState scrollableState;

    public ScrollAdapter(SeslScrollableState seslScrollableState, int i, int i2, CoroutineScope coroutineScope) {
        this.scrollableState = seslScrollableState;
        this.scrollBarSize = i;
        this.handleMinSize = i2;
        this.coroutineScope = coroutineScope;
        this.rawPosition = seslScrollableState.getPositionFraction() * (i - (((int) (seslScrollableState.getHandleSizeFraction() * i)) >= i2 ? r5 : i2));
    }

    public final void setRawPosition(float f) {
        this.rawPosition = f;
        BuildersKt.launch$default(this.coroutineScope, null, null, new ScrollAdapter$position$1(f, this, null), 3);
    }
}
