package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DragToDisplayTransitionHandler;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDragToDisplayTransitionHandlerFactory implements Provider {
    public static DragToDisplayTransitionHandler provideDragToDisplayTransitionHandler() {
        return new DragToDisplayTransitionHandler();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DragToDisplayTransitionHandler();
    }
}
