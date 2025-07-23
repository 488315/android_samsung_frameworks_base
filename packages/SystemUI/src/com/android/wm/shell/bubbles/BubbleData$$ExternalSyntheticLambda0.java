package com.android.wm.shell.bubbles;

import java.util.Comparator;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Bubble bubble = (Bubble) obj;
        Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
        return Long.valueOf(Math.max(bubble.mLastUpdated, bubble.mLastAccessed));
    }
}
