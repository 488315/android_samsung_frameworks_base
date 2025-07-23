package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainLooperFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideMainLooperFactory INSTANCE = new GlobalConcurrencyModule_ProvideMainLooperFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideMainLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideMainLooper() {
        Looper provideMainLooper = GlobalConcurrencyModule.provideMainLooper();
        provideMainLooper.getClass();
        return provideMainLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideMainLooper();
    }
}
