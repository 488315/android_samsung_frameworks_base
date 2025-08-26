package com.android.wm.shell.dagger;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler;
import com.android.wm.shell.desktopmode.DesktopDisplayModeController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;
import java.util.Optional;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopDisplayEventHandlerFactory implements Provider {
    public final Provider desksOrganizerProvider;
    public final Provider desksTransitionObserverProvider;
    public final Provider desktopDisplayModeControllerProvider;
    public final Provider desktopRepositoryInitializerProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayControllerProvider;
    public final Provider mainScopeProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;

    public WMShellModule_ProvideDesktopDisplayEventHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.shellInitProvider = provider;
        this.mainScopeProvider = provider2;
        this.shellControllerProvider = provider3;
        this.displayControllerProvider = provider4;
        this.rootTaskDisplayAreaOrganizerProvider = provider5;
        this.desksOrganizerProvider = provider6;
        this.desktopUserRepositoriesProvider = provider7;
        this.desktopTasksControllerProvider = provider8;
        this.desktopDisplayModeControllerProvider = provider9;
        this.desktopRepositoryInitializerProvider = provider10;
        this.desksTransitionObserverProvider = provider11;
        this.desktopStateProvider = provider12;
    }

    public static Optional provideDesktopDisplayEventHandler(ShellInit shellInit, CoroutineScope coroutineScope, ShellController shellController, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesksOrganizer desksOrganizer, Optional optional, Optional optional2, Optional optional3, DesktopRepositoryInitializer desktopRepositoryInitializer, Optional optional4, DesktopState desktopState) {
        Optional optionalEmpty = !((DesktopStateImpl) desktopState).canEnterDesktopMode ? Optional.empty() : Optional.of(new DesktopDisplayEventHandler(shellInit, coroutineScope, shellController, displayController, rootTaskDisplayAreaOrganizer, desksOrganizer, desktopRepositoryInitializer, (DesktopUserRepositories) optional.get(), (DesktopTasksController) optional2.get(), (DesktopDisplayModeController) optional3.get(), (DesksTransitionObserver) optional4.get(), desktopState));
        optionalEmpty.getClass();
        return optionalEmpty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopDisplayEventHandler((ShellInit) this.shellInitProvider.get(), (CoroutineScope) this.mainScopeProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (DesksOrganizer) this.desksOrganizerProvider.get(), (Optional) this.desktopUserRepositoriesProvider.get(), (Optional) this.desktopTasksControllerProvider.get(), (Optional) this.desktopDisplayModeControllerProvider.get(), (DesktopRepositoryInitializer) this.desktopRepositoryInitializerProvider.get(), (Optional) this.desksTransitionObserverProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
