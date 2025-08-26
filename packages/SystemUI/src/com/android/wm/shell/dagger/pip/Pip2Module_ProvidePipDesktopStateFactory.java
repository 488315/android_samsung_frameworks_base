package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipDesktopStateFactory implements Provider {
    public final Provider desktopUserRepositoriesOptionalProvider;
    public final Provider dragToDesktopTransitionHandlerOptionalProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;

    public Pip2Module_ProvidePipDesktopStateFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.pipDisplayLayoutStateProvider = provider;
        this.desktopUserRepositoriesOptionalProvider = provider2;
        this.dragToDesktopTransitionHandlerOptionalProvider = provider3;
        this.rootTaskDisplayAreaOrganizerProvider = provider4;
    }

    public static PipDesktopState providePipDesktopState(PipDisplayLayoutState pipDisplayLayoutState, Optional optional, Optional optional2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        return new PipDesktopState(pipDisplayLayoutState, optional, optional2, rootTaskDisplayAreaOrganizer);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipDesktopState((PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (Optional) this.desktopUserRepositoriesOptionalProvider.get(), (Optional) this.dragToDesktopTransitionHandlerOptionalProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get());
    }
}
