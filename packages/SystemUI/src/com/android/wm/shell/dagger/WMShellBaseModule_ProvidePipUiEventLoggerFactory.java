package com.android.wm.shell.dagger;

import android.content.pm.PackageManager;
import com.android.internal.logging.UiEventLogger;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePipUiEventLoggerFactory implements Provider {
    public final Provider packageManagerProvider;
    public final Provider uiEventLoggerProvider;

    public WMShellBaseModule_ProvidePipUiEventLoggerFactory(Provider provider, Provider provider2) {
        this.uiEventLoggerProvider = provider;
        this.packageManagerProvider = provider2;
    }

    public static PipUiEventLogger providePipUiEventLogger(UiEventLogger uiEventLogger, PackageManager packageManager) {
        return new PipUiEventLogger(uiEventLogger, packageManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipUiEventLogger((UiEventLogger) this.uiEventLoggerProvider.get(), (PackageManager) this.packageManagerProvider.get());
    }
}
