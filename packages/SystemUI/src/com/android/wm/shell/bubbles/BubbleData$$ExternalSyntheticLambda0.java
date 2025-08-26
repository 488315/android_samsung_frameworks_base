package com.android.wm.shell.bubbles;

import java.util.Comparator;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Bubble bubble = (Bubble) obj;
        Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
        return Long.valueOf(Math.max(bubble.mLastUpdated, bubble.mLastAccessed));
    }
}
