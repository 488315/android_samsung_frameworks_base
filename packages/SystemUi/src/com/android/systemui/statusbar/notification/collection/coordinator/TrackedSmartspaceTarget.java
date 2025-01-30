package com.android.systemui.statusbar.notification.collection.coordinator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TrackedSmartspaceTarget {
    public long alertExceptionExpires;
    public Runnable cancelTimeoutRunnable;
    public final String key;
    public boolean shouldFilter;

    public TrackedSmartspaceTarget(String str) {
        this.key = str;
    }
}
