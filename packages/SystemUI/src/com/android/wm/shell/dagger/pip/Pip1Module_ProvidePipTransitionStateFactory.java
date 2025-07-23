package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip.PipTransitionState;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
