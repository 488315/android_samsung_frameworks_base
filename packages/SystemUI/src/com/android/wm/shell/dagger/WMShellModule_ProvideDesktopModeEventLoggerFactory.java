package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
