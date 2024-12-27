package com.android.server.devicepolicy;

import android.app.admin.SecurityLog;

import java.util.Comparator;

public final /* synthetic */ class SecurityLogMonitor$$ExternalSyntheticLambda1
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.signum(
                ((SecurityLog.SecurityEvent) obj).getTimeNanos()
                        - ((SecurityLog.SecurityEvent) obj2).getTimeNanos());
    }
}
