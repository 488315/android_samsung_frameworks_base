package com.android.systemui.statusbar.notification.collection.coordinator;

final class TrackedSmartspaceTarget {
    private long alertExceptionExpires;
    private Runnable cancelTimeoutRunnable;
    private final String key;
    private boolean shouldFilter;

    public TrackedSmartspaceTarget(String str) {
        this.key = str;
    }

    public final long getAlertExceptionExpires() {
        return this.alertExceptionExpires;
    }

    public final Runnable getCancelTimeoutRunnable() {
        return this.cancelTimeoutRunnable;
    }

    public final String getKey() {
        return this.key;
    }

    public final boolean getShouldFilter() {
        return this.shouldFilter;
    }

    public final void setAlertExceptionExpires(long j) {
        this.alertExceptionExpires = j;
    }

    public final void setCancelTimeoutRunnable(Runnable runnable) {
        this.cancelTimeoutRunnable = runnable;
    }

    public final void setShouldFilter(boolean z) {
        this.shouldFilter = z;
    }
}
