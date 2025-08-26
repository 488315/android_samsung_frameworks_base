package com.android.wm.shell.dagger;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.HomeIntentProvider;
import com.android.wm.shell.crashhandling.ShellCrashHandler;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideShellCrashHandlerFactory implements Provider {
    public final Provider desktopStateProvider;
    public final Provider homeIntentProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;

    public WMShellModule_ProvideShellCrashHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.shellTaskOrganizerProvider = provider;
        this.homeIntentProvider = provider2;
        this.desktopStateProvider = provider3;
        this.shellInitProvider = provider4;
    }

    public static ShellCrashHandler provideShellCrashHandler(ShellTaskOrganizer shellTaskOrganizer, HomeIntentProvider homeIntentProvider, DesktopState desktopState, ShellInit shellInit) {
        return new ShellCrashHandler(shellTaskOrganizer, homeIntentProvider, desktopState, shellInit);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellCrashHandler((ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (HomeIntentProvider) this.homeIntentProvider.get(), (DesktopState) this.desktopStateProvider.get(), (ShellInit) this.shellInitProvider.get());
    }
}
