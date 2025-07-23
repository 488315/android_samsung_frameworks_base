package android.app;

import android.app.IUidObserver;

/* loaded from: classes.dex */
public class UidObserver extends IUidObserver.Stub {
    @Override // android.app.IUidObserver
    public void onUidActive(int i) {
    }

    @Override // android.app.IUidObserver
    public void onUidCachedChanged(int i, boolean z) {
    }

    public void onUidGone(int i, boolean z) {
    }

    @Override // android.app.IUidObserver
    public void onUidIdle(int i, boolean z) {
    }

    @Override // android.app.IUidObserver
    public void onUidProcAdjChanged(int i, int i2) {
    }

    public void onUidStateChanged(int i, int i2, long j, int i3) {
    }
}
