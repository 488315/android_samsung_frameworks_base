package com.android.server.enterprise.auditlog;

/* loaded from: classes2.dex */
public interface IObserver {
    void notifyDumpFinished(boolean z, boolean z2);

    void notifyReadyToDump(boolean z);
}
