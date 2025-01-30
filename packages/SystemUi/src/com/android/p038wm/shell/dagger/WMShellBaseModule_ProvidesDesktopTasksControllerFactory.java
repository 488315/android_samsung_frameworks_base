package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.desktopmode.DesktopModeStatus;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvidesDesktopTasksControllerFactory implements Provider {
    public final Provider desktopTasksControllerProvider;

    public WMShellBaseModule_ProvidesDesktopTasksControllerFactory(Provider provider) {
        this.desktopTasksControllerProvider = provider;
    }

    public static Optional providesDesktopTasksController(Optional optional) {
        Optional map = DesktopModeStatus.IS_PROTO2_ENABLED ? optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(0)) : Optional.empty();
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesDesktopTasksController((Optional) this.desktopTasksControllerProvider.get());
    }
}
