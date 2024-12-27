package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBroadcastRunningLooper() {
        Looper provideBroadcastRunningLooper = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningLooper();
        Preconditions.checkNotNullFromProvides(provideBroadcastRunningLooper);
        return provideBroadcastRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBroadcastRunningLooper();
    }
}
