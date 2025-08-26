package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip.PipTransitionState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipTransitionStateFactory implements Provider {
    public static PipTransitionState providePipTransitionState() {
        return new PipTransitionState();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTransitionState();
    }
}
