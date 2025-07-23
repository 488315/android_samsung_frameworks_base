package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideLongRunningLooperFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideLongRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideLongRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideLongRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideLongRunningLooper() {
        Looper provideLongRunningLooper = SysUIConcurrencyModule.INSTANCE.provideLongRunningLooper();
        provideLongRunningLooper.getClass();
        return provideLongRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideLongRunningLooper();
    }
}
