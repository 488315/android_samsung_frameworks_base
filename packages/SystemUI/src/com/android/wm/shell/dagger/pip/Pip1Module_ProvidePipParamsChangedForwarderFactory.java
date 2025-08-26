package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.pip.PipParamsChangedForwarder;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipParamsChangedForwarderFactory implements Provider {
    public static PipParamsChangedForwarder providePipParamsChangedForwarder() {
        return new PipParamsChangedForwarder();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipParamsChangedForwarder();
    }
}
