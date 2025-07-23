package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
