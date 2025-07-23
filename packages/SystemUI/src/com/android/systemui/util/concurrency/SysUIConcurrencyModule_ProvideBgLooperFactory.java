package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBgLooperFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideBgLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBgLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBgLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBgLooper() {
        Looper provideBgLooper = SysUIConcurrencyModule.INSTANCE.provideBgLooper();
        provideBgLooper.getClass();
        return provideBgLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBgLooper();
    }
}
