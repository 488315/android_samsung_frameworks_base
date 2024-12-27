package com.android.systemui.bixby2.util;

import android.app.KeyguardManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ActivityLauncher_Factory implements Provider {
    private final javax.inject.Provider desktopManagerProvider;
    private final javax.inject.Provider displayLifecycleProvider;
    private final javax.inject.Provider keyguardManagerProvider;

    public ActivityLauncher_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.desktopManagerProvider = provider;
        this.displayLifecycleProvider = provider2;
        this.keyguardManagerProvider = provider3;
    }

    public static ActivityLauncher_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new ActivityLauncher_Factory(provider, provider2, provider3);
    }

    public static ActivityLauncher newInstance(DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        return new ActivityLauncher(desktopManager, displayLifecycle, keyguardManager);
    }

    @Override // javax.inject.Provider
    public ActivityLauncher get() {
        return newInstance((DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get());
    }
}
