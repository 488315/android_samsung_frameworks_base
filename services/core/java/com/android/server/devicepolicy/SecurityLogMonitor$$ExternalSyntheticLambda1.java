package com.android.server.devicepolicy;

import android.app.admin.SecurityLog;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SecurityLogMonitor$$ExternalSyntheticLambda1
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.signum(
                ((SecurityLog.SecurityEvent) obj).getTimeNanos()
                        - ((SecurityLog.SecurityEvent) obj2).getTimeNanos());
    }
}
