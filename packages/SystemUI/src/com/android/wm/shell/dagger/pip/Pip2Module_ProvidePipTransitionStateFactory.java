package com.android.wm.shell.dagger.pip;

import android.os.Handler;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipTransitionStateFactory implements Provider {
    public final Provider handlerProvider;
    public final Provider pipDesktopStateProvider;

    public Pip2Module_ProvidePipTransitionStateFactory(Provider provider, Provider provider2) {
        this.handlerProvider = provider;
        this.pipDesktopStateProvider = provider2;
    }

    public static PipTransitionState providePipTransitionState(Handler handler, PipDesktopState pipDesktopState) {
        return new PipTransitionState(handler, pipDesktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTransitionState((Handler) this.handlerProvider.get(), (PipDesktopState) this.pipDesktopStateProvider.get());
    }
}
