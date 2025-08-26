package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ResetDeviceUtils;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideResetDeviceUtilsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;

    public SamsungServicesModule_ProvideResetDeviceUtilsFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.lockPatternUtilsProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
    }

    public static ResetDeviceUtils provideResetDeviceUtils(Context context, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new ResetDeviceUtils(context, lockPatternUtils, keyguardUpdateMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ResetDeviceUtils((Context) this.contextProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}
