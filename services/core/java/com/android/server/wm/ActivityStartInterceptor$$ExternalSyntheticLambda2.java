package com.android.server.wm;

import java.util.Comparator;

public final /* synthetic */ class ActivityStartInterceptor$$ExternalSyntheticLambda2
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(((Task) obj2).lastGainFocusTime, ((Task) obj).lastGainFocusTime);
    }
}
