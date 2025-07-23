package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.desktopmode.DesktopModeLoggerTransitionObserver;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideFreeformTaskListenerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopModeLoggerTransitionObserverProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayImeControllerProvider;
    public final Provider launchAdjacentControllerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider taskChangeListenerProvider;
    public final Provider windowDecorViewModelProvider;

    public WMShellModule_ProvideFreeformTaskListenerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.displayImeControllerProvider = provider2;
        this.shellInitProvider = provider3;
        this.shellTaskOrganizerProvider = provider4;
        this.desktopUserRepositoriesProvider = provider5;
        this.desktopTasksControllerProvider = provider6;
        this.desktopModeLoggerTransitionObserverProvider = provider7;
        this.launchAdjacentControllerProvider = provider8;
        this.windowDecorViewModelProvider = provider9;
        this.taskChangeListenerProvider = provider10;
        this.desktopStateProvider = provider11;
    }

    public static FreeformTaskListener provideFreeformTaskListener(Context context, DisplayImeController displayImeController, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Optional optional, Optional optional2, DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver, LaunchAdjacentController launchAdjacentController, WindowDecorViewModel windowDecorViewModel, Optional optional3, DesktopState desktopState) {
        return new FreeformTaskListener(context, displayImeController, shellInit, shellTaskOrganizer, optional, optional2, desktopModeLoggerTransitionObserver, launchAdjacentController, windowDecorViewModel, optional3, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FreeformTaskListener((Context) this.contextProvider.get(), (DisplayImeController) this.displayImeControllerProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (Optional) this.desktopUserRepositoriesProvider.get(), (Optional) this.desktopTasksControllerProvider.get(), (DesktopModeLoggerTransitionObserver) this.desktopModeLoggerTransitionObserverProvider.get(), (LaunchAdjacentController) this.launchAdjacentControllerProvider.get(), (WindowDecorViewModel) this.windowDecorViewModelProvider.get(), (Optional) this.taskChangeListenerProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
