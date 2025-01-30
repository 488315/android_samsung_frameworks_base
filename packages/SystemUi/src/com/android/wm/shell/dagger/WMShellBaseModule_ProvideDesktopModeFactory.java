package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeStatus;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideDesktopModeFactory implements Provider {
    public final Provider desktopModeControllerProvider;
    public final Provider desktopTasksControllerProvider;

    public WMShellBaseModule_ProvideDesktopModeFactory(Provider provider, Provider provider2) {
        this.desktopModeControllerProvider = provider;
        this.desktopTasksControllerProvider = provider2;
    }

    public static Optional provideDesktopMode(Optional optional, Optional optional2) {
        Optional map = DesktopModeStatus.IS_PROTO2_ENABLED ? optional2.map(new WMShellBaseModule$$ExternalSyntheticLambda0(6)) : optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(7));
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopMode((Optional) this.desktopModeControllerProvider.get(), (Optional) this.desktopTasksControllerProvider.get());
    }
}
