package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipSurfaceTransactionHelper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipAnimationControllerFactory implements Provider {
    public final Provider pipSurfaceTransactionHelperProvider;

    public WMShellModule_ProvidePipAnimationControllerFactory(Provider provider) {
        this.pipSurfaceTransactionHelperProvider = provider;
    }

    public static PipAnimationController providePipAnimationController(PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        return new PipAnimationController(pipSurfaceTransactionHelper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipAnimationController((PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get());
    }
}
