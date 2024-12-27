package com.android.systemui.popup.util;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import dagger.internal.Provider;

public final class KeyguardUpdateMonitorWrapper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider keyguardUpdateMonitorProvider;

    public KeyguardUpdateMonitorWrapper_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
    }

    public static KeyguardUpdateMonitorWrapper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new KeyguardUpdateMonitorWrapper_Factory(provider, provider2);
    }

    public static KeyguardUpdateMonitorWrapper newInstance(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new KeyguardUpdateMonitorWrapper(context, keyguardUpdateMonitor);
    }

    @Override // javax.inject.Provider
    public KeyguardUpdateMonitorWrapper get() {
        return newInstance((Context) this.contextProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}
