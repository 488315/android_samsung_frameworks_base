package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.pip2.phone.PipUiStateChangeController;
import dagger.internal.Provider;

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
