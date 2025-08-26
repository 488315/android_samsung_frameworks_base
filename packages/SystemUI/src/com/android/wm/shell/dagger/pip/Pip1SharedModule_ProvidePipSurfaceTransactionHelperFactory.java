package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip1SharedModule_ProvidePipSurfaceTransactionHelperFactory implements Provider {
    public final Provider contextProvider;

    public Pip1SharedModule_ProvidePipSurfaceTransactionHelperFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PipSurfaceTransactionHelper providePipSurfaceTransactionHelper(Context context) {
        return new PipSurfaceTransactionHelper(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipSurfaceTransactionHelper((Context) this.contextProvider.get());
    }
}
