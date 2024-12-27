package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import com.android.settingslib.devicestate.AndroidSecureSettings;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
