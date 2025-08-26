package com.android.wm.shell.bubbles;

import java.util.function.ToLongFunction;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda6 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        Bubble bubble = (Bubble) obj;
        return Math.max(bubble.mLastUpdated, bubble.mLastAccessed);
    }
}
