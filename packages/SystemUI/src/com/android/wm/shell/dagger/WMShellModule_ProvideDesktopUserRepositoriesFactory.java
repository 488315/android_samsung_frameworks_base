package com.android.wm.shell.dagger;

import android.os.UserManager;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopUserRepositoriesFactory implements Provider {
    public final Provider desktopConfigProvider;
    public final Provider desktopPersistentRepositoryProvider;
    public final Provider desktopRepositoryInitializerProvider;
    public final Provider desktopStateProvider;
    public final Provider mainScopeProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider userManagerProvider;

    public WMShellModule_ProvideDesktopUserRepositoriesFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.shellInitProvider = provider;
        this.shellControllerProvider = provider2;
        this.desktopPersistentRepositoryProvider = provider3;
        this.desktopRepositoryInitializerProvider = provider4;
        this.mainScopeProvider = provider5;
        this.userManagerProvider = provider6;
        this.desktopStateProvider = provider7;
        this.desktopConfigProvider = provider8;
    }

    public static DesktopUserRepositories provideDesktopUserRepositories(ShellInit shellInit, ShellController shellController, DesktopPersistentRepository desktopPersistentRepository, DesktopRepositoryInitializer desktopRepositoryInitializer, CoroutineScope coroutineScope, UserManager userManager, DesktopState desktopState, DesktopConfig desktopConfig) {
        return new DesktopUserRepositories(shellInit, shellController, desktopPersistentRepository, desktopRepositoryInitializer, coroutineScope, userManager, desktopState, desktopConfig);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopUserRepositories((ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (DesktopPersistentRepository) this.desktopPersistentRepositoryProvider.get(), (DesktopRepositoryInitializer) this.desktopRepositoryInitializerProvider.get(), (CoroutineScope) this.mainScopeProvider.get(), (UserManager) this.userManagerProvider.get(), (DesktopState) this.desktopStateProvider.get(), (DesktopConfig) this.desktopConfigProvider.get());
    }
}
