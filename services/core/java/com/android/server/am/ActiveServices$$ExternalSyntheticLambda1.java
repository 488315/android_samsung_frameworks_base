package com.android.server.am;

import java.util.Comparator;

public final /* synthetic */ class ActiveServices$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return (int)
                (((ServiceRecord) obj).nextRestartTime - ((ServiceRecord) obj2).nextRestartTime);
    }
}
