package com.android.wm.shell.bubbles;

import java.util.function.ToLongFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda6 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        Bubble bubble = (Bubble) obj;
        return Math.max(bubble.mLastUpdated, bubble.mLastAccessed);
    }
}
