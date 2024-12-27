package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import com.android.settingslib.devicestate.AndroidSecureSettings;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory implements Provider {
    public final javax.inject.Provider contextProvider;

    public StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static DeviceStateRotationLockSettingsManager provideAutoRotateSettingsManager(Context context) {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager;
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            try {
                if (DeviceStateRotationLockSettingsManager.sSingleton == null) {
                    Context applicationContext = context.getApplicationContext();
                    DeviceStateRotationLockSettingsManager.sSingleton = new DeviceStateRotationLockSettingsManager(applicationContext, new AndroidSecureSettings(applicationContext.getContentResolver()));
                }
                deviceStateRotationLockSettingsManager = DeviceStateRotationLockSettingsManager.sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        Preconditions.checkNotNullFromProvides(deviceStateRotationLockSettingsManager);
        return deviceStateRotationLockSettingsManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideAutoRotateSettingsManager((Context) this.contextProvider.get());
    }
}
