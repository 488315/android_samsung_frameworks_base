package com.android.wm.shell.dagger;

import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePipSnapAlgorithmFactory implements Provider {
    public static PipSnapAlgorithm providePipSnapAlgorithm() {
        return new PipSnapAlgorithm();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipSnapAlgorithm();
    }
}
