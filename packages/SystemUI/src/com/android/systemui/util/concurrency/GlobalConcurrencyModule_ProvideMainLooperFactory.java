package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainLooperFactory implements Provider {

    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideMainLooperFactory INSTANCE = new GlobalConcurrencyModule_ProvideMainLooperFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideMainLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideMainLooper() {
        Looper looperProvideMainLooper = GlobalConcurrencyModule.provideMainLooper();
        looperProvideMainLooper.getClass();
        return looperProvideMainLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideMainLooper();
    }
}
