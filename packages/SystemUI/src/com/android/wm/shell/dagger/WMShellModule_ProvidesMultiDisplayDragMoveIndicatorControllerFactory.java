package com.android.wm.shell.dagger;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorSurface;
import com.android.wm.shell.common.ShellExecutor;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvidesMultiDisplayDragMoveIndicatorControllerFactory implements Provider {
    public final Provider desktopExecutorProvider;
    public final Provider displayControllerProvider;
    public final Provider multiDisplayDragMoveIndicatorSurfaceFactoryProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;

    public WMShellModule_ProvidesMultiDisplayDragMoveIndicatorControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.displayControllerProvider = provider;
        this.rootTaskDisplayAreaOrganizerProvider = provider2;
        this.multiDisplayDragMoveIndicatorSurfaceFactoryProvider = provider3;
        this.desktopExecutorProvider = provider4;
    }

    public static MultiDisplayDragMoveIndicatorController providesMultiDisplayDragMoveIndicatorController(DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, MultiDisplayDragMoveIndicatorSurface.Factory factory, ShellExecutor shellExecutor) {
        return new MultiDisplayDragMoveIndicatorController(displayController, rootTaskDisplayAreaOrganizer, factory, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new MultiDisplayDragMoveIndicatorController((DisplayController) this.displayControllerProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (MultiDisplayDragMoveIndicatorSurface.Factory) this.multiDisplayDragMoveIndicatorSurfaceFactoryProvider.get(), (ShellExecutor) this.desktopExecutorProvider.get());
    }
}
