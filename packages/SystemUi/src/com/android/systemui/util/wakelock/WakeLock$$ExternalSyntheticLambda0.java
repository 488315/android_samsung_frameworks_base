package com.android.systemui.util.wakelock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WakeLock$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Runnable f$0;
    public final /* synthetic */ WakeLock f$1;

    public /* synthetic */ WakeLock$$ExternalSyntheticLambda0(WakeLock wakeLock, Runnable runnable) {
        this.f$0 = runnable;
        this.f$1 = wakeLock;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Runnable runnable = this.f$0;
        WakeLock wakeLock = this.f$1;
        int i = WakeLock.DEFAULT_LEVELS_AND_FLAGS;
        try {
            runnable.run();
        } finally {
            wakeLock.release("wrap");
        }
    }
}
