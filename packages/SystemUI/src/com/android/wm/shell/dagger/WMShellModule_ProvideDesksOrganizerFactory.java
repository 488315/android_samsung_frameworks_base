package com.android.wm.shell.dagger;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesksOrganizerFactory implements Provider {
    public final Provider launchAdjacentControllerProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;

    public WMShellModule_ProvideDesksOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.shellInitProvider = provider;
        this.shellCommandHandlerProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.launchAdjacentControllerProvider = provider4;
        this.rootTaskDisplayAreaOrganizerProvider = provider5;
    }

    public static RootTaskDesksOrganizer provideDesksOrganizer(ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellTaskOrganizer shellTaskOrganizer, LaunchAdjacentController launchAdjacentController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        return new RootTaskDesksOrganizer(shellInit, shellCommandHandler, shellTaskOrganizer, launchAdjacentController, rootTaskDisplayAreaOrganizer);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RootTaskDesksOrganizer((ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (LaunchAdjacentController) this.launchAdjacentControllerProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get());
    }
}
