package com.android.server.wm;

import java.util.Comparator;

public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((ActivityRecord) obj).compareTo((WindowContainer) obj2);
    }
}
