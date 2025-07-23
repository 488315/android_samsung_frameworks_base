package com.android.systemui.popup.util;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardUpdateMonitorWrapper_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider keyguardUpdateMonitorProvider;

    public KeyguardUpdateMonitorWrapper_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
    }

    public static KeyguardUpdateMonitorWrapper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new KeyguardUpdateMonitorWrapper_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static KeyguardUpdateMonitorWrapper newInstance(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new KeyguardUpdateMonitorWrapper(context, keyguardUpdateMonitor);
    }

    public static KeyguardUpdateMonitorWrapper_Factory create(Provider provider, Provider provider2) {
        return new KeyguardUpdateMonitorWrapper_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public KeyguardUpdateMonitorWrapper get() {
        return newInstance((Context) this.contextProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}
