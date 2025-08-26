package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip1SharedModule_ProvidePipAnimationControllerFactory implements Provider {
    public final Provider pipSurfaceTransactionHelperProvider;

    public Pip1SharedModule_ProvidePipAnimationControllerFactory(Provider provider) {
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
