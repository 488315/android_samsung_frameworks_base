package com.android.wm.shell.dagger;

import android.window.DesktopModeFlags;
import com.android.wm.shell.desktopmode.DesktopTaskChangeListener;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopTaskChangeListenerFactory implements Provider {
    public final Provider desktopStateProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider desktopUserRepositoriesProvider;

    public WMShellModule_ProvideDesktopTaskChangeListenerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.desktopTasksControllerProvider = provider;
        this.desktopUserRepositoriesProvider = provider2;
        this.desktopStateProvider = provider3;
    }

    public static Optional provideDesktopTaskChangeListener(Optional optional, DesktopUserRepositories desktopUserRepositories, DesktopState desktopState) {
        Optional optionalOf = (DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue() && ((DesktopStateImpl) desktopState).canEnterDesktopMode) ? Optional.of(new DesktopTaskChangeListener(optional, desktopUserRepositories)) : Optional.empty();
        optionalOf.getClass();
        return optionalOf;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopTaskChangeListener((Optional) this.desktopTasksControllerProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
