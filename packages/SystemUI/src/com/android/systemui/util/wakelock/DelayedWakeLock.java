package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes3.dex */
public class DelayedWakeLock implements WakeLock {
    private static final long RELEASE_DELAY_MS = 100;
    private static final String TO_STRING_PREFIX = "[DelayedWakeLock] ";
    private final Handler mHandler;
    private final WakeLock mInner;

    public interface Factory {
        DelayedWakeLock create(String str);
    }

    public DelayedWakeLock(Handler handler, Context context, WakeLockLogger wakeLockLogger, String str) {
        this.mInner = WakeLock.createPartial(context, wakeLockLogger, str);
        this.mHandler = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$0(String str) {
        this.mInner.release(str);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void acquire(String str) {
        this.mInner.acquire(str);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void release(final String str) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.wakelock.DelayedWakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$release$0(str);
            }
        }, RELEASE_DELAY_MS);
    }

    public String toString() {
        return TO_STRING_PREFIX + this.mInner;
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public Runnable wrap(Runnable runnable) {
        return WakeLock.wrapImpl(this, runnable);
    }
}
