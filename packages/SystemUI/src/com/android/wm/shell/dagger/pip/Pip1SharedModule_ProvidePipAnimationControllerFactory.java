package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
