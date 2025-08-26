package com.android.wm.shell.dagger.pip;

import android.window.DesktopModeFlags;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.desktopmode.DesktopPipTransitionController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class Pip2Module_ProvideDesktopPipTransitionControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopTasksControllerOptionalProvider;
    public final Provider desktopUserRepositoriesOptionalProvider;
    public final Provider pipDesktopStateProvider;

    public Pip2Module_ProvideDesktopPipTransitionControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.desktopTasksControllerOptionalProvider = provider2;
        this.desktopUserRepositoriesOptionalProvider = provider3;
        this.pipDesktopStateProvider = provider4;
        this.desktopStateProvider = provider5;
    }

    public static Optional provideDesktopPipTransitionController(Optional optional, Optional optional2, PipDesktopState pipDesktopState, DesktopState desktopState) {
        Optional optionalOf = (((DesktopStateImpl) desktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue()) ? Optional.of(new DesktopPipTransitionController((DesktopTasksController) optional.get(), (DesktopUserRepositories) optional2.get(), pipDesktopState)) : Optional.empty();
        optionalOf.getClass();
        return optionalOf;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopPipTransitionController((Optional) this.desktopTasksControllerOptionalProvider.get(), (Optional) this.desktopUserRepositoriesOptionalProvider.get(), (PipDesktopState) this.pipDesktopStateProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
