package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.settingslib.devicestate.PosturesHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarPolicyModule_ProvidePosturesHelperFactory implements Provider {
    public final Provider contextProvider;
    public final Provider deviceStateManagerProvider;

    public StatusBarPolicyModule_ProvidePosturesHelperFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.deviceStateManagerProvider = provider2;
    }

    public static PosturesHelper providePosturesHelper(Context context, DeviceStateManager deviceStateManager) {
        return new PosturesHelper(context, deviceStateManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PosturesHelper((Context) this.contextProvider.get(), (DeviceStateManager) this.deviceStateManagerProvider.get());
    }
}
