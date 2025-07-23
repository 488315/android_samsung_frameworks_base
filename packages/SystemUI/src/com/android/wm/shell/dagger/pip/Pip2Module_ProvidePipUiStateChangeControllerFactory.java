package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.pip2.phone.PipUiStateChangeController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipUiStateChangeControllerFactory implements Provider {
    public final Provider pipTransitionStateProvider;

    public Pip2Module_ProvidePipUiStateChangeControllerFactory(Provider provider) {
        this.pipTransitionStateProvider = provider;
    }

    public static PipUiStateChangeController providePipUiStateChangeController(PipTransitionState pipTransitionState) {
        return new PipUiStateChangeController(pipTransitionState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipUiStateChangeController((PipTransitionState) this.pipTransitionStateProvider.get());
    }
}
