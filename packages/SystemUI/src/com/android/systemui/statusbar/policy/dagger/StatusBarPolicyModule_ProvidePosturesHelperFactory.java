package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.settingslib.devicestate.PosturesHelper;
import dagger.internal.Provider;

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
