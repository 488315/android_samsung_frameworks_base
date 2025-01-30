package com.android.p038wm.shell.dagger;

import android.content.pm.PackageManager;
import com.android.internal.logging.UiEventLogger;
import com.android.p038wm.shell.pip.PipUiEventLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
