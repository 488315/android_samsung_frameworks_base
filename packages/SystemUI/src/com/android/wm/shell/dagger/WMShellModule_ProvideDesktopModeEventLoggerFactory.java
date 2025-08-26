package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopModeEventLoggerFactory implements Provider {
    public static DesktopModeEventLogger provideDesktopModeEventLogger() {
        return new DesktopModeEventLogger();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopModeEventLogger();
    }
}
