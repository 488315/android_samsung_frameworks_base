package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import com.android.settingslib.devicestate.AndroidSecureSettings;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory implements Provider {
    public final Provider contextProvider;

    public StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static DeviceStateRotationLockSettingsManager provideAutoRotateSettingsManager(Context context) {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager;
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            if (DeviceStateRotationLockSettingsManager.sSingleton == null) {
                Context applicationContext = context.getApplicationContext();
                DeviceStateRotationLockSettingsManager.sSingleton = new DeviceStateRotationLockSettingsManager(applicationContext, new AndroidSecureSettings(applicationContext.getContentResolver()));
            }
            deviceStateRotationLockSettingsManager = DeviceStateRotationLockSettingsManager.sSingleton;
        }
        Preconditions.checkNotNullFromProvides(deviceStateRotationLockSettingsManager);
        return deviceStateRotationLockSettingsManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideAutoRotateSettingsManager((Context) this.contextProvider.get());
    }
}
