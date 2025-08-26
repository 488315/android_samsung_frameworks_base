package com.android.wm.shell.dagger;

import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorSurface;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvidesMultiDisplayDragMoveIndicatorSurfaceFactoryFactory implements Provider {
    public static MultiDisplayDragMoveIndicatorSurface.Factory providesMultiDisplayDragMoveIndicatorSurfaceFactory() {
        return new MultiDisplayDragMoveIndicatorSurface.Factory();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new MultiDisplayDragMoveIndicatorSurface.Factory();
    }
}
