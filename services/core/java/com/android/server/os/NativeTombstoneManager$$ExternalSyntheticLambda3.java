package com.android.server.os;

import android.app.ApplicationExitInfo;

import java.util.Comparator;

public final /* synthetic */ class NativeTombstoneManager$$ExternalSyntheticLambda3
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long timestamp =
                ((ApplicationExitInfo) obj2).getTimestamp()
                        - ((ApplicationExitInfo) obj).getTimestamp();
        if (timestamp < 0) {
            return -1;
        }
        return timestamp == 0 ? 0 : 1;
    }
}
