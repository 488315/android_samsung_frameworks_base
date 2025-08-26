package com.android.wm.shell.dagger;

import android.content.pm.PackageManager;
import com.android.internal.logging.UiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopUiEventLoggerFactory implements Provider {
    public final Provider packageManagerProvider;
    public final Provider uiEventLoggerProvider;

    public WMShellModule_ProvideDesktopUiEventLoggerFactory(Provider provider, Provider provider2) {
        this.uiEventLoggerProvider = provider;
        this.packageManagerProvider = provider2;
    }

    public static DesktopModeUiEventLogger provideDesktopUiEventLogger(UiEventLogger uiEventLogger, PackageManager packageManager) {
        return new DesktopModeUiEventLogger(uiEventLogger, packageManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopModeUiEventLogger((UiEventLogger) this.uiEventLoggerProvider.get(), (PackageManager) this.packageManagerProvider.get());
    }
}
